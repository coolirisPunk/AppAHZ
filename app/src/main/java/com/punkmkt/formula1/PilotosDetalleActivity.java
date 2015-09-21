package com.punkmkt.formula1;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;

import com.punkmkt.formula1.models.Piloto;
import com.punkmkt.formula1.models.Premio;

public class PilotosDetalleActivity extends Activity {
    private String AHZ_PILOTOS_JSON_API_URL = "http://104.236.3.158/api/pilotos/";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pilotos_detalle);

        Intent intent = getIntent();
        Piloto piloto = new Piloto();
        String id = intent.getStringExtra("id");
        Log.d("id", id);
        AHZ_PILOTOS_JSON_API_URL = AHZ_PILOTOS_JSON_API_URL + id + "/";
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_pilotos_detalle, menu);
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
