package com.devmohamedibrahim1997.populartest.UI;

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
import android.widget.Toast;
import com.devmohamedibrahim1997.populartest.R;
import com.devmohamedibrahim1997.populartest.Room.WatchLaterMoviesViewModel;
import com.devmohamedibrahim1997.populartest.adapter.WatchLaterAdapter;
import com.devmohamedibrahim1997.populartest.databinding.ActivityWatchLaterBinding;


public class WatchLaterActivity extends AppCompatActivity {

    ActivityWatchLaterBinding watchLaterBinding;
    private WatchLaterAdapter adapter;
    private RecyclerView recyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        watchLaterBinding = DataBindingUtil.setContentView(this,R.layout.activity_watch_later);

        initRecyclerView();
        initActionBar();
        initViewModel();
    }

    private void initViewModel() {
        WatchLaterMoviesViewModel movieViewModel = ViewModelProviders.of(this).get(WatchLaterMoviesViewModel.class);

        //getMoviesFromRoomDataBase
        movieViewModel.getAllMovies().observe(this, movieEntities -> {
            adapter.setData(movieEntities);
            adapter.notifyDataSetChanged();
        });

        //on movie click listener
        adapter.setOnItemClickListener((position, v) -> {
            Intent intent = new Intent(WatchLaterActivity.this, DetailsActivity.class);
            if (movieViewModel.getAllMovies() != null) {
                intent.putExtra("movieId", movieViewModel.getAllMovies().getValue().get(position).getId());
            }
            startActivity(intent);
        });


        //on movie swapped left delete it from data base
        new ItemTouchHelper(new ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT) {
            @Override
            public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder viewHolder1) {
                return false;
            }

            @Override
            public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int i) {
                movieViewModel.delete(adapter.getMovieAt(viewHolder.getAdapterPosition()));
                Toast.makeText(WatchLaterActivity.this, "Movie Deleted", Toast.LENGTH_SHORT).show();
            }
        }).attachToRecyclerView(recyclerView);
    }

    private void initRecyclerView() {
        recyclerView = watchLaterBinding.watchLaterRecyclerView;
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        adapter = new WatchLaterAdapter(this);
        recyclerView.setAdapter(adapter);
    }

    private void initActionBar() {
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        setTitle("Watch Later Movies");
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
