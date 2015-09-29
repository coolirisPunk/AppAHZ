package com.punkmkt.formula1;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.support.v4.app.FragmentActivity;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseUser;
import com.parse.SaveCallback;
import com.parse.SignUpCallback;
import com.punkmkt.formula1.adapters.CustomizedSpinnerAdapter;
import com.punkmkt.formula1.fragments.LoginFBFragment;

import java.util.ArrayList;
import java.util.regex.Pattern;

public class LoginSingUpActivity extends FragmentActivity {
    private EditText first_nameView;
    private EditText last_nameView;
    private EditText emailView;
    private EditText zonaView;
    private EditText asientoView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_sing_up);
        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction().add(R.id.fragment_container, new LoginFBFragment()).commit();
        }
        first_nameView = (EditText) findViewById(R.id.first_name);
        last_nameView = (EditText) findViewById(R.id.last_name);
        emailView = (EditText) findViewById(R.id.email);
        zonaView = (EditText) findViewById(R.id.zona);
        asientoView = (EditText) findViewById(R.id.asiento);

        Spinner mySpinner = (Spinner) findViewById(R.id.spinner_gender);
        final String[] data_array = getResources().getStringArray(R.array.gender_arrays);

        final ArrayList<String> data = new ArrayList<String>();
        for (String valor: data_array ){
            data.add(valor);
        }

        data.add(0,"Género"); //Add element at 0th index
        final ArrayAdapter<String> adapter1 = new CustomizedSpinnerAdapter(
                LoginSingUpActivity.this, android.R.layout.simple_spinner_item,
                data);
        mySpinner.setAdapter(adapter1);



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


                ParseObject customUser = new ParseObject("CustomUser");
                //customUser.put("foo", "bar");
                customUser.put("first_name", first_nameView.getText().toString());
                customUser.put("last_name", last_nameView.getText().toString());
                customUser.put("email",emailView.getText().toString());
                customUser.put("zona",zonaView.getText().toString());
                customUser.put("asiento",asientoView.getText().toString());

                // Call the Parse signup method
                customUser.saveInBackground(new SaveCallback() {

                    @Override
                    public void done(ParseException e) {
                        dlg.dismiss();
                        if (e != null) {
                            // Show the error message
                            Toast.makeText(LoginSingUpActivity.this, e.getMessage(), Toast.LENGTH_LONG).show();
                        } else {
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
}
