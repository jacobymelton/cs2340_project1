package com.example.collegeschedulerapp.ui;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.navigation.fragment.NavHostFragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.collegeschedulerapp.ClassesActivity;
import com.example.collegeschedulerapp.Course;
import com.example.collegeschedulerapp.R;
import com.example.collegeschedulerapp.databinding.FragmentAddClassBinding;

public class AddClassFragment extends Fragment {

    private FragmentAddClassBinding binding;

    public AddClassFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(
            LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState
    ) {

        binding = FragmentAddClassBinding.inflate(inflater, container, false);
        return binding.getRoot();

    }

    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        binding.classSaveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String name = binding.className.getText().toString();
                String prof = binding.classProf.getText().toString();
                String time = binding.classTime.getText().toString();
                if (name.trim().equals("") || prof.trim().equals("") || time.trim().equals("")) {
                    Toast.makeText(getActivity(), "Item is empty", Toast.LENGTH_SHORT).show();
                    ((ClassesActivity) getActivity()).addToClasses(null);
                } else {
                    Course course = new Course(name, prof, time);
                    ((ClassesActivity) getActivity()).addToClasses(course);
                    binding.className.setText("");
                    binding.classProf.setText("");
                    binding.classTime.setText("");
                }
                getFragmentManager().beginTransaction().remove(AddClassFragment.this).commit();


            }
        });

        binding.classCancelButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ((ClassesActivity) getActivity()).addToClasses(null);
                getFragmentManager().beginTransaction().remove(AddClassFragment.this).commit();
            }
        });

    }

    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}