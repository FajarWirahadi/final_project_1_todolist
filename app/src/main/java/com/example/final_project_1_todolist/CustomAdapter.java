package com.example.final_project_1_todolist;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class CustomAdapter extends RecyclerView.Adapter<CustomAdapter.MyViewHolder> {

    Context context;
    ArrayList task_id, task_title;

    CustomAdapter(Context context, ArrayList task_id, ArrayList task_title) {
        this.context = context;
        this.task_id = task_id;
        this.task_title = task_title;
    }

    @NonNull
    @Override
    public CustomAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.my_row, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CustomAdapter.MyViewHolder holder, int position) {
        holder.task_id_text.setText(String.valueOf(task_id.get(position)));
        holder.task_title_text.setText(String.valueOf(task_title.get(position)));

//        holder.task_complete.setOnClickListener(new View.OnClickListener() {
    }

    @Override
    public int getItemCount() {
        Log.d("size2", String.valueOf(task_title.size()));
        return task_title.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{

        TextView task_id_text, task_title_text;
        Button task_complete;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            task_id_text = itemView.findViewById(R.id.task_id_text);
            task_title_text = itemView.findViewById(R.id.task_title_text);
            task_complete = itemView.findViewById(R.id.task_complete_button);
            task_complete.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            DatabaseHelper myDB = new DatabaseHelper(context);
            myDB.deleteOneRow(task_id_text.getText().toString());
            task_title.remove(getAdapterPosition());
            notifyItemRemoved(getAdapterPosition());
            notifyItemRangeChanged(getAdapterPosition(), task_title.size());
        }

//            @Override
//            public void onClick(View view) {
//
//            }
//        });
    }
}
