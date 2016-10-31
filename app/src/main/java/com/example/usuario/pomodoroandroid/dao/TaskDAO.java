package com.example.usuario.pomodoroandroid.dao;

import android.content.ContentValues;
import android.content.Context;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import com.example.usuario.pomodoroandroid.model.Task;
import com.example.usuario.pomodoroandroid.util.DbHelper;

import java.util.ArrayList;

/**
 * Created by Usuário on 22/10/2016.
 */

public class TaskDAO {
    public static ArrayList<Task> listTasks ;
    private Context context;
    private Task tsk;

    private DbHelper criaBanco;
    private SQLiteDatabase db;




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

        if (criaBanco == null){
            criaBanco = new DbHelper(context);
            carregaDados();
        }else{
            carregaDados();
        }


    }

    public String inserirTask(Task task){
        ContentValues valores;
        long resultado;

        db = criaBanco.getWritableDatabase();
        valores = new ContentValues();
        valores.put(criaBanco.title , task.getTitle());
        valores.put(criaBanco.description, task.getDescription());
        valores.put(criaBanco.qtdSegundos, task.getQtdSegundos());
        valores.put(criaBanco.qtdPomodoros, task.getQtdPomodoros());
        valores.put(criaBanco.pomodorosFeitos, task.getPomodorosFeitos());
        valores.put(criaBanco.concluded, task.isConcluded());
        valores.put(criaBanco.descanso, task.isDescanso());

        resultado = db.insert(criaBanco.tabela, null, valores);
        db.close();

        if (resultado ==-1)
            return "Erro ao inserir registro";
        else
            return "Registro Inserido com sucesso";

    }

    public Cursor carregaDados(){
        Cursor cursor;
        String[] campos =  {criaBanco.title ,criaBanco.description ,criaBanco.qtdSegundos, criaBanco.qtdPomodoros,
                criaBanco.pomodorosFeitos,criaBanco.concluded, criaBanco.descanso};
        db = criaBanco.getReadableDatabase();
        cursor = db.query(criaBanco.tabela, campos, null, null, null, null, null, null);

        if(cursor!=null){
            cursor.moveToFirst();
            while (cursor.moveToNext()){
                Task t = new Task();
                t.setTitle(cursor.getString(0));
                t.setDescription(cursor.getString(1));
                t.setQtdSegundos(cursor.getString(2));
                t.setQtdPomodoros(cursor.getString(3));
                t.setPomodorosFeitos(Integer.parseInt(cursor.getString(4)));
                t.setConcluded(Boolean.parseBoolean(cursor.getString(5)));
                t.setDescanso(Boolean.parseBoolean(cursor.getString(6)));

                listTasks.add(t);
            }
        }

        db.close();
        return cursor;
    }


    public static ArrayList<Task> getListTasks() {
        return listTasks;
    }

    public Task getTsk(int position) {
        return listTasks.get(position);
    }
}
