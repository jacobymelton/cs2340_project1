package com.example.collegeschedulerapp.ui.assignments;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.example.collegeschedulerapp.R;
import com.example.collegeschedulerapp.ui.assessments.Assessment;
import com.example.collegeschedulerapp.ui.classes.Course;

import org.w3c.dom.Text;

import java.util.ArrayList;

public class AssignmentAdapter extends ArrayAdapter<Assignment> {

    public AssignmentAdapter(Context context, ArrayList<Assignment> assignments) {
        super(context, 0, assignments);
    }

    public View getView(int pos, View convertView, ViewGroup parent) {
        Assignment assignment = getItem(pos);

        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.assignmentlist_view, parent, false);
        }

        TextView assignmentName = (TextView) convertView.findViewById(R.id.assignmentName);
        TextView assignmentCourse = (TextView) convertView.findViewById(R.id.assignmentCourse);
        TextView assignmentDate = (TextView) convertView.findViewById(R.id.assignmentDate);

        assignmentName.setText(assignment.getName());
        assignmentDate.setText(assignment.getDate().toString());
        assignmentCourse.setText(assignment.getCourse());

        return convertView;
    }
}
