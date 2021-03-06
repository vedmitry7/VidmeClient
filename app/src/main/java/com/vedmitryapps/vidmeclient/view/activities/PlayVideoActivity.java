package com.vedmitryapps.vidmeclient.view.activities;

import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.devbrackets.android.exomedia.listener.OnPreparedListener;
import com.devbrackets.android.exomedia.ui.widget.VideoView;
import com.vedmitryapps.vidmeclient.R;

import butterknife.BindView;
import butterknife.ButterKnife;

public class PlayVideoActivity extends AppCompatActivity implements OnPreparedListener {

    @BindView(R.id.video_view)
    VideoView videoView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_play_video);
        ButterKnife.bind(this);
        videoView.setOnPreparedListener(this);
        videoView.setVideoURI(Uri.parse(getIntent().getStringExtra("urlFull")));
    }

    @Override
    public void onPrepared() {
        videoView.start();
    }


}