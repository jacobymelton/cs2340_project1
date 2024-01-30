package com.example.collegeschedulerapp.ui.tasks;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.collegeschedulerapp.R;

public class TasksFragment extends Fragment {
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_tasks, container, false);

        String assignmentName = "Assignment: " + getArguments().getString("assignmentNameDetails");
        String dueDate = "Due Date: " + getArguments().getString("dueDateDetails");
        String className = "Class: " + getArguments().getString("classNameDetails");

        TextView assignmentNameText = (TextView) view.findViewById(R.id.assignmentName);
        assignmentNameText.setText(assignmentName);

        TextView classNameText = (TextView) view.findViewById(R.id.className);
        classNameText.setText(dueDate);

        TextView dueDateText = (TextView) view.findViewById(R.id.dueDate);
        dueDateText.setText(className);






        //Toast.makeText(this, "Hello, this is a toast!", Toast.LENGTH_SHORT).show();
        //Toast recieved = Toast.makeText(getActivity(), receivedData, Toast.LENGTH_SHORT);
        //recieved.show();

        return view;
    }
}
