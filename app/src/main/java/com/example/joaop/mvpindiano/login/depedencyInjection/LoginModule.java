package com.example.joaop.mvpindiano.login.depedencyInjection;

import com.example.joaop.mvpindiano.login.mvp.model.LoginModel;
import com.example.joaop.mvpindiano.login.mvp.LoginActivityMVP;
import com.example.joaop.mvpindiano.login.mvp.presenter.LoginActivityPresenter;
import com.example.joaop.mvpindiano.login.repository.LoginRepository;
import com.example.joaop.mvpindiano.login.repository.MemoryRepository;

import dagger.Module;
import dagger.Provides;

@Module
public class LoginModule {

    @Provides
    public LoginActivityMVP.Presenter provideLoginActivityPresenter(LoginActivityMVP.Model model){
        return new LoginActivityPresenter(model);
    }

    @Provides
    public LoginActivityMVP.Model provideLoginActivityModel(LoginRepository repository) {
        return new LoginModel(repository);
    }

    @Provides
    public LoginRepository provideLoginRepository() {
        return new MemoryRepository();
    }
}
