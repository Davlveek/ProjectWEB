package com.example.projectweb;

import android.Manifest;
import android.content.Context;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.concurrent.ExecutionException;

public class SettingsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);

        // Устанавливаем заголовок текущего активити
        setTitle("Settings");

        ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, 111);
        // Вызываем всякие REST-штуки чтобы получить инфу
        AsyncGetUser getUser = (AsyncGetUser) new AsyncGetUser();
        Context context = getApplicationContext();
        // Получаем в итоге токен
        String user = null;

        try {
            user = getUser.execute(MainActivity.token_).get();
            if(user.isEmpty()){
                Toast toastee = Toast.makeText(context, "Could not get user information", Toast.LENGTH_LONG);
                toastee.show();
            }else{
                try {
                    JSONObject userJson = new JSONObject(user);
                    MainActivity.itsMe = user;
                    ((EditText)findViewById(R.id.usernameEditText)).setText(userJson.getString("name"));
                    ((EditText)findViewById(R.id.ageEditText)).setText(userJson.getString("age"));
                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }

        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

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