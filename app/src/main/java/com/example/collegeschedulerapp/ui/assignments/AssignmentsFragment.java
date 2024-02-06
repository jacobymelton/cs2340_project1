package com.example.collegeschedulerapp.ui.assignments;


import static android.content.Context.MODE_PRIVATE;
import android.content.SharedPreferences;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.fragment.NavHostFragment;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.ListView;
import android.widget.Toast;
import android.widget.ToggleButton;

import java.lang.reflect.Type;
import java.util.ArrayList;

import com.example.collegeschedulerapp.R;
import com.example.collegeschedulerapp.databinding.FragmentAssignmentsBinding;
import com.example.collegeschedulerapp.ui.Date;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

public class AssignmentsFragment extends Fragment {

    private FragmentAssignmentsBinding binding;

    ListView assignmentList;
    ArrayList<Assignment> assignments = new ArrayList<>();
    AssignmentAdapter listAdapter;
    Button addButton;
    ToggleButton sortToggle;
    boolean toggle;




    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = FragmentAssignmentsBinding.inflate(inflater, container, false);
        loadData();
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        addButton = (Button) view.findViewById(R.id.button_addAssignment);
        sortToggle = (ToggleButton) view.findViewById(R.id.toggle_sort);
        assignmentList = (ListView) view.findViewById(R.id.assignmentList);
        listAdapter = new AssignmentAdapter(this.getContext(), assignments);

        assignmentList.setAdapter(listAdapter);
        listAdapter.notifyDataSetChanged();

        toggle = AssignmentsFragmentArgs.fromBundle(getArguments()).getToggle();

        if (toggle) {
            sortToggle.setChecked(true);
        } else {
            sortToggle.setChecked(false);
        }



        Assignment assignment = AssignmentsFragmentArgs.fromBundle(getArguments()).getAssignment();
        int pos = AssignmentsFragmentArgs.fromBundle(getArguments()).getPos();

        if (assignment == null) {

        } else {
            if (assignments.contains(assignment)) {
                Toast.makeText(getActivity(), "Assignment has already been added", Toast.LENGTH_SHORT).show();
            } else {
                if (pos == assignments.size()) {
                    assignments.add(pos, assignment);
                } else {
                    assignments.remove(pos);
                    assignments.add(pos, assignment);
                }

            }
        }
        if (toggle) {
            sortByCourse();
        } else {
            sortByDate();
        }
        listAdapter.notifyDataSetChanged();
        saveData();

        assignmentList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int i, long id) {

                new AlertDialog.Builder(getActivity()).setTitle("Edit or remove assignment from the list?").setPositiveButton("Edit", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        AssignmentsFragmentDirections.ActionNavAssignmentsToNavAddAssignment action = AssignmentsFragmentDirections.actionNavAssignmentsToNavAddAssignment().setAssignment(assignments.get(i)).setPos(i).setToggle(toggle);
                        NavHostFragment.findNavController(AssignmentsFragment.this).navigate(action);
                    }

                }).setNegativeButton("Remove", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        assignments.remove(i);
                        listAdapter.notifyDataSetChanged();
                        saveData();
                    }
                }).setNeutralButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                }).create().show();
            }
        });
        addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AssignmentsFragmentDirections.ActionNavAssignmentsToNavAddAssignment action = AssignmentsFragmentDirections.actionNavAssignmentsToNavAddAssignment().setPos(assignments.size()).setToggle(toggle);
                NavHostFragment.findNavController(AssignmentsFragment.this).navigate(action);
            }
        });

        sortToggle.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    toggle = true;
                    sortByCourse();
                } else {
                    toggle = false;
                    sortByDate();
                }
                listAdapter.notifyDataSetChanged();
                saveData();
            }
        });
    }



    private void saveData() {
        SharedPreferences sp = getActivity().getSharedPreferences("shared preferences", MODE_PRIVATE);
        SharedPreferences.Editor editor = sp.edit();
        Gson gson = new Gson();
        String json = gson.toJson(assignments);
        editor.putString("assignment list", json);
        editor.apply();
    }

    private void loadData() {
        SharedPreferences sp = getActivity().getApplicationContext().getSharedPreferences("shared preferences", MODE_PRIVATE);
        Gson gson = new Gson();
        String json = sp.getString("assignment list", null);
        Type type = new TypeToken<ArrayList<Assignment>>() {}.getType();
        assignments = gson.fromJson(json, type);

        if (assignments == null) {
            assignments = new ArrayList<>();
        }
    }


    private void sortByDate() {
        int n = assignments.size();
        for (int i = 0; i < n - 1; i++) {
            int min = i;
            for (int j = i + 1; j < n; j++) {
                Date date1 = assignments.get(j).getDate();
                Date date2 = assignments.get(min).getDate();
                if (date1.compareTo(date2) < 0) {
                    min = j;
                }
            }
            Assignment temp = assignments.get(min);
            assignments.set(min, assignments.get(i));
            assignments.set(i, temp);
        }
    }

    private void sortByCourse() {
        int n = assignments.size();
        for (int i = 0; i < n - 1; i++) {
            int min = i;
            for (int j = i + 1; j < n; j++) {
                String course1 = assignments.get(j).getCourse();
                String course2 = assignments.get(min).getCourse();
                if (course1.compareToIgnoreCase(course2) < 0) {
                    min = j;
                }
            }
            Assignment temp = assignments.get(min);
            assignments.set(min, assignments.get(i));
            assignments.set(i, temp);
        }

    }





}