package com.example.projectweb;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.util.Log;
import android.view.KeyEvent;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.projectweb.chat.Message;
import com.example.projectweb.chat.MessageListAdapter;
import com.example.projectweb.chat.MessageType;

import java.util.ArrayList;

public class ChatActivity extends AppCompatActivity {

    MessageListAdapter adapter;
    RecyclerView recyclerView;
    ArrayList<Message> messageList = new ArrayList<>();
    static ChatActivity instance;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);

        // Устанавливаем заголовок текущего активити
        setTitle("Chat room");
        instance = this;
        // Включаем кнопку "Назад"
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        // Привязываем адаптер
        adapter = new MessageListAdapter(this, messageList);
        recyclerView = (RecyclerView) findViewById(R.id.messagesView);
        recyclerView.setAdapter(adapter);

        // Настраиваем анимацию
        DefaultItemAnimator animator = new DefaultItemAnimator();
        recyclerView.setItemAnimator(animator);

        // Настраиваем LinearLayoutManager
        LinearLayoutManager lm = new LinearLayoutManager(this);
        lm.setStackFromEnd(true);
        recyclerView.setLayoutManager(lm);

        // Обработчик нажатия "Enter"
        EditText editTextMessage = (EditText) findViewById(R.id.messageEditText);
        editTextMessage.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                boolean handled = false;
                if (actionId == EditorInfo.IME_ACTION_DONE) {
                    onSendButtonClick(null);
                }
                return handled;
            }
        });


        new CountDownTimer(60000, 1000) {
            int time=60;
            public void onTick(long millisUntilFinished) {
                setTitle("Chat room 0:"+checkDigit(time));
                time--;
            }

            public void onFinish() {
                setTitle("END OF TIME");
                MainActivity.WEB_SOCKET.sendText(String.format("{" +
                        "\"type\":\"reset.connection\"," +
                            "\"chatter\":%s" +
                        "}", MainActivity.nowChatter));
                MainActivity.lastChatter = MainActivity.nowChatter;
                MainActivity.nowChatter = null;
                ChatActivity.getInstance().getBack();
            }

        }.start();

        // Включаем клавиатуру
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
    }
    public String checkDigit(int number) {
        return number <= 9 ? "0" + number : String.valueOf(number);
    }

    public static ChatActivity getInstance() {
        return instance;
    }
    // Закрытие текущего активити
    public void getBack() {
        finish();
    }

    // Обработчик нажаткия физической кнопки "Назад"
    @Override
    public void onBackPressed() {
        getBack();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            getBack();
        }
        return super.onOptionsItemSelected(item);
    }

    @SuppressLint("ResourceType")
    public void AddMessage(String msg, String author, MessageType msgType){

        // Добавляем новое сообщение в listView
        Message message = new Message(msg, author, msgType);
        messageList.add(message);

        // Прокручиваем listView вниз к новому сообщению
        recyclerView.scrollToPosition(messageList.size() - 1);

        // Уведомляем адаптер
        adapter.notifyItemInserted(messageList.size() - 1);
        adapter.notifyDataSetChanged();
    }

    public void onSendButtonClick(View view) {

        // Нажата кнопка "Отправить"
        EditText editTextMessage = (EditText) findViewById(R.id.messageEditText);
        if (editTextMessage.length() > 0) {

            // Отправляем сообщение
            // TODO
            MainActivity.WEB_SOCKET.sendText(String.format("{" +
                    "\"type\":\"message\"," +
                    "\"message\":{\"text\":\"%s\",\"author\":%s},\"chatter\":%s" +
                    "}", editTextMessage.getText().toString(), MainActivity.itsMe, MainActivity.nowChatter));
            Log.d("SENTD",String.format("{" +
                    "\"type\":\"message\"," +
                    "\"message\":{\"text\":\"%s\",\"author\":%s},\"chatter\":%s" +
                    "}", editTextMessage.getText().toString(), MainActivity.itsMe, MainActivity.nowChatter));
            AddMessage(editTextMessage.getText().toString(), "me", MessageType.sent);
            // Сбрасываем текст
            editTextMessage.setText("");
        }
    }
}