package com.example.joaop.mvpindiano.root;

import android.app.Application;

import dagger.Module;

@Module // PROVEDOR DA DEPENDENCIA "Application"
public class ApplicationModule {

    private Application application;

    public ApplicationModule(Application application){
        this.application = application;
    }
}
