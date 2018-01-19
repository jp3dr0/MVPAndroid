package com.example.joaop.mvpindiano.login.mvp.view;


import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.joaop.mvpindiano.R;
import com.example.joaop.mvpindiano.login.mvp.LoginActivityMVP;

public class LoginFragment extends Fragment {

    private EditText firstName, lastName;
    private Button login;
    private Button retrofit;

    LoginActivity activity;

    public LoginFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_login, container, false);

        activity = (LoginActivity) getActivity();

        firstName = (EditText) view.findViewById(R.id.loginActivity_lastName_editText);
        lastName = (EditText) view.findViewById(R.id.loginActivity_firstName_editText);
        login = (Button) view.findViewById(R.id.loginActivity_login_button);
        retrofit = (Button) view.findViewById(R.id.loginActivity_retrofit_button);

        activity.setFirstName(firstName);
        activity.setLastName(lastName);

        //firstName.setText("");
        //lastName.setText("");

        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // PRESENTER TRATA O CLICK NO BOTAO
                activity.loginButtonClicked();
            }
        });

        retrofit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                activity.retrofitButtonClicked();
            }
        });

        return view;
    }

}
