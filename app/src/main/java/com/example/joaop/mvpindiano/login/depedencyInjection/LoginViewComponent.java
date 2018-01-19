package com.example.joaop.mvpindiano.login.depedencyInjection;

import com.example.joaop.mvpindiano.login.mvp.view.LoginActivity;
import com.example.joaop.mvpindiano.root.dependencyInjection.AppComponent;

import dagger.Component;
import dagger.Provides;

@LoginActivityScope
@Component(modules = {LoginViewModule.class}, dependencies = {AppComponent.class})
public interface LoginViewComponent {

    void injectLoginActivity(LoginActivity loginActivity);
}
