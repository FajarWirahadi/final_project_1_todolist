package com.example.final_project_1_todolist;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.database.Cursor;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {


    ImageView addButton;
    AlertDialog dialog;
    TextView test;
    Button add, cancel, task_complete;
    AlertDialog.Builder builder;
    EditText task;
    RecyclerView recyclerView;
    DatabaseHelper myDB;
    ArrayList<String> task_id, task_title;
    CustomAdapter customAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);



        addButton = (ImageView) findViewById(R.id.alertDialog_button);
        task = findViewById(R.id.add_task);

        recyclerView = findViewById(R.id.recyclerView);

        myDB = new DatabaseHelper(MainActivity.this);
        task_id = new ArrayList<>();
        task_title = new ArrayList<>();
        builder = new AlertDialog.Builder(this);

        task_complete = findViewById(R.id.task_complete_button);
        storeDataInArrays();

        addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final EditText newTask = new EditText(MainActivity.this);
                newTask.setX(36);
                LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(
                        LinearLayout.LayoutParams.MATCH_PARENT,
                        LinearLayout.LayoutParams.MATCH_PARENT);
                lp.setMargins(50, 50, 50, 50);
                newTask.setLayoutParams(lp);
                builder.setTitle("Add a new Task");
                        builder.setMessage("What do you want to do next?");
                        builder.setCancelable(true);
                        builder.setPositiveButton("Add", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                DatabaseHelper myDB = new DatabaseHelper(MainActivity.this);
                                myDB.addTask(newTask.getText().toString().trim());
                                task_id.clear();
                                task_title.clear();
                                storeDataInArrays();
                                customAdapter.notifyDataSetChanged();
                            }
                        })
                        .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                dialogInterface.cancel();
                            }
                        }).setView(newTask)
                        .show();
            }
        });




        customAdapter = new CustomAdapter(MainActivity.this, task_id, task_title);
        recyclerView.setAdapter(customAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(MainActivity.this));




    }
    void storeDataInArrays() {
        Cursor cursor = myDB.readAllData();
        if (cursor.getCount() == 0) {
            Toast.makeText(this, "No data.", Toast.LENGTH_SHORT).show();
        } else {
            while (cursor.moveToNext()) {
                task_id.add(cursor.getString(0));
                task_title.add(cursor.getString(1));
            }
        }
    }

}