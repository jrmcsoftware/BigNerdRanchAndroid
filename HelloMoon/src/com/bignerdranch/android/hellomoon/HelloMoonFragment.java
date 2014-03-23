package com.bignerdranch.android.hellomoon;

import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.VideoView;

public class HelloMoonFragment extends Fragment {

    private final Uri resourceUri = Uri.parse("android.resource://" + this.getClass().getPackage().getName() + "/" + R.raw.fruit_bat);


    private VideoPlayer mPlayer;
    
    private Button mPlayButton;
    private Button mPauseButton;
    private Button mStopButton;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_hello_moon, container, false);

        mPlayButton = (Button) view.findViewById(R.id.play_button);
        mPauseButton = (Button) view.findViewById(R.id.pause_button);
        mStopButton = (Button) view.findViewById(R.id.stop_button);
        VideoView mVideoView = (VideoView) view.findViewById(R.id.video_view);
        

        mPlayButton.setOnClickListener(new AudioPlayOnClickListener());
        mPauseButton.setOnClickListener(new AudioPauseOnClickListener());
        mStopButton.setOnClickListener(new AudioStopOnClickListener());
        
        mPlayer = new VideoPlayer(mVideoView, resourceUri);

        return view;
    }

    class AudioPlayOnClickListener implements View.OnClickListener {

        @Override
        public void onClick(View v) {
            mPlayer.play();
        }
    }

    class AudioPauseOnClickListener implements View.OnClickListener {

        @Override
        public void onClick(View v) {
            mPlayer.pause();
        }
    }

    class AudioStopOnClickListener implements View.OnClickListener {

        @Override
        public void onClick(View v) {
            mPlayer.stop();
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mPlayer.stop();
    }

}
