package com.example.collegeschedulerapp.ui.classes;

import android.os.Parcel;
import android.os.Parcelable;

import androidx.annotation.NonNull;

public class Course implements Parcelable {
    private String name;
    private String prof;
    private String time;
    private String days;

    public Course(String name, String prof, String time, String days) {
        this.name = name;
        this.prof = prof;
        this.time = time;
        this.days = days;
    }

    public Course(Parcel in) {
        name = in.readString();
        prof = in.readString();
        time = in.readString();
        days = in.readString();
    }

    public String getName() {
        return this.name;
    }

    public String getTime() {
        return time;
    }

    public String getProf() {
        return prof;
    }

    public String getDays() {
        return days;
    }

    @NonNull
    @Override
    public String toString() {
        return String.format("%s\nProfessor: %s\t\t%s", name, prof, time);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(@NonNull Parcel dest, int flags) {
        dest.writeString(name);
        dest.writeString(prof);
        dest.writeString(time);
        dest.writeString(days);
    }
}
