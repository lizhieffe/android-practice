package com.example.hellomoon.fragment;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import com.example.hellomoon.R;
import com.example.hellomoon.model.AudioPlayer;

public class HelloMoonFragment extends Fragment {

	private AudioPlayer mPlayer = new AudioPlayer();
	private Button mPlayButton;
	private Button mPauseButton;
	private Button mStopButton;
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setRetainInstance(true);
	}
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup parent, Bundle savedInstanceState) {
		View v = inflater.inflate(R.layout.fragment_hello_moon, parent, false);
		
		mPlayButton = (Button)v.findViewById(R.id.hellomoon_playButton);
		mPlayButton.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View arg0) {
				mPlayer.play(getActivity());
			}
			
		});
		
		mPauseButton = (Button)v.findViewById(R.id.hellomoon_pauseButton);
		mPauseButton.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				mPlayer.pause();
			}
		});
		
		mStopButton = (Button)v.findViewById(R.id.hellomoon_stopButton);
		mStopButton.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				mPlayer.stop();
			}
		});
		
		return v;
	}
	
}
