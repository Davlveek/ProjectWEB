package com.example.projectweb;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class LoginActivity extends AppCompatActivity {

    final int LAUNCH_SIGNUP_ACTIVITY = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        // Подключаем к EditText слушателей изменения текста
        EditText usernameEditText = (EditText) findViewById(R.id.usernameLoginEditText);
        EditText passwordEditText = (EditText) findViewById(R.id.passwordLoginEditText);

        usernameEditText.addTextChangedListener(new TextWatcher() {

            @Override
            public void afterTextChanged(Editable s) { }

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) { }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                updateLoginButtonState();
            }
        });

        passwordEditText.addTextChangedListener(new TextWatcher() {

            @Override
            public void afterTextChanged(Editable s) { }

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) { }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                updateLoginButtonState();
            }
        });
    }

    @Override
    public void onBackPressed() {
        this.finishAffinity();
    }

    public void onLoginButtonClick(View v) {

        // TODO аутентификация

        this.finish();
    }

    public void onSignupButtonClick(View v) {

        // Открытие activity регистрации
        startActivityForResult(new Intent(this, SignupActivity.class), LAUNCH_SIGNUP_ACTIVITY);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == LAUNCH_SIGNUP_ACTIVITY) {

            switch (resultCode) {

                // Регстрация прошла успешно
                case Activity.RESULT_OK:
                    this.finish();
                    break;

                default:
                    break;
            }
        }
    }

    private void updateLoginButtonState() {

        EditText usernameEditText = (EditText) findViewById(R.id.usernameLoginEditText);
        EditText passwordEditText = (EditText) findViewById(R.id.passwordLoginEditText);
        Button loginButton = (Button) findViewById(R.id.loginButton);

        if(usernameEditText.getText().toString().isEmpty()
                || passwordEditText.getText().toString().isEmpty()) {
            loginButton.setEnabled(false);
        } else {
            loginButton.setEnabled(true);
        }
    }
}