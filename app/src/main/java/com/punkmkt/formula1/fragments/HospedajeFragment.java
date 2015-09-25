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
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.punkmkt.formula1.MyVolleySingleton;
import com.punkmkt.formula1.R;
import com.punkmkt.formula1.adapters.HospedajeAdapter;
import com.punkmkt.formula1.adapters.PremiosAdapter;
import com.punkmkt.formula1.models.Hotel;
import com.punkmkt.formula1.models.Premio;
import com.punkmkt.formula1.utils.AuthRequest;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * Created by germanpunk on 24/09/15.
 */
public class HospedajeFragment extends Fragment {
    private RecyclerView.Adapter adapter;
    private ArrayList<Hotel> hoteles = new ArrayList<Hotel>();
    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        //ReclyclerView, Adapter

        RecyclerView recyclerView = (RecyclerView) getActivity().findViewById(R.id.my_recycler_view);
        recyclerView.setHasFixedSize(true);

        hoteles.add(new Hotel(1, "W MEXICO CITY", "Av. Presidente Masaryk No. 390 – A, Col. Polanco Chapultepec.", "555-5555555", "21.1902126", "-86.8772295","img1.jpg"));
        hoteles.add(new Hotel(2,"ST. REGIS MEXICO CITY","Paseo de la Reforma No. 500, Juárez, Ciudad de México.","555-5555555","21.1902126","-86.8772295","img1.jpg"));
        hoteles.add(new Hotel(3,"HILTON SANTA FE","Antonio Dovalí Jaime No. 70, Santa Fe, Ciudad de México.","555-5555555","21.1902126","-86.8772295","img1.jpg"));
        hoteles.add(new Hotel(4, "FOUR SEASONS MÉXICO", "Paseo de la Reforma No. 500, Juárez, Ciudad de México.", "555-5555555", "21.1902126", "-86.8772295", "img1.jpg"));

        adapter = new HospedajeAdapter(hoteles);


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
