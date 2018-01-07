package com.example.joaop.mvpindiano.root;

import android.app.Application;

import com.example.joaop.mvpindiano.login.depedencyInjection.LoginModule;

// VAMOS DEFINIR NESSA CLASSE QUE É A BASE DE TODA A APLICAÇÃO QUAIS SAO OS COMPONENTES (GRUPOS DE MODULOS) QUE SERÃO UTILIZADOS
public class App extends Application {

    // COMPONENTES UTILIZADOS NA APLICAÇÃO
    private ApplicationComponent component;

    // GETTER DOS COMPONENTES
    public ApplicationComponent getComponent() {
        return component;
    }

    // DEFININDO ESSES COMPONENTES
    @Override
    public void onCreate() {
        super.onCreate();

        component = DaggerApplicationComponent.builder()
                .applicationModule(new ApplicationModule(this))
                .loginModule(new LoginModule())
                .build();
    }
}
