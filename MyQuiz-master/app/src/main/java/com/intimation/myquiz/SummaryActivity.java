package com.intimation.myquiz;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.intimation.myquiz.model.Data;
import com.intimation.myquiz.model.ModelClassMapper;
import com.intimation.myquiz.model.Question;
import com.intimation.myquiz.model.Questions;
import com.intimation.myquiz.rest.OnPostExecuteListener;
import com.intimation.myquiz.rest.RestApi;
import com.intimation.myquiz.utils.Utils;

import java.util.ArrayList;

/**
 * Created by gorillalogic on 6/12/15.
 */
public class SummaryActivity extends Activity implements OnPostExecuteListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_summary);
        findViewById(R.id.button_next).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
                showQuestionsScreen();
            }
        });
        getQuestionsFromServer();
    }

    private void showQuestionsScreen() {
        Intent i = new Intent(SummaryActivity.this, QuestionsActivity.class);
        i.putExtra("selected_subject", getIntent().getStringExtra("selected_subject"))
                .putExtra("selected_topic", getIntent().getStringExtra("selected_topic"));
        startActivity(i);
    }

    private void getQuestionsFromServer() {
        RestApi api = new RestApi(this);
        api.setMessage("Getting Questions...");
        api.setPostExecuteListener(this);
        api.get(Utils.URL_VIEW_QUESTION);
    }

    private void fillData(ArrayList<Question> questions) {
        ((TextView)findViewById(R.id.subject)).setText("Subject : " + getIntent().getStringExtra("selected_subject"));
        ((TextView)findViewById(R.id.topic)).setText("Topic : " + getIntent().getStringExtra("selected_topic"));
        int noOfQuestions = questions != null ? questions.size() : 0;
        ((TextView)findViewById(R.id.questions)).setText("Total Questions : " + noOfQuestions);
        ((TextView)findViewById(R.id.time)).setText("Time Left : " + Utils.getTotalTime(questions.size()));
    }

    @Override
    public void onSuccess(ModelClassMapper model) {
        ArrayList<Question> questions = ((Questions) model).questions;
        Data.getInstance().setQuestions(questions);
        fillData(questions);
    }

    @Override
    public void onFailure() {

    }
}
