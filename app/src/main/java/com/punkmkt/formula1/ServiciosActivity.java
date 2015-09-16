package com.punkmkt.formula1;

import android.support.v4.app.FragmentActivity;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

public class ServiciosActivity extends FragmentActivity {
    TextView titulo;
    TextView descripcion;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_servicios);
        titulo = (TextView) findViewById(R.id.titulo_servicio);
        descripcion = (TextView) findViewById(R.id.descripcion_servicio);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_servicios, menu);
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

    public void MostrarServiciosEmergencia(View view){
        titulo.setText(getString(R.string.titulo_emergencia));
        descripcion.setText(getString(R.string.descripcion_emergencia));
    }
    public void MostrarAlimentos(View view){
        titulo.setText(getString(R.string.titulo_alimentos));
        descripcion.setText(getString(R.string.descripcion_alimentos));
    }
    public void MostrarBanos(View view){
        titulo.setText(getString(R.string.titulo_banos));
        descripcion.setText(getString(R.string.descripcion_banos));
    }
    public void MostrarTiendas(View view){
        titulo.setText(getString(R.string.titulo_tienda));
        descripcion.setText(getString(R.string.descripcion_tienda));
    }



}
