package com.example.usuario.pomodoroandroid;

import android.app.AlarmManager;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.TaskStackBuilder;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Build;
import android.os.Bundle;
import android.os.IBinder;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.usuario.pomodoroandroid.model.Task;
import com.example.usuario.pomodoroandroid.services.ChronometerService;
import com.example.usuario.pomodoroandroid.util.ListAdapter;
import com.example.usuario.pomodoroandroid.util.NotificationSender;

import java.util.Calendar;

import static com.example.usuario.pomodoroandroid.util.ListAdapter.chronometerService;
import static com.example.usuario.pomodoroandroid.util.ListAdapter.sBound;

public class MainActivity extends AppCompatActivity {

    private static final short PENDING_INTENT_ID = 1;
    private static final short NOTIFICATION_ID = 1;
    public Button btnNew ;
    private RecyclerView mRecyclerView;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;
    public static TextView chro;
    private Task tsk = new Task();




    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mRecyclerView = (RecyclerView) findViewById(R.id.taskList);
        mRecyclerView.setHasFixedSize(true);

        // use a linear layout manager

        mLayoutManager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(mLayoutManager);

        // specify an adapter (see also next example)
        mAdapter = new ListAdapter(getApplicationContext());
        mRecyclerView.setAdapter(mAdapter);

        chro = (TextView) findViewById(R.id.chronometerTimer);

        chro.setText(tsk.getQtdPomodoros());

//        Intent it = new Intent("TOCAR_ALARME");
//        PendingIntent alaPendingIntent = PendingIntent.getBroadcast(this, 0 , it, 0);
//        Calendar cal = Calendar.getInstance();
//        cal.setTimeInMillis(System.currentTimeMillis());
//        cal.add(Calendar.SECOND , 3);
//        AlarmManager alarmManager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
//        alarmManager.set(AlarmManager.RTC , cal.getTimeInMillis() , alaPendingIntent);








        btnNew = (Button) findViewById(R.id.btnNew);

        btnNew.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(MainActivity.this , TaskDetails.class);


                startActivity(i);
            }
        });
    }


    @Override
    protected void onStart() {
        super.onStart();
        Intent itService = new Intent(this , ChronometerService.class);
        bindService(itService , sConnection , Context.BIND_AUTO_CREATE);
        sBound= true;


    }


    @Override
    protected void onStop() {
        super.onStop();
        if (sBound){
            unbindService(sConnection);
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        mRecyclerView.setAdapter(mAdapter);


    }

    public ServiceConnection sConnection = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            ChronometerService.LocalBinder binder = (ChronometerService.LocalBinder) service;
            chronometerService = binder.getService();
            sBound = true;
        }


        @Override
        public void onServiceDisconnected(ComponentName name) {
            sBound= false;
        }
    };

    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
    public void enviarNotification(){
        Notification.Builder mBuilder = new Notification.Builder(this).setSmallIcon(R.mipmap.ic_launcher)
                .setContentTitle("novo teste")
                .setContentText("testeando aadasijda")
                .setAutoCancel(true);

        Intent resultIntent = new Intent(this, NotificationSender.class);

        TaskStackBuilder stackBuilder = TaskStackBuilder.create(this);
        stackBuilder.addParentStack(NotificationSender.class);
        stackBuilder.addNextIntent(resultIntent);

        PendingIntent resultPendingIntent = stackBuilder.getPendingIntent(PENDING_INTENT_ID , PendingIntent.FLAG_UPDATE_CURRENT);
        mBuilder.setContentIntent(resultPendingIntent);

        NotificationManager notificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        notificationManager.notify(NOTIFICATION_ID , mBuilder.build());
    }

}
