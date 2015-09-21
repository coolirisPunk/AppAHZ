package com.punkmkt.formula1;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
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
import com.punkmkt.formula1.models.Premio;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class ResultadosDetalleActivity extends Activity {
    private String AHZ_PREMIOS_JSON_API_URL = "http://104.236.3.158/api/premios/";
    ImageLoader imageLoader = MyVolleySingleton.getInstance().getImageLoader();
    TextView nombre;
    NetworkImageView imagen;
    RelativeLayout MyrLayout;
    TableLayout tabla_resultados;
    private ArrayList<Etapa> etapas = new ArrayList<Etapa>();

    private ArrayList<Posicion> posiciones_p1 = new ArrayList<Posicion>();
    private ArrayList<Posicion> posiciones_p2 = new ArrayList<Posicion>();
    private ArrayList<Posicion> posiciones_p3 = new ArrayList<Posicion>();
    private ArrayList<Posicion> posiciones_clasificatoria = new ArrayList<Posicion>();
    private ArrayList<Posicion> posiciones_carrera = new ArrayList<Posicion>();


    Button p1;
    Button p2;
    Button p3;
    Button clasificatoria;
    Button carrera;
    StringRequest request;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_resultados_detalle);

        imagen = (NetworkImageView) findViewById(R.id.bandera_premio);
        nombre = (TextView) findViewById(R.id.nombre_premio);
        MyrLayout = (RelativeLayout) findViewById(R.id.container);
        tabla_resultados = (TableLayout) findViewById(R.id.tabla_resultados);
        p1 = (Button) findViewById(R.id.p1);
        p2 = (Button) findViewById(R.id.p2);
        p3 = (Button) findViewById(R.id.p3);
        clasificatoria = (Button) findViewById(R.id.clasificacion);
        carrera = (Button) findViewById(R.id.carrera);
        Intent intent = getIntent();
        Premio premio = new Premio();
        String id = intent.getStringExtra("id");
        AHZ_PREMIOS_JSON_API_URL = AHZ_PREMIOS_JSON_API_URL + id + "/";
        premio.setId(Integer.parseInt(id));
        String nombre_premio = intent.getStringExtra("nombre");
        premio.setName(nombre_premio);
        String image = intent.getStringExtra("image");
        premio.setImage(image);
        imagen.setImageUrl(premio.getImagen(), imageLoader);
        nombre.setText(premio.getName());



        request = new AuthRequest(Request.Method.GET, AHZ_PREMIOS_JSON_API_URL, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {

                    JSONObject object = new JSONObject(response);
                    JSONArray etapa_set = object.getJSONArray("etapa_set");

                    for (int count = 0; count < etapa_set.length(); count++) {
                        JSONObject anEntry = etapa_set.getJSONObject(count);
                        Etapa etapa = new Etapa();
                        etapa.setId(Integer.parseInt(anEntry.optString("id")));
                        etapa.setNombre(anEntry.optString("nombre"));
                        etapa.setTipo(anEntry.optString("tipo_etapa"));
                        Log.d("volley", etapa.getNombre());  //Etapas
                        etapas.add(etapa);
                        JSONArray posicion_set = anEntry.getJSONArray("posicion_set");
                        ArrayList<Posicion> array_posiciones = new ArrayList<Posicion>();
                        for (int count2 = 0; count2 < posicion_set.length(); count2++) {
                            JSONObject anSecondEntry = posicion_set.getJSONObject(count2);
                            //Log.d("volley",anSecondEntry.toString());
                            Posicion posicion  = new Posicion();
                            posicion.setId(Integer.parseInt(anSecondEntry.optString("id")));
                            posicion.setPosicion(Integer.parseInt(anSecondEntry.optString("numero_posicion")));
                            if(anSecondEntry.has("tiempo") && !anSecondEntry.optString("tiempo").equals("null")){
                                posicion.setTiempo(anSecondEntry.optString("tiempo"));
                            }
                            if(anSecondEntry.has("gap") && !anSecondEntry.optString("gap").equals("null")){
                                posicion.setGap(anSecondEntry.optString("gap"));
                            }
                            if(anSecondEntry.has("laps") && !anSecondEntry.optString("laps").equals("null")){
                                posicion.setLaps(anSecondEntry.optString("laps"));
                            }
                            if(anSecondEntry.has("q1") && !anSecondEntry.optString("q1").equals("null")){
                                posicion.setQ1(anSecondEntry.optString("q1"));
                            }
                            if(anSecondEntry.has("q2") && !anSecondEntry.optString("q2").equals("null")){
                                posicion.setQ2(anSecondEntry.optString("q2"));
                            }
                            if(anSecondEntry.has("q3") && !anSecondEntry.optString("q3").equals("null")){
                                posicion.setQ3(anSecondEntry.optString("q3"));
                            }
                            if(anSecondEntry.has("puntos") && !anSecondEntry.optString("puntos").equals("null")){
                                posicion.setPuntos(anSecondEntry.optString("puntos"));
                            }
                            JSONObject anpilot = anSecondEntry.optJSONObject("piloto");
                            posicion.setPiloto_sobrenombre(anpilot.optString("sobrenombre"));
                            JSONObject anEscuderia = anpilot.getJSONObject("escuderia");
                            posicion.setEscuderia(anEscuderia.optString("equipo_img"));
                            array_posiciones.add(posicion);
                        }
                        if(etapa.getNombre().equals("P1")){
                            posiciones_p1 = array_posiciones;
                        }
                        else if(etapa.getNombre().equals("P2")){
                            posiciones_p2 = array_posiciones;
                        }
                        else if(etapa.getNombre().equals("P3")){
                            posiciones_p2 = array_posiciones;
                        }
                        else if(etapa.getNombre().equals("Q")){
                            posiciones_clasificatoria = array_posiciones;
                        }
                        else if(etapa.getNombre().equals("R")){
                            posiciones_carrera = array_posiciones;
                        }
                    }

                    iniciarpractica("practica1");

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

        p1.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                iniciarpractica("practica1");

            }
        });
        p2.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                iniciarpractica("practica2");
            }
        });
        p3.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                iniciarpractica("practica3");
            }
        });
        clasificatoria.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                iniciarclasificacion();
            }
        });
        carrera.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                iniciarcarrera();
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_resultados_detalle, menu);
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

public int index_of_arraylist(String etapa){
        int iterator = 0;
        boolean flag = false;
        while (flag) {
            if(etapas.get(iterator).getNombre() == etapa){
                flag = true;
            }
            iterator++;
        }
        return iterator;
    }
    public void iniciarpractica(String practica){
        ArrayList<Posicion> copia = new ArrayList<Posicion>();
        if (practica.equals("practica1")){
            copia = posiciones_p1;
        }
        else if (practica.equals("practica2")){
            copia = posiciones_p2;
        }
        else if (practica.equals("practica3")){
            copia = posiciones_p3;
        }
        tabla_resultados.removeAllViews();
        TableRow row = (TableRow) LayoutInflater.from(ResultadosDetalleActivity.this).inflate(R.layout.title_practica1, null);
        tabla_resultados.addView(row);
        for(int count=0; count<copia.size();count++){
            Posicion posicion = copia.get(count);
            TableRow row_pos = (TableRow) LayoutInflater.from(ResultadosDetalleActivity.this).inflate(R.layout.row_practica, null);
            ((TextView)row_pos.findViewById(R.id.pos)).setText(Integer.toString(posicion.getPosicion()));
            ((TextView)row_pos.findViewById(R.id.piloto)).setText(posicion.getPiloto_sobrenombre());
            ((NetworkImageView)row_pos.findViewById(R.id.escuderia)).setImageUrl(posicion.getEscuderia(), imageLoader);
            ((TextView)row_pos.findViewById(R.id.tiempo)).setText(posicion.getTiempo());
            ((TextView)row_pos.findViewById(R.id.gap)).setText(posicion.getGap());
            ((TextView)row_pos.findViewById(R.id.laps)).setText(posicion.getLaps());
            tabla_resultados.addView(row_pos);
        }

    }
    public void iniciarclasificacion(){
        ArrayList<Posicion> copia = new ArrayList<Posicion>();
        copia = posiciones_clasificatoria;
        tabla_resultados.removeAllViews();
        TableRow row = (TableRow) LayoutInflater.from(ResultadosDetalleActivity.this).inflate(R.layout.title_clasificacion, null);
        tabla_resultados.addView(row);
        for(int count=0; count<copia.size();count++){
            Posicion posicion = copia.get(count);
            TableRow row_pos = (TableRow) LayoutInflater.from(ResultadosDetalleActivity.this).inflate(R.layout.row_clasificacion, null);
            ((TextView)row_pos.findViewById(R.id.pos)).setText(Integer.toString(posicion.getPosicion()));
            ((TextView)row_pos.findViewById(R.id.piloto)).setText(posicion.getPiloto_sobrenombre());
            ((NetworkImageView)row_pos.findViewById(R.id.escuderia)).setImageUrl(posicion.getEscuderia(), imageLoader);
            ((TextView)row_pos.findViewById(R.id.textview_q1)).setText(posicion.getQ1());
            ((TextView)row_pos.findViewById(R.id.textview_q2)).setText(posicion.getQ2());
            ((TextView)row_pos.findViewById(R.id.textview_q3)).setText(posicion.getQ3());
            ((TextView)row_pos.findViewById(R.id.textview_laps)).setText(posicion.getLaps());
            tabla_resultados.addView(row_pos);
        }
    }
    public void iniciarcarrera(){
        ArrayList<Posicion> copia = new ArrayList<Posicion>();
        copia = posiciones_carrera;
        tabla_resultados.removeAllViews();
        TableRow row = (TableRow) LayoutInflater.from(ResultadosDetalleActivity.this).inflate(R.layout.title_carrera, null);
        tabla_resultados.addView(row);
        for(int count=0; count<copia.size();count++){
            Posicion posicion = copia.get(count);
            TableRow row_pos = (TableRow) LayoutInflater.from(ResultadosDetalleActivity.this).inflate(R.layout.row_carrera, null);
            ((TextView)row_pos.findViewById(R.id.pos)).setText(Integer.toString(posicion.getPosicion()));
            ((TextView)row_pos.findViewById(R.id.piloto)).setText(posicion.getPiloto_sobrenombre());
            ((NetworkImageView)row_pos.findViewById(R.id.escuderia)).setImageUrl(posicion.getEscuderia(), imageLoader);
            ((TextView)row_pos.findViewById(R.id.tiempo)).setText(posicion.getTiempo());
            ((TextView)row_pos.findViewById(R.id.puntos)).setText(posicion.getPuntos());
            tabla_resultados.addView(row_pos);
        }


    }
    @Override
    public void onDestroy() {
        super.onDestroy();
        request.cancel();
    }
}
