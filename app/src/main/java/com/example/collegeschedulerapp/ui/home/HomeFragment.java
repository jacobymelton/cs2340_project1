package com.example.collegeschedulerapp.ui.home;

import static android.content.Context.MODE_PRIVATE;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.example.collegeschedulerapp.R;
import com.example.collegeschedulerapp.databinding.FragmentHomeBinding;
import com.example.collegeschedulerapp.ui.assessments.Assessment;
import com.example.collegeschedulerapp.ui.assessments.AssessmentAdapter;
import com.example.collegeschedulerapp.ui.assessments.AssessmentsFragment;
import com.example.collegeschedulerapp.ui.assignments.Assignment;
import com.example.collegeschedulerapp.ui.assignments.AssignmentAdapter;
import com.example.collegeschedulerapp.ui.classes.ClassAdapter;
import com.example.collegeschedulerapp.ui.classes.Course;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import com.example.collegeschedulerapp.ui.Date;

public class HomeFragment extends Fragment {

    private FragmentHomeBinding binding;
    ArrayAdapter<Object> listAdapter;
    ArrayList<Object> todo;
    ListView todoList;
    SimpleDateFormat sdf = new SimpleDateFormat("MM-dd-yy");
    String date = sdf.format(new java.util.Date());
    SimpleDateFormat dow = new SimpleDateFormat("E");
    String day = dow.format(new java.util.Date());



    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        HomeViewModel homeViewModel =
                new ViewModelProvider(this).get(HomeViewModel.class);

        binding = FragmentHomeBinding.inflate(inflater, container, false);
        View root = binding.getRoot();


        /*
        binding.classButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getActivity(), ClassesActivity.class);
                startActivity(i);
            }
        });

        binding.assessmentButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                NavHostFragment.findNavController(HomeFragment.this)
                        .navigate(R.id.action_nav_home_to_nav_assessments);
            }
        });

         */

        final TextView textView = binding.textHome;
        homeViewModel.getText().observe(getViewLifecycleOwner(), textView::setText);
        return root;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        todoList = (ListView) view.findViewById(R.id.home_list);
        todo = new ArrayList<>();
        loadData();
        listAdapter = new ArrayAdapter<>(getContext(), android.R.layout.simple_list_item_1, todo);
        todoList.setAdapter(listAdapter);
        listAdapter.notifyDataSetChanged();


    }

    private void loadData() {
        while (date.contains("-")) {
            date = date.replace('-', '/');
        }
        Date todayDate = new Date(date);
        String todayDay = day.substring(0, 2).toLowerCase();
        SharedPreferences sp = getActivity().getApplicationContext().getSharedPreferences("shared preferences", MODE_PRIVATE);
        Gson gson = new Gson();

        String json = sp.getString("assignment list", null);
        Type type = new TypeToken<ArrayList<Assignment>>() {}.getType();
        ArrayList<Object> assignments = gson.fromJson(json, type);
        if (assignments == null) {
            assignments = new ArrayList<>();
        }

        for (int i = 0; i < assignments.size(); i++) {
            Assignment obj = (Assignment) assignments.get(i);
            if (obj.getDate().compareTo(todayDate) == 0) {
                if (!(todo.contains(assignments.get(i)))) {
                    todo.add(assignments.get(i));
                }
            }
        }

        json = sp.getString("classes list", null);
        type = new TypeToken<ArrayList<Course>>() {}.getType();
        ArrayList<Object> courses = gson.fromJson(json, type);
        if (courses == null) {
            courses = new ArrayList<>();
        }
        for (int i = 0; i < courses.size(); i++) {
            if (!(todo.contains(courses.get(i)))) {
                ArrayList<String> daysOfWeek = new ArrayList<>();
                String dayOfWeek = ((Course) courses.get(i)).getDays();
                boolean last = false;
                while (!last) {
                    if (!dayOfWeek.contains(",")) {
                        last = true;
                        daysOfWeek.add(dayOfWeek.substring(0, 2).toLowerCase());
                    } else {
                        String d = dayOfWeek.substring(0, dayOfWeek.indexOf(","));
                        daysOfWeek.add(d.substring(0, 2).toLowerCase());
                        dayOfWeek = dayOfWeek.substring(dayOfWeek.indexOf(",") + 1);
                    }
                }
                if (daysOfWeek.contains(todayDay)) {
                    todo.add(courses.get(i));
                }
            }
        }

        json = sp.getString("assessment list", null);
        type = new TypeToken<ArrayList<Assessment>>() {}.getType();
        ArrayList<Object> assessments = gson.fromJson(json, type);

        if (assessments == null) {
            assessments = new ArrayList<>();
        }
        for (int i = 0; i < assessments.size(); i++) {
            Assessment obj = (Assessment) assessments.get(i);
            if (obj.getDate().compareTo(todayDate) == 0) {
                if (!(todo.contains(assessments.get(i)))) {
                    todo.add(assessments.get(i));
                }
            }
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}