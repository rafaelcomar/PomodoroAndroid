package com.example.usuario.pomodoroandroid;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.example.usuario.pomodoroandroid.dao.TaskDAO;
import com.example.usuario.pomodoroandroid.model.Task;

public class TaskDetails extends AppCompatActivity {


    EditText txtTile, txtDescription, txtPomodoros;
    Button btnSave;
    TaskDAO tsk ;
    private int index = -1;
    private int task_id;
    private Task taskRecebido;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_task_details);

        txtTile = (EditText) findViewById(R.id.txtTaskName);
        txtDescription = (EditText) findViewById(R.id.txtTaskDescription);
        txtPomodoros = (EditText) findViewById(R.id.txtTaskPomodoros);
        btnSave = (Button) findViewById(R.id.btnSave);


        if (getIntent().hasExtra("task_index")){
            index = getIntent().getIntExtra("task_index", -1);
            if (index != -1){
                tsk = new TaskDAO(getApplicationContext());
                taskRecebido = tsk.getTsk(index);

                txtTile.setText(taskRecebido.getTitle());
                txtDescription.setText(taskRecebido.getDescription());
                txtPomodoros.setText(taskRecebido.getQtdPomodoros());

            }
        }



        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(taskRecebido == null){
                    taskRecebido = new Task();
                }


                taskRecebido.setTitle(txtTile.getText().toString());
                taskRecebido.setDescription(txtDescription.getText().toString());
                taskRecebido.setQtdPomodoros(txtPomodoros.getText().toString());

                tsk = new TaskDAO(getApplicationContext());

                if(index!= -1){
//                    tsk.listTasks.remove(index);
//                    tsk.listTasks.add( index,task);

                     tsk.updateTask(taskRecebido);
                }else{
                    tsk.inserirTask(taskRecebido);
                }


                System.out.println("Quatidade de tasks na lista: "+tsk.listTasks.size());
                finish();

            }
        });





    }
}
