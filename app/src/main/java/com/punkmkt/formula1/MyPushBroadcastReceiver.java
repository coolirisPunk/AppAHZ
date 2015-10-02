package com.punkmkt.formula1;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import com.parse.ParseAnalytics;
import com.parse.ParsePushBroadcastReceiver;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by germanpunk on 13/09/15.
 */

    public class MyPushBroadcastReceiver extends ParsePushBroadcastReceiver {

        @Override
        public void onPushOpen(Context context, Intent intent) {

            //To track "App Opens"
            ParseAnalytics.trackAppOpenedInBackground(intent);

            String pushTitle="";
            try {
                Bundle extras = intent.getExtras();
                if (extras != null) {
                    String jsonData = extras.getString("com.parse.Data");
                    String Channel = extras.getString("com.parse.Channel");
                    Log.d("com.parse.push",jsonData);
                    Log.d("com.parse.push",Channel);
                    //eLog.e("com.parse.push",Channel);
                    JSONObject jsonDataFinal;
                    jsonDataFinal = new JSONObject(jsonData);
                    String pushContent = jsonDataFinal.getString("alert");
                    //String noticia = jsonDataFinal.getString("noticia");
                    String premio = jsonDataFinal.getString("premio");
                    String posicion = jsonDataFinal.getString("posicion");
                    Log.d("com.parse.push", premio);
                    if (Channel!=""){
                        if("notificacion1".equals(Channel) || "notificacion2".equals(Channel) || "notificacion3".equals(Channel) || "notificacion4".equals(Channel) || "notificacion5".equals(Channel)){
                            //Log.d("com.parse.push",Channel);
                            Intent i = new Intent(context, HorariosActivity.class);
                            i.putExtras(intent.getExtras());
                            i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                            context.startActivity(i);
                        }
                        else if("notificaciones".equals(Channel)){
                            String noticia = jsonDataFinal.getString("noticia");
                            Intent i = new Intent(context, MainActivity.class);
                            i.putExtras(intent.getExtras());
                            i.putExtra("id", noticia);
                            i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                            context.startActivity(i);
                        }
                    }
                    else{
                        Intent i = new Intent(context, MainActivity.class);
                        i.putExtras(intent.getExtras());
                        i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                        context.startActivity(i);
                    }
                }
            } catch (JSONException e) {

                e.printStackTrace();
                Log.e("com.parse.push", e.getMessage());
            }



        }
    }

