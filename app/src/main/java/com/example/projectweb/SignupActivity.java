package com.example.projectweb;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;

public class SignupActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);

        // Устанавливаем заголовок текущего активити
        setTitle("Sign up");

        // Включаем кнопку "Назад"
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()) {

            // Нажата кнопка "назад"
            case android.R.id.home:
                // Закрываем текущее activity
                this.finish();
                break;

            default:
                break;
        }

        return true;
    }

    public void onSignupButtonClick(View v) {

        if(((EditText)findViewById(R.id.usernameEditText)).getText().toString().isEmpty()) {
            MainActivity.showMessageBox("Please type a username", this);
            return;
        }

        if(((EditText)findViewById(R.id.passwordEditText)).getText().toString().isEmpty()) {
            MainActivity.showMessageBox("Please type a password", this);
            return;
        }

        if(!((EditText) findViewById(R.id.passwordEditText)).getText().toString().equals(
                ((EditText) findViewById(R.id.retypePasswordEditText)).getText().toString())) {
            MainActivity.showMessageBox("Passwords did not match", this);
            return;
        }

        setResult(Activity.RESULT_OK, new Intent());
        this.finish();
    }
}