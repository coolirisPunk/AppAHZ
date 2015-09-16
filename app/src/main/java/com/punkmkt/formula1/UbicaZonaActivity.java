package com.punkmkt.formula1;

import android.app.Activity;
import android.content.Context;
import android.support.v4.app.FragmentActivity;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.punkmkt.formula1.models.Zona;

import java.util.ArrayList;

public class UbicaZonaActivity extends FragmentActivity {
    String [] zonas_autodromo;
    private ListView vlist;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ubica_zona);
        ArrayList<Zona> arrayOfZonas = new ArrayList<Zona>();
        zonas_autodromo = getResources().getStringArray(R.array.zonas_autodromo);
        for (String s: zonas_autodromo){
            Zona zona = new Zona(s);
            arrayOfZonas.add(zona);
        }
        // Construct the data source


// Create the adapter to convert the array to views
        ZonasAdapter adapter = new ZonasAdapter(this, arrayOfZonas);
// Attach the adapter to a ListView
        ListView listView = (ListView) findViewById(R.id.listView);
        listView.setAdapter(adapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position,
                                    long id) {
                Log.e("Position",String.valueOf(position));
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_ubica_zona, menu);
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


    public class ZonasAdapter extends ArrayAdapter<Zona> {
        public ZonasAdapter(Context context, ArrayList<Zona> zonas) {
            super(context, 0, zonas);
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            // Get the data item for this position
            Zona user = getItem(position);
            // Check if an existing view is being reused, otherwise inflate the view
            if (convertView == null) {
                convertView = LayoutInflater.from(getContext()).inflate(R.layout.row_localiza_tu_zona, parent, false);
            }
            // Lookup view for data population
            TextView tvName = (TextView) convertView.findViewById(R.id.tvName);

            // Populate the data into the template view using the data object
            tvName.setText(user.name);
            if (position == 0) {
                convertView.setBackgroundColor(getResources().getColor(R.color.grandstands));
            } else if (position == 1) {
                convertView.setBackgroundColor(getResources().getColor(R.color.gradas_platino));
            }
            else if (position == 2) {
                convertView.setBackgroundColor(getResources().getColor(R.color.gradas_oro));
            }
            else if (position == 3) {
                convertView.setBackgroundColor(getResources().getColor(R.color.foro_sol_sur));
            }
            else if (position == 4) {
                convertView.setBackgroundColor(getResources().getColor(R.color.foro_sol_norte));
            }
            else if (position == 5) {
                convertView.setBackgroundColor(getResources().getColor(R.color.admision_general));
            }

            // Return the completed view to render on screen
            return convertView;
        }
    }
}
