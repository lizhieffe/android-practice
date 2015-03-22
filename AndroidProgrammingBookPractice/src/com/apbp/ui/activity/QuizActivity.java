package com.apbp.ui.activity;


import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.apbp.model.TrueFalse;
import com.example.androidprogrammingbookpractice.R;

public class QuizActivity extends Activity {

	private static final String TAG = QuizActivity.class.getName();
	private static final String KEY_CURR_QUESTION_INDEX = "question_index";
	public static final String EXTRA_ANSWER_IS_TRUE = TAG + ".answer_is_true";
	
	private Button mTrueButton;
	private Button mFalseButton;
	private Button mCheatButton;
	private Button mPrevButton;
	private Button mNextButton;
	private TextView mQuestionTextView;
	
	private TrueFalse[] mQuestionBank;
	private int mCurrQuestionIndex;
	
	private boolean[] mIsCheater;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_quiz);
		
		if (savedInstanceState != null)
			this.mCurrQuestionIndex = savedInstanceState.getInt(KEY_CURR_QUESTION_INDEX, 0);
		
		initQuestionBank();
		initQuestionTextView();
		
		initTrueButton();
		initFalseButton();
		initCheatButton();
		initNextButton();
		initPrevButton();
		
		Log.d(TAG, "onCreate() called");
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.quiz, menu);
		Log.d(TAG, "onCreateOptionsMenu() called");
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();
		if (id == R.id.action_settings) {
			return true;
		}
		return super.onOptionsItemSelected(item);
	}
	
	@Override
	public void onSaveInstanceState(Bundle savedInstanceState) {
		super.onSaveInstanceState(savedInstanceState);
		Log.d(TAG, "onSaveInstanceState(Bundle) called");
		savedInstanceState.putInt(KEY_CURR_QUESTION_INDEX, mCurrQuestionIndex);
	}
	
	@Override
	public void onActivityResult(int requestCode, int resultCode, Intent data) {
		if (data == null)
			return;
		this.mIsCheater[this.mCurrQuestionIndex] = data.getBooleanExtra(CheatActivity.EXTRA_ANSWER_IS_SHOWN, false);
	}
	
	private void initTrueButton() {
		this.mTrueButton = (Button)findViewById(R.id.true_button);
		this.mTrueButton.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				checkAnswer(true);
			}
		});
	}
	
	private void initFalseButton() {
		this.mFalseButton = (Button)findViewById(R.id.false_button);
		this.mFalseButton.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				checkAnswer(false);
			}
		});
	}
	
	private void initCheatButton() {
		this.mCheatButton = (Button)findViewById(R.id.cheat_button);
		this.mCheatButton.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				Intent i = new Intent(QuizActivity.this, CheatActivity.class);
				i.putExtra(EXTRA_ANSWER_IS_TRUE, mQuestionBank[mCurrQuestionIndex].ismTrueQuestion());
				startActivityForResult(i, 0);
			}
		});
	}
	
	private void initPrevButton() {
		this.mPrevButton = (Button)findViewById(R.id.prev_button);
		this.mPrevButton.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				mCurrQuestionIndex = (mCurrQuestionIndex - 1) % mQuestionBank.length;
				if (mCurrQuestionIndex < 0)
					mCurrQuestionIndex += mQuestionBank.length;
				updateQuestionTextView();
				Log.d(TAG, "prev button clicked");
			}
		});
	}
	
	private void initNextButton() {
		this.mNextButton = (Button)findViewById(R.id.next_button);
		this.mNextButton.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				mCurrQuestionIndex = (mCurrQuestionIndex + 1) % mQuestionBank.length;
				updateQuestionTextView();
				Log.d(TAG, "next button clicked");
			}
		});
	}
	
	private void initQuestionBank() {
		this.mQuestionBank = new TrueFalse[] {
			new TrueFalse(R.string.question_asia, true),
			new TrueFalse(R.string.question_ocean, false),
			new TrueFalse(R.string.question_mideast, true)
		};
		
		this.mIsCheater = new boolean[mQuestionBank.length];
	}
	
	private void initQuestionTextView() {
		mQuestionTextView = (TextView)findViewById(R.id.question_text_view);
		updateQuestionTextView();
	}
	
	private void updateQuestionTextView() {
		TrueFalse tf = mQuestionBank[mCurrQuestionIndex];
		mQuestionTextView.setText(tf.getmQuestion());
	}
	
	private void checkAnswer(boolean userPressTrue) {
		int msgResId = 0;
		if (mIsCheater[this.mCurrQuestionIndex])
			msgResId = R.string.judgement_toast;
		else {
			if (mQuestionBank[mCurrQuestionIndex].ismTrueQuestion() == userPressTrue)
				msgResId = R.string.correct_toast;
			else
				msgResId = R.string.incorrect_toast;
		}
		Toast.makeText(this, msgResId, Toast.LENGTH_SHORT).show();
	}
}
