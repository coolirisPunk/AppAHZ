package com.punkmkt.formula1;

import android.content.Intent;
import android.location.LocationListener;
import android.support.v4.app.FragmentActivity;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;

import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.punkmkt.formula1.databases.NotificationModel;
import com.raizlabs.android.dbflow.sql.language.Select;

public class MapActivity extends FragmentActivity {
    GoogleMap googleMap;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_map);
        // Getting reference to the SupportMapFragment
        SupportMapFragment fm = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map);
        // Getting GoogleMap object from the fragmentpremios
        googleMap = fm.getMap();
        Intent intent = getIntent();
        try {
            String latitud = intent.getStringExtra("latitud");
            String longitud = intent.getStringExtra("longitud");
            String titulo = intent.getStringExtra("titulo");
            googleMap.setMapType(GoogleMap.MAP_TYPE_NORMAL);
            LatLng centerLatLng = new LatLng(Float.parseFloat(latitud),Float.parseFloat(longitud));
            CameraUpdate cameraUpdate  = CameraUpdateFactory.newLatLngZoom(centerLatLng, 14);
            googleMap.moveCamera(cameraUpdate);
            googleMap.setMyLocationEnabled(true);
            googleMap.addMarker(new MarkerOptions().position(centerLatLng).title(titulo)).showInfoWindow();

        } catch (Throwable e) {
            e.printStackTrace();
            Log.e("Debug", "Iniciar throwable");
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_map, menu);
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
}
