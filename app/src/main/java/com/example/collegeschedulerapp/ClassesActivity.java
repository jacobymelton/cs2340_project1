package com.example.collegeschedulerapp;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import java.lang.reflect.Type;
import java.util.ArrayList;

import com.example.collegeschedulerapp.ui.classes.AddClassFragment;
import com.example.collegeschedulerapp.ui.classes.Course;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

public class ClassesActivity extends AppCompatActivity {

    private static final String SHARED_PREFS = "persist data";
    private static final String SHARED_PREFS_KEY = "list";
    ListView classList;
    ArrayList<Course> classes = new ArrayList<>();
    ArrayAdapter<Course> listAdapter;
    Button addButton;
    Button homeButton;
    private String edit_text = "";
    private String input_text = "";

    boolean replacement = false;
    int pos = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_classes);

        addButton = (Button) findViewById(R.id.button_addClass);
        classList = (ListView) findViewById(R.id.classList);
        listAdapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, classes);

        classList.setAdapter(listAdapter);
        listAdapter.notifyDataSetChanged();

        loadData();

        homeButton = findViewById(R.id.class_home_button);
        homeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(ClassesActivity.this, MainActivity.class);
                startActivity(i);
            }
        });

        classList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int i, long id) {
                //final EditText input = new EditText(ClassesActivity.this);
                //input.setInputType(InputType.TYPE_CLASS_TEXT);

                new AlertDialog.Builder(ClassesActivity.this).setTitle("Edit or remove " + classes.get(i) + " from the list?").setPositiveButton("Edit", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        replacement = true;
                        pos = i;
                        getSupportFragmentManager().beginTransaction().replace(R.id.frame_class, new AddClassFragment()).commit();
                        addButton.setVisibility(View.GONE);
                    }

                }).setNegativeButton("Remove", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        classes.remove(i);
                        listAdapter.notifyDataSetChanged();
                        saveData();
                    }
                }).setNeutralButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                }).create().show();
            }
        });
        addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getSupportFragmentManager().beginTransaction().replace(R.id.frame_class, new AddClassFragment()).commit();
                addButton.setVisibility(View.GONE);
            }
        });

    }

    private void saveData() {
        SharedPreferences sp = getSharedPreferences(SHARED_PREFS, MODE_PRIVATE);
        SharedPreferences.Editor editor = sp.edit();
        Gson gson = new Gson();
        String jsonString = gson.toJson(classes);
        editor.putString(SHARED_PREFS_KEY, jsonString);
        editor.apply();
    }

    private void loadData() {
        SharedPreferences sp = getSharedPreferences(SHARED_PREFS, MODE_PRIVATE);
        Gson gson = new Gson();
        String json = sp.getString(SHARED_PREFS_KEY, "");

        if (json.isEmpty()) {
            return;
        }

        Type type = new TypeToken<ArrayList<Course>>(){}.getType();
        classes.addAll((gson.fromJson(json, type)));
    }

    public boolean getReplacement() {
        return replacement;
    }

    public void addToClasses(Course course) {
        if (course == null) {

        } else if (classes.contains(course)) {
            Toast.makeText(ClassesActivity.this, "Class has already been added", Toast.LENGTH_SHORT).show();
        } else if (replacement){
            classes.remove(pos);
            classes.add(pos, course);
            replacement = false;
        } else {
            classes.add(course);
        }
        listAdapter.notifyDataSetChanged();
        saveData();
        addButton.setVisibility(View.VISIBLE);
    }

    // method from https://www.geeksforgeeks.org/how-to-programmatically-hide-android-soft-keyboard/
    private void closeKeyboard() {
        View view = this.getCurrentFocus();
        if (view != null) {
            InputMethodManager manager = (InputMethodManager)
                    getSystemService(Context.INPUT_METHOD_SERVICE);
            manager.hideSoftInputFromWindow(
                    view.getWindowToken(), 0);
        }
    }

}