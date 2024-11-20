package com.movieapp.android.activity;

import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.movieapp.android.R;
import com.movieapp.android.model.AuthenticationResponse;
import com.movieapp.android.model.RegisterRequest;
import com.movieapp.android.service.AuthenticationClient;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RegisterActivity extends AppCompatActivity {
    private final String TAG = RegisterActivity.class.getSimpleName();
    private final AuthenticationClient authenticationClient = new AuthenticationClient();

    private EditText emailText;
    private EditText passwordText;
    private EditText confirmPasswordText;
    private EditText firstNameText;
    private EditText lastNameText;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_form);
        setUpActivityComponents();
        Button registerButton = findViewById(R.id.register_button);
        setUpRegisterButton(registerButton);
    }

    private void setUpActivityComponents() {
        this.emailText = findViewById(R.id.email_register_input);
        this.passwordText = findViewById(R.id.email_register_password);
        this.confirmPasswordText = findViewById(R.id.confirm_password_field);

        this.firstNameText = findViewById(R.id.first_name_register_input);
        this.lastNameText = findViewById(R.id.last_name_register_input);
    }

    private void setUpRegisterButton(Button registerButton) {
        registerButton.setOnClickListener(onClick -> {
            if (!passwordsMatch()) {
                Log.d(TAG, "Passwords for registration do not match");
                Toast.makeText(this, "Passwords do not match - please check if they are equal", Toast.LENGTH_SHORT).show();
                return;
            }

            if (anyFieldIsEmpty()) {
                Log.d(TAG, "One or more fields are empty");
                Toast.makeText(this, "One or more fields are empty - please fill in all fields", Toast.LENGTH_SHORT).show();
                return;
            }

            RegisterRequest request = new RegisterRequest(
                    emailText.getText().toString(),
                    passwordText.getText().toString(),
                    firstNameText.getText().toString(),
                    lastNameText.getText().toString()
            );

            Call<AuthenticationResponse> registerCall = authenticationClient.register(request);
            registerCall.enqueue(new Callback<AuthenticationResponse>() {
                @Override
                public void onResponse(Call<AuthenticationResponse> call, Response<AuthenticationResponse> response) {
                    if(!response.isSuccessful()) {
                        Log.e(TAG, "Failed to register user: " + response.errorBody());
                        Toast.makeText(RegisterActivity.this, "Failed to register user", Toast.LENGTH_SHORT).show();
                        return;
                    }

                    Log.d(TAG, "Successfully registered user");
                    Toast.makeText(RegisterActivity.this, "Successfully registered user", Toast.LENGTH_SHORT).show();

                    finish();
                }

                @Override
                public void onFailure(Call<AuthenticationResponse> call, Throwable t) {
                    Log.e(TAG, "Failed to register user", t);
                    Toast.makeText(RegisterActivity.this, "Failed to register user", Toast.LENGTH_SHORT).show();
                }
            });
        });
    }

    private boolean passwordsMatch() {
        return passwordText.getText().toString().equals(confirmPasswordText.getText().toString());
    }

    private boolean anyFieldIsEmpty() {
        return emailText.getText().toString().isEmpty() ||
                passwordText.getText().toString().isEmpty() ||
                confirmPasswordText.getText().toString().isEmpty() ||
                firstNameText.getText().toString().isEmpty() ||
                lastNameText.getText().toString().isEmpty();
    }

}
