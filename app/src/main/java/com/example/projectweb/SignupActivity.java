package com.example.projectweb;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.projectweb.chat.User;
import com.example.projectweb.rest.Retrorest;

import java.util.concurrent.ExecutionException;

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

    public void onSignupButtonClick(View v) throws ExecutionException, InterruptedException {
        String username = ((EditText)findViewById(R.id.usernameEditText)).getText().toString();
        String password = ((EditText)findViewById(R.id.passwordEditText)).getText().toString();
        String password2 = ((EditText)findViewById(R.id.retypePasswordEditText)).getText().toString();
        String age = ((EditText)findViewById(R.id.ageEditText)).getText().toString();
        String gender = ((Spinner)findViewById(R.id.genderSpinner)).getSelectedItem().toString();
        Integer code = Retrorest.BAD_ERROR;

        if(username.isEmpty()) {
            MainActivity.showMessageBox("Please type a username", this);
            return;
        }

        if(password.isEmpty()) {
            MainActivity.showMessageBox("Please type a password", this);
            return;
        }

        if(!password.equals(password2)) {
            MainActivity.showMessageBox("Passwords did not match", this);
            return;
        }

        if(age.isEmpty()) {
            MainActivity.showMessageBox("Set your age", this);
            return;
        }

        User user = new User(username, password, age, gender);
        AsyncSignup signupThread = (AsyncSignup) new AsyncSignup();

        code = signupThread.execute(user).get();
        Context context = getApplicationContext();

        Toast toast = Toast.makeText(context, code.toString(), Toast.LENGTH_LONG);
        toast.show();

        if(code == Retrorest.BAD_ERROR){
            // чет вообще все плохо пошло, возможно интернета нет, или еще что-то страшное произошло
            Toast toastee = Toast.makeText(context, "Error while login. Check your internet connection", Toast.LENGTH_LONG);
            toastee.show();
        }
        else if(code == 201){
            // удачно зарегались
            Toast toastee = Toast.makeText(context, "Successfully signed up!", Toast.LENGTH_LONG);
            toastee.show();
            setResult(Activity.RESULT_OK, new Intent());
        }
        else if(code==500){
            // некорректная регистрация
            Toast toastee = Toast.makeText(context, "Nickname is in use. Please choose another", Toast.LENGTH_LONG);
            toastee.show();
        }
        this.finish();
    }
}