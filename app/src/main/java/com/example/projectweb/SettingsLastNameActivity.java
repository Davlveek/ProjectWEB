package com.example.projectweb;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

public class SettingsLastNameActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings_last_name);

        // Устанавливаем заголовок текущего активити
        setTitle("Change last name");

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
                this.finish();
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
}