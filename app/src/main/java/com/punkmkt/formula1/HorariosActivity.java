package com.punkmkt.formula1;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.media.Image;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.NetworkImageView;
import com.android.volley.toolbox.StringRequest;
import com.parse.Parse;
import com.parse.ParseAnalytics;
import com.parse.ParseException;
import com.parse.ParseInstallation;
import com.parse.ParsePush;
import com.parse.ParsePushBroadcastReceiver;
import com.parse.PushService;
import com.parse.SaveCallback;
import com.punkmkt.formula1.databases.NotificationModel;
import com.punkmkt.formula1.models.DiaCarrera;
import com.punkmkt.formula1.models.Etapa;
import com.punkmkt.formula1.models.EtapaDiaCarrera;
import com.punkmkt.formula1.models.Posicion;
import com.punkmkt.formula1.utils.AuthRequest;
import com.raizlabs.android.dbflow.annotation.Table;
import com.raizlabs.android.dbflow.runtime.FlowContentObserver;
import com.raizlabs.android.dbflow.sql.builder.Condition;
import com.raizlabs.android.dbflow.sql.language.Select;
import com.raizlabs.android.dbflow.sql.language.Update;
import com.raizlabs.android.dbflow.sql.language.Where;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;

public class HorariosActivity extends Activity {

    ImageView notificacion_practica1;
    ImageView notificacion_practica2;
    ImageView notificacion_practica3;
    ImageView notificacion_calificacion;
    ImageView notificacion_premio;
    TableLayout tabla_informacion;
    private String AHZ_HORARIOS_JSON_API_URL = "http://104.236.3.158/api/horarios/5/";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_horarios);

        tabla_informacion = (TableLayout) findViewById(R.id.tabla_informacion);

        StringRequest request = new AuthRequest(Request.Method.GET, AHZ_HORARIOS_JSON_API_URL, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject object = new JSONObject(response);
                    JSONArray object2 = object.getJSONArray("data");
                    //JSONArray etapa_set = object2.getJSONArray("etapa_dia_carrera_set");
                    for (int count = 0; count < object2.length(); count++) {
                        JSONObject anEntry = object2.getJSONObject(count);
                        DiaCarrera dia = new DiaCarrera();
                        dia.setId(Integer.parseInt(anEntry.optString("id")));
                        dia.setNombre(anEntry.optString("nombre"));
                        Log.d("volley", dia.getNombre());  //Etapas
                        CreateTitleRow(dia);
                        JSONArray etapa_dia_carrera_set = anEntry.getJSONArray("etapa_dia_carrera_set");
                        ArrayList<EtapaDiaCarrera> etapasdiascarrera = new ArrayList<EtapaDiaCarrera>();
                        for (int count2 = 0; count2 < etapa_dia_carrera_set.length(); count2++) {
                            JSONObject anSecondEntry = etapa_dia_carrera_set.getJSONObject(count2);
                            EtapaDiaCarrera etapadiacarrera  = new EtapaDiaCarrera();
                            etapadiacarrera.setId(Integer.parseInt(anSecondEntry.optString("id")));
                            etapadiacarrera.setNombre(anSecondEntry.optString("nombre"));
                            etapadiacarrera.setDescripcion(anSecondEntry.optString("descripcion"));
                            etapadiacarrera.setHora_inicio(anSecondEntry.optString("hora_inicio"));
                            etapadiacarrera.setHora_fin(anSecondEntry.optString("hora_fin"));
                            etapadiacarrera.setZona(anSecondEntry.optString("zona"));
                            etapasdiascarrera.add(etapadiacarrera);

                        }
                        CreateContentRow(etapasdiascarrera);
                    }

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
        getMenuInflater().inflate(R.menu.menu_horarios, menu);
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
    public void IniciarlizarNotificacion(ImageView image_notificacion,String nombre_notificacion){
        Log.e("Parsenotification",nombre_notificacion);
        try {
            NotificationModel notificacion = new Select().from(NotificationModel.class).where("name = "+"'"+nombre_notificacion+"'").querySingle();
            int active = notificacion.getActive();
            if (active == 1) {
                image_notificacion.setImageResource(R.drawable.active_notification_icon);
                Log.e("Debug", "Iniciar = 1");
            } else {
                image_notificacion.setImageResource(R.drawable.notification_icon);
                Log.e("Debug", "Iniciar = 0");
            }
        } catch (Throwable e) {
            image_notificacion.setImageResource(R.drawable.notification_icon);
            Log.e("Debug", "Iniciar throwable");
        }
    }
    public void CambiarEstadoNotificacion(final ImageView image_notificacion,final String nombre_notificacion) {
        try {
            final NotificationModel notificacion = new Select().from(NotificationModel.class).where("name = " + "'" + nombre_notificacion + "'").querySingle();
            int active = notificacion.getActive();
            if (active == 1) {

                //Log.e("Debug", "Cambiar estado Active = 1");
                //PushService.setDefaultPushCallback(this, MainActivity.class);
                //ParsePush.unsubscribeInBackground(nombre_notificacion);

                ParsePush.unsubscribeInBackground(nombre_notificacion, new SaveCallback() {
                    @Override
                    public void done(ParseException e) {
                        if (e == null) {
                            Log.d("com.parse.push", "successfully unsubscribed to the broadcast channel.");
                            image_notificacion.setImageResource(R.drawable.notification_icon);
                            notificacion.setActive(0);
                            notificacion.update();
                        } else {
                            Log.e("com.parse.push", "failed to subscribe for push", e);
                        }
                    }
                });
                //      ParseInstallation.getCurrentInstallation().saveInBackground();
            } else {
                //update
                ParsePush.subscribeInBackground(nombre_notificacion, new SaveCallback() {
                    @Override
                    public void done(ParseException e) {
                        if (e == null) {
                            Log.d("com.parse.push", "successfully subscribed to the broadcast channel.");
                            notificacion.setActive(1);
                            notificacion.update();
                            image_notificacion.setImageResource(R.drawable.active_notification_icon);

                        } else {
                            Log.e("com.parse.push", "failed to subscribe for push", e);
                        }
                    }
                });
                //ParsePush.subscribeInBackground(nombre_notificacion);

                Log.e("Debug", "Cambiar estado update = 0");
            }
        } catch (Throwable e) {
            Log.e("Debug", "Throwbable Cambiar estado");

            image_notificacion.setImageResource(R.drawable.active_notification_icon);
            ParsePush.subscribeInBackground(nombre_notificacion, new SaveCallback() {
                @Override
                public void done(ParseException e) {
                    if (e == null) {
                        Log.d("com.parse.push", "successfully subscribed to the broadcast channel.");

                        NotificationModel noti = new NotificationModel();
                        noti.setName(nombre_notificacion);
                        noti.setActive(1);
                        noti.save();
                    } else {
                        Log.e("com.parse.push", "failed to subscribe for push", e);
                    }
                }
            });
        }
    }


    public void CreateTitleRow(DiaCarrera dia){
        TableRow row_title = (TableRow) LayoutInflater.from(HorariosActivity.this).inflate(R.layout.title_diahorario, null);
        ((TextView)row_title.findViewById(R.id.dia)).setText(dia.getNombre());
        tabla_informacion.addView(row_title);
    }
    public void CreateContentRow(ArrayList<EtapaDiaCarrera> etapasdiascarrera){
        for(int count=0; count<etapasdiascarrera.size();count++){
            EtapaDiaCarrera etapadiacarrera = etapasdiascarrera.get(count);
            TableRow row_pos = (TableRow) LayoutInflater.from(HorariosActivity.this).inflate(R.layout.row_diahorario, null);
            ((TextView)row_pos.findViewById(R.id.nombre)).setText(etapadiacarrera.getNombre());
            ((TextView)row_pos.findViewById(R.id.contenido_descripcion)).setText(etapadiacarrera.getDescripcion());
            ((TextView)row_pos.findViewById(R.id.contenido_hora_inicio)).setText(etapadiacarrera.getHora_inicio());
            ((TextView)row_pos.findViewById(R.id.contenido_hora_fin)).setText(etapadiacarrera.getHora_fin());
            ((TextView)row_pos.findViewById(R.id.contenido_zona)).setText(etapadiacarrera.getZona());
            tabla_informacion.addView(row_pos);
        }
    }

}
