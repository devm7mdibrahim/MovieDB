package com.devmohamedibrahim1997.populartest.adapter;

import android.annotation.SuppressLint;

import androidx.databinding.DataBindingUtil;
import androidx.paging.PagedListAdapter;
import android.content.Context;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.devmohamedibrahim1997.populartest.R;
import com.devmohamedibrahim1997.populartest.databinding.MovieDataBinding;
import com.devmohamedibrahim1997.populartest.model.Movie;

public class MovieAdapter extends PagedListAdapter<Movie, MovieAdapter.MovieViewHolder> {


    private static ClickListener clickListener;
    private Context context;

    public MovieAdapter(Context context) {
        super(diffCallback);
        this.context = context;
    }

    @NonNull
    @Override
    public MovieViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {

        MovieDataBinding movieDataBinding = DataBindingUtil.inflate(LayoutInflater.from(context),R.layout.movie_item, viewGroup, false);
        return new MovieViewHolder(movieDataBinding);
    }

    @Override
    public void onBindViewHolder(@NonNull MovieViewHolder movieViewHolder, int i) {
        Movie movie = getItem(i);
        if (movie!= null) {
            movieViewHolder.bindMovie(movie);
        }

    }

    private static DiffUtil.ItemCallback<Movie> diffCallback =
            new DiffUtil.ItemCallback<Movie>() {
                @Override
                public boolean areItemsTheSame(@NonNull Movie oldMovie, @NonNull Movie newMovie) {
                    return oldMovie.getId().equals(newMovie.getId());
                }

                @SuppressLint("DiffUtilEquals")
                @Override
                public boolean areContentsTheSame(@NonNull Movie oldMovie, @NonNull Movie newMovie) {
                    return oldMovie.equals(newMovie);
                }
            };

    class MovieViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        MovieDataBinding movieDataBinding;

        MovieViewHolder(MovieDataBinding movieDataBinding) {
            super(movieDataBinding.getRoot());
            this.movieDataBinding = movieDataBinding;
            itemView.setOnClickListener(this);
        }

        void bindMovie(Movie movie) {
            movieDataBinding.setMovie(movie);
        }

        @Override
        public void onClick(View v) {
            clickListener.onItemClick(getAdapterPosition(), v);
        }
    }

    public void setOnItemClickListener(ClickListener clickListener) {
        MovieAdapter.clickListener = clickListener;
    }
}
