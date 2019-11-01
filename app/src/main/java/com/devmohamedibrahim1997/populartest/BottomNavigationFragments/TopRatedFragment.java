package com.devmohamedibrahim1997.populartest.BottomNavigationFragments;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.devmohamedibrahim1997.populartest.UI.MovieViewModel;
import com.devmohamedibrahim1997.populartest.R;
import com.devmohamedibrahim1997.populartest.UI.DetailsActivity;
import com.devmohamedibrahim1997.populartest.adapter.MovieAdapter;
import com.devmohamedibrahim1997.populartest.databinding.FragmentTopRatedBinding;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import static com.devmohamedibrahim1997.populartest.Utils.HelperClass.getScreenOrientation;
import static com.devmohamedibrahim1997.populartest.Utils.HelperClass.isNetworkAvailable;
import static com.devmohamedibrahim1997.populartest.Utils.HelperClass.showSnackBar;

public class TopRatedFragment extends Fragment {


    private RecyclerView movieRecyclerView;
    private MovieAdapter recyclerViewAdapter;
    private MovieViewModel movieViewModel;
    private FragmentActivity activity;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        FragmentTopRatedBinding topRatedBinding = DataBindingUtil.inflate(inflater,R.layout.fragment_top_rated, container, false);
        movieRecyclerView = topRatedBinding.topRatedRecyclerView;

        activity = getActivity();
        initRecyclerView();
        initViewModel();
        return topRatedBinding.getRoot();
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
        movieViewModel.getMovies("top_rated").observe(this, movies -> {
            if (movies != null) {
                recyclerViewAdapter.submitList(movies);
                movieRecyclerView.setAdapter(recyclerViewAdapter);
            } else {
                Toast.makeText(activity, "Unknown error, \n Please check your network", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void onMovieClick() {
        recyclerViewAdapter.setOnItemClickListener((position, v) -> {
            Intent intent = new Intent(activity, DetailsActivity.class);
            if (movieViewModel.getMovies("top_rated").getValue() != null) {
                intent.putExtra("movieId", movieViewModel.getMovies("top_rated").getValue().get(position).getId());
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
