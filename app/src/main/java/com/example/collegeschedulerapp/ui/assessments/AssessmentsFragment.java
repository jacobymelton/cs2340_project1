package com.example.collegeschedulerapp.ui.assessments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.fragment.NavHostFragment;

import com.example.collegeschedulerapp.R;
import com.example.collegeschedulerapp.databinding.FragmentAssessmentsBinding;
import com.example.collegeschedulerapp.ui.home.HomeFragment;

public class AssessmentsFragment extends Fragment {

    private FragmentAssessmentsBinding binding;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_assessments, container, false);
        /*binding = FragmentAssessmentsBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        Button b1 = (Button) root.findViewById(R.id.scheduleButton);
        b1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast myToast = Toast.makeText(getActivity(), "hello world", Toast.LENGTH_SHORT);
                myToast.show();

                //Bundle bundle = new Bundle();
                //bundle.putString("amount", editV.getText().toString());


                //NavHostFragment.findNavController(HomeFragment.this)
                  //      .navigate(R.id.navigation_home_to_navigation_dashboard, bundle);




            }
        });*/
        return view;
    }
}
