package com.example.daggerpractice.ui.auth;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.bumptech.glide.RequestManager;
import com.example.daggerpractice.R;
import com.example.daggerpractice.models.User;
import com.example.daggerpractice.ui.main.MainActivity;
import com.example.daggerpractice.viewmodels.ViewModelProviderFactory;

import javax.inject.Inject;

import dagger.android.support.DaggerAppCompatActivity;

public class AuthActivity extends DaggerAppCompatActivity implements View.OnClickListener {
    public static final String TAG = "AuthActivity";

    private AuthViewModel viewModel;
    private EditText userId;

    private ProgressBar progressBar;
    @Inject
    ViewModelProviderFactory providerFactory;
    @Inject
    Drawable logo;

    @Inject
    RequestManager requestManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_auth);
        userId = findViewById(R.id.user_id_input);
        progressBar = findViewById(R.id.progress_bar);


        findViewById(R.id.login_button).setOnClickListener(this);

        viewModel = ViewModelProviders.of(this, providerFactory).get(AuthViewModel.class);

        setLogo();
        subscribeObservers();
    }

    private void subscribeObservers() {
        viewModel.observeAuthState().observe(this, new Observer<AuthResource<User>>() {
            @Override
            public void onChanged(AuthResource<User> userAuthResource) {
                if (userAuthResource != null) {
                    switch (userAuthResource.status) {
                        case LOADING: {
                            showProgressBar(true);
                            break;
                        }

                        case AUTHENTICATED: {
                            Log.d(TAG, "onChanged: AUTHENTICATED" + userAuthResource.data.getEmail());
                            showProgressBar(false);
                            onLoginSuccess();
                            break;
                        }

                        case ERROR: {
                            showProgressBar(false);
                            Toast.makeText(AuthActivity.this, userAuthResource.message, Toast.LENGTH_LONG).show();
                            break;
                        }

                        case NOT_AUTHENTICATED: {
                            Log.d(TAG, "onChanged: NOT_AUTHENTICATED ");
                            showProgressBar(false);
                            break;
                        }
                    }
                }
            }
        });
    }

    private void showProgressBar(boolean isVisible) {
        if (isVisible) {
            progressBar.setVisibility(View.VISIBLE);
        } else {
            progressBar.setVisibility(View.GONE);
        }
    }

    private void onLoginSuccess() {
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
        finish();
    }

    private void setLogo() {
        requestManager
                .load(logo)
                .into((ImageView) findViewById(R.id.login_logo));
    }
    
    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.login_button) {
            attemptLogin();
            return;
        }
    }

    private void attemptLogin() {
        if (TextUtils.isEmpty(userId.getText().toString())) {
            return;
        }
        viewModel.authenticateWithId(Integer.parseInt(userId.getText().toString()));
    }


}