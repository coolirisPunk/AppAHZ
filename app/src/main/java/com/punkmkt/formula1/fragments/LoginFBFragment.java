package com.punkmkt.formula1.fragments;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.GraphRequest;
import com.facebook.GraphResponse;
import com.facebook.HttpMethod;
import com.facebook.Profile;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;
import com.punkmkt.formula1.R;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Arrays;

public class LoginFBFragment extends Fragment {

    private LoginButton loginButton;
    private CallbackManager callbackManager;

    private TextView first_name;
    private TextView last_name;
    private TextView email;
    private Spinner genero;
    private TextView name;
    private TextView facebook_id;
    private TextView locale;
    private TextView link;
    private TextView age_range;
    public LoginFBFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_login_fb, container, false);
        callbackManager = CallbackManager.Factory.create();
        //textView=(TextView)view.findViewById(R.id.DetailText);
        first_name=(TextView)getActivity().findViewById(R.id.first_name);
        last_name=(TextView)getActivity().findViewById(R.id.last_name);
        email=(TextView)getActivity().findViewById(R.id.email);
        genero=(Spinner)getActivity().findViewById(R.id.spinner_gender);
        name=(TextView)getActivity().findViewById(R.id.name);
        facebook_id=(TextView)getActivity().findViewById(R.id.facebook_id);
        locale=(TextView)getActivity().findViewById(R.id.locale);
        link=(TextView)getActivity().findViewById(R.id.link);
        age_range=(TextView)getActivity().findViewById(R.id.age_range);
        loginButton = (LoginButton) view.findViewById(R.id.login_button);
        //loginButton.setReadPermissions("user_friends");
        loginButton.setReadPermissions(Arrays.asList("public_profile", "user_friends", "email"));
        loginButton.setFragment(this);


        // Callback registration

        loginButton.registerCallback(callbackManager, new FacebookCallback<LoginResult>() {
            @Override
            public void onSuccess(LoginResult loginResult) {

                GraphRequest request = new GraphRequest(
                        loginResult.getAccessToken(),
                        "/"+loginResult.getAccessToken().getUserId(),
                        null,
                        HttpMethod.GET,
                        new GraphRequest.Callback() {
                            public void onCompleted(GraphResponse response) {

                                Log.d("Login", response.toString());
                                try {
                                    JSONObject object = response.getJSONObject();
                                    Log.d("login",object.toString());
                                    if(object.has("first_name") && !object.optString("first_name").equals("null")){
                                        first_name.setText(object.optString("first_name"));
                                    }
                                    if(object.has("last_name") && !object.optString("last_name").equals("null")){
                                        last_name.setText(object.optString("last_name"));
                                    }
                                    if(object.has("email") && !object.optString("email").equals("null")){
                                        email.setText(object.optString("email"));
                                    }
                                    if(object.has("gender") && !object.optString("gender").equals("null")){
                                        if ("male".equals(object.optString("gender"))){
                                            genero.setSelection(1);
                                        }
                                        else{
                                            genero.setSelection(2);
                                        }
                                    }
                                    if(object.has("name") && !object.optString("name").equals("null")){
                                        name.setText(object.optString("name"));
                                    }
                                    if(object.has("locale") && !object.optString("locale").equals("null")){
                                        locale.setText(object.optString("locale"));
                                    }

                                    if(object.has("id") && !object.optString("id").equals("null")){
                                        facebook_id.setText(object.optString("id"));
                                    }
                                    if(object.has("age_range") && !object.optString("age_range").equals("null")){
                                        age_range.setText(object.optString("age_range"));
                                    }
                                    if(object.has("link") && !object.optString("link").equals("null")){
                                        link.setText(object.optString("link"));
                                    }
                                } catch (FacebookException e) {
                                    e.printStackTrace();
                                }

                            }
                            }
                );
                Bundle parameters = new Bundle();
                parameters.putString("fields", "id,name,first_name,last_name,age_range,link,gender,birthday,email,locale");
                request.setParameters(parameters);
                request.executeAsync();
            }



            @Override
            public void onCancel() {
                Toast.makeText(getActivity(), "Login canceled", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onError(FacebookException exception) {
                Toast.makeText(getActivity(), "Login error"+exception.getMessage().toString(), Toast.LENGTH_SHORT).show();
            }
        });


        return view;
    }
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        callbackManager.onActivityResult(requestCode, resultCode, data);
    }





}
