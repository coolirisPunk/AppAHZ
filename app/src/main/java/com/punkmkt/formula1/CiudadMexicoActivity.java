package com.punkmkt.formula1;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

public class CiudadMexicoActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ciudad_mexico);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_ciudad_mexico, menu);
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

    public void IniciarActivityHospedaje(View view){
        Intent myIntent = new Intent(CiudadMexicoActivity.this, CiudadMexicoDetalleActivity.class);
        myIntent.putExtra("fragment","hospedaje");
        CiudadMexicoActivity.this.startActivity(myIntent);
    }

    public void IniciarActivityRestaurantes(View view){
        Intent myIntent = new Intent(CiudadMexicoActivity.this, CiudadMexicoDetalleActivity.class);
        myIntent.putExtra("fragment","restaurantes");
        CiudadMexicoActivity.this.startActivity(myIntent);
    }

    public void IniciarActivityAdondeir(View view){
        Intent myIntent = new Intent(CiudadMexicoActivity.this, CiudadMexicoDetalleActivity.class);
        myIntent.putExtra("fragment","adondeir");
        CiudadMexicoActivity.this.startActivity(myIntent);
    }
    public void IniciarActivityEventos(View view){
        Intent myIntent = new Intent(CiudadMexicoActivity.this, CiudadMexicoDetalleActivity.class);
        myIntent.putExtra("fragment","eventos");
        CiudadMexicoActivity.this.startActivity(myIntent);
    }


}
