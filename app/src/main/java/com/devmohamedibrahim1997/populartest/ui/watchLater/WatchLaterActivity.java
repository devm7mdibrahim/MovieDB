package com.devmohamedibrahim1997.populartest.ui.watchLater;

import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.ItemTouchHelper;
import android.view.MenuItem;
import com.devmohamedibrahim1997.populartest.R;
import com.devmohamedibrahim1997.populartest.database.WatchLaterMoviesViewModel;
import com.devmohamedibrahim1997.populartest.adapter.WatchLaterAdapter;
import com.devmohamedibrahim1997.populartest.databinding.ActivityWatchLaterBinding;
import com.devmohamedibrahim1997.populartest.ui.details.DetailsActivity;

import static com.devmohamedibrahim1997.populartest.utils.Constant.MOVIE_ID;
import static com.devmohamedibrahim1997.populartest.utils.HelperClass.showToast;


public class WatchLaterActivity extends AppCompatActivity {

    ActivityWatchLaterBinding watchLaterBinding;
    WatchLaterMoviesViewModel movieViewModel;
    private WatchLaterAdapter adapter;
    private RecyclerView recyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        watchLaterBinding = DataBindingUtil.setContentView(this,R.layout.activity_watch_later);

        initActionBar();
        initRecyclerView();
        initViewModel();
        getMovies();
    }

    private void initActionBar() {
        if(getSupportActionBar() != null) {
            getSupportActionBar().setDisplayShowHomeEnabled(true);
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            setTitle("Watch Later Movies");
        }
    }

    private void initRecyclerView() {
        recyclerView = watchLaterBinding.watchLaterRecyclerView;
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        adapter = new WatchLaterAdapter(this);
        recyclerView.setAdapter(adapter);
    }

    private void initViewModel() {
        movieViewModel = ViewModelProviders.of(this).get(WatchLaterMoviesViewModel.class);
    }

    private void getMovies(){
        movieViewModel.getAllMovies().observe(this, movies -> {
            adapter.setData(movies);
            adapter.notifyDataSetChanged();
        });

        onMovieClick();
        onMovieSwiped();
    }

    private void onMovieClick() {
        adapter.setOnItemClickListener((position, v) -> {
            if (movieViewModel.getAllMovies().getValue() != null) {
                Intent intent = new Intent(WatchLaterActivity.this, DetailsActivity.class);
                intent.putExtra(MOVIE_ID, movieViewModel.getAllMovies().getValue().get(position).getId());
                startActivity(intent);
            }
        });
    }

    private void onMovieSwiped(){
        new ItemTouchHelper(new ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT) {
            @Override
            public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder viewHolder1) {
                return false;
            }

            @Override
            public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int i) {
                movieViewModel.delete(adapter.getMovieAt(viewHolder.getAdapterPosition()));
                showToast(WatchLaterActivity.this, "Movie Deleted");
            }
        }).attachToRecyclerView(recyclerView);
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
