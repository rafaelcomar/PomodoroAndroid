package com.example.usuario.pomodoroandroid.services;

import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Binder;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.widget.Toast;

import com.example.usuario.pomodoroandroid.model.Task;

/**
 * Created by Usuário on 23/10/2016.
 */

public class ChronometerService extends Service {
    public ChronometerService() {
    }

    private DataListener<RecyclerView.ViewHolder> listener;
    //private Task task = new Task();
    private Task task;
    private IBinder binder;
    private boolean stop;

    public interface DataListener<V extends RecyclerView.ViewHolder>{
        void onDataChanged(Task task);
    }

    public void setListener(DataListener<RecyclerView.ViewHolder> listener) {
        this.listener = listener;
    }


    public class LocalBinder extends Binder{
        public ChronometerService getService(){
            return ChronometerService.this;
        }
    }

    public ChronometerService(IBinder binder) {
        this.binder = new LocalBinder();
        this.stop = false;
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return binder;
    }

    public void onStart(Task tk , Context context){
        this.task = tk;
        this.stop = false;

        if (context == null){
            System.out.println("nuloooooo");
        }else{
            Toast t  = Toast.makeText(context, "A task " + task.getTitle().toString() + " foi iniciada." , Toast.LENGTH_LONG);
            t.show();
        }
        //this.task.setDescanso(true);

        LoadTask lt = new LoadTask();
        lt.execute("10");
    }

    public void onStop(Task tk){
        this.task = tk;
        this.stop = true;

    }

    public class LoadTask extends AsyncTask<String, Task, Void>{
        //ListAdapter lss = new ListAdapter(getApplicationContext());


        //Chronometer cr =
        int i = 0;

        @Override
        protected Void doInBackground(String... params) {

            //String num = task.getQtdSegundos();
            String num = params[0];

            if (task.isDescanso()){
                int count = 6;

                while (task.isDescanso() && count >= 0){
                    try {
                        Thread.sleep(1000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    //Chronometer cr =
                    Log.i("APP", "Value : "+count );
                    task.setQtdSegundos(String.valueOf(count));
                    count--;
                    if (count == 0){
                        task.setDescanso(false);
                    }
                    publishProgress(task);
                }
            }else{

                i = Integer.parseInt(num);
                while(!stop && i >= 0 ){
                    //faça algo.
                    try {
                        Thread.sleep(1000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    //Chronometer cr =
                    Log.i("APP", "Value : "+i );
                    task.setQtdSegundos(String.valueOf(i));
                    i--;
                    if (i == 0 ){
                        task.setPomodorosFeitos(task.getPomodorosFeitos()+1);
                        task.setDescanso(true);
                    }
                    publishProgress(task);
                }
            }
            return null;
        }

        @Override
        protected void onProgressUpdate(Task... values) {
            super.onProgressUpdate(values);
            if(listener != null){
                listener.onDataChanged(task);
            }

        }
    }


}
