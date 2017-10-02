package com.vedmitryapps.vidmeclient.view.activities;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.pm.ActivityInfo;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.Window;
import android.view.WindowManager;
import android.widget.MediaController;
import android.widget.VideoView;

import com.vedmitryapps.vidmeclient.R;

public class FullVideo extends Activity {

    ProgressDialog pDialog;
    VideoView videoview;
    private int position = 0;
    String videoUrl, imageUrl, cid;

    private MediaController mediaControls;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_full_video);

        videoUrl = "https:\\/\\/vid.me\\/e\\/LT6mi";

        if (mediaControls == null) {
            mediaControls = new MediaController(FullVideo.this);
        }

        videoview = (VideoView) findViewById(R.id.videoView);
        // Execute StreamVideo AsyncTask

        // Create a progressbar
        pDialog = new ProgressDialog(FullVideo.this);
        // Set progressbar title
        pDialog.setTitle("Streaming video, please wait.");
        // Set progressbar message
        pDialog.setMessage("Buffering...");
        pDialog.setMessage("Buffering...");
        pDialog.setIndeterminate(false);
        pDialog.setCancelable(false);
        // Show progressbar
        pDialog.show();

        try {

            mediaControls.setAnchorView(videoview);
            // Get the URL from String VideoURL
            Uri video = Uri.parse(videoUrl);
            videoview.setMediaController(mediaControls);
            // mediaControls.setMediaPlayer(videoview);
            videoview.setVideoURI(video);

        } catch (Exception e) {
            Log.e("Error", e.getMessage());
            e.printStackTrace();
        }

        videoview.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
            // Close the progress bar and play the video
            public void onPrepared(MediaPlayer mp) {
                pDialog.dismiss();
                videoview.requestFocus();
                videoview.seekTo(position);
                if (position == 0) {
                    videoview.start();
                } else {
                    videoview.pause();
                }
            }
        });

        videoview.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {

            @Override
            public void onCompletion(MediaPlayer mp) {
                // TODO Auto-generated method stub
                finish();

            }
        });

    }

    @Override
    public void onBackPressed() {
        // TODO Auto-generated method stub
        super.onBackPressed();
        finish();

    }

    @Override
    protected void onStart() {
        // TODO Auto-generated method stub
        super.onStart();
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
    }

    @Override
    protected void onRestart() {
        // TODO Auto-generated method stub
        super.onRestart();
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        // TODO Auto-generated method stub
        super.onSaveInstanceState(outState);
        outState.putInt("Position", videoview.getCurrentPosition());
        videoview.pause();

    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        // TODO Auto-generated method stub
        super.onRestoreInstanceState(savedInstanceState);
        position = savedInstanceState.getInt("Position");
        videoview.seekTo(position);
    }

}