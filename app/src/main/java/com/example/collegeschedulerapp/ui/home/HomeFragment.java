package com.example.collegeschedulerapp.ui.home;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.fragment.NavHostFragment;

import com.example.collegeschedulerapp.AssignmentsActivity;
import com.example.collegeschedulerapp.AssignmentsActivity;
import com.example.collegeschedulerapp.ClassesActivity;
import com.example.collegeschedulerapp.R;
import com.example.collegeschedulerapp.databinding.FragmentHomeBinding;

import android.content.Context;
import android.widget.Toast;

public class HomeFragment extends Fragment {

    private FragmentHomeBinding binding;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        HomeViewModel homeViewModel =
                new ViewModelProvider(this).get(HomeViewModel.class);

        binding = FragmentHomeBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        binding.classButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getActivity(), ClassesActivity.class);
                startActivity(i);
            }
        });

        binding.assignmentButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getActivity(), AssignmentsActivity.class);
                startActivity(i);
                //NavHostFragment.findNavController(HomeFragment.this)
                  //      .navigate(R.id.navigation_home_to_navigation_assignments);
                //Toast toast = Toast.makeText(getActivity().getApplicationContext(), "hello world", Toast.LENGTH_SHORT);
                //toast.show();
            }
        });



        final TextView textView = binding.textHome;
        homeViewModel.getText().observe(getViewLifecycleOwner(), textView::setText);
        return root;
    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}