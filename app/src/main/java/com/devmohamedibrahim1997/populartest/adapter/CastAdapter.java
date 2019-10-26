package com.devmohamedibrahim1997.populartest.adapter;

import android.content.Context;
import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import com.devmohamedibrahim1997.populartest.R;
import com.devmohamedibrahim1997.populartest.databinding.CastDataBinding;
import com.devmohamedibrahim1997.populartest.model.Cast;
import java.util.List;


public class CastAdapter extends RecyclerView.Adapter<CastAdapter.viewHolder> {

    private Context context;
    private List<Cast> castList;

    public CastAdapter(Context context){
        this.context = context;
    }
    @NonNull
    @Override
    public viewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {

        CastDataBinding castDataBinding = DataBindingUtil.inflate(LayoutInflater.from(context), R.layout.cast_item, viewGroup, false);
        return new viewHolder(castDataBinding);
    }

    @Override
    public void onBindViewHolder(@NonNull viewHolder viewHolder, int i) {
        Cast cast = castList.get(i);
        if(cast != null){
            viewHolder.bindCast(cast);
        }
    }

    @Override
    public int getItemCount() {
        return castList!=null? castList.size():0;
    }

    public void setCast(List<Cast> castList){
        this.castList = castList;
    }

    class viewHolder extends RecyclerView.ViewHolder{

        CastDataBinding castDataBinding;
        viewHolder(CastDataBinding castDataBinding) {
            super(castDataBinding.getRoot());
            this.castDataBinding = castDataBinding;
        }

        void bindCast(Cast cast) {
            castDataBinding.setCast(cast);
        }
    }
}
