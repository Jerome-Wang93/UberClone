package com.example.jerome.uberclone;

import android.app.Application;

import com.parse.Parse;

public class StartServer extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        Parse.initialize(new Parse.Configuration.Builder(this)
                .applicationId("2f69089a7249521d74763fb010a578d8a3a20865")
                .clientKey("af1572c13b73e766108cfc242826800d7fd9b126")
                .server("http://13.211.115.155:80/parse/")
                .build());
    }
}
