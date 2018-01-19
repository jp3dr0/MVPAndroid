package com.example.joaop.mvpindiano.login.mvp.view;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import com.example.joaop.mvpindiano.R;

import com.example.joaop.mvpindiano.network.ApiService;
import com.example.joaop.mvpindiano.network.model.User;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import rx.Observable;
import rx.Observer;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Func1;
import rx.schedulers.Schedulers;

public class ApiFragment extends Fragment {

    private List<String> usernames = new ArrayList<>();
    private List<User> usuarios = new ArrayList<>();
    LoginActivity activity;
    Button getUsersBtn;
    ApiService apiService;
    UserAdapter userAdapter;
    RecyclerView rv;

    private static final int REACTIVE = 0;
    private static final int NORMAL_RETROFIT_ASYNCHRONOUS = 1;

    public ApiFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_api, container, false);

        activity = (LoginActivity) getActivity();
        apiService = activity.getApiService();
        getUsersBtn = view.findViewById(R.id.getUsersBtn);
        rv = view.findViewById(R.id.rv);

        activity.toolbar.setDisplayShowHomeEnabled(true);
        activity.toolbar.setDisplayHomeAsUpEnabled(true);

        // configurarRecyclerView(NORMAL_RETROFIT_ASYNCHRONOUS);
        configurarRecyclerView(REACTIVE);
        // getUsernamesReactive();

        getUsersBtn.setText("Get Usernames");

        getUsersBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //getUsers();
                //atualizarRecyclerView();
                getUsernamesReactive();
            }
        });

        return view;
    }

    public void atualizarRecyclerView() {
        userAdapter.notifyDataSetChanged();
    }

    public void configurarRecyclerView(int choice){
        //userAdapter = new UserAdapter(usuarios);
        switch(choice){
            case NORMAL_RETROFIT_ASYNCHRONOUS:
                Call<List<User>> call = apiService.getUsers();
                call.enqueue(new Callback<List<User>>() {
                    @Override
                    public void onResponse(Call<List<User>> call, Response<List<User>> response) {
                        // Configurando Adapter
                        usuarios = response.body();
                        userAdapter = new UserAdapter(usuarios, null);

                        // Configurando Recyclew View de fato
                        LinearLayoutManager llm = new LinearLayoutManager(getContext());
                        llm.setOrientation(LinearLayoutManager.VERTICAL);
                        //llm.setReverseLayout(true);
                        rv.setLayoutManager(llm);
                        rv.setHasFixedSize(true);
                        rv.setAdapter(userAdapter);
                    }

                    @Override
                    public void onFailure(Call<List<User>> call, Throwable t) {
                        userAdapter = new UserAdapter(usuarios, null);

                        LinearLayoutManager llm = new LinearLayoutManager(getContext());
                        llm.setOrientation(LinearLayoutManager.VERTICAL);
                        //llm.setReverseLayout(true);
                        rv.setLayoutManager(llm);
                        rv.setHasFixedSize(true);
                        rv.setAdapter(userAdapter);
                    }
                });
                break;

            case REACTIVE:
                apiService.getUsersObservable()
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(new Observer<List<User>>() {
                            @Override
                            public void onCompleted() {
                                // Configurando Adapter
                                userAdapter = new UserAdapter(usuarios,null);

                                // Configurando Recyclew View de fato
                                LinearLayoutManager llm = new LinearLayoutManager(getContext());
                                llm.setOrientation(LinearLayoutManager.VERTICAL);
                                //llm.setReverseLayout(true);
                                rv.setLayoutManager(llm);
                                rv.setHasFixedSize(true);
                                rv.setAdapter(userAdapter);
                            }

                            @Override
                            public void onError(Throwable e) {
                                e.printStackTrace();
                            }

                            @Override
                            public void onNext(List<User> users) {
                                usuarios = users;
                            }
                        });
                break;
        }

    }

    public void getUsernamesReactive(){
        Observable<List<User>> call = apiService.getUsersObservable();
        call
                .flatMapIterable(new Func1<List<User>, Iterable<User>>() {
                    @Override
                    public Iterable<User> call(List<User> users) {
                        return users;
                    }
                })
                .flatMap(new Func1<User, Observable<String>>() {
                    @Override
                    public Observable<String> call(User user) {
                        return Observable.just(user.getUsername());
                    }
                })
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<String>() {
                    @Override
                    public void onCompleted() {
                        // Configurando Adapter
                        userAdapter = new UserAdapter(null, usernames);

                        // Configurando Recyclew View de fato
                        LinearLayoutManager llm = new LinearLayoutManager(getContext());
                        llm.setOrientation(LinearLayoutManager.VERTICAL);
                        //llm.setReverseLayout(true);
                        rv.setLayoutManager(llm);
                        rv.setHasFixedSize(true);
                        rv.setAdapter(userAdapter);
                    }

                    @Override
                    public void onError(Throwable e) {
                        e.printStackTrace();
                    }

                    @Override
                    public void onNext(String s) {
                        usernames.add(s);
                    }
                });
    }

    public void getUsers(){
        Call<List<User>> call = apiService.getUsers();
        call.enqueue(new Callback<List<User>>() {
            @Override
            public void onResponse(Call<List<User>> call, Response<List<User>> response) {
                //Toast.makeText(getContext(), response, Toast.LENGTH_SHORT).show();

                List<User> usuarioss = response.body();

                //Toast.makeText(getContext(), usuarioss.size(), Toast.LENGTH_SHORT).show();

                for(User u : usuarioss){
                    Toast.makeText(getContext(), "Name: " + u.getName() + " Email: " + u.getEmail() + " Phone: " + u.getPhone(), Toast.LENGTH_SHORT).show();
                    usuarios.add(u);
                }
                //usuarios = response.body();
                // userAdapter.notifyDataSetChanged();
            }

            @Override
            public void onFailure(Call<List<User>> call, Throwable t) {
                getUsersOffline();
            }
        });
    }

    public void getUsersOffline(){
        usuarios.add(new User("pedrinho@gmail.com", "32085204", "pedrin"));
        usuarios.add(new User("fernando@gmail.com", "4636344", "ferno"));
        usuarios.add(new User("lucas@gmail.com", "3574547", "lucas"));
        usuarios.add(new User("laurinha@gmail.com", "63467", "laura"));

        atualizarRecyclerView();
    }
}
