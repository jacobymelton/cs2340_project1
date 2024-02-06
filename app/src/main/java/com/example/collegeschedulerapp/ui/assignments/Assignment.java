package com.example.collegeschedulerapp.ui.assignments;

import android.os.Parcel;
import android.os.Parcelable;

import androidx.annotation.NonNull;

import com.example.collegeschedulerapp.ui.Date;

import kotlinx.parcelize.Parcelize;

//@Parcelize
public class Assignment implements Parcelable {
    private String name;
    private String date;
    private String course;



    public Assignment(String name, String course, String date) {
        this.name = name;
        this.course = course;
        this.date = date;
    }

    protected Assignment(Parcel in) {
        name = in.readString();
        course = in.readString();
        date = in.readString();
    }

    public Date getDate() {
        return new Date(date);
    }

    public String getName() {
        return name;
    }

    public String getCourse() {
        return course;
    }

    @NonNull
    @Override
    public String toString() {
        return String.format("%s\nClass: %s\n%s", name, course, date);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(@NonNull Parcel dest, int flags) {
        dest.writeString(name);
        dest.writeString(course);
        dest.writeString(date);
    }
}
