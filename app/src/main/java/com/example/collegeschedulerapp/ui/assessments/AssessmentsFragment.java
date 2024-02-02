package com.example.collegeschedulerapp.ui.assessments;


import static android.content.Context.MODE_PRIVATE;
import android.content.SharedPreferences;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.datastore.preferences.core.MutablePreferences;
import androidx.datastore.preferences.core.Preferences;
import androidx.datastore.preferences.core.PreferencesKeys;
import androidx.datastore.preferences.rxjava2.RxPreferenceDataStoreBuilder;
import androidx.datastore.rxjava2.RxDataStore;
import androidx.fragment.app.Fragment;
import androidx.navigation.fragment.NavHostFragment;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Map;

import com.example.collegeschedulerapp.R;
import com.example.collegeschedulerapp.databinding.FragmentAssessmentsBinding;
import com.example.collegeschedulerapp.ui.Date;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import io.reactivex.Single;

public class AssessmentsFragment extends Fragment {

    private FragmentAssessmentsBinding binding;
    //private static final String SHARED_PREFS = "persist data";
    //private static final String SHARED_PREFS_KEY = "list";

    RxDataStore<Preferences> dataStoreRX;
    ListView assessmentList;
    ArrayList<Assessment> assessments = new ArrayList<>();
    ArrayAdapter<Assessment> listAdapter;
    Button addButton;




    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = FragmentAssessmentsBinding.inflate(inflater, container, false);
        dataStoreRX = new RxPreferenceDataStoreBuilder(getActivity(), "assessments").build();
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        addButton = (Button) view.findViewById(R.id.button_addAssessment);
        assessmentList = (ListView) view.findViewById(R.id.assessmentList);
        listAdapter = new ArrayAdapter<>(getActivity(), android.R.layout.simple_list_item_1, assessments);

        assessmentList.setAdapter(listAdapter);
        listAdapter.notifyDataSetChanged();



        Assessment assessment = AssessmentsFragmentArgs.fromBundle(getArguments()).getAssessment();
        int pos = AssessmentsFragmentArgs.fromBundle(getArguments()).getPos();

        if (assessment == null) {

        } else {
            if (assessments.contains(assessment)) {
                Toast.makeText(getActivity(), "Class has already been added", Toast.LENGTH_SHORT).show();
            } else {
                if (pos == assessments.size()) {
                    assessments.add(pos, assessment);
                } else {
                    assessments.remove(pos);
                    assessments.add(pos, assessment);
                }

            }
            sortAssessments();
            listAdapter.notifyDataSetChanged();
        }

        assessmentList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int i, long id) {
                //final EditText input = new EditText(ClassesActivity.this);
                //input.setInputType(InputType.TYPE_CLASS_TEXT);

                new AlertDialog.Builder(getActivity()).setTitle("Edit or remove " + assessments.get(i) + " from the list?").setPositiveButton("Edit", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        AssessmentsFragmentDirections.ActionAssessmentsToAddAssessment action = AssessmentsFragmentDirections.actionAssessmentsToAddAssessment().setPosition(i);
                        NavHostFragment.findNavController(AssessmentsFragment.this).navigate(action);
                    }

                }).setNegativeButton("Remove", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        assessments.remove(i);
                        listAdapter.notifyDataSetChanged();
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
                AssessmentsFragmentDirections.ActionAssessmentsToAddAssessment action = AssessmentsFragmentDirections.actionAssessmentsToAddAssessment().setPosition(assessments.size());
                NavHostFragment.findNavController(AssessmentsFragment.this).navigate(action);
            }
        });
    }


    /*
    private void saveData() {
        SharedPreferences sp = getActivity().getSharedPreferences(SHARED_PREFS, MODE_PRIVATE);
        SharedPreferences.Editor editor = sp.edit();
        Gson gson = new Gson();
        String jsonString = gson.toJson(assessments);
        editor.putString(SHARED_PREFS_KEY, jsonString);
        editor.apply();
    }

    private void loadData() {
        SharedPreferences sp = getActivity().getSharedPreferences(SHARED_PREFS, MODE_PRIVATE);
        Gson gson = new Gson();
        String json = sp.getString(SHARED_PREFS_KEY, null);
        if (json.isEmpty()) {
            return;
        }

        Type type = new TypeToken<ArrayList<Assessment>>(){}.getType();
        assessments.addAll((gson.fromJson(json, type)));
    }

     */

    public boolean putStringValue(String Key, String value) {
        boolean returnvalue;
        Preferences pref_error = new Preferences() {
            @Override
            public <T> boolean contains(@NonNull Preferences.Key<T> key) {
                return false;
            }

            @Nullable
            @Override
            public <T> T get(@NonNull Preferences.Key<T> key) {
                return null;
            }

            @NonNull
            @Override
            public Map<Preferences.Key<?>, Object> asMap() {
                return null;
            }
        };
        Preferences.Key<String> PREF_KEY = PreferencesKeys.stringKey(Key);
        Single<Preferences> updateResult = dataStoreRX.updateDataAsync(prefsIn -> {
            MutablePreferences mutablePreferences = prefsIn.toMutablePreferences();
            mutablePreferences.set(PREF_KEY, value);
            return Single.just(mutablePreferences);
        }).onErrorReturnItem(pref_error);

        returnvalue = updateResult.blockingGet() != pref_error;
        return returnvalue;
    }

    String getStringValue(String Key) {
        Preferences.Key<String> PREF_KEY = PreferencesKeys.stringKey(Key);
        Single<String> value = dataStoreRX.data().firstOrError().map(prefs -> prefs.get(PREF_KEY)).onErrorReturnItem("null");
        return value.blockingGet();
    }

    public void sortAssessments() {
        int n = assessments.size();
        for (int i = 0; i < n - 1; i++) {
            int min = i;
            for (int j = i + 1; j < n; j++) {
                Date date1 = assessments.get(j).getDate();
                Date date2 = assessments.get(min).getDate();
                if (date1.compareTo(date2) < 0) {
                    min = j;
                }
            }
            Assessment temp = assessments.get(min);
            assessments.set(min, assessments.get(i));
            assessments.set(i, temp);
        }
    }





}