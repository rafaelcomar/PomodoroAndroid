package com.example.usuario.pomodoroandroid.util;

import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;

import com.example.usuario.pomodoroandroid.R;

public class NotificationSender extends AppCompatActivity {

    private MyBroadcastReceiver myBroadcastReceiver;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notification_sender);

        this.myBroadcastReceiver = new MyBroadcastReceiver();
    }

    @Override
    protected void onStart() {
        super.onStart();
        registerReceiver(myBroadcastReceiver , new IntentFilter(Intent.ACTION_BOOT_COMPLETED));

    }

    @Override
    protected void onStop() {
        super.onStop();
        unregisterReceiver(myBroadcastReceiver);
    }
}
