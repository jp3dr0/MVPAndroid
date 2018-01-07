package com.example.joaop.mvpindiano.login.repository;

import com.example.joaop.mvpindiano.login.domain.User;

// IMPLEMENTAÇÃO DE LOGIN REPOSITORY, OU SEJA É UM TIPO DE LOGIN REPOSITORY
// NESSE CASO, É UM REPOSITORIO LOCAL (MEMORIA)
public class MemoryRepository implements LoginRepository {

    private User user;

    @Override
    public User getUser() {
        if (user == null) {
            User user = new User("Usuario", "Padrao");
            user.setId(0);
            return user;
        }
        else {
            return this.user;
        }
    }

    @Override
    public void saveUser(User user) {
        if (user == null){
            user = getUser();
        }

        this.user = user;
    }
}
