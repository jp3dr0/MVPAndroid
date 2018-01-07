package com.example.joaop.mvpindiano.login.mvp;

import com.example.joaop.mvpindiano.login.domain.User;

// AQUI VAMOS DEFINIR A ESTRUTURA DO MVP DO LOGIN
public interface LoginActivityMVP {

    // VIEW -> APENAS METODOS QUE FAZEM ALGUMA COISA QUE O USUARIO VE NA TELA
    interface View {
        // metodos para receber o que o usuario digitar no edit text
        String getFirstName();
        String getLastName();

        // metodos para dar um output para o usuario (alertas)
        void showUserNotAvaiable();
        void showInputError();
        void showUserSavedMessage();

        // metodos para setar o edit text da view
        void setFirstName(String firstName);
        void setLastName(String lastName);
    }

    // PREENTER -> APENAS METODOS QUE TRATA DE PASSAR E TRATAR DADOS ENTRE A VIEW E O MODEL E VICE VERSA
    interface Presenter {
        // metodo para definir a view que o presenter esta trabalhando
        void setView(LoginActivityMVP.View view);

        // logica de clicks na view. como a view é burra, quando receber um click vai apenas chamar um metodo aqui
        void loginButtonClicked();

        void getCurrentUser();
    }

    // MODEL -> APENAS METODOS QUE MANIPULAM OS DADOS
    interface Model {
        // CRUD
        void createUser(String firstName, String lastName);
        User getUser();
    }
}
