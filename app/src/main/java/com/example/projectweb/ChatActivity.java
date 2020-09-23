package com.example.projectweb;

import android.os.Bundle;
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

    static MessageListAdapter adapter;
    static RecyclerView recyclerView;
    static ArrayList<Message> messageList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);

        // Устанавливаем заголовок текущего активити
        setTitle("Chat room");

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

        // Включаем клавиатуру
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
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

    public static void AddMessage(String msg, MessageType msgType){
        // Добавляем новое сообщение в listView
        Message message = new Message(msg, MessageType.sent);
        messageList.add(message);
        // Прокручиваем listView вниз к новому сообщению
        recyclerView.scrollToPosition(messageList.size() - 1);

        // Уведомляем адаптер
        adapter.notifyItemInserted(messageList.size() - 1);
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

            AddMessage(editTextMessage.getText().toString(), MessageType.sent);
            // Сбрасываем текст
            editTextMessage.setText("");
        }
    }
}