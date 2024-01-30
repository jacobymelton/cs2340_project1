package com.example.collegeschedulerapp;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.DisplayCutout;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Scanner;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

public class ClassesActivity extends AppCompatActivity {

    private static final String SHARED_PREFS = "persist data";
    private static final String SHARED_PREFS_KEY = "list";
    ListView classList;
    ArrayList<String> classes = new ArrayList<>();
    ArrayAdapter<String> listAdapter;
    Button addButton;
    EditText textInput;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_classes);

        addButton = (Button) findViewById(R.id.button_addClass);
        textInput = (EditText) findViewById(R.id.classAddInfo);
        classList = (ListView) findViewById(R.id.classList);
        listAdapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, classes);

        classList.setAdapter(listAdapter);
        listAdapter.notifyDataSetChanged();

        loadData();

        classList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int i, long id) {
                new AlertDialog.Builder(ClassesActivity.this).setTitle("Remove " + classes.get(i) + " from the list?").setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        classes.remove(i);
                        listAdapter.notifyDataSetChanged();
                        saveData();
                    }
                }).setNegativeButton("No", new DialogInterface.OnClickListener() {
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
                String text = textInput.getText().toString();

                if (text == null || text.trim().equals("")) {
                    Toast.makeText(ClassesActivity.this, "Item is empty", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (classes.contains(text)) {
                    Toast.makeText(ClassesActivity.this, "Class has already been added", Toast.LENGTH_SHORT).show();
                    return;
                }
                classes.add(text);
                listAdapter.notifyDataSetChanged();
                closeKeyboard();
                textInput.setText("");

                saveData();
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

        Type type = new TypeToken<ArrayList<String>>() {}.getType();
        classes.addAll((gson.fromJson(json, type)));
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