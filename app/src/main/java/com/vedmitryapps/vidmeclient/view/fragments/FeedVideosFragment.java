package com.vedmitryapps.vidmeclient.view.fragments;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.vedmitryapps.vidmeclient.R;
import com.vedmitryapps.vidmeclient.model.api.ApiFactory;
import com.vedmitryapps.vidmeclient.model.objects.AuthResponse;
import com.vedmitryapps.vidmeclient.model.objects.Video;
import com.vedmitryapps.vidmeclient.model.objects.VidmeError;
import com.vedmitryapps.vidmeclient.model.objects.VidmeResponse;
import com.vedmitryapps.vidmeclient.view.activities.MainActivity;
import com.vedmitryapps.vidmeclient.view.adapters.RecyclerViewAdapter;
import com.vedmitryapps.vidmeclient.view.listeners.EndlessRecyclerViewScrollListener;
import com.vedmitryapps.vidmeclient.view.listeners.RecyclerItemClickListener;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.vedmitryapps.vidmeclient.App.*;


public class FeedVideosFragment extends BaseFragment implements SwipeRefreshLayout.OnRefreshListener {

    @BindView(R.id.loginBtn)
    Button loginBtn;
    @BindView(R.id.loginEt)
    EditText loginEt;
    @BindView(R.id.passwordEt)
    EditText passwordEt;
    @BindView(R.id.login_container)
    LinearLayout loginContainer;
    @BindView(R.id.feedRecyclerView)
    RecyclerView recyclerView;
    @BindView(R.id.feedVideoRefresh)
    SwipeRefreshLayout mSwipeRefreshLayout;

    private RecyclerViewAdapter recyclerViewAdapter;
    private SharedPreferences sharedPreferences;
    private List<Video> videos;
    private String token;
    private int offset;


    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable final ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_feed_video, container, false);
        ButterKnife.bind(this, view);

        videos = new ArrayList<>();
        initRecyclerView();

        mSwipeRefreshLayout.setColorSchemeColors(Color.RED, Color.GREEN, Color.BLUE, Color.MAGENTA);
        mSwipeRefreshLayout.setOnRefreshListener(this);

        sharedPreferences = getContext().getSharedPreferences(APP_PREFERENCES, Context.MODE_PRIVATE);

        token = sharedPreferences.getString(KEY_TOKEN, null);
        if(token == null){
            showLoginContainer();
        } else {
            hideLoginContainer();
            createAuth(sharedPreferences.getString(KEY_LOGIN, null), sharedPreferences.getString(KEY_PASSWORD, null));
            loadDate();
        }

        loginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
              createAuth(loginEt.getText().toString(),
                      passwordEt.getText().toString());
            }
        });

        return view;
    }

    private void createAuth(final String login, final String password) {
        if(!hasConnection(getContext())) {
            ((MainActivity)getActivity()).showSnackBar(getString(R.string.no_connection));
            return;
        }
        Log.i("TAG22", login + " : " + password);

        if(login == null && password == null){
            showLoginContainer();
            ((MainActivity)getActivity()).showSnackBar(getString(R.string.auth_error));

        }

        ApiFactory.getService().createAuth(login, password).enqueue(new Callback<AuthResponse>() {
            @Override
            public void onResponse(Call<AuthResponse> call, Response<AuthResponse> response) {
                AuthResponse authResponse = response.body();

                if(authResponse == null){
                    String s = null;
                    try {
                        s = response.errorBody().string();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    Gson gson = new GsonBuilder().setPrettyPrinting().create();
                    VidmeError error =  gson.fromJson(s, VidmeError.class);
                    ((MainActivity)getActivity()).showSnackBar(error.getError());
                    return;
                }
                if(authResponse != null && authResponse.getStatus() == true){
                    token = authResponse.getAuth().getToken();
                    saveAuthDate(login, password, token, authResponse.getAuth().getExpires());
                    loadDate();
                    hideLoginContainer();
                    ((MainActivity) getActivity()).setLogoutButtonVisibility(true);
                }
            }

            @Override
            public void onFailure(Call<AuthResponse> call, Throwable t) {
                ((MainActivity)getActivity()).showSnackBar(t.getMessage());
            }
        });
    }

    private void hideLoginContainer() {
        loginContainer.setVisibility(View.GONE);
        recyclerView.setVisibility(View.VISIBLE);
        mSwipeRefreshLayout.setEnabled(true);
    }
    public void showLoginContainer() {
        recyclerView.setVisibility(View.GONE);
        loginContainer.setVisibility(View.VISIBLE);
        mSwipeRefreshLayout.setEnabled(false);
    }

    public void saveAuthDate(String login, String password, String token, String date) {
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(KEY_LOGIN, login);
        editor.putString(KEY_PASSWORD, password);
        editor.putString(KEY_TOKEN, token);
        editor.putString(KEY_TOKEN_END, date);
        editor.commit();
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

        recyclerView.addOnItemTouchListener(
                new RecyclerItemClickListener(getContext(), recyclerView , new RecyclerItemClickListener.OnItemClickListener() {
                    @Override public void onItemClick(View view, int position) {
                        if(videos.get(position).getYoutubeOverrideSource() != null){
                            startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse(videos.get(position).getYoutubeOverrideSource())));
                        } else {
                            startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse(videos.get(position).getFullUrl())));
                        }
                    }

                    @Override public void onLongItemClick(View view, int position) {
                    }
                })
        );
    }

    void loadDate(){
        ApiFactory.getService().getFeedVideo(offset, DOWNLOAD_LIMIT, token).enqueue(new Callback<VidmeResponse>() {
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