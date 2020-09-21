package com.example.projectweb;

import android.os.AsyncTask;
import android.util.Log;

import com.example.projectweb.chat.User;
import com.example.projectweb.rest.RestApi;
import com.example.projectweb.rest.Retrorest;

import retrofit2.Call;
import retrofit2.Response;

public class AsyncSignup extends AsyncTask<User, Void, Integer> {
    private Exception exception;

    protected Integer doInBackground(User... users) {
        Integer code = 1;
        try {
            // Делаем REST-запрос
            RestApi service = Retrorest.getRetrofitInstance().create(RestApi.class);
            Call<Integer> call = service.Signup(users[0]);

            try {
                Response<Integer> response = call.execute();
                    code = response.code();

            }catch (Exception e) {
                Log.d("EXCPTN", e.toString());
            }
        } catch (Exception e) {
            Log.d("EXCPTN", e.toString());

        }
        // Если произошла ошибка, вернется пустой токен, иначе нормальный
        return code;
    }

    protected void onPostExecute(Void v) {
        // TODO: check this.exception
        // TODO: do something with the feed
    }
}
