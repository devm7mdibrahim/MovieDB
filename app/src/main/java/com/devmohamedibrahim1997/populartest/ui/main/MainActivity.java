package com.devmohamedibrahim1997.populartest.ui.main;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.devmohamedibrahim1997.populartest.R;
import com.devmohamedibrahim1997.populartest.databinding.ActivityMainBinding;
import com.devmohamedibrahim1997.populartest.ui.search.SearchActivity;
import com.devmohamedibrahim1997.populartest.ui.watchLater.WatchLaterActivity;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.core.view.GravityCompat;
import androidx.databinding.DataBindingUtil;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;


public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener,
        BottomNavigationView.OnNavigationItemSelectedListener {

    private DrawerLayout drawerLayout;
    private ActivityMainBinding mainBinding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mainBinding = DataBindingUtil.setContentView(this, R.layout.activity_main);

        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction().replace(R.id.fragmentContainer, new NowPlayingFragment()).commit();
        }

        initSearchView();
        initNavigationDrawer();
        initBottomNavigationView();
    }

    public void initSearchView() {
        SearchView searchView = mainBinding.searchBar;
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

    public void initNavigationDrawer() {
        NavigationView navigationView = mainBinding.mainNavigationView;
        drawerLayout = mainBinding.drawerLayout;
        setSupportActionBar(mainBinding.mainToolBar);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawerLayout, mainBinding.mainToolBar,
                R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawerLayout.addDrawerListener(toggle);
        toggle.syncState();
        navigationView.setNavigationItemSelectedListener(this);

        View headerView = navigationView.getHeaderView(0);
        TextView textView = headerView.findViewById(R.id.nameTextView);
        textView.setText(R.string.user_name);
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {

        if (menuItem.getItemId() == R.id.nav_watchLater) {
            startActivity(new Intent(MainActivity.this, WatchLaterActivity.class));
        }

        drawerLayout.closeDrawer(GravityCompat.START);
        return true;
    }

    public void initBottomNavigationView() {
        BottomNavigationView bottomNavigationView = mainBinding.mainBottomNavigationView;
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

        bottomNavigationView.setOnNavigationItemReselectedListener(menuItem -> {});
    }

    @Override
    public void onBackPressed() {
        if (drawerLayout.isDrawerOpen(GravityCompat.START)) {
            drawerLayout.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }
}
