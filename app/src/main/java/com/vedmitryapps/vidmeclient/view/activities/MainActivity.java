package com.vedmitryapps.vidmeclient.view.activities;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageButton;
import android.widget.PopupMenu;

import com.vedmitryapps.vidmeclient.App;
import com.vedmitryapps.vidmeclient.R;
import com.vedmitryapps.vidmeclient.view.adapters.PagerAdapter;
import com.vedmitryapps.vidmeclient.view.fragments.FeedVideosFragment;

import butterknife.BindView;
import butterknife.ButterKnife;

import static com.vedmitryapps.vidmeclient.App.APP_PREFERENCES;
import static com.vedmitryapps.vidmeclient.App.KEY_LOGIN;
import static com.vedmitryapps.vidmeclient.App.KEY_PASSWORD;
import static com.vedmitryapps.vidmeclient.App.KEY_TOKEN;
import static com.vedmitryapps.vidmeclient.App.KEY_TOKEN_END;

public class MainActivity extends AppCompatActivity {

    @BindView(R.id.menuButton)
    ImageButton button;
    @BindView(R.id.coordinatorLayout)
    CoordinatorLayout coordinatorLayout;
    SharedPreferences sharedPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        final PagerAdapter pagerAdapter = new PagerAdapter(getSupportFragmentManager());
        final ViewPager viewPager = (ViewPager) findViewById(R.id.viewPager);
        viewPager.setAdapter(pagerAdapter);

        TabLayout tabLayout = (TabLayout) findViewById(R.id.tabs);
        tabLayout.setupWithViewPager(viewPager);

        sharedPreferences = getSharedPreferences(APP_PREFERENCES, Context.MODE_PRIVATE);
        if(sharedPreferences.getString(App.KEY_TOKEN, null) == null){
            setLogoutButtonVisibility(false);
        }

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                PopupMenu popupMenu = new PopupMenu(getApplicationContext(), view );
                popupMenu.inflate(R.menu.menu);
                popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                    @Override
                    public boolean onMenuItemClick(MenuItem item) {
                        if(item.getItemId() == R.id.logout){
                            setLogoutButtonVisibility(false);
                            ((FeedVideosFragment) pagerAdapter.getActiveFragment(viewPager, 2)).showLoginContainer();
                            clearAuthDate();
                        }
                        return false;
                    }
                });
                popupMenu.show();
            }
        });

    }

    private void clearAuthDate() {
        SharedPreferences.Editor editor = getSharedPreferences(APP_PREFERENCES, Context.MODE_PRIVATE).edit();
        editor.putString(KEY_LOGIN, null);
        editor.putString(KEY_PASSWORD, null);
        editor.putString(KEY_TOKEN, null);
        editor.putString(KEY_TOKEN_END, null);
        editor.commit();
    }

    public void setLogoutButtonVisibility(boolean b){
        if(b){
            button.setVisibility(View.VISIBLE);
        } else {
            button.setVisibility(View.GONE);
        }
    }

    public void showSnackBar(String s){
        Snackbar.make(coordinatorLayout, s, Snackbar.LENGTH_LONG).show();
    }

}