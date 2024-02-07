package com.example.collegeschedulerapp.ui.tasks;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;


import com.example.collegeschedulerapp.R;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.example.collegeschedulerapp.R;

import java.util.ArrayList;

import java.util.List;

public class TasksListAdapter extends ArrayAdapter<Task> {
    //interface enabling each task to be added to the list

    public TasksListAdapter(Context context, List<Task> tasks) {
        super(context, 0, tasks);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        Task task = getItem(position);

        if(convertView == null) {
            /*fixed error of R not being able to find fragment_task_cell by building first
            because R dynamically generates ids during runtime
            */
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.fragment_task_cell, parent, false);
        }

        //creating task cell, adding information, and returning it
        TextView title = convertView.findViewById(R.id.cellTitle);
        title.setText(task.getTitle());

        return convertView;
    }
}
