package com.example.usuario.pomodoroandroid.dao;

import android.content.Context;

import com.example.usuario.pomodoroandroid.model.Task;

import java.util.ArrayList;

/**
 * Created by Usuário on 22/10/2016.
 */

public class TaskDAO {
    public static ArrayList<Task> listTasks ;
    private Context context;
    private Task tsk;




    public TaskDAO(Context context) {
        this.context = context;
        //this.listTasks = new ArrayList<>();
        if(listTasks == null){
            listTasks = new ArrayList<>();
            Task t = new Task();
            t.setTitle("NOVO");
            t.setDescription("testando array");
            t.setQtdPomodoros("4");
            listTasks.add(t);
            Task t2 = new Task();
            t2.setTitle("NOVO2");
            t2.setDescription("testando array");
            t2.setQtdPomodoros("1");
            listTasks.add(t2);

        }
    }

    public static ArrayList<Task> getListTasks() {
        return listTasks;
    }

    public Task getTsk(int position) {
        return listTasks.get(position);
    }
}
