package com.example.joaop.mvpindiano.network;

import com.example.joaop.mvpindiano.network.model.User;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import rx.Observable;

public interface ApiService {
    @GET("users")
    Call<List<User>> getUsers();

    @GET("users")
    Observable<List<User>> getUsersObservable();
}
