package com.example.usuario.pomodoroandroid;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.IBinder;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.usuario.pomodoroandroid.model.Task;
import com.example.usuario.pomodoroandroid.services.ChronometerService;
import com.example.usuario.pomodoroandroid.util.ListAdapter;

import static com.example.usuario.pomodoroandroid.util.ListAdapter.chronometerService;
import static com.example.usuario.pomodoroandroid.util.ListAdapter.sBound;

public class MainActivity extends AppCompatActivity {

    public Button btnNew ;
    private RecyclerView mRecyclerView;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;
    public static TextView chro;
    private Task tsk = new Task();




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

}
