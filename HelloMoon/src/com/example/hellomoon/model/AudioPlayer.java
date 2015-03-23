package com.example.hellomoon.model;

import com.example.hellomoon.R;

import android.content.Context;
import android.media.MediaPlayer;

public class AudioPlayer {
	
	private MediaPlayer mPlayer;
	
	public void stop() {
		if (this.mPlayer != null) {
			mPlayer.release();
			mPlayer = null;
		}
	}
	
	public void play(Context c) {
		if (mPlayer == null) {
			mPlayer = MediaPlayer.create(c, R.raw.one_small_step);
			mPlayer.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
				
				@Override
				public void onCompletion(MediaPlayer mp) {
					stop();
				}
			});
		}
		
		// If mPlayer is paused, start() will resume from where is paused
		// If not, start() will start from the beginning
		mPlayer.start();
	}
	
	public void pause() {
		if (mPlayer == null)
			return;
		mPlayer.pause();
	}
}
