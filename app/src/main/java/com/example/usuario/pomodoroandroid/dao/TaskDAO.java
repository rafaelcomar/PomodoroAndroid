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
        if (criaBanco == null){
            criaBanco = new DbHelper(context);
//            carregaDados();
        }
//        this.listTasks = carregaDados();
        if(listTasks == null){
            listTasks = new ArrayList<>();
//            Task t = new Task();
//            t.setTitle("NOVO");
//            t.setDescription("testando array");
//            t.setQtdPomodoros("4");
//            inserirTask(t);
////            listTasks.add(t);
//            Task t2 = new Task();
//            t2.setTitle("NOVO2");
//            t2.setDescription("testando array");
//            t2.setQtdPomodoros("1");
////            listTasks.add(t2);
//            inserirTask(t2);
//            this.listTasks = carregaDados();

        }else{
//            this.listTasks = carregaDados();
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
//            carregaDados();
            return "Registro Inserido com sucesso";

    }

    public ArrayList<Task> carregaDados(){
            Cursor cursor;
            String[] campos =  {criaBanco.KEY_ID ,criaBanco.title ,criaBanco.description ,criaBanco.qtdSegundos, criaBanco.qtdPomodoros,
                    criaBanco.pomodorosFeitos,criaBanco.concluded, criaBanco.descanso};


            db = criaBanco.getReadableDatabase();
            cursor = db.query(criaBanco.tabela, campos, null, null, null, null,  null);
            listTasks.clear();

            if(cursor!=null){
                cursor.moveToFirst();
                while (cursor.moveToNext()){
                    Task t = new Task();
                    t.setId(Integer.parseInt(cursor.getString(0)));
                    t.setTitle(cursor.getString(1));
                    t.setDescription(cursor.getString(2));
                    t.setQtdSegundos(cursor.getString(3));
                    t.setQtdPomodoros(cursor.getString(4));
                    t.setPomodorosFeitos(Integer.parseInt(cursor.getString(5)));
                    t.setConcluded(Boolean.parseBoolean(cursor.getString(6)));
                    t.setDescanso(Boolean.parseBoolean(cursor.getString(7)));

                    listTasks.add(t);
                }
            }

        db.close();
        return listTasks;
    }



    public Task searchTaskById(int index){
        db = criaBanco.getReadableDatabase();
        Task t= null;
        Cursor cursor;
        String[] campos =  {criaBanco.title ,criaBanco.description ,criaBanco.qtdSegundos, criaBanco.qtdPomodoros,
                criaBanco.pomodorosFeitos,criaBanco.concluded, criaBanco.descanso};
        String where = criaBanco.KEY_ID + "=" + index;
        db = criaBanco.getReadableDatabase();
        cursor = db.query(criaBanco.tabela,campos,where, null, null, null, null, null);

        if(cursor!=null){
            cursor.moveToFirst();

            t.setTitle(cursor.getString(0));
            t.setDescription(cursor.getString(1));
            t.setQtdSegundos(cursor.getString(2));
            t.setQtdPomodoros(cursor.getString(3));
            t.setPomodorosFeitos(Integer.parseInt(cursor.getString(4)));
            t.setConcluded(Boolean.parseBoolean(cursor.getString(5)));
            t.setDescanso(Boolean.parseBoolean(cursor.getString(6)));
        }
        db.close();
        return t;

    }

    public void updateTask(Task task){
        ContentValues valores;
        String where;

        db = criaBanco.getWritableDatabase();

        where = criaBanco.KEY_ID + "=" + task.getId();

        valores = new ContentValues();
        valores.put(criaBanco.title , task.getTitle());
        valores.put(criaBanco.description, task.getDescription());
        valores.put(criaBanco.qtdSegundos, task.getQtdSegundos());
        valores.put(criaBanco.qtdPomodoros, task.getQtdPomodoros());
        valores.put(criaBanco.pomodorosFeitos, task.getPomodorosFeitos());
        valores.put(criaBanco.concluded, task.isConcluded());
        valores.put(criaBanco.descanso, task.isDescanso());

        db.update(criaBanco.tabela,valores,where,null);
        db.close();

    }

    public void delete(int position){

        Task task = getTsk(position);
        String where = criaBanco.KEY_ID + "=" + task.getId();
        db = criaBanco.getReadableDatabase();
        db.delete(criaBanco.tabela,where,null);
        db.close();
    }


    public static ArrayList<Task> getListTasks() {
        return listTasks;
    }

    public Task getTsk(int position) {
        return listTasks.get(position);
    }
}
