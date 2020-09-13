package com.example.projectweb;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;

public class SettingsBirthdayActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings_birthday);

        // Устанавливаем заголовок текущего активити
        setTitle("Change birthday");

        // Включаем кнопку "Назад"
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        // Инициализация даты рождения
        initBirthdaySpinners();
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
                if(checkBirthday()) {
                    this.finish();
                }
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

    private void initBirthdaySpinners() {

        // Настраиваем года рождения
        int currentYear = Calendar.getInstance().get(Calendar.YEAR);        // получаем текущий год
        int endYear = currentYear - 100;                                    // максимальный допустимый возраст человека - 100 лет

        // Заполняем массив годов рождения
        ArrayList<String> yearArray = new ArrayList<>();
        yearArray.add(getResources().getString(R.string.empty_value));      // добавляем пустой год
        for(int i = currentYear - 16; i > endYear; --i) {
            yearArray.add(String.valueOf(i));
        }

        // Добавляем в spinner
        Spinner birthdayYearSpinner = (Spinner)findViewById(R.id.birthdayYearSpinner);
        ArrayAdapter<String> yearAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, yearArray);
        yearAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        birthdayYearSpinner.setAdapter(yearAdapter);

        // Обновляем даты рождения
        updateBirthdayDateSpinner();

        // Настраиваем обработчик изменения года
        birthdayYearSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position, long id) {
                updateBirthdayDateSpinner();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parentView) { }

        });

        // Настраиваем обработчик изменения месяца
        Spinner birthdayMonthSpinner = (Spinner)findViewById(R.id.birthdayMonthSpinner);
        birthdayMonthSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position, long id) {
                updateBirthdayDateSpinner();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parentView) { }

        });
    }

    private void updateBirthdayDateSpinner() {

        Spinner birthdayDateSpinner = (Spinner)findViewById(R.id.birthdayDateSpinner);
        Spinner birthdayYearSpinner = (Spinner)findViewById(R.id.birthdayYearSpinner);

        // Настраиваем даты рождения
        int chosenDate = -1;
        if(birthdayDateSpinner.getSelectedItem() != null &&
                !birthdayDateSpinner.getSelectedItem().toString().equals(getResources().getString(R.string.empty_value))) {
            chosenDate = Integer.parseInt(birthdayDateSpinner.getSelectedItem().toString());
        }

        int maxDate = 31;
        ArrayList<String> months =
                new ArrayList<>(Arrays.asList(getResources().getStringArray(R.array.months)));

        Spinner birthdayMonthSpinner = (Spinner)findViewById(R.id.birthdayMonthSpinner);        // получаем выбранный месяц
        int chosenMonth = months.indexOf(birthdayMonthSpinner.getSelectedItem().toString());    // получаем индекс выбранного месяца

        int chosenYear = Calendar.getInstance().get(Calendar.YEAR);
        if(!birthdayYearSpinner.getSelectedItem().toString().equals(
                getResources().getString(R.string.empty_value))) {                              // получаем выбранный год
            chosenYear = Integer.parseInt(birthdayYearSpinner.getSelectedItem().toString());
        }

        switch (chosenMonth) {
            case 1:
            case 3:
            case 5:
            case 7:
            case 8:
            case 10:
            case 12:
                maxDate = 31;
                break;
            case 4:
            case 6:
            case 9:
            case 11:
                maxDate = 30;
                break;
            case 2:
                maxDate = chosenYear % 4 == 0 ? 29 : 28;                    // является ли выбранный год високосным
                break;
            default:
                break;
        }

        // Заполняем массив дат рождения
        ArrayList<String> dateArray = new ArrayList<>();
        dateArray.add(getResources().getString(R.string.empty_value));      // добавляем пустую дату
        for(int i = 1; i <= maxDate; ++i) {
            dateArray.add(String.valueOf(i));
        }

        // Добавляем в spinner
        ArrayAdapter<String> dateAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, dateArray);
        dateAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        birthdayDateSpinner.setAdapter(dateAdapter);

        // Возвращаем выбранную изначально дату
        if(chosenDate != -1) {
            birthdayDateSpinner.setSelection(dateArray.indexOf(String.valueOf(chosenDate)));
        }
    }

    private boolean checkBirthday() {
        Spinner birthdayDateSpinner = (Spinner)findViewById(R.id.birthdayDateSpinner);
        if(birthdayDateSpinner.getSelectedItem().toString().equals(getResources().getString(R.string.empty_value))) {
            MainActivity.showMessageBox("Please select a date", this);
            return false;
        }

        Spinner birthdayMonthSpinner = (Spinner)findViewById(R.id.birthdayMonthSpinner);
        if(birthdayMonthSpinner.getSelectedItem().toString().equals(getResources().getString(R.string.empty_value))) {
            MainActivity.showMessageBox("Please select a month", this);
            return false;
        }

        Spinner birthdayYearSpinner = (Spinner)findViewById(R.id.birthdayYearSpinner);
        if(birthdayYearSpinner.getSelectedItem().toString().equals(getResources().getString(R.string.empty_value))) {
            MainActivity.showMessageBox("Please select a year", this);
            return false;
        }

        return true;
    }
}