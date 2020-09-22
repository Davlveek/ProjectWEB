package com.example.projectweb;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.res.TypedArrayUtils;

import android.app.AlertDialog;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;

public class SettingsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);

        // Устанавливаем заголовок текущего активити
        setTitle("Settings");

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

            case R.id.action_apply:     // Применение изменений

                if(((EditText)findViewById(R.id.usernameEditText)).getText().toString().isEmpty()) {
                    MainActivity.showMessageBox("Please type a username", this);
                    break;
                }

                if(((EditText)findViewById(R.id.currentPasswordEditText)).getText().toString().isEmpty()) {
                    MainActivity.showMessageBox("Please type a current password to confirm changes", this);
                    break;
                }

                if(!((EditText) findViewById(R.id.newPasswordEditText)).getText().toString().equals(
                        ((EditText) findViewById(R.id.retypeNewPasswordEditText)).getText().toString())) {
                    MainActivity.showMessageBox("New password and retyped new password did not match", this);
                    break;
                }

                // TODO

            case android.R.id.home:     // Нажата кнопка "назад"

                // Закрываем текущее activity
                this.finish();
                break;

            default:
                break;
        }

        return true;
    }
}