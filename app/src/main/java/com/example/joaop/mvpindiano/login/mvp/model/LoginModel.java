package com.example.joaop.mvpindiano.login.mvp.model;

import com.example.joaop.mvpindiano.login.mvp.LoginActivityMVP;
import com.example.joaop.mvpindiano.login.domain.User;
import com.example.joaop.mvpindiano.login.repository.LoginRepository;

public class LoginModel implements LoginActivityMVP.Model{

    private LoginRepository repository;

    public LoginModel(LoginRepository repository) {
        this.repository = repository;
    }

    @Override
    public void createUser(String firstName, String lastName) {
        repository.saveUser(new User(firstName, lastName));
    }

    @Override
    public User getUser() {
        return repository.getUser();
    }
}
