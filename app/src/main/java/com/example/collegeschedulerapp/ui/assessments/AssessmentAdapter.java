package com.example.collegeschedulerapp.ui.assessments;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.example.collegeschedulerapp.R;
import com.example.collegeschedulerapp.ui.classes.Course;

import org.w3c.dom.Text;

import java.util.ArrayList;

// Custom ArrayAdapter tutorial: https://www.geeksforgeeks.org/custom-arrayadapter-with-listview-in-android/

public class AssessmentAdapter extends ArrayAdapter<Assessment> {

    public AssessmentAdapter(Context context, ArrayList<Assessment> assessments) {
        super(context, 0, assessments);
    }

    public View getView(int pos, View convertView, ViewGroup parent) {
        Assessment assessment = getItem(pos);

        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.assessmentlist_view, parent, false);
        }

        TextView assessmentName = (TextView) convertView.findViewById(R.id.assessmentName);
        TextView assessmentTime = (TextView) convertView.findViewById(R.id.assessmentTime);
        TextView assessmentLocation = (TextView) convertView.findViewById(R.id.assessmentLocation);
        TextView assessmentCourse = (TextView) convertView.findViewById(R.id.assessmentCourse);
        TextView assessmentDate = (TextView) convertView.findViewById(R.id.assessmentDate);

        assessmentName.setText(assessment.getName());
        assessmentTime.setText(assessment.getTime());
        assessmentDate.setText(assessment.getDate().toString());
        assessmentLocation.setText(assessment.getLocation());
        assessmentCourse.setText(assessment.getCourse());

        return convertView;
    }
}
