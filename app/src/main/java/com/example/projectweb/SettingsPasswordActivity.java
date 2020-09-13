package com.example.projectweb;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.EditText;

public class SettingsPasswordActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings_password);

        // Устанавливаем заголовок текущего активити
        setTitle("Change password");

        // Включаем кнопку "Назад"
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        // Включаем кнопку применения изменений
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_apply, menu);

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()) {

            case R.id.action_apply:     // TODO Применение изменений
                finishCurrentActivity();
                break;

            case android.R.id.home:     // Нажата кнопка "назад"

                // Закрываем текущее activity
                this.finish();
                break;

            default:
                break;
        }

        return true;
    }

    private void finishCurrentActivity() {

        if(((EditText)findViewById(R.id.currentPasswordEditText)).getText().toString().isEmpty()) {
            MainActivity.showMessageBox("Please type a current password", this);
            return;
        }

        if(((EditText)findViewById(R.id.newPasswordEditText)).getText().toString().isEmpty()) {
            MainActivity.showMessageBox("Please type a new password", this);
            return;
        }

        if(!((EditText) findViewById(R.id.newPasswordEditText)).getText().toString().equals(
                ((EditText) findViewById(R.id.retypeNewPasswordEditText)).getText().toString())) {
            MainActivity.showMessageBox("Passwords did not match", this);
            return;
        }

        setResult(Activity.RESULT_OK, new Intent());
        this.finish();

    }
}