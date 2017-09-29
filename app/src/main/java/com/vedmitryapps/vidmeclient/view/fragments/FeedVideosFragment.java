package com.vedmitryapps.vidmeclient.view.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.vedmitryapps.vidmeclient.R;

import butterknife.BindView;


public class FeedVideosFragment extends Fragment {

    @BindView(R.id.logIn)
    Button login;

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        Log.i("TAG22", "onCreateLogIn");

        View view = inflater.inflate(R.layout.log_in_fragment, container, false);
        return view;
    }
}