package com.vedmitryapps.vidmeclient.view.fragments;

import android.content.Context;
import android.content.SharedPreferences;
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
import android.widget.Toast;

import com.vedmitryapps.vidmeclient.R;
import com.vedmitryapps.vidmeclient.model.api.ApiFactory;
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

import static com.vedmitryapps.vidmeclient.App.APP_PREFERENCES;
import static com.vedmitryapps.vidmeclient.App.KEY_LOGIN;
import static com.vedmitryapps.vidmeclient.App.KEY_PASSWORD;
import static com.vedmitryapps.vidmeclient.App.KEY_TOKEN;
import static com.vedmitryapps.vidmeclient.App.KEY_TOKEN_END;


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


    SharedPreferences sharedPreferences;
    private String token;

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable final ViewGroup container, @Nullable Bundle savedInstanceState) {
        Log.i("TAG22", "onCreateLogIn");
        View view = inflater.inflate(R.layout.log_in_fragment, container, false);
        ButterKnife.bind(this, view);
        sharedPreferences = getContext().getSharedPreferences(APP_PREFERENCES, Context.MODE_PRIVATE);

        initRecyclerView();

        token = sharedPreferences.getString(KEY_TOKEN, null);
        String tokenDate = sharedPreferences.getString(KEY_TOKEN_END, null);
        if(token == null){
            showLoginContainer();
        } else {
            hideLoginContainer();
            onLoadMore();
        }

        loginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                final String login = loginEt.getText().toString();
                final String password = passwordEt.getText().toString();

                ApiFactory.getService().createAuth(login, password).enqueue(new Callback<AuthResponse>() {
                    @Override
                    public void onResponse(Call<AuthResponse> call, Response<AuthResponse> response) {
                        AuthResponse authResponse = response.body();
                        Log.i("TAG22", String.valueOf("RESPONSE NULL - " + authResponse == null));
                        Log.i("TAG22", String.valueOf("RESPONSE STATUS NULL - " + authResponse.getStatus() == null));
                        if(authResponse.getStatus() == true){
                            token = authResponse.getAuth().getToken();
                            saveAuthDate(login, password, token, authResponse.getAuth().getExpires());
                            onLoadMore();
                            hideLoginContainer();
                        }
                    }

                    @Override
                    public void onFailure(Call<AuthResponse> call, Throwable t) {
                        Toast.makeText(getContext(),"Password/login error", Toast.LENGTH_LONG);
                    }
                });
            }
        });

        return view;
    }

    private void hideLoginContainer() {
        loginContainer.setVisibility(View.GONE);
        recyclerView.setVisibility(View.VISIBLE);
    }
    private void showLoginContainer() {
        loginContainer.setVisibility(View.VISIBLE);
        recyclerView.setVisibility(View.GONE);
    }

    private void saveAuthDate(String login, String password, String token, String date) {
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(KEY_LOGIN, login);
        editor.putString(KEY_PASSWORD, password);
        editor.putString(KEY_TOKEN, token);
        editor.putString(KEY_TOKEN_END, date);
        editor.commit();
    }

    private void initRecyclerView() {
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