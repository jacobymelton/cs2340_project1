package com.example.collegeschedulerapp.ui.tasks;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.fragment.NavHostFragment;

import com.example.collegeschedulerapp.R;

public class TasksFragment extends Fragment {

    private ListView taskListView;

    public void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        Task.taskArrayList.clear();
        loadDatabase();

    }
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_tasks_list, container, false);


        initalizeWidgets(view);
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
        //load database into list array
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
                Task selectedTask = (Task) taskListView.getItemAtPosition(position);

                Bundle IDBundle = new Bundle();
                IDBundle.putInt("id", selectedTask.getId());
                NavHostFragment.findNavController(TasksFragment.this).navigate(R.id.action_TaskList_to_AddTask, IDBundle);

            }
        });
    }

    private void setTaskAdapter() {
        //setting our list view to the adapter with a list of tasks
        TasksListAdapter tasksListAdapter = new TasksListAdapter(getContext(),Task.nonDeletedTasks());
        taskListView.setAdapter(tasksListAdapter);
    }

    private void initalizeWidgets(View view) {
        //creating functionality for task list view
        taskListView = view.findViewById(R.id.taskListView);
    }


}


