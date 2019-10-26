package com.devmohamedibrahim1997.populartest.UI;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.devmohamedibrahim1997.populartest.BottomNavigationFragments.NowPlayingFragment;
import com.devmohamedibrahim1997.populartest.BottomNavigationFragments.PopularFragment;
import com.devmohamedibrahim1997.populartest.BottomNavigationFragments.TopRatedFragment;
import com.devmohamedibrahim1997.populartest.BottomNavigationFragments.UpcomingFragment;
import com.devmohamedibrahim1997.populartest.R;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationView;
import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import butterknife.BindView;
import butterknife.ButterKnife;


public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener,
        BottomNavigationView.OnNavigationItemSelectedListener {

    @BindView(R.id.mainBottomNavigationView)
    BottomNavigationView bottomNavigationView;
    @BindView(R.id.search_bar)
    SearchView searchView;
    @BindView(R.id.mainToolBar)
    Toolbar toolbar;
    @BindView(R.id.mainNavigationView)
    NavigationView navigationView;
    @BindView(R.id.drawerLayout)
    DrawerLayout drawerLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction().replace(R.id.fragmentContainer, new NowPlayingFragment()).commit();
        }
        setSearchView();
        setNavigationDrawer();
        setBottomNavigationView();
    }

    public void setSearchView() {
        searchView.onActionViewExpanded();
        searchView.clearFocus();
        searchView.setIconifiedByDefault(false);

        setSearchViewOnClickListener(searchView, view -> startActivity(new Intent(MainActivity.this, SearchActivity.class)));
    }

    public static void setSearchViewOnClickListener(View v, View.OnClickListener listener) {
        if (v instanceof ViewGroup) {
            ViewGroup group = (ViewGroup) v;
            int count = group.getChildCount();
            for (int i = 0; i < count; i++) {
                View child = group.getChildAt(i);
                if (child instanceof LinearLayout || child instanceof RelativeLayout) {
                    setSearchViewOnClickListener(child, listener);
                }
                if (child instanceof TextView) {
                    TextView text = (TextView) child;
                    text.setFocusable(false);
                }
                child.setOnClickListener(listener);
            }
        }
    }

    @SuppressLint("SetTextI18n")
    public void setNavigationDrawer() {
        setSupportActionBar(toolbar);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawerLayout, toolbar,
                R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawerLayout.addDrawerListener(toggle);
        toggle.syncState();
        navigationView.setNavigationItemSelectedListener(this);

        try {
            View headerView = navigationView.getHeaderView(0);
            TextView textView = headerView.findViewById(R.id.nameTextView);
            textView.setText("User Name");
        }catch (IndexOutOfBoundsException e){
            Log.e("setNavigationDrawer: ", e.getMessage() );
        }

    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {

        switch (menuItem.getItemId()) {
            case R.id.nav_profile:
                break;
            case R.id.nav_watchLater:
                startActivity(new Intent(MainActivity.this, WatchLaterActivity.class));
                break;

            case R.id.nav_settings:
                break;

            case R.id.nav_share:
                break;

            case R.id.nav_aboutus:
                break;

        }

        drawerLayout.closeDrawer(GravityCompat.START);
        return true;
    }

    @Override
    public void onBackPressed() {
        if (drawerLayout.isDrawerOpen(GravityCompat.START)) {
            drawerLayout.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    public void setBottomNavigationView() {
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            Fragment fragment = null;

            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                switch (menuItem.getItemId()) {
                    case R.id.bottom_nav_nowPlaying:
                        fragment = new NowPlayingFragment();
                        break;
                    case R.id.bottom_nav_popular:
                        fragment = new PopularFragment();
                        break;
                    case R.id.bottom_nav_topRated:
                        fragment = new TopRatedFragment();
                        break;
                    case R.id.bottom_nav_upcoming:
                        fragment = new UpcomingFragment();
                        break;
                }
                getSupportFragmentManager().beginTransaction().replace(R.id.fragmentContainer, fragment).commit();
                return true;
            }
        });

        bottomNavigationView.setOnNavigationItemReselectedListener(menuItem -> {

        });
    }
}
