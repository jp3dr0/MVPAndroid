package com.example.joaop.mvpindiano.root.dependencyInjection;

import android.content.Context;

import dagger.Module;
import dagger.Provides;

@Module
public class ContextModule {
    private final Context context;

    public ContextModule (Context context) {
        this.context = context.getApplicationContext();
    }

    @Provides
    @ApplicationContext
    @AppScope
    public Context provideContext(){
        return context;
    }

}
