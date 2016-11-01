package com.example.usuario.pomodoroandroid.util;

import android.content.Context;
import android.database.DatabaseErrorHandler;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.provider.Contacts;

/**
 * Created by rafael.comar on 31/10/2016.
 */
public class DbHelper extends SQLiteOpenHelper {
    private static final int DATABASE_VERSION = 1;
    private static final String DATABASE_NAME = "PomodoroAndroid.db";
    String SQL_CREATE_ENTRIES;

    public static String tabela = "tasks";
    public static final String KEY_ID = "_id";
    public static String title = "titulo";
    public static  String description = "descricao";
    public static  String qtdPomodoros = "total_pomodoros";
    public static  String qtdSegundos = "segundos_restantes";
    public static  String descanso="descanso";
    public static  String concluded="concluido";
    public static  String  pomodorosFeitos="pomodoros_feitos";



//    public DbHelper(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
//        super(context, name, factory, version);
//    }

    public DbHelper(Context context) {
        super(context, DATABASE_NAME , null, DATABASE_VERSION);
    }


    @Override
    public void onCreate(SQLiteDatabase db) {
        SQL_CREATE_ENTRIES = "CREATE TABLE " + tabela +"(" +
                KEY_ID  +" integer primary key autoincrement,"
                + title + " text,"
                + description + " text,"
                + qtdPomodoros + " text,"
                + qtdSegundos + " text,"
                + descanso + " integer,"
                + concluded + " integer,"
                + pomodorosFeitos + " integer"
                +")";
        db.execSQL(SQL_CREATE_ENTRIES);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS tasks");
        onCreate(db);
    }
}
