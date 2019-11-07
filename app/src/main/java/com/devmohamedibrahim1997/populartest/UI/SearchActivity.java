package com.devmohamedibrahim1997.populartest.UI;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.SearchView;

import com.devmohamedibrahim1997.populartest.R;
import com.devmohamedibrahim1997.populartest.adapter.SearchAdapter;
import com.devmohamedibrahim1997.populartest.databinding.ActivitySearchBinding;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import static com.devmohamedibrahim1997.populartest.Utils.HelperClass.hideKeyboard;
import static com.devmohamedibrahim1997.populartest.Utils.HelperClass.isNetworkAvailable;
import static com.devmohamedibrahim1997.populartest.Utils.HelperClass.showSnackBar;


public class SearchActivity extends AppCompatActivity {

    private SearchAdapter searchAdapter;
    private MovieViewModel movieViewModel;
    private SearchActivity activity;
    private RecyclerView searchRecyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ActivitySearchBinding searchBinding = DataBindingUtil.setContentView(this,R.layout.activity_search);
        searchRecyclerView = searchBinding.searchRecyclerView;
        activity = SearchActivity.this;
        initActionBar();
        initSearchRecyclerView();
    }

    void initViewModel(String query) {
        if (isNetworkAvailable(activity)) {
            movieViewModel = ViewModelProviders.of(this).get(MovieViewModel.class);
            movieViewModel.setLiveData();
            searchAdapter.submitList(null);
            getMovies(query);
            onMovieClick(query);

        } else {
            showSnackBar(activity);
        }
    }

    private void getMovies(String query) {
        movieViewModel.getMovies(query).observe(this, movies -> {
            searchAdapter.submitList(movies);
            searchAdapter.notifyDataSetChanged();
        });
    }

    private void onMovieClick(String query) {
        searchAdapter.setOnItemClickListener((position, v) -> {
            Intent intent = new Intent(SearchActivity.this, DetailsActivity.class);
            if (movieViewModel.getMovies(query).getValue() != null) {
                intent.putExtra("movieId", movieViewModel.getMovies(query).getValue().get(position).getId());
            }
            startActivity(intent);
        });
    }

    private void initSearchRecyclerView() {
        searchRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        searchAdapter = new SearchAdapter(this);
        searchRecyclerView.setAdapter(searchAdapter);
    }

    private void initActionBar() {
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    //init SearchView and get the query
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu, menu);
        MenuItem item = menu.findItem(R.id.search);
        SearchView searchView = (SearchView) item.getActionView();
        searchView.onActionViewExpanded();

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                initViewModel(query);
                searchView.clearFocus();
                hideKeyboard(activity);
                return false;
            }

            @Override
            public boolean onQueryTextChange(String query) {
                return false;
            }
        });
        return super.onCreateOptionsMenu(menu);
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
