package com.example.projectweb.chat;


import android.os.Parcel;
import android.os.Parcelable;

import java.util.Date;

public class Message implements Parcelable {

    String message;
    Date time;
    MessageType type;

    public Message(String message, MessageType type) {
        this.message = message;
        this.type = type;
        this.time = new Date();
    }

    protected Message(Parcel in) {
        message = in.readString();
        time = (Date) in.readSerializable();
        type = (MessageType) in.readSerializable();
    }

    public static final Creator<Message> CREATOR = new Creator<Message>() {
        @Override
        public Message createFromParcel(Parcel in) {
            return new Message(in);
        }

        @Override
        public Message[] newArray(int size) {
            return new Message[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(message);
        dest.writeSerializable(time);
        dest.writeSerializable(type);
    }
}
