package com.example.joaop.mvpindiano.root;

import android.app.Activity;
import android.app.Application;

import com.example.joaop.mvpindiano.login.repository.LoginRepository;
import com.example.joaop.mvpindiano.network.ApiService;
import com.example.joaop.mvpindiano.network.dependencyInjection.ApiServiceModule;
import com.example.joaop.mvpindiano.root.dependencyInjection.*;

// VAMOS DEFINIR NESSA CLASSE QUE É A BASE DE TODA A APLICAÇÃO QUAIS SAO OS COMPONENTES (GRUPOS DE MODULOS) QUE SERÃO UTILIZADOS
public class App extends Application {

    private ApiService apiService;

    // COMPONENTES UTILIZADOS NA APLICAÇÃO
    private AppComponent component;

    // GETTER DESSA CLASSE
    public static App get(Activity activity) {
        return (App) activity.getApplication();
    }

    // GETTER DOS COMPONENTES
    public AppComponent getComponent() {
        return component;
    }

    // DEFININDO ESSES COMPONENTES
    @Override
    public void onCreate() {
        super.onCreate();

        component = DaggerAppComponent.builder()
                .apiServiceModule(new ApiServiceModule())
                .build();

        apiService = component.getApiService();
    }
}
