package com.example.collegeschedulerapp.ui.assessments;

import android.content.Intent;
import android.os.Bundle;

import androidx.activity.OnBackPressedCallback;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
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
import com.example.collegeschedulerapp.MainActivity;
import com.example.collegeschedulerapp.R;
import com.example.collegeschedulerapp.databinding.FragmentAddAssessmentBinding;
import com.example.collegeschedulerapp.databinding.FragmentAddClassBinding;
import com.example.collegeschedulerapp.ui.AddClassFragment;

public class AddAssessmentFragment extends Fragment {

    private FragmentAddAssessmentBinding binding;

    public AddAssessmentFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(
            LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState
    ) {

        binding = FragmentAddAssessmentBinding.inflate(inflater, container, false);
        return binding.getRoot();

    }

    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        binding.assessmentSaveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int pos = AddAssessmentFragmentArgs.fromBundle(getArguments()).getPosition();
                String name = binding.assessmentName.getText().toString();
                String course = binding.assessmentCourse.getText().toString();
                String date = binding.assessmentDate.getText().toString();
                String time = binding.assessmentTime.getText().toString();
                String location = binding.assessmentLocation.getText().toString();
                if (name.trim().equals("") || course.trim().equals("") || date.trim().equals("")) {
                    Toast.makeText(getActivity(), "An item is empty", Toast.LENGTH_SHORT).show();
                    AddAssessmentFragmentDirections.ActionAddAssessmentToAssessments action = AddAssessmentFragmentDirections.actionAddAssessmentToAssessments();
                    NavHostFragment.findNavController(AddAssessmentFragment.this).navigate(action);
                } else {
                    Assessment assessment = new Assessment(name, course, date, time, location);
                    binding.assessmentName.setText("");
                    binding.assessmentCourse.setText("");
                    binding.assessmentDate.setText("");
                    AddAssessmentFragmentDirections.ActionAddAssessmentToAssessments action = AddAssessmentFragmentDirections.actionAddAssessmentToAssessments().setAssessment(assessment).setPos(pos);
                    NavHostFragment.findNavController(AddAssessmentFragment.this).navigate(action);
                }
            }
        });

        binding.assessmentCancelButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AddAssessmentFragmentDirections.ActionAddAssessmentToAssessments action = AddAssessmentFragmentDirections.actionAddAssessmentToAssessments();
                NavHostFragment.findNavController(AddAssessmentFragment.this).navigate(action);
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