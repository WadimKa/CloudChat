package com.wadimkazak.cloudchat;

import com.google.firebase.database.IgnoreExtraProperties;

import java.io.Serializable;

/**
 * Created by Wadim on 27.05.2018.
 */
@IgnoreExtraProperties
public class Message implements Serializable {
    private String name;
    private String message;

    public Message() {
    }

    public Message(String name, String message) {
        this.name = name;
        this.message = message;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
