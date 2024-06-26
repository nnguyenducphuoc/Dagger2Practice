package com.example.daggerpractice;

import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MediatorLiveData;
import androidx.lifecycle.Observer;

import com.example.daggerpractice.models.User;
import com.example.daggerpractice.ui.auth.AuthResource;

import javax.inject.Inject;
import javax.inject.Singleton;

@Singleton
public class SessionManager {
    public static final String TAG = "SessionManager";

    private MediatorLiveData<AuthResource<User>> cachedUser = new MediatorLiveData<>();

    @Inject
    public SessionManager() {
    }

    public void authenticateWithId (final LiveData<AuthResource<User>> source) {
        if (cachedUser != null) {
            cachedUser.setValue(AuthResource.authenticated(null));
            cachedUser.addSource(source, new Observer<AuthResource<User>>() {
                @Override
                public void onChanged(AuthResource<User> userAuthResource) {
                    cachedUser.setValue(userAuthResource);
                    cachedUser.removeSource(source);
                }
            });
        }
        else {
            Log.d(TAG, "session manager");
        }
    }

    public void logOut() {
        Log.d(TAG, "log out");
        cachedUser.setValue(AuthResource.logout());
    }

    public LiveData<AuthResource<User>> getAuthUser() {
        return cachedUser;
    }
}
