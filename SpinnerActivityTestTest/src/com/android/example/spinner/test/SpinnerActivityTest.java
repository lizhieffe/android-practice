package com.android.example.spinner.test;

import android.app.Instrumentation;
import android.test.ActivityInstrumentationTestCase2;
import android.test.UiThreadTest;
import android.view.KeyEvent;
import android.widget.Spinner;
import android.widget.SpinnerAdapter;
import android.widget.TextView;

import com.android.example.spinner.SpinnerActivity;

public class SpinnerActivityTest extends
		ActivityInstrumentationTestCase2<SpinnerActivity> {

	public static final int ADAPTER_COUNT = 9;
	public static final int INITIAL_POSITION = 0;
	public static final int TEST_POSITION = 5;

	// The initial position corresponds to Mercury

    public static final String INITIAL_SELECTION = "Mercury";

    // Test values of position and selection for the testStateDestroy test

    public static final int TEST_STATE_DESTROY_POSITION = 2;
    public static final String TEST_STATE_DESTROY_SELECTION = "Earth";

    // Test values of position and selection for the testStatePause test

    public static final int TEST_STATE_PAUSE_POSITION = 4;
    public static final String TEST_STATE_PAUSE_SELECTION = "Jupiter";
    
	private SpinnerActivity mActivity;
	private Spinner mSpinner;
	private SpinnerAdapter mPlanetData;
    private int mPos;
    private String mSelection;
    
	public SpinnerActivityTest() {
	    super(SpinnerActivity.class);
	  } // end of SpinnerActivityTest constructor definition
	
	@Override
	protected void setUp() throws Exception {
		super.setUp();

		setActivityInitialTouchMode(false);
		
		mActivity = getActivity();

		mSpinner = (Spinner) mActivity.findViewById(com.android.example.spinner.R.id.Spinner01);

		mPlanetData = mSpinner.getAdapter();

	  } // end of setUp() method definition
	
	public void testPreConditions() {
	    assertTrue(mSpinner.getOnItemSelectedListener() != null);
	    assertTrue(mPlanetData != null);
	    assertEquals(mPlanetData.getCount(),ADAPTER_COUNT);
	} // end of testPreConditions() method definition
	
	public void testSpinnerUI() {

		mActivity.runOnUiThread(
			new Runnable() {
				public void run() {
					mSpinner.requestFocus();
					mSpinner.setSelection(INITIAL_POSITION);
				} // end of run() method definition
			} // end of anonymous Runnable object instantiation
	    ); // end of invocation of runOnUiThread
		
		// Activate the spinner by clicking the center keypad key

        this.sendKeys(KeyEvent.KEYCODE_DPAD_CENTER);

        // send 5 down arrow keys to the spinner

        for (int i = 1; i <= TEST_POSITION; i++) {

            this.sendKeys(KeyEvent.KEYCODE_DPAD_DOWN);
        }

        // select the item at the current spinner position

        this.sendKeys(KeyEvent.KEYCODE_DPAD_CENTER);

        // get the position of the selected item

        mPos = mSpinner.getSelectedItemPosition();
        
        mSelection = (String)mSpinner.getItemAtPosition(mPos);

        /*
         * Get the TextView widget that displays the result of selecting an item from the spinner
         */

        TextView resultView =
                (TextView) mActivity.findViewById(com.android.example.spinner.R.id.SpinnerResult);

        // Get the String value in the EditText object

        String resultText = (String) resultView.getText();

        /*
         * Confirm that the EditText contains the same value as the data in the mLocalAdapter
         */

        assertEquals(resultText,mSelection);
	}
	
	public void testStateDestroy() {

        /*
         * Set the position and value of the spinner in the Activity. The test runner's
         * instrumentation enables this by running the test app and the main app in the same
         * process.
         */


        mActivity.setSpinnerPosition(TEST_STATE_DESTROY_POSITION);

        mActivity.setSpinnerSelection(TEST_STATE_DESTROY_SELECTION);

        // Halt the Activity by calling Activity.finish() on it

        mActivity.finish();

        // Restart the activity by calling ActivityInstrumentationTestCase2.getActivity()

        mActivity = this.getActivity();

        /*
         * Get the current position and selection from the activity.
         */

        int currentPosition = mActivity.getSpinnerPosition();
        String currentSelection = mActivity.getSpinnerSelection();

        // test that they are the same.

        assertEquals(TEST_STATE_DESTROY_POSITION, currentPosition);

        assertEquals(TEST_STATE_DESTROY_SELECTION, currentSelection);
    }
	
	@UiThreadTest
	public void testStatePause() {

        /*
         * Get the instrumentation object for this application. This object
         * does all the instrumentation work for the test runner
         */

        Instrumentation instr = this.getInstrumentation();

        /*
         * Set the activity's fields for the position and value of the spinner
         */

        mActivity.setSpinnerPosition(TEST_STATE_PAUSE_POSITION);

        mActivity.setSpinnerSelection(TEST_STATE_PAUSE_SELECTION);

        /*
         *  Use the instrumentation to onPause() on the currently running Activity.
         *  This analogous to calling finish() in the testStateDestroy() method.
         *  This way demonstrates using the test class' instrumentation.
         */

        instr.callActivityOnPause(mActivity);

        /*
         * Set the spinner to a test position
         */

        mActivity.setSpinnerPosition(0);
        mActivity.setSpinnerSelection("");

        /*
         * Call the activity's onResume() method. This forces the activity
         * to restore its state.
         */

        instr.callActivityOnResume(mActivity);

        /*
         * Get the current state of the spinner
         */

        int currentPosition = mActivity.getSpinnerPosition();

        String currentSelection = mActivity.getSpinnerSelection();

        assertEquals(TEST_STATE_PAUSE_POSITION,currentPosition);
        assertEquals(TEST_STATE_PAUSE_SELECTION,currentSelection);
	}
}
