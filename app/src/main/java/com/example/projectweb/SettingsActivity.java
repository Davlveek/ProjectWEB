package com.example.projectweb;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.SimpleAdapter;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;

public class SettingsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);

        // Устанавливаем заголовок текущего активити
        setTitle("Settings");

        // Включаем кнопку "Назад"
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        // Инициализируем список настроек
        initSettings();

        // Вешаем слушатель выбора настройки
        final ListView settingsListView = findViewById(R.id.settingsListView);
        settingsListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            final ArrayList<String> settings =
                    new ArrayList<>( Arrays.asList(getResources().getStringArray(R.array.settings)));

            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                Intent intent = null;

                switch (settings.get(position)) {
                    case "Username":
                        intent = new Intent(SettingsActivity.this, SettingsUsernameActivity.class);
                        break;

                    case "Password":
                        intent = new Intent(SettingsActivity.this, SettingsPasswordActivity.class);
                        break;

                    case "Gender":
                        AlertDialog.Builder adb = new AlertDialog.Builder(SettingsActivity.this);
                        CharSequence[] genders = (new ArrayList<>(
                                        Arrays.asList(getResources().getStringArray(R.array.gender)))
                                ).toArray(new String[0]);
                        //                                |-- начальная выбранная позиция
                        //                                V
                        adb.setSingleChoiceItems(genders, 0, new DialogInterface.OnClickListener() {

                            @Override
                            public void onClick(DialogInterface d, int n) {
                                // ...
                            }

                        });
                        adb.setNegativeButton("OK", null);
                        adb.setTitle("Choose gender");
                        adb.show();
                        break;

                    case "First name":
                        intent = new Intent(SettingsActivity.this, SettingsFirstNameActivity.class);
                        break;

                    case "Last name":
                        intent = new Intent(SettingsActivity.this, SettingsLastNameActivity.class);
                        break;

                    case "Birthday":
                        intent = new Intent(SettingsActivity.this, SettingsBirthdayActivity.class);
                        break;

                    default:
                        break;

                }
                if(intent != null) {
                    startActivity(intent);
                }
            }
        });
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()) {
            case android.R.id.home:     // Нажата кнопка "назад"
                // Закрываем текущее activity
                this.finish();
                break;

            default:
                break;
        }

        return true;
    }

    private void initSettings() {

        ListView listView = findViewById(R.id.settingsListView);

        ArrayList<String> settings =
                new ArrayList<>( Arrays.asList(getResources().getStringArray(R.array.settings)));
        ArrayList<HashMap<String, String>> arrayList = new ArrayList<>();

        HashMap<String, String> map;
        for(String setting : settings) {
            map = new HashMap<>();
            map.put("Value",    setting);    // TODO тут должен быть запрос к базе
            if(setting.equals("First name") || setting.equals("Last name") || setting.equals("Birthday")) {
                map.put("Setting1", setting);
                map.put("Setting2", "(optional)");
            } else {
                map.put("Setting",  setting);
            }
            arrayList.add(map);
        }

        SimpleAdapter adapter = new SimpleAdapter(this, arrayList, R.layout.settings_row,
                new String[]{"Setting", "Setting1", "Setting2", "Value"},
                new int[]{R.id.setting, R.id.setting1, R.id.setting2, R.id.value});

        listView.setAdapter(adapter);
    }

}