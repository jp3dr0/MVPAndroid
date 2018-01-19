package com.example.joaop.mvpindiano.login.depedencyInjection;

import com.example.joaop.mvpindiano.login.mvp.LoginActivityMVP;
import com.example.joaop.mvpindiano.login.mvp.model.LoginModel;
import com.example.joaop.mvpindiano.login.mvp.presenter.LoginActivityPresenter;
import com.example.joaop.mvpindiano.login.mvp.view.LoginActivity;
import com.example.joaop.mvpindiano.login.repository.LoginRepository;
import com.example.joaop.mvpindiano.login.repository.MemoryRepository;

import dagger.Module;
import dagger.Provides;

@Module
public class LoginViewModule {

    private final LoginActivity loginActivity;

    public LoginViewModule(LoginActivity loginActivity){
        this.loginActivity = loginActivity;
    }

    @Provides
    @LoginActivityScope
    public LoginActivity loginActivity() {
        return loginActivity;
    }

    @Provides
    @LoginActivityScope
    public LoginActivityMVP.Presenter provideLoginActivityPresenter(LoginActivityMVP.Model model){
        return new LoginActivityPresenter(model);
    }

    @Provides
    @LoginActivityScope
    public LoginActivityMVP.Model provideLoginActivityModel(LoginRepository repository) {
        return new LoginModel(repository);
    }

    @Provides
    @LoginActivityScope
    public LoginRepository provideLoginRepository() {
        return new MemoryRepository();
    }
}
