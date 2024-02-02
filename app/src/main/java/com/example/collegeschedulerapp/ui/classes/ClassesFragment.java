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
import android.view.GestureDetector;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.lang.reflect.Type;
import java.util.ArrayList;


import com.example.collegeschedulerapp.R;
import com.example.collegeschedulerapp.databinding.FragmentClassesBinding;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

public class ClassesFragment extends Fragment {

    private FragmentClassesBinding binding;
    private static final String SHARED_PREFS = "persist data";
    private static final String SHARED_PREFS_KEY = "list";
    ListView classList;
    ArrayList<Course> classes = new ArrayList<>();
    ClassAdapter listAdapter;
    Button addButton;



    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = FragmentClassesBinding.inflate(inflater, container, false);
        loadData();
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        addButton = (Button) view.findViewById(R.id.button_addClasses);
        classList = (ListView) view.findViewById(R.id.classList);
        listAdapter = new ClassAdapter(this.getContext(), classes);

        classList.setAdapter(listAdapter);
        listAdapter.notifyDataSetChanged();


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
                    Toast.makeText(getActivity(), "Class has already been added", Toast.LENGTH_SHORT).show();
                }

            }
            listAdapter.notifyDataSetChanged();
            saveData();
        }

        classList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int i, long id) {
                new AlertDialog.Builder(getActivity()).setTitle("Edit or remove class from the list?").setPositiveButton("Edit", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        ClassesFragmentDirections.ActionNavClassesToNavAddClass action = ClassesFragmentDirections.actionNavClassesToNavAddClass().setPos(i).setCourse(classes.get(i));
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
        SharedPreferences sp = getActivity().getSharedPreferences("shared preferences", MODE_PRIVATE);
        SharedPreferences.Editor editor = sp.edit();
        Gson gson = new Gson();
        String jsonString = gson.toJson(classes);
        editor.putString("classes list", jsonString);
        editor.apply();
    }

    private void loadData() {
        SharedPreferences sp = getActivity().getSharedPreferences("shared preferences", MODE_PRIVATE);
        Gson gson = new Gson();
        String json = sp.getString("classes list", null);
        Type type = new TypeToken<ArrayList<Course>>(){}.getType();
        classes = gson.fromJson(json, type);
        if (classes == null) {
            classes = new ArrayList<>();
        }
    }

}