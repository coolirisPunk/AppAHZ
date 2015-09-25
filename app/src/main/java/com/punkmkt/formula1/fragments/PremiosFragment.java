package com.punkmkt.formula1.fragments;

/**
 * Created by germanpunk on 30/07/15.
 */
import android.app.Fragment;
import android.os.Bundle;
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
import com.punkmkt.formula1.adapters.PremiosAdapter;
import com.punkmkt.formula1.models.Premio;
import com.punkmkt.formula1.utils.AuthRequest;


import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;


/**
 * Created by thespianartist on 23/07/14. (: pufu!!!!!
 */
public class PremiosFragment extends Fragment {

    private RecyclerView.Adapter adapter;

    private final String AHZ_PREMIOS_JSON_API_URL = "http://104.236.3.158/api/premios/";
    private ArrayList<Premio> premios = new ArrayList<Premio>();

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        //ReclyclerView, Adapter

        RecyclerView recyclerView = (RecyclerView) getActivity().findViewById(R.id.my_recycler_view);
        recyclerView.setHasFixedSize(true);


        adapter = new PremiosAdapter(premios);

        StringRequest request = new AuthRequest(Request.Method.GET, AHZ_PREMIOS_JSON_API_URL, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONArray object = new JSONArray(response);
                    for (int count = 0; count < object.length(); count++) {
                        JSONObject anEntry = object.getJSONObject(count);
                        Premio premio = new Premio();
                        premio.setId(Integer.parseInt(anEntry.optString("id")));
                        premio.setName(anEntry.optString("nombre"));
                        premio.setImage(anEntry.optString("bandera"));
                        premio.setImageCategoria(anEntry.optString("imagen_categoria"));
                        premios.add(premio);
                    }
                    adapter.notifyDataSetChanged();

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e("volley", "Error during request");
                error.printStackTrace();
            }
        });
        MyVolleySingleton.getInstance().addToRequestQueue(request);

        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

        //Por si quieren configurar algom como Grilla solo cambian la linea de arriba por esta:
        //recyclerView.setLayoutManager(new GridLayoutManager(getActivity(),2));

        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(adapter);

    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragmentpremios, container, false);
        return rootView;
    }
    private void parseJSONRespone(JSONObject response) {
        try {
            Log.d("parseJSONRespone ",response.toString());
            JSONArray entries = response.getJSONArray("objects");
//            for (int count = 0; count < entries.length(); count++) {
//                JSONObject anEntry = entries.getJSONObject(count);
//                Participante participante = new Participante();
//                participante.setId(Integer.parseInt(anEntry.optString("id")));
//                participante.setName(anEntry.optString("name"));
//                participante.setImage(anEntry.optString("picture"));
//                participante.setThumbnail(anEntry.optString("thumbnail"));
//                if(anEntry.has("year") && !anEntry.optString("year").equals("null")){
//                    // Log.e("Lista",anEntry.optString("year"));
//                    participante.setYear(anEntry.optString("year"));
//                }
//                else{
//                    //participante.setYear(" ");
//                }
//                participantes.add(participante);
//            }
//            adapter.notifyDataSetChanged();

        } catch (JSONException e) {
            e.printStackTrace();
        }

    }


}