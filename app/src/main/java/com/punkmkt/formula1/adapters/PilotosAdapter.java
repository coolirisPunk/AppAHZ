package com.punkmkt.formula1.adapters;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.NetworkImageView;
import com.punkmkt.formula1.MyVolleySingleton;
import com.punkmkt.formula1.PilotosDetalleActivity;
import com.punkmkt.formula1.R;
import com.punkmkt.formula1.ResultadosDetalleActivity;
import com.punkmkt.formula1.models.Piloto;
import com.punkmkt.formula1.models.Premio;

import java.util.List;

/**
 * Created by germanpunk on 20/09/15.
 */
public class PilotosAdapter extends RecyclerView.Adapter<PilotosAdapter.PilotoViewHolder> {
    private List<Piloto> items;
    ImageLoader imageLoader = MyVolleySingleton.getInstance().getImageLoader();
    private Context context;

    public static class PilotoViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        public TextView nombre;
        NetworkImageView imagen;
        public IMyViewHolderClicks mListener;

        public PilotoViewHolder(View v, IMyViewHolderClicks listener) {
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

    public PilotosAdapter(List<Piloto> items) {
        this.items = items;
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    @Override
    public PilotosAdapter.PilotoViewHolder onCreateViewHolder(final ViewGroup viewGroup, int i) {
        View v = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.row_piloto, viewGroup, false);
        if (imageLoader == null)
            imageLoader = MyVolleySingleton.getInstance().getImageLoader();

        PilotosAdapter.PilotoViewHolder vh = new PilotoViewHolder(v, new PilotosAdapter.PilotoViewHolder.IMyViewHolderClicks() {

            public void onPotato(View caller, int i) {
                Piloto piloto  = items.get(i);
                Intent Idetail = new Intent (viewGroup.getContext(), PilotosDetalleActivity.class);
                Idetail.putExtra("id", Integer.toString(piloto.getId()));
                Idetail.putExtra("nombre", piloto.getNombre());
                Idetail.putExtra("image", piloto.getFoto());
                viewGroup.getContext().startActivity(Idetail);
            };
            public void onTomato(NetworkImageView callerImage, int i) {
                Piloto piloto  = items.get(i);

                Intent Idetail = new Intent (viewGroup.getContext(), PilotosDetalleActivity.class);
                Idetail.putExtra("id", Integer.toString(piloto.getId()));
                Idetail.putExtra("nombre", piloto.getNombre());
                Idetail.putExtra("image", piloto.getFoto());
                viewGroup.getContext().startActivity(Idetail);
            }
        });

        return vh;

    }

    @Override
    public void onBindViewHolder(PilotoViewHolder viewHolder, int i) {
        viewHolder.imagen.setImageUrl(items.get(i).getFoto(), imageLoader);
        viewHolder.nombre.setText(items.get(i).getNombre());
    }




}