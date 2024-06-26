package com.example.daggerpractice.di.main;

import com.example.daggerpractice.di.network.main.MainApi;
import com.example.daggerpractice.ui.main.posts.PostRecyclerAdapter;

import dagger.Module;
import dagger.Provides;
import retrofit2.Retrofit;

@Module
public class MainModule {

    @MainScope
    @Provides
    static MainApi provideMainApi(Retrofit retrofit) {
        return retrofit.create(MainApi.class);
    }

    @MainScope
    @Provides
    static PostRecyclerAdapter provideAdapter() {
        return new PostRecyclerAdapter();
    }
}
