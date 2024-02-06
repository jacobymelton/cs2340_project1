package com.example.collegeschedulerapp.ui.assignments;

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
import com.example.collegeschedulerapp.databinding.FragmentAddAssignmentBinding;
import com.example.collegeschedulerapp.ui.assessments.AddAssessmentFragment;
import com.example.collegeschedulerapp.ui.assessments.AddAssessmentFragmentArgs;
import com.example.collegeschedulerapp.ui.assessments.AddAssessmentFragmentDirections;
import com.example.collegeschedulerapp.ui.assessments.Assessment;

public class AddAssignmentFragment extends Fragment {

    private FragmentAddAssignmentBinding binding;

    public AddAssignmentFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(
            LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState
    ) {

        binding = FragmentAddAssignmentBinding.inflate(inflater, container, false);
        return binding.getRoot();

    }

    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        Assignment in = AddAssignmentFragmentArgs.fromBundle(getArguments()).getAssignment();
        boolean toggle = AddAssignmentFragmentArgs.fromBundle(getArguments()).getToggle();
        if (in != null) {
            binding.assignmentDate.setText(in.getDate().toString());
            binding.assignmentCourse.setText(in.getCourse());
            binding.assignmentName.setText(in.getName());
        }

        binding.assignmentSaveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int pos = AddAssignmentFragmentArgs.fromBundle(getArguments()).getPos();
                String name = binding.assignmentName.getText().toString();
                String course = binding.assignmentCourse.getText().toString();
                String date = binding.assignmentDate.getText().toString();
                if (name.trim().equals("") || course.trim().equals("") || date.trim().equals("")) {
                    Toast.makeText(getActivity(), "An item is empty", Toast.LENGTH_SHORT).show();
                    AddAssignmentFragmentDirections.ActionNavAddAssignmentToNavAssignments action = AddAssignmentFragmentDirections.actionNavAddAssignmentToNavAssignments().setToggle(toggle);
                    NavHostFragment.findNavController(AddAssignmentFragment.this).navigate(action);
                } else if (!checkDate(date)) {
                    Toast.makeText(getActivity(), "Please enter due date in MM/DD/YY format.", Toast.LENGTH_SHORT).show();
                } else {
                    Assignment assignment = new Assignment(name, course, date);
                    binding.assignmentDate.setText("");
                    binding.assignmentCourse.setText("");
                    binding.assignmentName.setText("");
                    AddAssignmentFragmentDirections.ActionNavAddAssignmentToNavAssignments action = AddAssignmentFragmentDirections.actionNavAddAssignmentToNavAssignments().setAssignment(assignment).setPos(pos).setToggle(toggle);
                    NavHostFragment.findNavController(AddAssignmentFragment.this).navigate(action);
                }
            }
        });

        binding.assignmentCancelButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AddAssignmentFragmentDirections.ActionNavAddAssignmentToNavAssignments action = AddAssignmentFragmentDirections.actionNavAddAssignmentToNavAssignments().setToggle(toggle);
                NavHostFragment.findNavController(AddAssignmentFragment.this).navigate(action);
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