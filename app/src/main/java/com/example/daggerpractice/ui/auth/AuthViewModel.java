package com.example.daggerpractice.ui.auth;

import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.LiveDataReactiveStreams;
import androidx.lifecycle.MediatorLiveData;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModel;

import com.example.daggerpractice.SessionManager;
import com.example.daggerpractice.di.network.auth.AuthApi;
import com.example.daggerpractice.models.User;

import javax.inject.Inject;

import io.reactivex.rxjava3.functions.Function;
import io.reactivex.rxjava3.schedulers.Schedulers;

public class AuthViewModel extends ViewModel {

    public static final String TAG = "AuthViewModel";
    private final AuthApi authApi;

    private SessionManager sessionManager;
    @Inject
    public AuthViewModel(AuthApi authApi, SessionManager sessionManager) {
        this.authApi = authApi;
        this.sessionManager = sessionManager;
    }

    public void authenticateWithId(int userId) {
        Log.d(TAG, "authenticateWithId: attempting to login ... ");

        sessionManager.authenticateWithId(queryUserId(userId));
    }

    private LiveData<AuthResource<User>> queryUserId(int userId) {
        return LiveDataReactiveStreams
                .fromPublisher(authApi.getUser(userId)
                        .subscribeOn(Schedulers.io())
                        .onErrorReturn(new Function<Throwable, User>() {
                            @Override
                            public User apply(Throwable throwable) throws Throwable {
                                User errorUser = new User();
                                errorUser.setId(-1);
                                return errorUser;
                            }
                        }).map(new Function<User, AuthResource<User>>() {
                            @Override
                            public AuthResource<User> apply(User user) throws Throwable {
                                if (user.getId() == -1) {
                                    return AuthResource.error("Could not authenticate ", (User)null);
                                }
                                return AuthResource.authenticated(user);
                            }
                        })
                );
    }

    public LiveData<AuthResource<User>> observeAuthState() {
        return sessionManager.getAuthUser();
    }
}

