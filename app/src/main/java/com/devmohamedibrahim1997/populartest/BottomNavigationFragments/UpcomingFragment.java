package com.devmohamedibrahim1997.populartest.BottomNavigationFragments;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.devmohamedibrahim1997.populartest.UI.MovieViewModel;
import com.devmohamedibrahim1997.populartest.R;
import com.devmohamedibrahim1997.populartest.UI.DetailsActivity;
import com.devmohamedibrahim1997.populartest.adapter.MovieAdapter;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import butterknife.BindView;
import butterknife.ButterKnife;

import static com.devmohamedibrahim1997.populartest.Utils.HelperClass.getScreenOrientation;
import static com.devmohamedibrahim1997.populartest.Utils.HelperClass.isNetworkAvailable;
import static com.devmohamedibrahim1997.populartest.Utils.HelperClass.showSnackBar;

public class UpcomingFragment extends Fragment {

    @BindView(R.id.upcomingRecyclerView)
    RecyclerView movieRecyclerView;
    private MovieAdapter recyclerViewAdapter;
    private FragmentActivity activity;
    private MovieViewModel movieViewModel;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_upcoming, container, false);
        ButterKnife.bind(this, view);

        activity = getActivity();
        initRecyclerView();
        initViewModel();
        return view;
    }

    private void initViewModel() {
        if (isNetworkAvailable(activity)) {
            movieViewModel = ViewModelProviders.of(this).get(MovieViewModel.class);
            getMovies();
            onMovieClick();

        } else {
            showSnackBar(activity);
        }
    }

    private void getMovies() {
        movieViewModel.getMovies("upcoming").observe(this, movies -> {
            if (movies != null) {
                recyclerViewAdapter.submitList(movies);
                movieRecyclerView.setAdapter(recyclerViewAdapter);
            }
        });
    }

    private void onMovieClick() {
        recyclerViewAdapter.setOnItemClickListener((position, v) -> {
            Intent intent = new Intent(activity, DetailsActivity.class);
            if (movieViewModel.getMovies("upcoming").getValue() != null) {
                intent.putExtra("movieId", movieViewModel.getMovies("upcoming").getValue().get(position).getId());
            }
            startActivity(intent);
        });
    }

    private void initRecyclerView() {
        int spanCount = getScreenOrientation(activity);
        movieRecyclerView.setLayoutManager(new GridLayoutManager(activity, spanCount));
        recyclerViewAdapter = new MovieAdapter(activity);
    }
}