package com.example.collegeschedulerapp.ui.assessments;

import android.os.Parcel;
import android.os.Parcelable;

import androidx.annotation.NonNull;

import com.example.collegeschedulerapp.ui.Date;

import kotlinx.parcelize.Parcelize;

//@Parcelize
// tutorial to make class Parcelable: https://medium.com/techmacademy/how-to-implement-and-use-a-parcelable-class-in-android-part-1-28cca73fc2d1
public class Assessment implements Parcelable {
    private String course;
    private String date;
    private String time;
    private String location;
    private String name;

    public Assessment(String name, String course, String date, String time, String location) {
        this.name = name;
        this.course = course;
        this.time = time;
        this.date = date;
        this.location = location;
    }

    protected Assessment(Parcel in) {
        name = in.readString();
        course = in.readString();
        date = in.readString();
        time = in.readString();
        location = in.readString();
    }

    public Date getDate() {
        return new Date(date);
    }

    public String getName() {
        return name;
    }

    public String getLocation() {
        return location;
    }

    public String getTime() {
        return time;
    }

    public String getCourse() {
        return course;
    }

    @NonNull
    @Override
    public String toString() {
        return String.format("%s\t\t%s\n%s\t\t%s", name, course, location, time);
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
        dest.writeString(time);
        dest.writeString(location);
    }
}
