package com.example.collegeschedulerapp.ui.classes;

import android.os.Parcel;
import android.os.Parcelable;

import androidx.annotation.NonNull;

public class Course implements Parcelable {
    private String name;
    private String prof;
    private String time;

    public Course(String name, String prof, String time) {
        this.name = name;
        this.prof = prof;
        this.time = time;
    }

    public Course(Parcel in) {
        name = in.readString();
        prof = in.readString();
        time = in.readString();
    }

    public String getName() {
        return this.name;
    }

    @NonNull
    @Override
    public String toString() {
        return String.format("%s\nProfessor: %s\n%s", name, prof, time);
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
    }
}
