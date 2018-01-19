package com.example.joaop.mvpindiano.root.dependencyInjection;

import com.example.joaop.mvpindiano.login.depedencyInjection.LoginViewModule;
import com.example.joaop.mvpindiano.login.repository.LoginRepository;
import com.example.joaop.mvpindiano.network.ApiService;
import com.example.joaop.mvpindiano.network.dependencyInjection.ApiServiceModule;

import dagger.Component;
import dagger.Provides;

@AppScope
@Component(modules = {ApiServiceModule.class, ContextModule.class, LoginViewModule.class})
public interface AppComponent {
    // LoginRepository getApiService();

    ApiService getApiService();
}
