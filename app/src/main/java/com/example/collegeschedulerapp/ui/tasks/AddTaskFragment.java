package com.example.collegeschedulerapp.ui.tasks;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.fragment.NavHostFragment;

import com.example.collegeschedulerapp.R;

public class AddTaskFragment extends Fragment {

    private EditText titleEditText;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_tasks, container, false);

        titleEditText = view.findViewById(R.id.editTaskName);

        Button add = (Button) view.findViewById(R.id.enterTaskButton);
        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                saveTask(v);
                NavHostFragment.findNavController(AddTaskFragment.this).popBackStack();
            }
        });

        Button cancel = (Button) view.findViewById(R.id.task_cancel_button);
        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                NavHostFragment.findNavController(AddTaskFragment.this).popBackStack();
            }
        });
        return view;
    }

    public void saveTask(View view) {
        String title = String.valueOf(titleEditText.getText());
        int id = Task.taskArrayList.size();

        Task newTask = new Task(id, title);
        Task.taskArrayList.add(newTask);
        //add in return to previous view
    }
}
