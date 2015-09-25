package com.punkmkt.formula1.fragments;

import android.app.Fragment;
import android.content.Context;
import android.graphics.Rect;
import android.os.Bundle;
import android.support.annotation.DimenRes;
import android.support.annotation.NonNull;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.punkmkt.formula1.R;
import com.punkmkt.formula1.adapters.HospedajeAdapter;
import com.punkmkt.formula1.adapters.LugaresAdapter;
import com.punkmkt.formula1.models.Hotel;
import com.punkmkt.formula1.models.Lugar;

import java.util.ArrayList;

/**
 * Created by germanpunk on 24/09/15.
 */

public class LugaresFragment extends Fragment {
    private RecyclerView.Adapter adapter;
    private ArrayList<Lugar> lugares = new ArrayList<Lugar>();
    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        //ReclyclerView, Adapter

        RecyclerView recyclerView = (RecyclerView) getActivity().findViewById(R.id.my_recycler_view);
        recyclerView.setHasFixedSize(true);

        lugares.add(new Lugar(1, "W MEXICO CITY", "Av. Presidente Masaryk No. 390 – A, Col. Polanco Chapultepec.", "555-5555555", "21.1902126", "-86.8772295","img1.jpg"));
        lugares.add(new Lugar(2,"ST. REGIS MEXICO CITY","Paseo de la Reforma No. 500, Juárez, Ciudad de México.","555-5555555","21.1902126","-86.8772295","img1.jpg"));
        lugares.add(new Lugar(3,"HILTON SANTA FE","Antonio Dovalí Jaime No. 70, Santa Fe, Ciudad de México.","555-5555555","21.1902126","-86.8772295","img1.jpg"));
        lugares.add(new Lugar(4, "FOUR SEASONS MÉXICO", "Paseo de la Reforma No. 500, Juárez, Ciudad de México.", "555-5555555", "21.1902126", "-86.8772295", "img1.jpg"));

        adapter = new LugaresAdapter(lugares);


        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity().getApplicationContext()));
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(adapter);
        //recyclerView.scrollToPosition(0);
        ItemOffsetDecoration itemDecoration = new ItemOffsetDecoration(getActivity().getApplicationContext(), R.dimen.item_offset);
        recyclerView.addItemDecoration(itemDecoration);

    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragmentdetalleciudadmexico, container, false);
        return rootView;
    }

    public class ItemOffsetDecoration extends RecyclerView.ItemDecoration {

        private int mItemOffset;

        public ItemOffsetDecoration(int itemOffset) {
            mItemOffset = itemOffset;
        }

        public ItemOffsetDecoration(@NonNull Context context, @DimenRes int itemOffsetId) {
            this(context.getResources().getDimensionPixelSize(itemOffsetId));
        }
        @Override
        public void getItemOffsets(Rect outRect, View view, RecyclerView parent,
                                   RecyclerView.State state) {
            super.getItemOffsets(outRect, view, parent, state);
            outRect.set(mItemOffset, mItemOffset, mItemOffset, mItemOffset);
        }
    }
}
