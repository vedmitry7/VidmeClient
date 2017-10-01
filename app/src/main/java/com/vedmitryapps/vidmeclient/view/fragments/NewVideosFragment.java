package com.vedmitryapps.vidmeclient.view.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.vedmitryapps.vidmeclient.R;
import com.vedmitryapps.vidmeclient.model.api.ApiFactory;
import com.vedmitryapps.vidmeclient.model.objects.Video;
import com.vedmitryapps.vidmeclient.model.objects.VidmeResponse;
import com.vedmitryapps.vidmeclient.view.activities.PlayVideoActivity;
import com.vedmitryapps.vidmeclient.view.adapters.RecyclerViewAdapter;
import com.vedmitryapps.vidmeclient.view.listeners.EndlessRecyclerViewScrollListener;
import com.vedmitryapps.vidmeclient.view.listeners.RecyclerItemClickListener;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class NewVideosFragment extends Fragment {

    @BindView(R.id.featuredRecyclerView)
    RecyclerView recyclerView;

    private List<Video> videos;
    private RecyclerViewAdapter recyclerViewAdapter;

    private int offset;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        Log.i("TAG22", "onCreateNewView");

        View view = inflater.inflate(R.layout.feature_video_fragment, container, false);
        ButterKnife.bind(this, view);
        videos = new ArrayList<>();

        recyclerViewAdapter = new RecyclerViewAdapter(getContext(), videos);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());

        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(recyclerViewAdapter);

        recyclerView.addOnItemTouchListener(
                new RecyclerItemClickListener(getContext(), recyclerView , new RecyclerItemClickListener.OnItemClickListener() {
                    @Override public void onItemClick(View view, int position) {
                        Intent intent = new Intent(getContext(), PlayVideoActivity.class);
                        intent.putExtra("url", videos.get(position).getUrl());
                        intent.putExtra("urlFull", videos.get(position).getFullUrl());
                        startActivity(intent);
                    }

                    @Override public void onLongItemClick(View view, int position) {
                    }
                })
        );

        recyclerView.setOnScrollListener(new EndlessRecyclerViewScrollListener(layoutManager) {
            @Override
            public void onLoadMore(int page, int totalItemsCount) {
                Log.i("TAG22", "LoadMore - page " + page);
                loadDate();
            }
        });

        SwipeRefreshLayout swipeRefreshLayout = (SwipeRefreshLayout) view.findViewById(R.id.refresh);

        loadDate();

        return view;
    }

    void loadDate(){
        Log.i("TAG22", "LoadDate");

        ApiFactory.getService().getFeaturedVideo(offset, 10).enqueue(new Callback<VidmeResponse>() {
            @Override
            public void onResponse(Call<VidmeResponse> call, Response<VidmeResponse> response) {
                VidmeResponse vidmeResponse = response.body();

                Log.i("TAG22", String.valueOf(vidmeResponse.getStatus()));
                Log.i("TAG22", String.valueOf(vidmeResponse.getVideos().size()));
                videos.addAll(vidmeResponse.getVideos());
                recyclerViewAdapter.update(videos);

                offset += 10;
                Log.i("TAG22", "offset = " + offset);
            }

            @Override
            public void onFailure(Call<VidmeResponse> call, Throwable t) {
                Log.i("TAG22", "fail");
                Log.i("TAG22", t.getMessage());
            }
        });
    }

}
