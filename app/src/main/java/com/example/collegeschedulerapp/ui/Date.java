package com.example.collegeschedulerapp.ui;

public class Date implements Comparable<Date> {
    int month;
    int day;
    int year;

    public Date(String date) {
        date = date.trim();
        this.month = Integer.parseInt(date.substring(0, date.indexOf('/')));
        date = date.substring(date.indexOf('/') + 1);
        this.day = Integer.parseInt(date.substring(0, date.indexOf('/')));
        this.year = Integer.parseInt(date.substring(date.indexOf('/') + 1));
    }

    public String toString() {
        return String.format("%02d/%02d/%02d", month, day, year);
    }

    public int compareTo(Date d) {
        if (d == null) {
            return 1;
        }
        if (this.year > d.year) {
            return 1;
        } else if (this.year < d.year) {
            return -1;
        } else if (this.month > d.month) {
            return 1;
        } else if (this.month < d.month) {
            return -1;
        } else if (this.day > d.day) {
            return 1;
        } else if (this.day < d.day) {
            return -1;
        } else {
            return 0;
        }
    }

}
