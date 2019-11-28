package com.devmohamedibrahim1997.populartest.ui.search;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import com.devmohamedibrahim1997.populartest.R;
import com.devmohamedibrahim1997.populartest.adapter.SearchAdapter;
import com.devmohamedibrahim1997.populartest.databinding.ActivitySearchBinding;
import com.devmohamedibrahim1997.populartest.ui.details.DetailsActivity;
import com.devmohamedibrahim1997.populartest.ui.main.MovieViewModel;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.appcompat.widget.Toolbar;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import static com.devmohamedibrahim1997.populartest.utils.Constant.MOVIE_ID;
import static com.devmohamedibrahim1997.populartest.utils.HelperClass.hideKeyboard;
import static com.devmohamedibrahim1997.populartest.utils.HelperClass.isNetworkAvailable;
import static com.devmohamedibrahim1997.populartest.utils.HelperClass.showSnackBar;


public class SearchActivity extends AppCompatActivity {

    private ActivitySearchBinding searchBinding;
    private SearchAdapter searchAdapter;
    private MovieViewModel movieViewModel;
    private SearchView searchView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        searchBinding = DataBindingUtil.setContentView(this, R.layout.activity_search);
        initViewModel();
        initToolBar();
        initSearchView();
        initSearchRecyclerView();
    }

    private void initViewModel() {
        movieViewModel = ViewModelProviders.of(this).get(MovieViewModel.class);
    }

    private void initToolBar() {
        setSupportActionBar(searchBinding.searchToolBar);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayShowHomeEnabled(true);
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }
    }

    private void initSearchView() {
        searchView = searchBinding.searchSearchView;
        searchView.onActionViewExpanded();
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                hideKeyboard(SearchActivity.this);
                searchView.clearFocus();
                searchMovie(query);
                return false;
            }

            @Override
            public boolean onQueryTextChange(String query) {
                return false;
            }
        });
    }

    private void initSearchRecyclerView() {
        RecyclerView searchRecyclerView = searchBinding.searchRecyclerView;
        searchRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        searchAdapter = new SearchAdapter(this);
        searchRecyclerView.setAdapter(searchAdapter);
    }

    private void searchMovie(String query) {
        if (isNetworkAvailable(this)) {
            movieViewModel.removeLiveData();
            searchAdapter.submitList(null);
            movieViewModel.getMovies(query).observe(this, movies -> {
                searchAdapter.submitList(movies);
                searchAdapter.notifyDataSetChanged();
                onMovieClick(query);
            });
        } else {
            showSnackBar(this);
        }
    }

    private void onMovieClick(String query) {
        searchAdapter.setOnItemClickListener((position, v) -> {
            Intent intent = new Intent(SearchActivity.this, DetailsActivity.class);
            if (movieViewModel.getMovies(query).getValue() != null) {
                intent.putExtra(MOVIE_ID, movieViewModel.getMovies(query).getValue().get(position).getId());
            }
            startActivity(intent);
        });
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == android.R.id.home) {
            this.finish();
        }
        return super.onOptionsItemSelected(item);
    }
}
