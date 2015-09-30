package com.punkmkt.formula1;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.Signature;
import android.support.v4.app.FragmentActivity;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Base64;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.FacebookSdk;
import com.facebook.login.LoginManager;
import com.facebook.login.LoginResult;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseUser;
import com.parse.SaveCallback;
import com.parse.SignUpCallback;
import com.punkmkt.formula1.adapters.CustomizedSpinnerAdapter;
import com.punkmkt.formula1.fragments.LoginFBFragment;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.regex.Pattern;

public class LoginSingUpActivity extends FragmentActivity {
    private EditText first_nameView;
    private EditText last_nameView;
    private EditText emailView;
    private EditText zonaView;
    private EditText asientoView;
    private Spinner genero;
    private TextView name;
    private TextView facebook_id;
    private TextView locale;
    private TextView link;
    private TextView age_range;
    CallbackManager callbackManager;
    private ParseObject customUser;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_sing_up);
        FacebookSdk.sdkInitialize(getApplicationContext());
        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction().add(R.id.fragment_container, new LoginFBFragment()).commit();
        }
        callbackManager = CallbackManager.Factory.create();
        LoginManager.getInstance().registerCallback(callbackManager,
                new FacebookCallback<LoginResult>() {
                    @Override
                    public void onSuccess(LoginResult loginResult) {
                        // App code
                        Log.d("ohhh", loginResult.getAccessToken().toString());
                    }

                    @Override
                    public void onCancel() {
                        // App code
                    }

                    @Override
                    public void onError(FacebookException exception) {
                        // App code
                    }
                });
        showHashKey(getApplicationContext());

        first_nameView = (EditText) findViewById(R.id.first_name);
        last_nameView = (EditText) findViewById(R.id.last_name);
        emailView = (EditText) findViewById(R.id.email);
        zonaView = (EditText) findViewById(R.id.zona);
        asientoView = (EditText) findViewById(R.id.asiento);
        name = (TextView) findViewById(R.id.name);
        facebook_id = (TextView) findViewById(R.id.facebook_id);
        locale = (TextView) findViewById(R.id.locale);
        link = (TextView) findViewById(R.id.link);
        age_range = (TextView) findViewById(R.id.age_range);
         genero = (Spinner) findViewById(R.id.spinner_gender);

        final String[] data_array = getResources().getStringArray(R.array.gender_arrays);

        final ArrayList<String> data = new ArrayList<String>();
        for (String valor: data_array ){
            data.add(valor);
        }

        data.add(0,"Género"); //Add element at 0th index
        final ArrayAdapter<String> adapter1 = new CustomizedSpinnerAdapter(
                LoginSingUpActivity.this, android.R.layout.simple_spinner_item,
                data);
        genero.setAdapter(adapter1);



        findViewById(R.id.action_button).setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {

                // Validate the sign up data
                boolean validationError = false;
                StringBuilder validationErrorMessage =
                        new StringBuilder(getResources().getString(R.string.error_intro));
                if (isEmpty(first_nameView)) {
                    validationError = true;
                    validationErrorMessage.append(getResources().getString(R.string.error_blank_first_name));
                }
                if (isEmpty(last_nameView)) {
                    validationError = true;
                    validationErrorMessage.append(getResources().getString(R.string.error_blank_last_name));
                }
                if(!isValidEmail(emailView.getText().toString())){
                    validationError = true;
                    validationErrorMessage.append(getResources().getString(R.string.error_invalid_email));
                }
                if (isEmpty(zonaView)) {
                    validationError = true;
                    validationErrorMessage.append(getResources().getString(R.string.error_blank_zona));
                }
                if (isEmpty(asientoView)) {
                    validationError = true;
                    validationErrorMessage.append(getResources().getString(R.string.error_blank_asiento));
                }

                if(!ValidateSpinner(genero,"Género")){
                    validationError = true;
                    validationErrorMessage.append(getResources().getString(R.string.error_blank_genero));
                }

                validationErrorMessage.append(getResources().getString(R.string.error_end));

                // If there is a validation error, display the error
                if (validationError) {
                    Toast.makeText(LoginSingUpActivity.this, validationErrorMessage.toString(), Toast.LENGTH_LONG)
                            .show();
                    return;
                }

                // Set up a progress dialog
                final ProgressDialog dlg = new ProgressDialog(LoginSingUpActivity.this);
                dlg.setTitle("Please wait.");
                dlg.setMessage("Signing up.  Please wait.");
                dlg.show();


                customUser = new ParseObject("CustomUser");

                //customUser.put("foo", "bar");
                customUser.put("first_name", first_nameView.getText().toString());
                customUser.put("last_name", last_nameView.getText().toString());
                customUser.put("email",emailView.getText().toString());
                customUser.put("gender", genero.getSelectedItem().toString());
                customUser.put("name",name.getText().toString());
                customUser.put("facebook_id",facebook_id.getText().toString());
                customUser.put("locale",locale.getText().toString());
                customUser.put("link",link.getText().toString());
                customUser.put("age_range",age_range.getText().toString());
                customUser.put("zona",zonaView.getText().toString());
                customUser.put("asiento", asientoView.getText().toString());

                // Call the Parse signup method
                customUser.saveInBackground(new SaveCallback() {

                    @Override
                    public void done(ParseException e) {
                        dlg.dismiss();
                        if (e != null) {

                            // Show the error message
                            Toast.makeText(LoginSingUpActivity.this, e.getMessage(), Toast.LENGTH_LONG).show();

                            Log.d("parse",customUser.getObjectId().toString());
                        } else {
                            Log.d("parse",customUser.getObjectId().toString());
                            // Start an intent for the dispatch activity
                            Intent intent = new Intent(LoginSingUpActivity.this, MainActivity.class);
                            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                            startActivity(intent);
                        }
                    }
                });


            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_login_sing_up, menu);
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


    private boolean isEmpty(EditText etText) {
        if (etText.getText().toString().trim().length() > 0) {
            return false;
        } else {
            return true;
        }
    }

    private boolean isMatching(EditText etText1, EditText etText2) {
        if (etText1.getText().toString().equals(etText2.getText().toString())) {
            return true;
        } else {
            return false;
        }
    }


    public final static boolean isValidEmail(CharSequence target) {
        if (target == null)
            return false;

        return android.util.Patterns.EMAIL_ADDRESS.matcher(target).matches();
    }

    public static void showHashKey(Context context) {

        try {
            PackageInfo info = context.getPackageManager().getPackageInfo(
                    "com.punkmkt.formula1", PackageManager.GET_SIGNATURES); //Your            package name here
            for (Signature signature : info.signatures) {
                MessageDigest md = MessageDigest.getInstance("SHA");
                md.update(signature.toByteArray());

                Log.i("KeyHash:", Base64.encodeToString(md.digest(), Base64.DEFAULT));
            }
        } catch (PackageManager.NameNotFoundException e) {
        } catch (NoSuchAlgorithmException e) {
        }
    }

    public final static boolean ValidateSpinner(Spinner s,String s_option){
        String st =s.getSelectedItem().toString();
        int pos =s.getSelectedItemPosition();
        if(pos==0)
        {
            return false;
        }
        if(!st.equals(s_option))
        {
            return true;
        }
        else{
            return false;
        }
    }
}
