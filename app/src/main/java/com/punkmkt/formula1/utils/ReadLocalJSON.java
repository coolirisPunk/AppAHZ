package com.punkmkt.formula1.utils;

/**
 * Created by germanpunk on 30/07/15.
 */
import android.content.Context;
import android.util.Log;
import android.widget.Toast;

import com.punkmkt.formula1.models.Course;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;


/**
 * Created by thespianartist on 23/07/14.
 */

public class ReadLocalJSON {

    private String json = "";
    private ArrayList<Course> courses = new ArrayList<Course>();
    private BufferedReader bufferedReader;
    private StringBuilder builder;


    public ArrayList<Course> getCourses(Context c){


        try {

            builder = new StringBuilder();
            bufferedReader = new BufferedReader(new InputStreamReader(c.getAssets().open("cursos.json")));

            String line = "";

            while ((line=bufferedReader.readLine()) != null){
                builder.append(line);
            }

            bufferedReader.close();
            json = builder.toString();


            JSONArray jsonArray = new JSONArray(json);

            for (int index = 0; index < jsonArray.length(); index++) {
                Course course = new Course();

                JSONObject jsonObject = jsonArray.getJSONObject(index);
                course.setId(jsonObject.getInt("id"));
                course.setName(jsonObject.getString("name"));
                course.setDescription(jsonObject.getString("description"));
                courses.add(course);
            }

        } catch (IOException ex) {
            ex.printStackTrace();
            Toast.makeText(c,"No se pudo obtener los datos",Toast.LENGTH_SHORT).show();

        } catch (JSONException e) {
            e.printStackTrace();
            Toast.makeText(c,"No se pudo obtener los datos",Toast.LENGTH_SHORT).show();
        }

        return courses;
    }



}