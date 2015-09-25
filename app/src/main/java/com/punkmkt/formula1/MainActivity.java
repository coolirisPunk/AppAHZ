package com.punkmkt.formula1;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.AsyncTask;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GooglePlayServicesUtil;
import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptor;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.GroundOverlayOptions;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.VisibleRegion;
import com.parse.Parse;
import com.parse.ParseInstallation;

import java.text.DecimalFormat;
import java.util.ArrayList;


public class MainActivity extends FragmentActivity implements LocationListener {


    ArrayList<LatLng> grand_stands;
    ArrayList<LatLng> gradas_platino;
    ArrayList<LatLng> gradas_oro;
    ArrayList<LatLng> foro_sol_sur;
    ArrayList<LatLng> foro_sol_norte;
    ArrayList<LatLng> admision_general;
    ArrayList<LatLng> banos;
    ArrayList<LatLng> personas_incapacitadas;
    ArrayList<LatLng> salidas;

    private RecyclerView mRecyclerView;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;

    GoogleMap googleMap;
    static final LatLng AUTODROMO_HNOZ_RODRIGUEZ = new LatLng(19.404514,-99.0892086);
    static final LatLng KIEL = new LatLng(53.551, 9.993);
    LocationManager locationManager;
    LocationListener mLocationListener;
    //provide any location
    LatLng point1 = new LatLng(21.1812425,-86.8470536);


    private final LatLngBounds BOUNDS = new LatLngBounds(new LatLng(41.8138,12.3891), new LatLng(41.9667, 12.5938));
    private final int MAX_ZOOM = 18;
    private final int MIN_ZOOM = 14;
    private OverscrollHandler mOverscrollHandler = new OverscrollHandler();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //check if Google play services is available
        int status = GooglePlayServicesUtil.isGooglePlayServicesAvailable(this);

        // Showing status
        if(status!= ConnectionResult.SUCCESS){
            int requestCode = 10;
            Dialog dialog = GooglePlayServicesUtil.getErrorDialog(status, this, requestCode);
            dialog.show();

        }
        else {
            grand_stands = new ArrayList<LatLng>();
            gradas_platino = new ArrayList<LatLng>();
            gradas_oro = new ArrayList<LatLng>();
            foro_sol_sur = new ArrayList<LatLng>();
            foro_sol_norte = new ArrayList<LatLng>();
            admision_general = new ArrayList<LatLng>();
            //Grandstands
            grand_stands.add(new LatLng(19.403991, -99.092812));
            grand_stands.add(new LatLng(19.405533, -99.092758));

            //Gradas platino
            gradas_platino.add(new LatLng(19.403554, -99.089679));
            gradas_platino.add(new LatLng(19.402835, -99.089271));

            //Gradas oro
            gradas_oro.add(new LatLng(19.404542, -99.087708));
            gradas_oro.add(new LatLng(19.404098, -99.087708));

            //Foro sol sur
            foro_sol_sur.add(new LatLng(19.405069, -99.089722));

            //Foro sol norte
            foro_sol_norte.add(new LatLng(19.404653, -99.091868));

            //admision general
            admision_general.add(new LatLng(19.404778, -99.086355));
            admision_general.add(new LatLng(19.401644, -99.089045));

            // Getting reference to the SupportMapFragment
            SupportMapFragment fm = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map);
            // Getting GoogleMap object from the fragmentpremios
            googleMap = fm.getMap();
            googleMap.setMapType(GoogleMap.MAP_TYPE_SATELLITE);
            LatLng centerLatLng = AUTODROMO_HNOZ_RODRIGUEZ;
            CameraUpdate cameraUpdate  = CameraUpdateFactory.newLatLngZoom(centerLatLng,14);
            googleMap.moveCamera(cameraUpdate);


            // Enabling MyLocation Layer of Google Map
            googleMap.setMyLocationEnabled(true);


            //add overlay
            if (googleMap!=null){
                Marker AHZ = googleMap.addMarker(new MarkerOptions().position(AUTODROMO_HNOZ_RODRIGUEZ)
                        .title("Autodromo Hermanos Rodriguez"));
                GroundOverlayOptions newarkMap = new GroundOverlayOptions()
                        .image(BitmapDescriptorFactory.fromResource(R.drawable.meixoco))
                        .position(AUTODROMO_HNOZ_RODRIGUEZ, 1300f, 1000f);
                //GroundOverlay imageOverlay = map.addGroundOverlay(newarkMap);
                googleMap.addGroundOverlay(newarkMap);
            }
//            //Set Anchors
//            LatLng NE_ANCHOR = new LatLng(19.409926, -99.102646);
//            LatLng SW_ANCHOR = new LatLng(19.393276, -99.083904);
//            LatLngBounds bounds = new LatLngBounds(SW_ANCHOR,NE_ANCHOR);
//
//            BitmapDescriptor image = BitmapDescriptorFactory.fromResource(R.drawable.meixoco);
//
//            GroundOverlayOptions groundOverlayOptions = new GroundOverlayOptions();
//            groundOverlayOptions.positionFromBounds(bounds);
//            //groundOverlayOptions.transparency((float) 0.5);
//            groundOverlayOptions.image(image);
//            googleMap.addGroundOverlay(groundOverlayOptions);
//
//            mOverscrollHandler.sendEmptyMessageDelayed(0,100);


        }

        //    if (savedInstanceState == null) {
     //       getFragmentManager().beginTransaction()
      //              .add(R.id.container, new CursosFragment())
      //              .commit();
      //  }

    }

    private LatLng getLatLngCorrection(LatLngBounds cameraBounds) {
        double latitude=0, longitude=0;
        if(cameraBounds.southwest.latitude < BOUNDS.southwest.latitude) {
            latitude = BOUNDS.southwest.latitude - cameraBounds.southwest.latitude;
        }
        if(cameraBounds.southwest.longitude < BOUNDS.southwest.longitude) {
            longitude = BOUNDS.southwest.longitude - cameraBounds.southwest.longitude;
        }
        if(cameraBounds.northeast.latitude > BOUNDS.northeast.latitude) {
            latitude = BOUNDS.northeast.latitude - cameraBounds.northeast.latitude;
        }
        if(cameraBounds.northeast.longitude > BOUNDS.northeast.longitude) {
            longitude = BOUNDS.northeast.longitude - cameraBounds.northeast.longitude;
        }
        return new LatLng(latitude, longitude);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
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

    @Override
    public void onLocationChanged(Location location) {

    }

    @Override
    public void onStatusChanged(String provider, int status, Bundle extras) {

    }

    @Override
    public void onProviderEnabled(String provider) {

    }

    @Override
    public void onProviderDisabled(String provider) {

    }

    private class OverscrollHandler extends Handler {
        @Override
        public void handleMessage(Message msg) {
            CameraPosition position = googleMap.getCameraPosition();
            VisibleRegion region = googleMap.getProjection().getVisibleRegion();
            float zoom = 0;
            if(position.zoom < MIN_ZOOM) zoom = MIN_ZOOM;
            if(position.zoom > MAX_ZOOM) zoom = MAX_ZOOM;
            LatLng correction = getLatLngCorrection(region.latLngBounds);
            if(zoom != 0 || correction.latitude != 0 || correction.longitude != 0) {
                zoom = (zoom==0)?position.zoom:zoom;
                double lat = position.target.latitude + correction.latitude;
                double lon = position.target.longitude + correction.longitude;
                CameraPosition newPosition = new CameraPosition(new LatLng(lat,lon), zoom, position.tilt, position.bearing);
                CameraUpdate update = CameraUpdateFactory.newCameraPosition(newPosition);
                googleMap.moveCamera(update);
            }
        /* Recursively call handler every 100ms */
            sendEmptyMessageDelayed(0,100);
        }
    }


    public double CalculationByDistance(LatLng StartP, LatLng EndP) {
        int Radius = 6371;// radius of earth in Km
        double lat1 = StartP.latitude;
        double lat2 = EndP.latitude;
        double lon1 = StartP.longitude;
        double lon2 = EndP.longitude;
        double dLat = Math.toRadians(lat2 - lat1);
        double dLon = Math.toRadians(lon2 - lon1);
        double a = Math.sin(dLat / 2) * Math.sin(dLat / 2)
                + Math.cos(Math.toRadians(lat1))
                * Math.cos(Math.toRadians(lat2)) * Math.sin(dLon / 2)
                * Math.sin(dLon / 2);
        double c = 2 * Math.asin(Math.sqrt(a));
        double valueResult = Radius * c;
        double km = valueResult / 1;
        DecimalFormat newFormat = new DecimalFormat("####");
        int kmInDec = Integer.valueOf(newFormat.format(km));
        double meter = valueResult % 1000;
        int meterInDec = Integer.valueOf(newFormat.format(meter));
        Log.i("Radius Value", "" + valueResult + "   KM  " + kmInDec
                + " Meter   " + meterInDec);

        return Radius * c;
    }
}
