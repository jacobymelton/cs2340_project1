package com.example.collegeschedulerapp;

import androidx.annotation.NonNull;

public class Course {
    private String name;
    private String prof;
    private String time;

    public Course(String name, String prof, String time) {
        this.name = name;
        this.prof = prof;
        this.time = time;
    }

    public String getName() {
        return this.name;
    }

    @NonNull
    @Override
    public String toString() {
        return String.format("%s\nProfessor: %s\n%s", name, prof, time);
    }
}
