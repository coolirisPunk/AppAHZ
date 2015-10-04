package com.punkmkt.formula1;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.location.LocationManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.SwitchCompat;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.CompoundButton;

import com.parse.ParseException;
import com.parse.ParsePush;
import com.parse.SaveCallback;
import com.punkmkt.formula1.databases.NotificationModel;
import com.raizlabs.android.dbflow.sql.language.Select;

import java.util.Set;

public class SettingsActivity extends Activity {
    SwitchCompat Notificaciones;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);
        LocationManager locationManager = (LocationManager) this.getSystemService(Context.LOCATION_SERVICE);
        SwitchCompat mSwitchGPS = (SwitchCompat) findViewById(R.id.turnGPS);
        Notificaciones= (SwitchCompat) findViewById(R.id.turnNotification);
        IniciarlizarNotificacion("notificaciones");
        if (locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)){
            mSwitchGPS.setChecked(true);
        }else{
            mSwitchGPS.setChecked(false);
        }



        mSwitchGPS.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked){
                    startActivityForResult(new Intent(android.provider.Settings.ACTION_LOCATION_SOURCE_SETTINGS), 100);
                } else {
                    startActivityForResult(new Intent(android.provider.Settings.ACTION_LOCATION_SOURCE_SETTINGS), 100);
                }
            }
        });

        Notificaciones.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                final String nombre_notificacion = "notificaciones";
                try {
                    final NotificationModel notificacion = new Select().from(NotificationModel.class).where("name = " + "'" + nombre_notificacion + "'").querySingle();
                    int active = notificacion.getActive();
                    if (active == 1) {
                        ParsePush.unsubscribeInBackground(nombre_notificacion, new SaveCallback() {
                            @Override
                            public void done(ParseException e) {
                                if (e == null) {
                                    Log.d("com.parse.push", "successfully unsubscribed to the broadcast channel.");

                                    notificacion.setActive(0);
                                    notificacion.update();
                                } else {
                                    Log.e("com.parse.push", "failed to subscribe for push", e);
                                }
                            }
                        });

                    } else {
                        //update
                        ParsePush.subscribeInBackground(nombre_notificacion, new SaveCallback() {
                            @Override
                            public void done(ParseException e) {
                                if (e == null) {
                                    Log.d("com.parse.push", "successfully subscribed to the broadcast channel.");
                                    notificacion.setActive(1);
                                    notificacion.update();
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
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_settings, menu);
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

    public void IniciarlizarNotificacion(String nombre_notificacion){
        try {
            NotificationModel notificacion = new Select().from(NotificationModel.class).where("name = "+"'"+nombre_notificacion+"'").querySingle();
            int active = notificacion.getActive();
            if (active == 1) {
                Notificaciones.setChecked(true);
                Log.e("Debug", "Iniciar = 1");
            } else {
                Notificaciones.setChecked(false);
                Log.e("Debug", "Iniciar = 0");
            }
        } catch (Throwable e) {
            Notificaciones.setChecked(false);
            Log.e("Debug", "Iniciar throwable");
        }
    }

    public void IniciarPoliticasPrivacidadActivity(View view){
        Intent myIntent = new Intent(SettingsActivity.this, PoliticasPrivacidadActivity.class);
        SettingsActivity.this.startActivity(myIntent);
    }

    public void IniciarTerminosServicioActivity(View view){
        Intent myIntent = new Intent(SettingsActivity.this, TerminosServicioActivity.class);
        SettingsActivity.this.startActivity(myIntent);
    }


}
