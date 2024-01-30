package com.example.collegeschedulerapp.ui.assignments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavHost;
import androidx.navigation.fragment.NavHostFragment;

import com.example.collegeschedulerapp.R;
import com.example.collegeschedulerapp.ui.home.HomeFragment;

public class AssignmentsFragment extends Fragment {
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_assignments, container, false);
        //Button b = (Button) view.findViewById(R.id.scheduleButton);
        //b.setOnClickListener();
        //NavHostFragment.findNavController(AssignmentsFragment.this).navigate(R.id.nav_classes);
        Button b1 = (Button) view.findViewById(R.id.scheduleButton);

        EditText assignmentName = (EditText) view.findViewById(R.id.editAssignmentName);
        EditText className = (EditText) view.findViewById(R.id.editClassName);
        EditText dueDate = (EditText) view.findViewById(R.id.editDueDate);

        b1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Toast myToast = Toast.makeText(getActivity(), "hello world", Toast.LENGTH_SHORT);
                //myToast.show();
                Bundle assignmentDetails = new Bundle();
                assignmentDetails.putString("assignmentNameDetails", assignmentName.getText().toString());


                //Bundle classNameBundle = new Bundle();
                assignmentDetails.putString("classNameDetails", className.getText().toString());

                //Bundle dueDateBundle = new Bundle();
                assignmentDetails.putString("dueDateDetails", dueDate.getText().toString());

                NavHostFragment.findNavController(AssignmentsFragment.this).navigate(R.id.nav_assignments_to_nav_tasks, assignmentDetails);


            }
        });
        return view;
    }

}
