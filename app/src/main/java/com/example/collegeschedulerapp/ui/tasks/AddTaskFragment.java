package com.example.collegeschedulerapp.ui.tasks;

import android.content.Intent;
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

import java.util.Date;

public class AddTaskFragment extends Fragment {


    private EditText titleEditText;
    private Task selectedTask;

    private Button deleteButton;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_tasks, container, false);

        titleEditText = (EditText) view.findViewById(R.id.editTaskName);
        deleteButton = (Button) view.findViewById(R.id.task_delete_button);
        Button add = (Button) view.findViewById(R.id.enterTaskButton);
        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                saveTask(v);


            }
        });

        Button cancel = (Button) view.findViewById(R.id.task_cancel_button);
        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                NavHostFragment.findNavController(AddTaskFragment.this).popBackStack();
            }
        });


        deleteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                deleteNote(v);
                //NavHostFragment.findNavController(AddTaskFragment.this).popBackStack();
            }
        });

       checkForEditNote();



        return view;
    }

    private void checkForEditNote()
    {
        if (getArguments() != null) {
            int editTaskID = getArguments().getInt("id");
            selectedTask = Task.getTaskFromID(editTaskID);

            if (selectedTask != null)
            {
                titleEditText.setText(selectedTask.getTitle());
                //selectedTask.setText(selectedNote.getDescription());
            }
            else
            {
                deleteButton.setVisibility(View.INVISIBLE);
            }
        } else {
            deleteButton.setVisibility(View.INVISIBLE);
        }
    }

    public void saveTask(View view) {
        SQLiteManager sqLiteManager = SQLiteManager.instanceOfDatabase(getContext());

        String title = String.valueOf(titleEditText.getText());

        //create new task
        if(selectedTask == null)
        {

            int id = Task.taskArrayList.size();
            Task newTask = new Task(id, title);
            Task.taskArrayList.add(newTask);
            sqLiteManager.addTaskToDatabase(newTask);
        }
        //edit existing task
        else
        {
            selectedTask.setTitle(title);
            //selectedNote.setDescription(desc);
            sqLiteManager.updateTaskInDB(selectedTask);
        }


        NavHostFragment.findNavController(AddTaskFragment.this).popBackStack();
        //add in return to previous view
    }

    public void deleteNote(View view) {
        selectedTask.setDeleted(new Date());
        SQLiteManager sqLiteManager = SQLiteManager.instanceOfDatabase(getContext());
        sqLiteManager.updateTaskInDB(selectedTask);
        NavHostFragment.findNavController(AddTaskFragment.this).popBackStack();

    }
}
