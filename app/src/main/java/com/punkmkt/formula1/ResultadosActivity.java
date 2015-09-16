package com.punkmkt.formula1;

import android.app.Activity;
import android.content.Intent;
import android.support.v4.app.FragmentActivity;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

import com.punkmkt.formula1.fragments.PremiosFragment;

import org.w3c.dom.Text;

public class ResultadosActivity extends FragmentActivity {
    TextView ranking_general;
    ImageButton ranking_general_icon;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_resultados);

        if (savedInstanceState == null) {
               getFragmentManager().beginTransaction()
                      .add(R.id.container_fragment, new PremiosFragment())
                      .commit();
        }
        ranking_general = (TextView) findViewById(R.id.ranking_general);
        ranking_general_icon = (ImageButton) findViewById(R.id.ranking_general_icon);


        ranking_general.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent myIntent = new Intent(ResultadosActivity.this, RankingGeneralActivity.class);
                ResultadosActivity.this.startActivity(myIntent);

            }
        });
        ranking_general_icon.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent myIntent = new Intent(ResultadosActivity.this, RankingGeneralActivity.class);
                ResultadosActivity.this.startActivity(myIntent);

            }
        });

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_resultados, menu);
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

    public void IniciarActividad(Activity from, Activity to){

    }
}
