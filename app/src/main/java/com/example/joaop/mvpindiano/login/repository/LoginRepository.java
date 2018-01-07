package com.example.joaop.mvpindiano.login.repository;

import com.example.joaop.mvpindiano.login.domain.User;

// METODOS QUE UM REPOSITORIO DO LOGIN DEVE TER -> CONTRATO
// UM REPOSITORIO PODE SER DA INTERNET, LOCAL, ETC, É ONDE VAI FICAR GUARDADO OS DADOS DO LOGIN
public interface LoginRepository {

    User getUser();

    void saveUser(User user);
}
