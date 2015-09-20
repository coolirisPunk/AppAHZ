package com.punkmkt.formula1;

import android.app.Activity;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.RelativeLayout;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.NetworkImageView;
import com.android.volley.toolbox.StringRequest;
import com.punkmkt.formula1.models.Etapa;
import com.punkmkt.formula1.models.Posicion;
import com.punkmkt.formula1.utils.AuthRequest;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class RankingGeneralActivity extends Activity {
    private String AHZ_RANKING_GENERAL = "http://104.236.3.158/api/premios/ranking_general/";
    ImageLoader imageLoader = MyVolleySingleton.getInstance().getImageLoader();
    RelativeLayout MyrLayout;
    TableLayout tabla_resultados;
    private ArrayList<Posicion> posiciones_ranking_general = new ArrayList<Posicion>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ranking_general);
        tabla_resultados = (TableLayout) findViewById(R.id.tabla_resultados);

        StringRequest request = new AuthRequest(Request.Method.GET, AHZ_RANKING_GENERAL, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject object = new JSONObject(response);
                    JSONArray data = object.getJSONArray("data");
                    for (int count = 0; count < data.length(); count++) {
                        JSONObject anEntry = data.getJSONObject(count);
                        //Log.d("volley",anSecondEntry.toString());
                        Posicion posicion  = new Posicion();
                        posicion.setPiloto_sobrenombre(anEntry.optString("piloto"));
                        posicion.setEscuderia(anEntry.optString("escuderia"));
                        posicion.setPuntos(anEntry.optString("puntos"));
                        posiciones_ranking_general.add(posicion);
                    }
                    iniciarranking();

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
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_ranking_general, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }


    public void iniciarranking() {
        ArrayList<Posicion> copia = new ArrayList<Posicion>();
        copia = posiciones_ranking_general;
        tabla_resultados.removeAllViews();
        TableRow row = (TableRow) LayoutInflater.from(RankingGeneralActivity.this).inflate(R.layout.title_ranking_general, null);
        tabla_resultados.addView(row);
        for (int count = 0; count < copia.size(); count++) {
            Posicion posicion = copia.get(count);
            TableRow row_pos = (TableRow) LayoutInflater.from(RankingGeneralActivity.this).inflate(R.layout.row_ranking_general, null);
            ((TextView) row_pos.findViewById(R.id.pos)).setText(Integer.toString(count + 1));
            ((TextView) row_pos.findViewById(R.id.piloto)).setText(posicion.getPiloto_sobrenombre());
            ((NetworkImageView) row_pos.findViewById(R.id.escuderia)).setImageUrl(posicion.getEscuderia(), imageLoader);
            ((TextView) row_pos.findViewById(R.id.puntos)).setText(posicion.getPuntos());
            tabla_resultados.addView(row_pos);
        }
    }


}
