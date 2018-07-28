package com.bignerdranch.android.knowmalaysia;

import android.app.Activity;
import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.database.MatrixCursor;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class QuizChallenge extends AppCompatActivity {

    private static final String TAG = "QuizChallenge";
    private  static  final String KEY_INDEX = "index";
    private static final int REQUEST_CODE_CHEAT = 0;

    private Button mTrueButton;
    private Button mFalseButton;
    private Button mNextButton;
    private Button mCheatButton;
    private TextView mQuestionTextView;
    private TextView mFactTextView;

    private Question [] mQuestionBank = new Question[]{
            new Question(R.string.question_roundabout,true),
            new Question(R.string.question_tall_building,true),
            new Question(R.string.question_cave,false),
            new Question(R.string.question_rainforest,false),
            new Question(R.string.question_cobra,true),
            new Question(R.string.question_drink,false),
            new Question(R.string.question_jimmy,true),
            new Question(R.string.question_first,true),
            new Question(R.string.question_old,true)
    };
    private int mCurrentIndex = 0;
    private boolean mIsCheater;

    private Facts [] mFactBank = new Facts[]{
            new Facts(R.string.fact_roundabout,true),
            new Facts(R.string.fact_tall_building,true),
            new Facts(R.string.fact_cave,false),
            new Facts(R.string.fact_rainforest,false),
            new Facts(R.string.fact_cobra,true),
            new Facts(R.string.fact_drink,false),
            new Facts(R.string.fact_jimmy,true),
            new Facts(R.string.fact_first,true),
            new Facts(R.string.fact_old,true)
    };

    private int mFactIndex = 0;

    private void updateQuestion(){
        int question = mQuestionBank[mCurrentIndex].getTextResId();
        mQuestionTextView.setText(question);
    }

    private void checkAnswer (boolean userPressedTrue){
        boolean answerIsTrue = mQuestionBank[mCurrentIndex].isAnswerTrue();

        int messageResId = 0;

        if (mIsCheater) {
            messageResId = R.string.judgement_toast;
        }

        else {
            if (userPressedTrue == answerIsTrue) {
                messageResId = R.string.correct_toast;
            } else {
                messageResId = R.string.incorrect_toast;
            }
        }
        Toast.makeText(this, messageResId,Toast.LENGTH_SHORT)
                .show();
    }

    private void updateFact(){
        int fact = mFactBank[mFactIndex].getFactResId();
        mFactTextView.setText(fact);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quiz_challenge);

        mQuestionTextView = (TextView) findViewById(R.id.question_text_view);

        mFactTextView = (TextView) findViewById(R.id.fact_text_view);


        mTrueButton = (Button) findViewById(R.id.true_button);
        mTrueButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                checkAnswer(true);

            }
        });
        mFalseButton = (Button) findViewById(R.id.false_button);
        mFalseButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               checkAnswer(false);
            }
        });
        mNextButton =(Button) findViewById(R.id.next_button);
        mNextButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mCurrentIndex = (mCurrentIndex + 1)% mQuestionTextView.length();
                mIsCheater = false;
                updateQuestion();

                mFactIndex = (mFactIndex + 1)% mFactBank. length;
                updateFact();
            }
        });
        mCheatButton = (Button) findViewById(R.id.cheat_button);
        mCheatButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                //Start Activity
                boolean answerIsTrue = mQuestionBank [mCurrentIndex].isAnswerTrue();
                Intent i = CheatActivity.newIntent(QuizChallenge.this,answerIsTrue);
                startActivityForResult(i, REQUEST_CODE_CHEAT);
            }
        });

        if (savedInstanceState != null){
            mCurrentIndex = savedInstanceState.getInt(KEY_INDEX,0);
        }
        updateQuestion();
        updateFact();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data){
        if ( (resultCode != Activity.RESULT_OK)){
            return;
        }
        if (requestCode == REQUEST_CODE_CHEAT){
            if (data == null){
                return;
            }
            mIsCheater = CheatActivity.wasAnswerShown(data);
        }
    }
    @Override
    public void onSaveInstanceState (Bundle savedInstanceState){
        super.onSaveInstanceState(savedInstanceState);
        Log.i(TAG,"onSaveInstanceState");
        savedInstanceState.putInt(KEY_INDEX,mCurrentIndex);
    }
}
