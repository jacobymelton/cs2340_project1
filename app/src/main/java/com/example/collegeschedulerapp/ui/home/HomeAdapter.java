package com.example.collegeschedulerapp.ui.home;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.example.collegeschedulerapp.R;
import com.example.collegeschedulerapp.ui.assessments.Assessment;
import com.example.collegeschedulerapp.ui.assignments.Assignment;
import com.example.collegeschedulerapp.ui.classes.Course;

import org.w3c.dom.Text;

import java.util.ArrayList;

public class HomeAdapter extends ArrayAdapter<Object> {

    public HomeAdapter(Context context, ArrayList<Object> todos) {
        super(context, 0, todos);
    }

    public View getView(int pos, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.homelist_view, parent, false);
        }
        TextView name = (TextView) convertView.findViewById(R.id.homeName);
        TextView time = (TextView) convertView.findViewById(R.id.homeTime);
        TextView location = (TextView) convertView.findViewById(R.id.homeLocation);
        TextView course = (TextView) convertView.findViewById(R.id.homeCourse);

        if (getItem(pos) instanceof Course) {
            Course classes = (Course) getItem(pos);
            name.setText(classes.getTime());
            time.setText("");
            course.setText(classes.getName());
            location.setText("");
        } else if (getItem(pos) instanceof Assignment) {
            Assignment assignment = (Assignment) getItem(pos);
            name.setText(assignment.getName());
            course.setText(assignment.getCourse());
            time.setText("");
            location.setText("");
        } else {
            Assessment assessment = (Assessment) getItem(pos);
            name.setText(assessment.getName());
            course.setText(assessment.getCourse());
            time.setText(assessment.getTime());
            location.setText(assessment.getLocation());
        }

        return convertView;
    }
}
