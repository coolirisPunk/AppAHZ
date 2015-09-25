package com.punkmkt.formula1.fragments;

import android.app.Fragment;
import android.content.Context;
import android.graphics.Rect;
import android.os.Bundle;
import android.support.annotation.DimenRes;
import android.support.annotation.NonNull;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.punkmkt.formula1.R;
import com.punkmkt.formula1.adapters.PilotosAdapter;
import com.punkmkt.formula1.models.Piloto;

import java.util.ArrayList;

/**
 * Created by germanpunk on 20/09/15.
 */
public class PilotosFragment extends Fragment {

    private RecyclerView.Adapter adapter;

    private final String AHZ_PILOTOS_JSON_API_URL = "http://104.236.3.158/api/pilotos/";
    private ArrayList<Piloto> pilotos = new ArrayList<Piloto>();

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        RecyclerView recyclerView = (RecyclerView) getActivity().findViewById(R.id.my_recycler_view);
        recyclerView.setHasFixedSize(true);






        Piloto p1 = new Piloto(1, "Piloto1", "piloto_pic.jpg", "1", "Mexicana", "20-10-2015", "Chiapas", "Ninguno");
        Piloto p2 = new Piloto(2, "Piloto2", "piloto_pic.jpg", "2", "Mexicana", "20-10-2015", "Chiapas", "Ninguno");




        pilotos.add(p1);
        pilotos.add(p2);
        adapter = new PilotosAdapter(pilotos);

//        StringRequest request = new AuthRequest(Request.Method.GET, AHZ_PILOTOS_JSON_API_URL, new Response.Listener<String>() {
//            @Override
//            public void onResponse(String response) {
//                try {
//                    JSONArray object = new JSONArray(response);
//                    for (int count = 0; count < object.length(); count++) {
//                        JSONObject anEntry = object.getJSONObject(count);
//                        Piloto piloto = new Piloto();
//                        piloto.setId(Integer.parseInt(anEntry.optString("id")));
//                        piloto.setNombre(anEntry.optString("nombre"));
//                        piloto.setFoto(anEntry.optString("foto"));
//                        pilotos.add(piloto);
//                    }
//                    adapter.notifyDataSetChanged();
//
//                } catch (JSONException e) {
//                    e.printStackTrace();
//                }
//            }
//        }, new Response.ErrorListener() {
//            @Override
//            public void onErrorResponse(VolleyError error) {
//                Log.e("volley", "Error during request");
//                error.printStackTrace();
//            }
//        });
//        MyVolleySingleton.getInstance().addToRequestQueue(request);

        //recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

        //Por si quieren configurar algom como Grill solo cambian la linea de arriba por esta:
        recyclerView.setLayoutManager(new GridLayoutManager(getActivity(), 2));
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(adapter);
        ItemOffsetDecoration itemDecoration = new ItemOffsetDecoration(getActivity().getApplicationContext(), R.dimen.item_offset);
        recyclerView.addItemDecoration(itemDecoration);
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragmentpremios, container, false);
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