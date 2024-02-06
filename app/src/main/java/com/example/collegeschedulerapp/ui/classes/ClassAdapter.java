package com.example.collegeschedulerapp.ui.classes;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.example.collegeschedulerapp.R;

import org.w3c.dom.Text;

import java.util.ArrayList;

public class ClassAdapter extends ArrayAdapter<Course> {

    public ClassAdapter(Context context, ArrayList<Course> classes) {
        super(context, 0, classes);
    }

    public View getView(int pos, View convertView, ViewGroup parent) {
        Course course = getItem(pos);

        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.classlist_view, parent, false);
        }

        TextView className = (TextView) convertView.findViewById(R.id.className);
        TextView classTime = (TextView) convertView.findViewById(R.id.classTime);
        TextView classProf = (TextView) convertView.findViewById(R.id.classProf);
        TextView classDays = (TextView) convertView.findViewById(R.id.classDays);

        className.setText(course.getName());
        classTime.setText(course.getTime());
        classProf.setText(course.getProf());
        classDays.setText(course.getDays());

        return convertView;
    }
}
