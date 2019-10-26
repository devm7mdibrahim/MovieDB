package com.devmohamedibrahim1997.populartest.adapter;

import android.content.Context;
import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.devmohamedibrahim1997.populartest.R;
import com.devmohamedibrahim1997.populartest.model.MovieEntity;
import com.devmohamedibrahim1997.populartest.databinding.EntityDataBinding;
import java.util.List;


public class WatchLaterAdapter extends RecyclerView.Adapter<WatchLaterAdapter.WatchLaterHolder> {

    private static ClickListener clickListener;
    private List<MovieEntity> moviesList;
    private Context context;

    public WatchLaterAdapter(Context context){
        this.context = context;
    }

    @NonNull
    @Override
    public WatchLaterHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        EntityDataBinding entityDataBinding = DataBindingUtil.inflate(LayoutInflater.from(context), R.layout.movie_entity, viewGroup, false);
        return new WatchLaterHolder(entityDataBinding);
    }

    @Override
    public void onBindViewHolder(@NonNull WatchLaterHolder watchLaterHolder, int i) {
        MovieEntity movie = moviesList.get(i);
        if (movie != null){
         watchLaterHolder.bindMovie(movie);
        }
    }

    @Override
    public int getItemCount() {
        return moviesList!=null?moviesList.size():0;
    }

    public void setData(List<MovieEntity> moviesList){
        this.moviesList = moviesList;
    }

    public MovieEntity getMovieAt(int position) {
        return moviesList.get(position);
    }


    class WatchLaterHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        EntityDataBinding entityDataBinding;
        WatchLaterHolder(EntityDataBinding entityDataBinding) {
            super(entityDataBinding.getRoot());
            this.entityDataBinding = entityDataBinding;
            itemView.setOnClickListener(this);
        }

        void bindMovie(MovieEntity movie) {
            entityDataBinding.setMovie(movie);
        }

        @Override
        public void onClick(View v) {
            clickListener.onItemClick(getAdapterPosition(), v);
        }
    }

    public void setOnItemClickListener(ClickListener clickListener) {
        WatchLaterAdapter.clickListener = clickListener;
    }
}