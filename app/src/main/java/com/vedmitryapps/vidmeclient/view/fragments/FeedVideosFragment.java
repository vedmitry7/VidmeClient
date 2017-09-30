package com.vedmitryapps.vidmeclient.view.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RelativeLayout;

import com.vedmitryapps.vidmeclient.R;
import com.vedmitryapps.vidmeclient.model.api.ApiFactory;
import com.vedmitryapps.vidmeclient.model.api.VidmeService;
import com.vedmitryapps.vidmeclient.model.objects.AuthResponse;
import com.vedmitryapps.vidmeclient.model.objects.Video;
import com.vedmitryapps.vidmeclient.model.objects.VidmeResponse;
import com.vedmitryapps.vidmeclient.view.adapters.RecyclerViewAdapter;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;


public class FeedVideosFragment extends Fragment {

    @BindView(R.id.loginBtn)
    Button loginBtn;

    @BindView(R.id.loginEt)
    EditText loginEt;
    @BindView(R.id.passwordEt)
    EditText passwordEt;

    @BindView(R.id.login_container)
    RelativeLayout loginContainer;


    @BindView(R.id.featuredRecyclerView)
    RecyclerView recyclerView;

    private List<Video> videos;
    RecyclerViewAdapter recyclerViewAdapter;

    private String token;

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable final ViewGroup container, @Nullable Bundle savedInstanceState) {
        Log.i("TAG22", "onCreateLogIn");

        View view = inflater.inflate(R.layout.log_in_fragment, container, false);
        ButterKnife.bind(this, view);

        initView();

        loginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Retrofit builder = new Retrofit.Builder()
                        .baseUrl("https://api.vid.me/")
                        .addConverterFactory(GsonConverterFactory.create())
                        .build();

                VidmeService client = builder.create(VidmeService.class);

                client.createAuth(loginEt.getText().toString(), passwordEt.getText().toString()).enqueue(new Callback<AuthResponse>() {
                    @Override
                    public void onResponse(Call<AuthResponse> call, Response<AuthResponse> response) {
                        AuthResponse authResponse = response.body();

                        if(authResponse.getStatus() == true){
                            loginContainer.setVisibility(View.GONE);
                            token = authResponse.getAuth().getToken();
                            onLoadMore();
                            recyclerView.setVisibility(View.VISIBLE);
                            //container.setVisibility(View.INVISIBLE);
                        }
                    }

                    @Override
                    public void onFailure(Call<AuthResponse> call, Throwable t) {
                    }
                });

            }
        });

        return view;
    }

    private void initView() {
        videos = new ArrayList<>();

        recyclerViewAdapter = new RecyclerViewAdapter(getContext(), videos);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(recyclerViewAdapter);
    }

    void onLoadMore(){
        ApiFactory.getService().getFeedVideo(0, 10, token).enqueue(new Callback<VidmeResponse>() {
            @Override
            public void onResponse(Call<VidmeResponse> call, Response<VidmeResponse> response) {

                Log.i("TAG22", "Feed Response");

                VidmeResponse vidmeResponse = response.body();

                Log.i("TAG22", String.valueOf(vidmeResponse.getStatus()));
                Log.i("TAG22", String.valueOf(vidmeResponse.getVideos().size()));
                videos = vidmeResponse.getVideos();
                recyclerViewAdapter.update(videos);
            }

            @Override
            public void onFailure(Call<VidmeResponse> call, Throwable t) {
                Log.i("TAG22", "fail");
                Log.i("TAG22", t.getMessage());
            }
        });
    }
}