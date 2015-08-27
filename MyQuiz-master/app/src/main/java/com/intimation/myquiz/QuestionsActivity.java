package com.intimation.myquiz;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.View;
import android.view.animation.AnimationUtils;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.intimation.myquiz.model.Data;
import com.intimation.myquiz.model.Question;
import com.intimation.myquiz.utils.Utils;

import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

/**
 * Created by gorillalogic on 7/1/15.
 */
public class QuestionsActivity extends Activity implements View.OnClickListener {

    private List<Question> mQuestions;
    private TextView mTimeLeftValue, mOptionA, mOptionB, mOptionC, mOptionD;
    private LinearLayout mQuestionsLayout;
    private Timer mTimer;
    private int hh, mm, ss;
    private int mQNo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.view_qpage);

        mQNo = 0;
        mQuestions = Data.getInstance().getAllQuestions();
        String total_time = Utils.getTotalTime(mQuestions.size());

        ((TextView)findViewById(R.id.subject_value)).setText(getIntent().getStringExtra("selected_subject"));
        ((TextView)findViewById(R.id.topic_value)).setText(getIntent().getStringExtra("selected_topic"));
        mTimeLeftValue = (TextView)findViewById(R.id.time_left_value);
        mQuestionsLayout = (LinearLayout) findViewById(R.id.question_layout);
        findViewById(R.id.exit).setOnClickListener(this);
        findViewById(R.id.button_next).setOnClickListener(this);
        mOptionA = (TextView) findViewById(R.id.option_a_value);
        mOptionB = (TextView) findViewById(R.id.option_b_value);
        mOptionC = (TextView) findViewById(R.id.option_c_value);
        mOptionD = (TextView) findViewById(R.id.option_d_value);
        mOptionA.setOnClickListener(this);
        mOptionB.setOnClickListener(this);
        mOptionC.setOnClickListener(this);
        mOptionD.setOnClickListener(this);
        findViewById(R.id.option_a).setOnClickListener(this);
        findViewById(R.id.option_b).setOnClickListener(this);
        findViewById(R.id.option_c).setOnClickListener(this);
        findViewById(R.id.option_d).setOnClickListener(this);
        setCurrentQuestion(mQuestions.get(mQNo));
        updateTimerText(total_time); // initial
        startTimer();

    }

    private void setCurrentQuestion(Question q) {
        Data.getInstance().setCurrentQuestion(q);
        ((TextView)findViewById(R.id.question)).setText(q.question);
        ((TextView)findViewById(R.id.question_number)).setText("" + (mQNo+1));
        mOptionA.setText(q.ch1);
        mOptionB.setText(q.ch2);
        mOptionC.setText(q.ch3);
        mOptionD.setText(q.ch4);
    }

    private void startTimer() {
        mTimer = new Timer();

        ss = 60;
        mm = mQuestions.size() * 1;
        hh = 0;
        if (mm > 59) {
            hh = mm / 60;
            mm = mm % (hh * 60);
        }

        TimerTask t = new TimerTask() {
            @Override
            public void run() {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        if (--ss < 0) {
                            mm--;
                            ss = 59;
                        }
                        if (mm < 0) {
                            hh--;
                            mm = 59;
                        }
                        if (hh < 0)
                            timeUp();
                        updateTimerText(hh == 0
                                        ? String.format("%02d", mm) + ":" + String.format("%02d", ss)
                                        : String.format("%02d", hh) + ":" + String.format("%02d", mm) + ":" + String.format("%02d", ss)
                        );
                    }
                });
            }
        };
        mTimer.schedule(t, 50, 1000);
    }

    private void timeUp() {
        Data.getInstance().setQuestions(mQuestions);
        finish();
        // show result page
    }

    private void updateTimerText(String time) {
        mTimeLeftValue.setText(time);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.button_next:
                mQuestionsLayout.startAnimation(AnimationUtils.loadAnimation(this, android.R.anim.slide_in_left));
                if (++mQNo < mQuestions.size()) {
                    setCurrentQuestion(mQuestions.get(mQNo));
                } else {
                    timeUp();
                }
                break;

            case R.id.exit:
                new AlertDialog.Builder(this)
                        .setTitle("Confirm Exit")
                        .setMessage("Are you sure you want to exit?")
                        .setPositiveButton("Confirm", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                timeUp();
                            }
                        })
                        .setNegativeButton(android.R.string.cancel, null)
                        .show();
                break;


        }
    }
}
