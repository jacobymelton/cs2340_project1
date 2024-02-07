package com.example.collegeschedulerapp.ui.assessments;

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

public class AddAssessmentFragment extends Fragment {

    private FragmentAddAssessmentBinding binding;

    public AddAssessmentFragment() {
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

        Assessment in = AddAssessmentFragmentArgs.fromBundle(getArguments()).getAssessment();
        if (in != null) {
            binding.assessmentDate.setText(in.getDate().toString());
            binding.assessmentCourse.setText(in.getCourse());
            binding.assessmentName.setText(in.getName());
            binding.assessmentLocation.setText(in.getLocation());
            binding.assessmentTime.setText(in.getTime());
        }

        binding.assessmentSaveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int pos = AddAssessmentFragmentArgs.fromBundle(getArguments()).getPosition();
                String name = binding.assessmentName.getText().toString();
                String course = binding.assessmentCourse.getText().toString();
                String date = binding.assessmentDate.getText().toString();
                String time = binding.assessmentTime.getText().toString();
                String location = binding.assessmentLocation.getText().toString();
                if (name.trim().equals("") || course.trim().equals("") || date.trim().equals("") || time.trim().equals("") || location.trim().equals("")) {
                    Toast.makeText(getActivity(), "An item is empty", Toast.LENGTH_SHORT).show();
                    AddAssessmentFragmentDirections.ActionAddAssessmentToAssessments action = AddAssessmentFragmentDirections.actionAddAssessmentToAssessments();
                    NavHostFragment.findNavController(AddAssessmentFragment.this).navigate(action);
                } else if (!checkDate(date)) {
                    Toast.makeText(getActivity(), "Please enter date in MM/DD/YY format.", Toast.LENGTH_SHORT).show();
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

    // hide and show action bar tutorial: https://www.geeksforgeeks.org/different-ways-to-hide-action-bar-in-android-with-examples/
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

    public boolean checkDate(String date) {
        if (!(date.contains("/"))) {
            return false;
        } else if (date.substring(0, date.indexOf('/')).length() > 2) {
            return false;
        }
        date = date.substring(date.indexOf('/') + 1);
        if (date.substring(0, date.indexOf('/')).length() > 2) {
            return false;
        } else if (date.substring(date.indexOf('/') + 1).length() > 2) {
            return false;
        }
        return true;
    }

    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}