package com.punkmkt.formula1.adapters;

/**
 * Created by germanpunk on 02/10/15.
 */
import android.app.Activity;
import android.app.Fragment;
import android.content.Context;
import android.content.Intent;
import android.os.Parcelable;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.SwitchCompat;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.animation.BounceInterpolator;
import android.widget.CompoundButton;


import com.parse.ParseException;
import com.parse.ParsePush;
import com.parse.SaveCallback;
import com.punkmkt.formula1.ConfiguracionActivity;
import com.punkmkt.formula1.R;
import com.punkmkt.formula1.databases.NotificationModel;
import com.raizlabs.android.dbflow.sql.language.Select;

/**
 * Created by sebastianmendezgiron on 29/09/15.
 */

public class ConfigurationPageAdapter extends PagerAdapter {
    Activity activity;
    public ConfigurationPageAdapter(Activity activity){
            this.activity = activity;
    }
    public int getCount() {
        return 2;
    }

    public Object instantiateItem(View collection, int position) {
        LayoutInflater inflater = (LayoutInflater) collection.getContext()
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        int resId = 0;
        switch (position) {
            case 0:

                resId = R.layout.activar_ubicacion;
                break;
            case 1:
                resId = R.layout.activar_notificaciones;
                break;
        }

        View view = inflater.inflate(resId, null);

        ((ViewPager) collection).addView(view);

        switch (position) {
            case 0:
                SwitchCompat mSwitchGPS = (SwitchCompat) view.findViewById(R.id.turnGPSOn);
                mSwitchGPS.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                        if(isChecked){
                            Log.d("checked", "yeaah");
                            ConfiguracionActivity.onCheckedChanged(activity,isChecked);
                        } else {

                        }
                    }
                });

                break;
            case 1:
                SwitchCompat mSwitchGPS2 = (SwitchCompat) view.findViewById(R.id.turnNotification);
                mSwitchGPS2.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                            Log.d("checked", "yeaah");
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

                                }
                                else {
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

                            }
                            catch (Throwable e) {
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
                break;
        }

        view.setTag("myview" + position);

        return view;
    }

    @Override
    public void destroyItem(View arg0, int arg1, Object arg2) {
        ((ViewPager) arg0).removeView((View) arg2);

    }

    @Override
    public void finishUpdate(View arg0) {
        // TODO Auto-generated method stub

    }

    @Override
    public boolean isViewFromObject(View arg0, Object arg1) {
        return arg0 == ((View) arg1);

    }

    @Override
    public void restoreState(Parcelable arg0, ClassLoader arg1) {
        // TODO Auto-generated method stub

    }

    @Override
    public Parcelable saveState() {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public void startUpdate(View arg0) {
        // TODO Auto-generated method stub

    }





}