package com.punkmkt.formula1;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.media.Image;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;

import com.parse.Parse;
import com.parse.ParseAnalytics;
import com.parse.ParseException;
import com.parse.ParseInstallation;
import com.parse.ParsePush;
import com.parse.ParsePushBroadcastReceiver;
import com.parse.PushService;
import com.parse.SaveCallback;
import com.punkmkt.formula1.databases.NotificationModel;
import com.raizlabs.android.dbflow.annotation.Table;
import com.raizlabs.android.dbflow.runtime.FlowContentObserver;
import com.raizlabs.android.dbflow.sql.builder.Condition;
import com.raizlabs.android.dbflow.sql.language.Select;
import com.raizlabs.android.dbflow.sql.language.Update;
import com.raizlabs.android.dbflow.sql.language.Where;

import java.io.IOException;

public class HorariosActivity extends Activity {

    ImageView notificacion_practica1;
    ImageView notificacion_practica2;
    ImageView notificacion_practica3;
    ImageView notificacion_calificacion;
    ImageView notificacion_premio;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_horarios);
        notificacion_practica1 = (ImageView) findViewById(R.id.notificacion_practica1);
        notificacion_practica2 = (ImageView) findViewById(R.id.notificacion_practica2);
        notificacion_practica3 = (ImageView) findViewById(R.id.notificacion_practica3);
        notificacion_calificacion = (ImageView) findViewById(R.id.notificacion_calificacion);
        notificacion_premio = (ImageView) findViewById(R.id.notificacion_premio);

        FlowContentObserver observer = new FlowContentObserver();

        // registers for callbacks from the specified table
        observer.registerForContentChanges(getApplicationContext(), NotificationModel.class);

        IniciarlizarNotificacion(notificacion_practica1, "notificacion1");
        IniciarlizarNotificacion(notificacion_practica2 ,"notificacion2");
        IniciarlizarNotificacion(notificacion_practica3 ,"notificacion3");
        IniciarlizarNotificacion(notificacion_calificacion ,"notificacion4");
        IniciarlizarNotificacion(notificacion_premio, "notificacion5");


        notificacion_practica1.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                CambiarEstadoNotificacion(notificacion_practica1, "notificacion1");

            }
        });
        notificacion_practica2.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                CambiarEstadoNotificacion(notificacion_practica2,"notificacion2");

            }
        });
        notificacion_practica3.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                CambiarEstadoNotificacion(notificacion_practica3,"notificacion3");

            }
        });
        notificacion_calificacion.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                CambiarEstadoNotificacion(notificacion_calificacion,"notificacion4");

            }
        });

        notificacion_premio.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                CambiarEstadoNotificacion(notificacion_premio,"notificacion5");

            }
        });



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

}
