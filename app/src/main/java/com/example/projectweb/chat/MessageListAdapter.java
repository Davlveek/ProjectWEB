package com.example.projectweb.chat;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.example.projectweb.R;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;

public class MessageListAdapter extends RecyclerView.Adapter {

    private static final int VIEW_TYPE_MESSAGE_SENT = 1;
    private static final int VIEW_TYPE_MESSAGE_RECEIVED = 2;

    ArrayList<Message> messages;

    final String pattern = "HH:mm";
    DateFormat df = new SimpleDateFormat(pattern);

    public MessageListAdapter(Activity context, ArrayList<Message> messages ){
        this.messages = messages;
    }

    @Override
    public void onAttachedToRecyclerView(RecyclerView recyclerView) {
        super.onAttachedToRecyclerView(recyclerView);
    }

    @Override
    public int getItemViewType(int item_position) {
        Message message = messages.get(item_position);
        if (message.type == MessageType.sent) {
            return VIEW_TYPE_MESSAGE_SENT;
        } else {
            return VIEW_TYPE_MESSAGE_RECEIVED;
        }
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View view;

        if (viewType == VIEW_TYPE_MESSAGE_SENT) {
            view = LayoutInflater.from(parent.getContext()).inflate(R.layout.sent_message, parent, false);
            return new SentMessageHolder(view);
        } else if (viewType == VIEW_TYPE_MESSAGE_RECEIVED) {
            view = LayoutInflater.from(parent.getContext()).inflate(R.layout.received_message, parent, false);
            return new ReceivedMessageHolder(view);
        }

        return null;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {

        Message message = messages.get(position);

        switch (holder.getItemViewType()) {
            case VIEW_TYPE_MESSAGE_SENT:
                ((SentMessageHolder) holder).bind(message);
                break;
            case VIEW_TYPE_MESSAGE_RECEIVED:
                ((ReceivedMessageHolder) holder).bind(message);
        }
    }

    @Override
    public int getItemCount() {
        return messages.size();
    }

    private class ReceivedMessageHolder extends RecyclerView.ViewHolder {

        TextView messageText, timeText;

        ReceivedMessageHolder(View itemView) {
            super(itemView);
            messageText = (TextView) itemView.findViewById(R.id.message_body);
//            timeText = (TextView) itemView.findViewById(R.id.text_message_time);
        }

        void bind(Message message) {
            messageText.setText(message.message);
//            timeText.setText(df.format(message.time));
        }
    }

    private class SentMessageHolder extends RecyclerView.ViewHolder {
        TextView messageText, timeText;

        SentMessageHolder(View itemView) {
            super(itemView);

            messageText = (TextView) itemView.findViewById(R.id.message_body);
//            timeText = (TextView) itemView.findViewById(R.id.text_message_time);
        }

        void bind(Message message) {
            messageText.setText(message.message);
//            timeText.setText(df.format(message.time));
        }
    }
}
