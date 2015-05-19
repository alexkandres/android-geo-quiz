package com.alexkandres.geoquiz;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;


public class QuizActivity extends Activity{

    private static final String TAG = "QuizActivity";
    private static final String KEY_INDEX = "index";
    private static final String CHEATER = "cheater";
    private Button mTrueButton;
    private Button mFalseButton;
    private ImageButton mNextButton;
    private ImageButton mPrevButton;
    private TextView mQuestionTextView;
    private int mCurrentIndex = 0;
    private  Button mCheatButton;
    private boolean mIsCheater;
    private TrueFalse[] mQuestionBank = {//TrueFalse w/ Resource id of string and boolean as args
        new TrueFalse(R.string.question_africa,false),
        new TrueFalse(R.string.question_americas,true),
        new TrueFalse(R.string.question_asia,true),
        new TrueFalse(R.string.question_mideast,false),
        new TrueFalse(R.string.question_oceans,true)
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.d(TAG, "onCreate(Bundle) called");
        setContentView(R.layout.activity_quiz);

        mFalseButton = (Button)findViewById(R.id.false_button);
        mTrueButton = (Button)findViewById(R.id.true_button);
        mNextButton = (ImageButton) findViewById(R.id.next_button);
        mQuestionTextView = (TextView) findViewById(R.id.question_text_view);
        mPrevButton = (ImageButton) findViewById(R.id.prev_button);
        mCheatButton = (Button) findViewById(R.id.cheatButton);

        if(savedInstanceState != null){
            mCurrentIndex = savedInstanceState.getInt(KEY_INDEX, 0);
            mIsCheater = savedInstanceState.getBoolean(CHEATER, false);
        }

        updateTextView();

        View.OnClickListener myListener = new View.OnClickListener() {
            @Override
            public void onClick(View view) { //listener for the question textView and next button
                mCurrentIndex++;//increment
                mIsCheater = false; //resets every cheat back to false
                updateTextView();
            }
        };
        mNextButton.setOnClickListener(myListener);
        mQuestionTextView.setOnClickListener(myListener);//same listener in questiontextview and nextbutton
        updateTextView();

        mCheatButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent mCheatActivityIntent = new Intent(QuizActivity.this,CheatActivity.class);
                boolean answerIsTrue = mQuestionBank[mCurrentIndex%5].isTrueQuestion();
                mCheatActivityIntent.putExtra(CheatActivity.EXTRA_ANSWER_IS_TRUE, answerIsTrue);
                //startActivity(mCheatActivityIntent);
                startActivityForResult(mCheatActivityIntent, 0);
            }
        });
        mPrevButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mCurrentIndex--;
                updateTextView();
            }
        });

        mTrueButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                checkAnswer(true);
            }
        });

        mFalseButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                checkAnswer(false);
            }
        });

    }
    
    private void checkAnswer(boolean userPressedTrue){
        int messageResId;
        if(mIsCheater)
            messageResId = R.string.judgment_toast;
        else if(userPressedTrue == (mQuestionBank[mCurrentIndex%5].isTrueQuestion()))
            messageResId = R.string.correct_toast;
        else
            messageResId = R.string.incorrect_toast;
        Toast.makeText(QuizActivity.this,messageResId,Toast.LENGTH_SHORT).show();
    }

    public void updateTextView(){
        //Log.d(TAG, "Updating question text for question #"+mCurrentIndex, new Exception());
        int question = mQuestionBank[mCurrentIndex%5].getQuestion();//getting the resource id from index o
        mQuestionTextView.setText(question);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data){
        if(data == null)
            return;
        mIsCheater = data.getBooleanExtra(CheatActivity.EXTRA_ANSWER_SHOWN, false); //false is default value
    }
    @Override
    public void onSaveInstanceState(Bundle savedInstanceState){
        super.onSaveInstanceState(savedInstanceState);
        Log.i(TAG,"onSavedInstanceState");
        savedInstanceState.putInt(KEY_INDEX, mCurrentIndex);
        savedInstanceState.putBoolean(CHEATER, mIsCheater);
        Log.d(TAG,"Current index "+mCurrentIndex);
    }

    @Override
    protected void onStart() {
        super.onStart();
        Log.d(TAG,"onStart() called");
    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.d(TAG,"onResume() called");
    }

    @Override
    protected void onPause() {
        super.onPause();
        Log.d(TAG,"onPause() called");
    }

    @Override
    protected void onStop() {
        super.onStop();
        Log.d(TAG, "onStop() called");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.d(TAG, "onDestroy() called");
    }


















    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_quiz, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}