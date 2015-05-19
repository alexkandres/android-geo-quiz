package com.alexkandres.geoquiz;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

/**
 * Created by Alex on 4/25/2015.
 */
public class CheatActivity extends Activity {

    public final static String EXTRA_ANSWER_IS_TRUE =
            "com.alexkandres.geoquiz.answer_is_true";
    public final static String EXTRA_ANSWER_SHOWN = "com.alexkandres.geoquiz.answer_shown";
    public final static String ANSWER_PRESSED = "Pressed";
    private boolean mAnswerIsTrue;
    private TextView mAnswerTextView;
    private Button mShowAnswer;
    private boolean mShowAnswerPressed;

    @Override
    protected void onCreate(Bundle onSavedInstanceState){
        super.onCreate(onSavedInstanceState);
        setContentView(R.layout.activity_cheat);


        mAnswerIsTrue = getIntent().getBooleanExtra(EXTRA_ANSWER_IS_TRUE, false);
        mAnswerTextView = (TextView)findViewById(R.id.answerTextView);
        mShowAnswer = (Button)findViewById(R.id.showAnswerButton);
        mShowAnswerPressed = false;
        setAnswerShownResult(false);
        if(onSavedInstanceState != null) {
            mShowAnswerPressed = onSavedInstanceState.getBoolean(ANSWER_PRESSED, false);
            if(mAnswerIsTrue)
                mAnswerTextView.setText(R.string.true_button);
            else
                mAnswerTextView.setText(R.string.false_button);
        }
        //answer will not be shown until user presses the button


        mShowAnswer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(mAnswerIsTrue)
                    mAnswerTextView.setText(R.string.true_button);
                else
                    mAnswerTextView.setText(R.string.false_button);
                setAnswerShownResult(true);
            }
        });

        mShowAnswerPressed = mShowAnswer.isPressed();

    }

    private void setAnswerShownResult(boolean isAnswerShown){
        Intent data = new Intent();
        data.putExtra(EXTRA_ANSWER_SHOWN, isAnswerShown);
        setResult(RESULT_OK, data);
    }

    @Override
    public void onSaveInstanceState(Bundle savedInstanceState){
        savedInstanceState.putBoolean(ANSWER_PRESSED, mShowAnswerPressed);
    }
}
