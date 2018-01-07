package com.example.joaop.mvpindiano.login.mvp.presenter;

import android.support.annotation.Nullable;

import com.example.joaop.mvpindiano.login.mvp.LoginActivityMVP;
import com.example.joaop.mvpindiano.login.domain.User;

public class LoginActivityPresenter implements LoginActivityMVP.Presenter{

    // VIEW ATUAL QUE O PRESENTER ESTA TRABALHANDO
    @Nullable
    private LoginActivityMVP.View view;

    // MODEL ATUAL QUE O PRESENTER ESTA TRABALHANDO
    private LoginActivityMVP.Model model;

    // INJEÇÃO DA DEPENDENCIA "model" -> CONSTRUCTOR INJECTION
    public LoginActivityPresenter(LoginActivityMVP.Model model){
        this.model = model;
    }

    // SETANDO O ATRIBUTO VIEW
    @Override
    public void setView(LoginActivityMVP.View view) {
        this.view = view;
    }

    @Override
    public void loginButtonClicked() {
        if (view != null){
            if(view.getFirstName().trim().equals("") || view.getLastName().trim().equals("")){
                view.showInputError();
            }
            else {
                model.createUser(view.getFirstName(), view.getLastName());
                view.showUserSavedMessage();
            }
        }
    }

    @Override
    public void getCurrentUser() {

        User user = model.getUser();

        if (user == null){
            if (view != null){
                view.showUserNotAvaiable();
            }
        }
        else {
            if(view != null){
                view.setFirstName(user.getFirstName());
                view.setLastName(user.getLastName());
            }
        }
    }
}
