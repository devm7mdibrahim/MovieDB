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
import com.devmohamedibrahim1997.populartest.databinding.SearchDataBinding;
import com.devmohamedibrahim1997.populartest.model.Movie;


public class SearchAdapter extends PagedListAdapter<Movie, SearchAdapter.SearchHolder> {


    private Context context;
    private static ClickListener clickListener;

    public SearchAdapter(Context context) {
        super(diffCallback);
        this.context = context;
    }

    @NonNull
    @Override
    public SearchHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {

        SearchDataBinding searchDataBinding =
                DataBindingUtil.inflate(LayoutInflater.from(context), R.layout.search_item, viewGroup, false);
        return new SearchHolder(searchDataBinding);
    }

    @Override
    public void onBindViewHolder(@NonNull SearchHolder searchHolder, int i) {

        Movie movie = getItem(i);
        if(movie!= null) {
            searchHolder.bindMovie(movie);
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

    class SearchHolder extends RecyclerView.ViewHolder implements View.OnClickListener{

        SearchDataBinding searchDataBinding;
        SearchHolder(SearchDataBinding searchDataBinding) {
            super(searchDataBinding.getRoot());
            this.searchDataBinding = searchDataBinding;
            itemView.setOnClickListener(this);
        }

        void bindMovie(Movie movie) {
            searchDataBinding.setMovie(movie);
        }

        @Override
        public void onClick(View v) {
            clickListener.onItemClick(getAdapterPosition(), v);
        }
    }

    public void setOnItemClickListener(ClickListener clickListener) {
        SearchAdapter.clickListener = clickListener;
    }
}
