package com.example.collegeschedulerapp.ui.tasks;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.fragment.NavHostFragment;

import com.example.collegeschedulerapp.R;
import com.example.collegeschedulerapp.TaskActivity;
import com.example.collegeschedulerapp.ui.classes.AddClassFragment;

import java.util.EventListener;

public class TasksFragment extends Fragment {

    private ListView taskListView;

    public void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        loadDatabase();

    }
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_tasks_list, container, false);


        initWidgets(view);

        //create database
        //loadDatabase();
        setOnClickListener();
        setTaskAdapter();


        Button addTask = (Button) view.findViewById(R.id.addTaskButton);
        addTask.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                NavHostFragment.findNavController(TasksFragment.this).navigate(R.id.action_TaskList_to_AddTask);
                //Toast.makeText(getContext(), "hello world", Toast.LENGTH_SHORT).show();
            }
        });
        return view;
    }

    private void loadDatabase() {
        SQLiteManager sqLiteManager = SQLiteManager.instanceOfDatabase(getContext());
        sqLiteManager.populateTaskListArray();
    }

    private void setOnClickListener()
    {
        taskListView.setOnItemClickListener(new AdapterView.OnItemClickListener()
        {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long l)
            {
                Task selectedNote = (Task) taskListView.getItemAtPosition(position);

                Bundle bundle = new Bundle();
                bundle.putInt("id", selectedNote.getId());
                NavHostFragment.findNavController(TasksFragment.this).navigate(R.id.action_TaskList_to_AddTask, bundle);

                //Intent editNoteIntent = new Intent(getApplicationContext(), NoteDetailActivity.class);
                //editNoteIntent.putExtra(Note.NOTE_EDIT_EXTRA, selectedNote.getId());
                //startActivity(editNoteIntent);
            }
        });
    }

    private void setTaskAdapter() {
        //setting our list view to the adapter with a list of tasks
        TasksListAdapter tasksListAdapter = new TasksListAdapter(getContext(),Task.nonDeletedTasks());
        taskListView.setAdapter(tasksListAdapter);
    }

    private void initWidgets(View view) {
        //creating functionality for task list view
        taskListView = view.findViewById(R.id.taskListView);
    }




   // public void newTask(View view) {
     //   //Toast.makeText(getContext(), "hello world", Toast.LENGTH_SHORT).show();
    //}
}


