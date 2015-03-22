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

import com.example.androidprogrammingbookpractice.R;

public class CheatActivity extends Activity {

	private static final String TAG = CheatActivity.class.getName();
//	private static final String KEY_ANSWER_IS_TRUE = "answer_is_true"; 
	private static final String KEY_ANSWER_IS_SHOWN = "answer_is_shown";
	public static final String EXTRA_ANSWER_IS_SHOWN = TAG + ".answer_is_shown";
	
	private boolean mAnswerIsTrue;
	private boolean mAnswerIsShown;
	private TextView mAnswerTextView;
	private Button mShowAnswerButton;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_cheat);
		
		if (savedInstanceState != null) {
//			this.mAnswerIsTrue = savedInstanceState.getBoolean(KEY_ANSWER_IS_TRUE, false);
			this.mAnswerIsShown = savedInstanceState.getBoolean(KEY_ANSWER_IS_SHOWN, false);
		}
		
		initAnswer();
		initAnswerTextView();
		initShowAnswerButton();
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.cheat, menu);
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
//		savedInstanceState.putBoolean(KEY_ANSWER_IS_TRUE, this.mAnswerIsTrue);
		savedInstanceState.putBoolean(KEY_ANSWER_IS_SHOWN, this.mAnswerIsShown);
	}
	
	private void initAnswer() {
		this.mAnswerIsTrue = getIntent().getBooleanExtra(QuizActivity.EXTRA_ANSWER_IS_TRUE, false);
	}
	
	private void initAnswerTextView() {
		this.mAnswerTextView = (TextView)findViewById(R.id.answer_text_view);
		
		if (this.mAnswerIsShown)
			showAnswer();
	}
	
	private void showAnswer() {
		if (this.mAnswerIsTrue)
			this.mAnswerTextView.setText(R.string.true_button);
		else
			this.mAnswerTextView.setText(R.string.false_button);
		
		this.mAnswerIsShown = true;
	}
	
	private void initShowAnswerButton() {
		this.mShowAnswerButton = (Button)findViewById(R.id.show_answer_button);
		this.mShowAnswerButton.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				if (!CheatActivity.this.mAnswerIsShown) {
					showAnswer();
					setAnswerShownResult();
				}
			}
		});
	}
	
	private void setAnswerShownResult() {
		Intent data = new Intent();
		data.putExtra(EXTRA_ANSWER_IS_SHOWN, true);
		setResult(RESULT_OK, data);
	}
}
