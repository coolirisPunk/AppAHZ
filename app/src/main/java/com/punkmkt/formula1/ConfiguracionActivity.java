package com.punkmkt.formula1;

/**
 * Created by germanpunk on 02/10/15.
 */
import android.app.Activity;
import android.app.Fragment;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.SwitchCompat;
import android.util.Log;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.Switch;
import android.widget.TextView;

import com.punkmkt.formula1.adapters.ConfigurationPageAdapter;

import org.w3c.dom.Text;


/**
 * Created by sebastianmendezgiron on 28/09/15.
 */
public class ConfiguracionActivity extends Activity{

    public SwitchCompat mSwitchGPS = null;

    public SwitchCompat mSwitchGPS2 = null;
    public SwitchCompat mSwitchGP3S = null;
    public Context mContext;
    private ViewPager myPager;
    private View view;
    private TextView first_next;
    private ImageButton first_next_image;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_configuration_activity);

        ConfigurationPageAdapter pageAdapter = new ConfigurationPageAdapter(this);
         myPager = (ViewPager) findViewById(R.id.mytwopanelpager);
        myPager.setAdapter(pageAdapter);
        myPager.setCurrentItem(0);

    }
    private int getItem(int i) {
        return myPager.getCurrentItem() + i;
    }

    public void NextViewPager(View v){
        myPager.setCurrentItem(getItem(+1),true);
    }

    public void BackViewPager(View v){
        myPager.setCurrentItem(getItem(-1),true);
    }
    public void EndViewPager(View v){
        Intent myIntent = new Intent(ConfiguracionActivity.this, MainActivity.class);
        ConfiguracionActivity.this.startActivity(myIntent);
    }

    static public void onCheckedChanged(Activity activity, boolean isChecked) {
        if (isChecked) {
            activity.startActivityForResult(new Intent(android.provider.Settings.ACTION_LOCATION_SOURCE_SETTINGS), 100);
        } else {
            activity.startActivityForResult(new Intent(android.provider.Settings.ACTION_LOCATION_SOURCE_SETTINGS), 100);
        }
    }
}