package com.example.collegeschedulerapp.ui.classes;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.navigation.fragment.NavHostFragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.collegeschedulerapp.databinding.FragmentAddAssessmentBinding;
import com.example.collegeschedulerapp.databinding.FragmentAddClassBinding;
import com.example.collegeschedulerapp.ui.assessments.AddAssessmentFragment;
import com.example.collegeschedulerapp.ui.assessments.AddAssessmentFragmentArgs;
import com.example.collegeschedulerapp.ui.assessments.AddAssessmentFragmentDirections;
import com.example.collegeschedulerapp.ui.assessments.Assessment;


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
                int pos = AddAssessmentFragmentArgs.fromBundle(getArguments()).getPosition();
                String name = binding.className.getText().toString();
                String time = binding.classTime.getText().toString();
                String prof = binding.classProf.getText().toString();
                if (name.trim().equals("") || time.trim().equals("") || prof.trim().equals("")) {
                    Toast.makeText(getActivity(), "An item is empty", Toast.LENGTH_SHORT).show();
                    AddClassFragmentDirections.ActionNavAddClassToNavClasses action = AddClassFragmentDirections.actionNavAddClassToNavClasses();
                    NavHostFragment.findNavController(AddClassFragment.this).navigate(action);
                } else {
                    Course course = new Course(name, prof, time);
                    AddClassFragmentDirections.ActionNavAddClassToNavClasses action = AddClassFragmentDirections.actionNavAddClassToNavClasses().setCourse(course).setPos(pos);
                    NavHostFragment.findNavController(AddClassFragment.this).navigate(action);
                }
            }
        });

        binding.classCancelButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AddClassFragmentDirections.ActionNavAddClassToNavClasses action = AddClassFragmentDirections.actionNavAddClassToNavClasses();
                NavHostFragment.findNavController(AddClassFragment.this).navigate(action);
            }
        });

    }
    @Override
    public void onResume() {
        super.onResume();
        ((AppCompatActivity)getActivity()).getSupportActionBar().hide();
    }
    @Override
    public void onStop() {
        super.onStop();
        ((AppCompatActivity)getActivity()).getSupportActionBar().show();
    }

    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}