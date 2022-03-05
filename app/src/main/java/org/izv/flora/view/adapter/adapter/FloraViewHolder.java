package org.izv.flora.view.adapter.adapter;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import org.izv.flora.R;
import org.izv.flora.view.ViewFlora;
import org.izv.flora.model.entity.Flora;

public class FloraViewHolder extends RecyclerView.ViewHolder {

    public Flora flora;
    public Bundle bundle;
    public TextView tvNombreFloraItem;
    public ImageView ivFloraItem;

    public FloraViewHolder(@NonNull View itemView) {
        super(itemView);

        tvNombreFloraItem = itemView.findViewById(R.id.tvNombreFloraItem);
        ivFloraItem = itemView.findViewById(R.id.ivFloraItem);


        itemView.setOnClickListener(view -> {
            bundle = new Bundle();
            bundle.putParcelable("flora", flora);
            Intent i = new Intent(view.getContext(), ViewFlora.class);
            i.putExtras(bundle);
            view.getContext().startActivity(i);
        });
    }
}
