package com.punkmkt.formula1.adapters;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.NetworkImageView;
import com.punkmkt.formula1.MyVolleySingleton;
import com.punkmkt.formula1.R;
import com.punkmkt.formula1.ResultadosActivity;
import com.punkmkt.formula1.ResultadosDetalleActivity;
import com.punkmkt.formula1.models.Premio;

import java.util.List;

/**
 * Created by germanpunk on 15/09/15.
 */
public class PremiosAdapter extends RecyclerView.Adapter<PremiosAdapter.PremioViewHolder> {
    private List<Premio> items;
    ImageLoader imageLoader = MyVolleySingleton.getInstance().getImageLoader();
    private Context context;

    public static class PremioViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        public TextView nombre;
        NetworkImageView imagen;
        public IMyViewHolderClicks mListener;

        public PremioViewHolder(View v, IMyViewHolderClicks listener) {
            super(v);
            mListener = listener;
            imagen = (NetworkImageView) v.findViewById(R.id.netork_imageView);
            nombre = (TextView) v.findViewById(R.id.name);
            imagen.setOnClickListener(this);
            v.setOnClickListener(this);
        }
        @Override
        public void onClick(View v) {
            if (v instanceof NetworkImageView){
                mListener.onTomato((NetworkImageView)v, getLayoutPosition());
            } else {
                mListener.onPotato(v,getLayoutPosition());
            }
        }
        public static interface IMyViewHolderClicks {
            public void onPotato(View caller, int i);
            public void onTomato(NetworkImageView callerImage, int i);
        }


    }

    public PremiosAdapter(List<Premio> items) {
        this.items = items;
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    @Override
    public PremiosAdapter.PremioViewHolder onCreateViewHolder(final ViewGroup viewGroup, int i) {
        View v = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.row_premio, viewGroup, false);
        if (imageLoader == null)
            imageLoader = MyVolleySingleton.getInstance().getImageLoader();

        PremiosAdapter.PremioViewHolder vh = new PremioViewHolder(v, new PremiosAdapter.PremioViewHolder.IMyViewHolderClicks() {

            public void onPotato(View caller, int i) {
                Premio premio  = items.get(i);


                Intent Idetail = new Intent (viewGroup.getContext(), ResultadosDetalleActivity.class);
                Idetail.putExtra("id", Integer.toString(premio.getId()));
                Idetail.putExtra("nombre", premio.getName());
                Idetail.putExtra("image", premio.getImagen());
                viewGroup.getContext().startActivity(Idetail);

            };
            public void onTomato(NetworkImageView callerImage, int i) {
                Premio premio  = items.get(i);

                Intent Idetail = new Intent (viewGroup.getContext(), ResultadosDetalleActivity.class);
                Idetail.putExtra("id", Integer.toString(premio.getId()));
                Idetail.putExtra("nombre", premio.getName());
                Idetail.putExtra("image", premio.getImagen());
                viewGroup.getContext().startActivity(Idetail);
            }
        });

        return vh;

    }

    @Override
    public void onBindViewHolder(PremioViewHolder viewHolder, int i) {
        viewHolder.imagen.setImageUrl(items.get(i).getImagenCategoria(), imageLoader);
        viewHolder.nombre.setText(items.get(i).getName());
    }




}