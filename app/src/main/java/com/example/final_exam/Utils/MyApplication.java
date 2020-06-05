package com.example.final_exam.Utils;

import android.app.Application;

import com.example.final_exam.types.User;

public class MyApplication extends Application {
    private User myUser;

    private static MyApplication single_instance = null;

    private MyApplication(){}

    @Override
    public void onCreate() { super.onCreate(); }

    public static MyApplication getInstance(){
        if(single_instance == null){
            single_instance = new MyApplication();
        }
        return  single_instance;
    }

    public User getMyUser() {
        return myUser;
    }

    public void setMyUser(User myUser) {
        this.myUser = myUser;
    }
}
