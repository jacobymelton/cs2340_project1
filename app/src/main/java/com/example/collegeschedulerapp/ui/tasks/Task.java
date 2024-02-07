package com.example.collegeschedulerapp.ui.tasks;

import java.util.ArrayList;
import java.util.Date;

public class Task {

    public static ArrayList<Task> taskArrayList = new ArrayList<>();
    private int id;
    private String title;
    private Date deleted;



    public Task(int id, String title, Date deleted) {
        this.id = id;
        this.title = title;
        this.deleted = deleted;
    }

    public Task(int id, String title) {
        this(id, title, null);
    }

    public static Task getTaskFromID(int editTaskID) {
        for (Task task : taskArrayList)
        {
            if(task.getId() == editTaskID)
                return task;
        }

        return null;
    }

    public static ArrayList<Task> nonDeletedTasks()
    {
        ArrayList<Task> nonDeleted = new ArrayList<>();
        for(Task task : taskArrayList)
        {
            if(task.getDeleted() == null)
                nonDeleted.add(task);
        }

        return nonDeleted;
    }



    public String getDescription(){
        return "Task added on " + getDeleted().toString();
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }


    public Date getDeleted() {return deleted;}

    public void setDeleted(Date deleted) {this.deleted = deleted;}
}
