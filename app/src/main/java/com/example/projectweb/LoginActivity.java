package com.example.projectweb;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import com.example.projectweb.chat.User;

import java.util.concurrent.ExecutionException;

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

    public void onLoginButtonClick(View v) throws ExecutionException, InterruptedException {

        String username = ((EditText) findViewById(R.id.usernameLoginEditText)).getText().toString(),
                password = ((EditText) findViewById(R.id.passwordLoginEditText)).getText().toString();

        ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, 111);
        // Вызываем всякие REST-штуки чтобы логнуться
        User user = new User(username, password);
        AsyncLogin loginThread = (AsyncLogin) new AsyncLogin();

        // Получаем в итоге токен
        Token tok = loginThread.execute(user).get();
        if (tok.refresh.equals("") && tok.access.equals("")){
            Context context = getApplicationContext();
            Toast toast = Toast.makeText(context, "Could not login", Toast.LENGTH_LONG);
            toast.show();
        } else {
            this.finish();
        }
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
                    //this.finish(); << тип зарегались и сразу предложим залогиниться
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