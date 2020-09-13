package com.example.projectweb;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        startActivity(new Intent(this, LoginActivity.class));

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        FloatingActionButton myFab = (FloatingActionButton) findViewById(R.id.addChatFloatingActionButton);
        myFab.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                onAddChatClick();
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        // Включаем кнопку настроек
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_settings, menu);

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()) {

            // Открытие activity настроек
            case R.id.action_settings:
                Intent intent = new Intent(this, SettingsActivity.class);
                startActivity(intent);
                break;

            default:
                break;
        }

        return true;
    }

    private void onAddChatClick() {
        Intent intent = new Intent(this, ChatActivity.class);
        startActivity(intent);
    }

    public static void showMessageBox(String text, Context context) {
        AlertDialog.Builder dlgAlert = new AlertDialog.Builder(context);
        dlgAlert.setMessage(text);
        dlgAlert.setPositiveButton("OK", null);
        dlgAlert.setCancelable(false);
        dlgAlert.create().show();
    }
}