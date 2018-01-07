package com.example.joaop.mvpindiano.login.mvp.view;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.joaop.mvpindiano.R;
import com.example.joaop.mvpindiano.login.mvp.LoginActivityMVP;
import com.example.joaop.mvpindiano.root.App;

import javax.inject.Inject;

public class LoginActivity extends AppCompatActivity implements LoginActivityMVP.View {

    private EditText firstName, lastName;
    private Button login;

    // DEPENDENCIAS DA VIEW
    @Inject
    LoginActivityMVP.Presenter presenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        // RECEBENDO AS DEPENDENCIAS
        ((App) getApplication()).getComponent().inject(this);

        firstName = (EditText) findViewById(R.id.loginActivity_lastName_editText);
        lastName = (EditText) findViewById(R.id.loginActivity_firstName_editText);
        login = (Button) findViewById(R.id.loginActivity_login_button);

        //firstName.setText("");
        //lastName.setText("");

        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // PRESENTER TRATA O CLICK NO BOTAO
                presenter.loginButtonClicked();
            }
        });
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
