package com.vedmitryapps.vidmeclient.view.fragments;

import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.vedmitryapps.vidmeclient.R;
import com.vedmitryapps.vidmeclient.model.api.ApiFactory;
import com.vedmitryapps.vidmeclient.model.objects.Video;
import com.vedmitryapps.vidmeclient.model.objects.VidmeResponse;
import com.vedmitryapps.vidmeclient.view.activities.MainActivity;
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

import static com.vedmitryapps.vidmeclient.App.DOWNLOAD_LIMIT;


public class NewVideosFragment extends BaseFragment implements SwipeRefreshLayout.OnRefreshListener {

    @BindView(R.id.newVideoRecyclerView)
    RecyclerView recyclerView;
    @BindView(R.id.newVideoRefresh)
    SwipeRefreshLayout mSwipeRefreshLayout;

    private List<Video> videos;
    private RecyclerViewAdapter recyclerViewAdapter;
    private int offset;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_new_video, container, false);
        ButterKnife.bind(this, view);
        videos = new ArrayList<>();

        initRecyclerView();

        mSwipeRefreshLayout.setColorSchemeColors(Color.RED, Color.GREEN, Color.BLUE, Color.MAGENTA);
        mSwipeRefreshLayout.setOnRefreshListener(this);

        if(hasConnection(getContext())) {
            loadDate();
        } else {
            ((MainActivity)getActivity()).showSnackBar(getString(R.string.no_connection));
        }

        return view;
    }

    private void initRecyclerView() {
        recyclerViewAdapter = new RecyclerViewAdapter(getContext(), videos);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(recyclerViewAdapter);

        recyclerView.addOnItemTouchListener(
                new RecyclerItemClickListener(getContext(), recyclerView , new RecyclerItemClickListener.OnItemClickListener() {
                    @Override public void onItemClick(View view, int position) {
                        if(videos.get(position).getYoutubeOverrideSource() != null){
                            startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse(videos.get(position).getYoutubeOverrideSource())));
                        } else {
                            startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse(videos.get(position).getFullUrl())));
                        }
                          /* Intent intent = new Intent(getContext(), PlayVideoActivity.class);
                        intent.putExtra("url", videos.get(position).getUrl());
                        intent.putExtra("urlFull", videos.get(position).getFullUrl());
                        startActivity(intent);*/
                    }

                    @Override public void onLongItemClick(View view, int position) {
                    }
                })
        );

        recyclerView.setOnScrollListener(new EndlessRecyclerViewScrollListener(layoutManager) {
            @Override
            public void onLoadMore(int page, int totalItemsCount) {
                loadDate();
            }
        });
    }

    void loadDate(){
        ApiFactory.getService().getNewVideo(offset, DOWNLOAD_LIMIT).enqueue(new Callback<VidmeResponse>() {
            @Override
            public void onResponse(Call<VidmeResponse> call, Response<VidmeResponse> response) {
                VidmeResponse vidmeResponse = response.body();
                videos.addAll(vidmeResponse.getVideos());
                recyclerViewAdapter.update(videos);
                offset += DOWNLOAD_LIMIT;
            }

            @Override
            public void onFailure(Call<VidmeResponse> call, Throwable t) {
            }
        });
    }

    @Override
    public void onRefresh() {
        videos.clear();
        recyclerView.getAdapter().notifyDataSetChanged();
        if(hasConnection(getContext())) {
            loadDate();
        } else {
            ((MainActivity)getActivity()).showSnackBar(getString(R.string.no_connection));
        }
        mSwipeRefreshLayout.setRefreshing(false);
    }

}
