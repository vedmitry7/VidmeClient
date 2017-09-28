package com.vedmitryapps.vidmeclient.view.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.vedmitryapps.vidmeclient.R;


public class FeaturedVideosFragment extends Fragment {

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
       //return super.onCreateView(inflater, container, savedInstanceState);

        View view = inflater.inflate(R.layout.fiture_video_fragment, container, false);
        RecyclerView recyclerView = (RecyclerView) view.findViewById(R.id.featuredRecyclerView);
        SwipeRefreshLayout swipeRefreshLayout = (SwipeRefreshLayout) view.findViewById(R.id.refresh);



        return view;
    }
}
