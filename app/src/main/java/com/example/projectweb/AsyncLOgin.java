package com.example.projectweb;

import android.os.AsyncTask;

import com.example.projectweb.chat.User;
import com.example.projectweb.rest.RestApi;
import com.example.projectweb.rest.Retrorest;

import retrofit2.Call;
import retrofit2.Response;

// Вернет или пустой токен или нормальный токен
public class AsyncLOgin extends AsyncTask<User, Void, Token> {
    private Exception exception;

    protected Token doInBackground(User... users) {
        Token token = new Token("", "");
        try {
            // Делаем REST-запрос
            RestApi service = Retrorest.getRetrofitInstance().create(RestApi.class);
            Call<Token> call = service.Login(users[0]);

            try {
                Response<Token> response = call.execute();
                if (response.isSuccessful()){
                    token = response.body();
                }
            }catch (Exception e) {
                ;
            }
        } catch (Exception e) {
            this.exception = e;

        }
        // Если произошла ошибка, вернется пустой токен, иначе нормальный
        return token;
    }

    protected void onPostExecute(Void v) {
        // TODO: check this.exception
        // TODO: do something with the feed
    }
}
