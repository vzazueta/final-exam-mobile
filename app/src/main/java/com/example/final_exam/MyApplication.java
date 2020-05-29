package com.example.final_exam;

import android.app.Application;

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
