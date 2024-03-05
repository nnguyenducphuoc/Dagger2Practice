package com.example.daggerpractice.di.network.main;

import com.example.daggerpractice.models.Post;

import java.util.List;

import io.reactivex.rxjava3.core.Flowable;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface MainApi {
    // posts?userId=1
    @GET("posts")
    Flowable<List<Post>> getPostsFromUser (
            @Query("userId") int id
    );
}
