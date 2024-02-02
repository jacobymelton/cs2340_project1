package com.example.collegeschedulerapp.ui.classes;


import static android.content.Context.MODE_PRIVATE;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.fragment.NavHostFragment;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import java.lang.reflect.Type;
import java.util.ArrayList;


import com.example.collegeschedulerapp.R;
import com.example.collegeschedulerapp.databinding.FragmentClassesBinding;
import com.example.collegeschedulerapp.ui.classes.ClassesFragmentDirections;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

public class ClassesFragment extends Fragment {

    private FragmentClassesBinding binding;
    private static final String SHARED_PREFS = "persist data";
    private static final String SHARED_PREFS_KEY = "list";
    ListView classList;
    ArrayList<Course> classes = new ArrayList<>();
    ArrayAdapter<Course> listAdapter;
    Button addButton;



    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = FragmentClassesBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        addButton = (Button) view.findViewById(R.id.button_addClasses);
        classList = (ListView) view.findViewById(R.id.classList);
        listAdapter = new ArrayAdapter<>(getActivity(), android.R.layout.simple_list_item_1, classes);

        classList.setAdapter(listAdapter);
        listAdapter.notifyDataSetChanged();
        loadData();



        Course course = ClassesFragmentArgs.fromBundle(getArguments()).getCourse();
        int pos = ClassesFragmentArgs.fromBundle(getArguments()).getPos();

        if (course != null) {
            if (classes.contains(course)) {
                Toast.makeText(getActivity(), "Class has already been added", Toast.LENGTH_SHORT).show();
            } else {
                if (pos == classes.size()) {
                    classes.add(pos, course);
                } else {
                    classes.remove(pos);
                    classes.add(pos, course);
                }

            }
            listAdapter.notifyDataSetChanged();
            saveData();
        }

        classList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int i, long id) {
                new AlertDialog.Builder(getActivity()).setTitle("Edit or remove " + classes.get(i) + " from the list?").setPositiveButton("Edit", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        ClassesFragmentDirections.ActionNavClassesToNavAddClass action = ClassesFragmentDirections.actionNavClassesToNavAddClass().setPos(i);
                        NavHostFragment.findNavController(ClassesFragment.this).navigate(action);
                    }

                }).setNegativeButton("Remove", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        classes.remove(i);
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
                ClassesFragmentDirections.ActionNavClassesToNavAddClass action = ClassesFragmentDirections.actionNavClassesToNavAddClass().setPos(classes.size());
                NavHostFragment.findNavController(ClassesFragment.this).navigate(action);
            }
        });
    }


    private void saveData() {
        SharedPreferences sp = getActivity().getSharedPreferences(SHARED_PREFS, MODE_PRIVATE);
        SharedPreferences.Editor editor = sp.edit();
        Gson gson = new Gson();
        String jsonString = gson.toJson(classes);
        editor.putString(SHARED_PREFS_KEY, jsonString);
        editor.apply();
    }

    private void loadData() {
        SharedPreferences sp = getActivity().getSharedPreferences(SHARED_PREFS, MODE_PRIVATE);
        Gson gson = new Gson();
        String json = sp.getString(SHARED_PREFS_KEY, "");

        if (json.isEmpty()) {
            return;
        }

        Type type = new TypeToken<ArrayList<Course>>(){}.getType();
        classes.addAll((gson.fromJson(json, type)));
    }

}