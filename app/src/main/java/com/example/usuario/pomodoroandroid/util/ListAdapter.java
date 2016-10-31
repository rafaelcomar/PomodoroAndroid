package com.example.usuario.pomodoroandroid.util;


import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.usuario.pomodoroandroid.MainActivity;
import com.example.usuario.pomodoroandroid.R;
import com.example.usuario.pomodoroandroid.TaskDetails;
import com.example.usuario.pomodoroandroid.dao.TaskDAO;
import com.example.usuario.pomodoroandroid.model.Task;
import com.example.usuario.pomodoroandroid.services.ChronometerService;

import java.util.ArrayList;

//import com.squareup.picasso.Picasso;

//import br.edu.unifor.jsonapp.R;
//import br.edu.unifor.jsonapp.activities.DetailActivity;
//import br.edu.unifor.jsonapp.dao.PostDAO;
//import br.edu.unifor.jsonapp.model.Post;

/**
 * Created by Usuário on 22/10/2016.
 */

public class ListAdapter  extends RecyclerView.Adapter<ListAdapter.ViewHolder> implements ChronometerService.DataListener{

    private Context context;

    public static boolean sBound;
    public static boolean sChronometerStarted;
    public static ChronometerService chronometerService = new ChronometerService() ;

    public ListAdapter(Context applicationContext){

        this.context = applicationContext;
        chronometerService.setListener(this);

    }

    public String strzero(int n){
        if (n<10){
            return "0" + String.valueOf(n);
        }else{
            return String.valueOf(n);
        }

    }
    public String formataTempo(int tempo){
        int ss = tempo %60;
        tempo /=60;
        int min = tempo % 60;
        tempo /= 60;
        int hh = tempo % 24;

        return strzero(hh) + ":" + strzero(min) + ":" + strzero(ss) ;
    }

    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
    @Override
    public void onDataChanged(Task task) {
        //m.atualizaCronometro(task);
        //SimpleDateFormat dt = new SimpleDateFormat("MM:SS");

        if (task.isDescanso() && Integer.parseInt(task.getQtdSegundos()) == 0){
            MainActivity.chro.setTextColor(Color.RED);
            MainActivity.chro.setText(formataTempo(Integer.parseInt(task.getQtdSegundos())));
            chronometerService.onStop(task , context);
            sChronometerStarted = false;
            chronometerService.onStart(task, context);
        }else if (task.isDescanso() == false && Integer.parseInt(task.getQtdSegundos()) == 0 && task.isConcluded() == false){
            MainActivity.chro.setTextColor(Color.BLACK);
            MainActivity.chro.setText(formataTempo(Integer.parseInt(task.getQtdSegundos())));
            chronometerService.onStop(task,context);
            sChronometerStarted = true;
            chronometerService.onStart(task, context);
        }else if (task.isConcluded()){
            chronometerService.onStop(task,context);


        }else{
            MainActivity.chro.setText(formataTempo(Integer.parseInt(task.getQtdSegundos())));
        }


        notifyDataSetChanged();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        TextView txtTitle, txtDescription, txtPomodoros, txtPomodorosFeitos;
        Button btnPlayPause, btnConcluded, btnEdit;


        public ViewHolder(View itemView) {
            super(itemView);
            txtTitle = (TextView) itemView.findViewById(R.id.taskTitle);
            txtDescription = (TextView) itemView.findViewById(R.id.taskDescription);
            txtPomodoros = (TextView) itemView.findViewById(R.id.taskPomodoros);
            txtPomodorosFeitos = (TextView) itemView.findViewById(R.id.taskPomodorosFeitos);


            btnPlayPause = (Button) itemView.findViewById(R.id.btnPlayPause);
            btnConcluded = (Button) itemView.findViewById(R.id.btnConcluded);
            btnEdit = (Button) itemView.findViewById(R.id.btnEdit);

        }
    }
    TaskDAO tsk = new TaskDAO(context);
    ArrayList<Task> listTasks = tsk.listTasks;

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.card, parent, false);

        ViewHolder viewHolder = new ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {
         final Task task = listTasks.get(position);

        holder.txtTitle.setText(task.getTitle());
        holder.txtDescription.setText(task.getDescription());
        holder.txtPomodoros.setText("Pomodoros:" + task.getQtdPomodoros());

        holder.txtPomodorosFeitos.setText("Pomodoros feitos: " + task.getPomodorosFeitos());


        holder.btnEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(context.getApplicationContext(), TaskDetails.class);
                i.putExtra("task_index", position);
                i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                context.startActivity(i);
            }
        });
        holder.btnPlayPause.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                if (sBound){
                    if (!sChronometerStarted){
                        holder.btnPlayPause.setText("Pausar");
                        chronometerService.onStart(task , context);
                        sChronometerStarted = true;
                    }else{
                        holder.btnPlayPause.setText("Iniciar");
                        chronometerService.onStop(task , context);
                        sChronometerStarted = false;
                    }

                }

            }
        });



    }


    @Override
    public int getItemCount() {
        if(listTasks == null){
            return 0;
        }

        return listTasks.size();
    }




}
