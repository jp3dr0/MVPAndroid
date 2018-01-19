package com.example.joaop.mvpindiano;

import com.example.joaop.mvpindiano.login.domain.User;
import com.example.joaop.mvpindiano.login.mvp.LoginActivityMVP;
import com.example.joaop.mvpindiano.login.mvp.presenter.LoginActivityPresenter;

import org.junit.Before;
import org.junit.Test;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyZeroInteractions;
import static org.mockito.Mockito.when;

// aqui vamos testar a camada presenter do MVP, sem precisar rodar o app, apenas na JVM
public class PresenterTests {
    // como o app nao vai rodar de fato, e a camada presenter é dependente da camada model e view, vamos criar uma view e uma model "fake"
    // vamos usar interfaces e nao implementações concretas, pois isso facilita o trabalho do Mockito
    LoginActivityMVP.Model mockLoginModel;
    LoginActivityMVP.View mockLoginView;
    // presenter que vamos testar. isso sim precisa ser uma implementação concreta
    LoginActivityMVP.Presenter presenter;
    User user;

    // metodo de "setup", para configurar tudo antes do teste
    // isso certifica que cada teste realizado nao vai ter interferencia de testes passados, vai sempre resetar tudo
    // se voce tiver 4 testes para serem rodados esse metodo vai ser executado 4 vezes
    @Before
    public void setup(){
        // vamos inicializar os objetos "fake". reforçando que serao utilizadas interfaces para facilitar o Mockito
        mockLoginModel = mock(LoginActivityMVP.Model.class);
        mockLoginView = mock(LoginActivityMVP.View.class);

        // criando um user fake para testes
        user = new User("Fox", "Mulder");

        // podemos "mandar" no nosso model fake, inclusive controlar oq seus metodos devem retornar
        // aqui vamos definir oq deve acontecer quando o metodo getUser() for chamado
        when(mockLoginModel.getUser()).thenReturn(user);

        // agora vamos instanciar uma implementação concreta do presenter, que é o objetivo dos nossos testes aqui
        presenter = new LoginActivityPresenter(mockLoginModel);
        presenter.setView(mockLoginView);

    }

    // primeiro teste: testando se ao interagir com presenter, nada acontece na view
    @Test
    public void noInteractionWithView(){
        // chamando um metodo qualquer do presenter
        presenter.getCurrentUser();

        // verificando se a view ficou quietinha sem fazer nada
        // vai dar errado, já que nao aconteceria nada se o usuario atual fosse nulo, mas no setup nos definimos um usuario, entao a view vai fazer algo
        // se voce comentar a definição do usuario no setup, o teste vai passar
        verifyZeroInteractions(mockLoginView);
    }

    // segundo teste: carregar um usuario do repositorio quando
    @Test
    public void loadTheUserFromRepositoryWhenValidUserIsPresent(){
        // quando o getUser() do model for chamado, ira retornar o usuario fake do setup
        when(mockLoginModel.getUser()).thenReturn(user);

        // o metodo getCurrentUser() do presenter chama o getUser() do model apenas uma vez
        presenter.getCurrentUser();

        // aqui vamos verificar se isso realmente aconteceu e se foi so uma vez
        verify(mockLoginModel, times(1)).getUser();

        // aqui vamos verificar se a view setou o usuario fake apenas uma vez como mandamos e se o metodo de nenhum usuario valido nao foi executado nunca
        verify(mockLoginView, times(1)).setFirstName("Fox");
        verify(mockLoginView, times(1)).setLastName("Mulder");
        verify(mockLoginView, never()).showUserNotAvaiable();
    }

    // terceiro teste: verificar se a view mostra um toast de usuario nao disponivel quando o usuario atual for nulo
    @Test
    public void shouldShowErrorMessageWhenUserIsNull(){
        // quando o getUser() do model for chamado, ira retornar nulo
        when(mockLoginModel.getUser()).thenReturn(null);

        // o metodo getCurrentUser() do presenter chama o getUser() do model apenas uma vez
        presenter.getCurrentUser();

        // aqui vamos verificar se isso realmente aconteceu e se foi so uma vez
        verify(mockLoginModel, times(1)).getUser();

        // aqui vamos verificar se a view setou o usuario fake apenas uma vez como mandamos e se o metodo de nenhum usuario valido nao foi executado nunca
        verify(mockLoginView, never()).setFirstName("Fox");
        verify(mockLoginView, never()).setLastName("Mulder");
        verify(mockLoginView, times(1)).showUserNotAvaiable();
    }

    // quarto teste: verificar se a view apresenta um toast de erro se o usuario nao digitar nada
    @Test
    public void shouldCreateErrorMessageIfFieldAreEmpty(){
        // setando a view, simulando que o usuario nao digitou nada no first name
        when(mockLoginView.getFirstName()).thenReturn("");

        // simulando que o usuario clicou no botao de login
        presenter.loginButtonClicked();

        // verificando se apenas o getFirstName() foi chamado, consequentemente chamando o showInputError()
        verify(mockLoginView, times(1)).getFirstName();
        verify(mockLoginView, never()).getLastName();
        verify(mockLoginView, times(1)).showInputError();

        // segunda parte: simulando que o usuario digitou o first name mas nada no last name
        when(mockLoginView.getFirstName()).thenReturn("Dana");
        when(mockLoginView.getLastName()).thenReturn("");

        // simulando que o usuario clicou no botao de login
        presenter.loginButtonClicked();

        verify(mockLoginView, times(2)).getFirstName(); // verificando se foi chamado antes e agora (2 vezes)
        verify(mockLoginView, times(1)).getLastName(); // verificando se foi chamado apenas uma vez (na segunda parte)
        verify(mockLoginView, times(2)).showInputError(); // verificando se foi chamado antes e agora (2 vezes)
    }

    // quinto teste: verificando se o presenter salva o usuario recebido da view se ele for valido
    @Test
    public void shouldBeAbleToSaveAValidUser(){
        // simulando que o usuario digitou um usuario valido
        when(mockLoginView.getFirstName()).thenReturn("Dana");
        when(mockLoginView.getLastName()).thenReturn("Scully");

        // simulando que o usuario clicou no botao de login
        presenter.loginButtonClicked();

        // verificando se o getFirstName e getLastName foi chamado duas vezes, primeira vez foi na simulação de digitação do usuario e depois no loginButtonClicked() do presenter
        verify(mockLoginView, times(2)).getFirstName();
        verify(mockLoginView, times(2)).getLastName();

        // verificando se o presenter realmente criou o usuario
        verify(mockLoginModel, times(1)).createUser("Dana", "Scully");

        // verificando se a view mostrou o toast de confirmação ao usuario
        verify(mockLoginView, times(1)).showUserSavedMessage();
    }
}
