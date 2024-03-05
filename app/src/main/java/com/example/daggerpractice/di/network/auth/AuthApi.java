package com.example.daggerpractice.di.network.auth;

import com.example.daggerpractice.models.User;

import io.reactivex.rxjava3.core.Flowable;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface AuthApi {
    @GET("users/{id}")
    Flowable<User> getUser(
            @Path("id") int id
    );
}
