package org.izv.flora.view.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;


import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;

import org.izv.flora.R;
import org.izv.flora.model.entity.Flora;
import org.izv.flora.view.adapter.adapter.FloraViewHolder;

import java.util.ArrayList;

public class FloraAdapter extends RecyclerView.Adapter<FloraViewHolder> {
    private ArrayList<Flora> floraList;
    private Context context;

    public FloraAdapter(Context context) {
        this.context = context;
    }

    @NonNull
    @Override
    public FloraViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_flora, parent, false);

        return new FloraViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull FloraViewHolder holder, int position) {
        Flora flora = floraList.get(position);
        holder.flora = flora;
        String url = "https://informatica.ieszaidinvergeles.org:10009/ad/felixRDLFApp/public/api/imagen/" + flora.getId() + "/flora";
        holder.tvNombreFloraItem.setText(flora.getNombre());
        Glide.with(context).load(url).diskCacheStrategy(DiskCacheStrategy.NONE).skipMemoryCache(true).into(holder.ivFloraItem);
    }

    @Override
    public int getItemCount() {
        if (floraList == null) {
            return 0;
        }
        return floraList.size();
    }

    public void setFloraList(ArrayList<Flora> floraList) {
        this.floraList = floraList;
    }
}
