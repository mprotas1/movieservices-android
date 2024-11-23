package com.movieapp.android.activity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.movieapp.android.R;
import com.movieapp.android.model.AuthenticationRequest;
import com.movieapp.android.model.AuthenticationResponse;
import com.movieapp.android.service.AuthenticationClient;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoginActivity extends AppCompatActivity {
    private final String TAG = "LoginActivity";
    private final AuthenticationClient authenticationClient = new AuthenticationClient();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_form);
        setUpComponents();
    }

    private void setUpComponents() {
        EditText emailText = findViewById(R.id.email_field);
        EditText passwordText = findViewById(R.id.password_field);
        Button loginButton = findViewById(R.id.login_button);
        Button notRegisteredButton = findViewById(R.id.go_to_register_button);

        setUpLoginButton(emailText, passwordText, loginButton);
        setUpNotRegisteredButton(notRegisteredButton);
    }

    private void setUpLoginButton(EditText emailText, EditText passwordText, Button loginButton) {
        loginButton.setOnClickListener(listener -> {
            String email = emailText.getText().toString();
            String password = passwordText.getText().toString();

            AuthenticationRequest request = new AuthenticationRequest(email, password);
            Call<AuthenticationResponse> authenticationCall = authenticationClient.authenticate(request);

            authenticationCall.enqueue(new Callback<AuthenticationResponse>() {
                @Override
                public void onResponse(Call<AuthenticationResponse> call, Response<AuthenticationResponse> response) {
                    if (!response.isSuccessful()) {
                        int code = response.code();
                        String text = "Login failed. Check your credentials.\nStatus code: " + code + "\n URL: " + call.request().url().toString();
                        Log.d(TAG, text);
                        Toast.makeText(LoginActivity.this, text, Toast.LENGTH_LONG).show();
                        return;
                    }

                    AuthenticationResponse authenticationResponse = response.body();

                    if(authenticationResponse == null) {
                        Toast.makeText(LoginActivity.this, "Login failed. Check your credentials.", Toast.LENGTH_LONG).show();
                        return;
                    }

                    String token = authenticationResponse.getToken();
                    applyTokenToCache(token);
                    goToCinemasActivity();
                }

                @Override
                public void onFailure(Call<AuthenticationResponse> call, Throwable t) {
                    Log.e(TAG, "Error: " + t.getMessage());
                    Toast.makeText(LoginActivity.this, "Error: " + t.getMessage(), Toast.LENGTH_SHORT).show();
                }
            });
        });
    }

    private void goToCinemasActivity() {
        Intent cinemasActivity = new Intent(LoginActivity.this, DisplayCinemasActivity.class);
        cinemasActivity.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(cinemasActivity);
    }

    private void setUpNotRegisteredButton(Button notRegisteredButton) {
        notRegisteredButton.setOnClickListener(listener -> {
            Intent registerActivity = new Intent(LoginActivity.this, RegisterActivity.class);
            startActivity(registerActivity);
        });
    }

    private void applyTokenToCache(String token) {
        SharedPreferences preferences = getSharedPreferences("auth", MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();
        editor.putString("jwt-token", token);
        editor.apply();
    }

}
