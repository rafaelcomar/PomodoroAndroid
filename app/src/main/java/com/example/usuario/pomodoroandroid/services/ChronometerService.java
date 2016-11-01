package com.example.usuario.pomodoroandroid.services;

import android.app.AlarmManager;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.app.TaskStackBuilder;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Binder;
import android.os.Build;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.widget.Toast;

import com.example.usuario.pomodoroandroid.MainActivity;
import com.example.usuario.pomodoroandroid.R;
import com.example.usuario.pomodoroandroid.TaskDetails;
import com.example.usuario.pomodoroandroid.dao.TaskDAO;
import com.example.usuario.pomodoroandroid.model.Task;
import com.example.usuario.pomodoroandroid.util.NotificationSender;


import java.util.Calendar;



/**
 * Created by Usuário on 23/10/2016.
 */

public class ChronometerService extends Service {
    private static final short PENDING_INTENT_ID = 1;
    private static final short NOTIFICATION_ID = 1;


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
            //Toast t  = Toast.makeText(context, "A task " + task.getTitle().toString() + " foi iniciada." , Toast.LENGTH_LONG);
            //t.show();
        }
        //this.task.setDescanso(true);

        LoadTask lt = new LoadTask();
        lt.execute("25");
    }

    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
    public void onStop(Task tk , Context context){
        this.task = tk;
        this.stop = true;
        if (tk.isConcluded()){
            Toast t  = Toast.makeText(context, "A task " + task.getTitle().toString() + " foi concluida." , Toast.LENGTH_LONG);
            t.show();
            TaskDAO.listTasks.remove(tk);
//            playAlarm(context);
//            NotificationSender ntf = new NotificationSender();
//            Intent i = new Intent(context.getApplicationContext() , NotificationSender.class);
//            context.getApplicationContext().startActivity(i);

//            MainActivity m = new MainActivity();
//            m.enviarNotification();
//            enviarNotification(context);

        }


    }


    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
    public void enviarNotification(Context context){
        Notification.Builder mBuilder = new Notification.Builder(context.getApplicationContext()).setSmallIcon(R.mipmap.ic_launcher)
                .setContentTitle("novo teste")
                .setContentText("testeando aadasijda")
                .setAutoCancel(true);

        Intent resultIntent = new Intent(context.getApplicationContext(), NotificationSender.class);

        TaskStackBuilder stackBuilder = TaskStackBuilder.create(context.getApplicationContext());
        stackBuilder.addParentStack(NotificationSender.class);
        stackBuilder.addNextIntent(resultIntent);

        PendingIntent resultPendingIntent = stackBuilder.getPendingIntent(PENDING_INTENT_ID , PendingIntent.FLAG_UPDATE_CURRENT);
        mBuilder.setContentIntent(resultPendingIntent);

        NotificationManager notificationManager = (NotificationManager) getSystemService(context.getApplicationContext().NOTIFICATION_SERVICE);
        notificationManager.notify(NOTIFICATION_ID , mBuilder.build());
    }


    public void playAlarm(Context context){
//        Intent it = new Intent( "TOCAR_ALARME");
//        PendingIntent alaPendingIntent = PendingIntent.getBroadcast(context, 0 , it, 0);
//        Calendar cal = Calendar.getInstance();
//        cal.setTimeInMillis(System.currentTimeMillis());
//        cal.add(Calendar.SECOND , 3);
//        AlarmManager alarmManager = (AlarmManager) getSystemService(context.ALARM_SERVICE);
//        alarmManager.set(AlarmManager.RTC , cal.getTimeInMillis() , alaPendingIntent);


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
                int count;
                if (task.getPomodorosFeitos()%4 ==0 ){
                    count = 10;
                }else{
                    count = 5;
                }


                while (task.isDescanso() && count >= 0){
                    try {
                        Thread.sleep(1000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    //Chronometer cr =
                    Log.i("APP", "Value : "+count );
                    task.setQtdSegundos(String.valueOf(count));

                    if (count == 0){
                        task.setDescanso(false);
                    }
                    publishProgress(task);
                    count--;
                }
            }else if (Integer.parseInt(task.getQtdPomodoros()) > task.getPomodorosFeitos()){

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
            }else{
                task.setConcluded(true);
                publishProgress(task);
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
