package com.example.joaop.mvpindiano.login.mvp.view;

import android.net.Uri;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import android.widget.Toolbar;

import com.example.joaop.mvpindiano.R;
import com.example.joaop.mvpindiano.login.depedencyInjection.DaggerLoginViewComponent;
import com.example.joaop.mvpindiano.login.depedencyInjection.LoginViewComponent;
import com.example.joaop.mvpindiano.login.depedencyInjection.LoginViewModule;
import com.example.joaop.mvpindiano.login.mvp.LoginActivityMVP;
import com.example.joaop.mvpindiano.network.ApiService;
import com.example.joaop.mvpindiano.network.model.User;
import com.example.joaop.mvpindiano.root.App;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

public class LoginActivity extends AppCompatActivity implements LoginActivityMVP.View {

    private EditText firstName, lastName;

    private LoginFragment loginFragment;
    private ApiFragment apiFragment;
    private FragmentManager fragmentManager;
    private List<User> usuarios = new ArrayList<User>();

    public ActionBar toolbar;

    // DEPENDENCIAS DA VIEW
    @Inject
    LoginActivityMVP.Presenter presenter;

    @Inject
    ApiService apiService;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        toolbar = getSupportActionBar();

        LoginViewComponent component = DaggerLoginViewComponent.builder()
                .appComponent(((App)getApplication()).getComponent())
                .loginViewModule(new LoginViewModule(this))
                .build();

        component.injectLoginActivity(this);

        if (savedInstanceState == null) {
            iniciarLoginFragment();
        }

    }

    public void iniciarLoginFragment(){
        loginFragment = new LoginFragment();
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.add(loginFragment, "login");
        fragmentTransaction.addToBackStack("login");
        fragmentTransaction.replace(R.id.frag_container, loginFragment);
        fragmentTransaction.commit();
    }

    public ApiService getApiService() {
        return apiService;
    }

    public List<User> getUsuarios() {
        return usuarios;
    }

    public void setUsuarios(List<User> usuarios) {
        this.usuarios = usuarios;
    }

    public void setFirstName(EditText firstName) {
        this.firstName = firstName;
    }

    public void setLastName(EditText lastName) {
        this.lastName = lastName;
    }

    public void loginButtonClicked(){
        presenter.loginButtonClicked();
    }

    public void retrofitButtonClicked(){
        apiFragment = new ApiFragment();
        if(fragmentManager == null){
            fragmentManager = getSupportFragmentManager();
        }
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.frag_container, apiFragment, "api");
        fragmentTransaction.addToBackStack("api");
        fragmentTransaction.commit();
    }

    @Override
    protected void onResume() {
        super.onResume();
        // AVISA PARA O PRESENTER QUE ESSA É A VIEW ATUAL
        presenter.setView(this);
        presenter.getCurrentUser();
    }

    @Override
    public String getFirstName() {
        return firstName.getText().toString();
    }

    @Override
    public String getLastName() {
        return lastName.getText().toString();
    }

    @Override
    public void showUserNotAvaiable() {
        Toast.makeText(this, "Usuário não disponível.", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void showInputError() {
        Toast.makeText(this, "Digite os campos necessários.", Toast.LENGTH_SHORT).show();

    }

    @Override
    public void showUserSavedMessage() {
        Toast.makeText(this, "Usuário salvo.", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void setFirstName(String firstName) {
        this.firstName.setText(firstName);
    }

    @Override
    public void setLastName(String lastName) {
        this.lastName.setText(lastName);
    }

}
