package com.hi.boardify;

import android.content.Context;
import android.media.Image;

import java.io.FileOutputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;

// Only using this class for the UserID technically
public class DataHolder {
    private static DataHolder data_holder = null;

    private String userID;

    private DataHolder(){
    }

    public static DataHolder getInstance() {
        if (data_holder == null) {
            data_holder = new DataHolder();
            return data_holder;
        } else {
            return data_holder;
        }
    }
    public void addUserID(String id){
        userID = id;
    }
    public String getUserID(){
        return userID;
    }
}
