package com.devmohamedibrahim1997.populartest.ui.main;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.devmohamedibrahim1997.populartest.R;
import com.devmohamedibrahim1997.populartest.ui.details.DetailsActivity;
import com.devmohamedibrahim1997.populartest.adapter.MovieAdapter;
import com.devmohamedibrahim1997.populartest.databinding.FragmentTopRatedBinding;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import static com.devmohamedibrahim1997.populartest.utils.Constant.MOVIE_ID;
import static com.devmohamedibrahim1997.populartest.utils.Constant.TOPRATED_QUERY;
import static com.devmohamedibrahim1997.populartest.utils.HelperClass.getScreenOrientation;
import static com.devmohamedibrahim1997.populartest.utils.HelperClass.isNetworkAvailable;
import static com.devmohamedibrahim1997.populartest.utils.HelperClass.showSnackBar;
import static com.devmohamedibrahim1997.populartest.utils.HelperClass.showToast;

public class TopRatedFragment extends Fragment {

    private FragmentTopRatedBinding topRatedBinding;
    private MovieAdapter recyclerViewAdapter;
    private MovieViewModel movieViewModel;
    private FragmentActivity activity;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        topRatedBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_top_rated, container, false);
        activity = getActivity();
        initRecyclerView();
        initViewModel();
        getMovies();
        return topRatedBinding.getRoot();
    }

    private void initViewModel() {
        movieViewModel = ViewModelProviders.of(this).get(MovieViewModel.class);
    }

    private void getMovies() {
        if (isNetworkAvailable(activity)) {
            movieViewModel.getMovies(TOPRATED_QUERY).observe(this, movies -> {
                if (movies != null) {
                    recyclerViewAdapter.submitList(movies);
                    recyclerViewAdapter.notifyDataSetChanged();
                    onMovieClick();
                } else {
                    showToast(activity, "Unknown error, \n Please check your network");
                }
            });
        } else {
            showSnackBar(activity);
        }
    }

    private void onMovieClick() {
        recyclerViewAdapter.setOnItemClickListener((position, v) -> {
            if (movieViewModel.getMovies(TOPRATED_QUERY).getValue() != null) {
                Intent intent = new Intent(activity, DetailsActivity.class);
                intent.putExtra(MOVIE_ID, movieViewModel.getMovies(TOPRATED_QUERY).getValue().get(position).getId());
                startActivity(intent);
            }
        });
    }

    private void initRecyclerView() {
        int spanCount = getScreenOrientation(activity);
        RecyclerView movieRecyclerView = topRatedBinding.topRatedRecyclerView;
        movieRecyclerView.setLayoutManager(new GridLayoutManager(activity, spanCount));
        recyclerViewAdapter = new MovieAdapter(activity);
        movieRecyclerView.setAdapter(recyclerViewAdapter);
    }
}