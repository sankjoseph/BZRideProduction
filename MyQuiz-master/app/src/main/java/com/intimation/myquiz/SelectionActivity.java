package com.intimation.myquiz;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.SpinnerAdapter;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by gorillalogic on 6/11/15.
 */
public class SelectionActivity extends Activity implements AdapterView.OnItemSelectedListener, View.OnClickListener {

    private Button mButtonNext;

    private String mSubjectSelected, mTopicSelected;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_selection);

        Spinner subjects_spinner = (Spinner) findViewById(R.id.spinner_select_subject);
        subjects_spinner.setOnItemSelectedListener(this);
        ArrayAdapter<String> subjects_adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, getSubjectsList());
        subjects_spinner.setAdapter(subjects_adapter);

        mButtonNext = (Button) findViewById(R.id.button_next);
        mButtonNext.setOnClickListener(this);
        mButtonNext.setEnabled(false);
    }

    private List<String> getSubjectsList() {
        ArrayList<String> subjectsList = new ArrayList<>();
        subjectsList.add("History");
        subjectsList.add("Science");
        subjectsList.add("Mathematics");
        return subjectsList;
    }

    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
        if (adapterView.getId() == R.id.spinner_select_subject) {
            mSubjectSelected = adapterView.getSelectedItem().toString();
            Spinner topics_spinner = (Spinner) findViewById(R.id.spinner_select_topic);
            topics_spinner.setOnItemSelectedListener(this);
            ArrayAdapter<String> topics_adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, getTopicsList());
            topics_spinner.setAdapter(topics_adapter);
        } else {
            // topic selected
            mTopicSelected = adapterView.getSelectedItem().toString();
            mButtonNext.setEnabled(true);
        }
    }

    private List<String> getTopicsList() {

        ArrayList<String> topicsList = new ArrayList<>();
        if (mSubjectSelected.equalsIgnoreCase("history")) {
            topicsList.add("Christ Era");
            topicsList.add("Indian Monuments");
            topicsList.add("Dynasties");
        } else if (mSubjectSelected.equalsIgnoreCase("science")) {
            topicsList.add("Scientific Research");
            topicsList.add("Newton's Laws");
            topicsList.add("Modern world Science");
        } else {
            topicsList.add("Algebra");
            topicsList.add("Geometry");
            topicsList.add("General");
        }
        return topicsList;
    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.button_next:
                showSummaryScreen();
                break;
        }
    }

    private void showSummaryScreen() {
        startActivity(new Intent(this, SummaryActivity.class)
                .putExtra("selected_subject", mSubjectSelected)
                .putExtra("selected_topic", mTopicSelected)
        );
    }
}
