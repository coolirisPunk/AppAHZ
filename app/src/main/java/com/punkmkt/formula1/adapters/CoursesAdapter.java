package com.punkmkt.formula1.adapters;

/**
 * Created by germanpunk on 30/07/15.
 */
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.punkmkt.formula1.R;
import com.punkmkt.formula1.models.Course;

import java.util.ArrayList;


/**
 * Created by thespianartist on 24/07/14.
 */
public class CoursesAdapter  extends RecyclerView.Adapter<CoursesAdapter.ViewHolder> {

    private ArrayList<Course> courses;
    private int itemLayout;


    public  CoursesAdapter(ArrayList<Course> data,  int itemLayout){
        courses = data;
        this.itemLayout = itemLayout;
    }

    public static class ViewHolder extends RecyclerView.ViewHolder implements AdapterView.OnClickListener {

        public ImageView image;
        public TextView name;
        public TextView description;

        public ViewHolder(View itemView) {

            super(itemView);

            itemView.setOnClickListener(this);
            image = (ImageView) itemView.findViewById(R.id.image);
            name = (TextView) itemView.findViewById(R.id.name);
            //description = (TextView) itemView.findViewById(R.id.description);
        }

        @Override
        public void onClick(View view) {
            Toast.makeText(view.getContext(),"OnItemClick :D", Toast.LENGTH_SHORT).show();
        }

    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup  parent, int i) {

        View itemLayoutView = LayoutInflater.from(parent.getContext()).inflate(itemLayout, parent, false);
        ViewHolder viewHolder = new ViewHolder(itemLayoutView);
        return viewHolder;

    }

    @Override
    public void onBindViewHolder(ViewHolder viewHolder, int position) {

        Course course = courses.get(position);
        viewHolder.name.setText(course.getName());
        viewHolder.description.setText(course.getDescription());

        switch (course.getId()){
            case 1:

                viewHolder.image.setImageResource(R.drawable.disenio);
                break;

            case 2:

                viewHolder.image.setImageResource(R.drawable.android);
                break;

            case 3:
                viewHolder.image.setImageResource(R.drawable.swift);
                break;

            case 4:
                viewHolder.image.setImageResource(R.drawable.backend);
                break;

            case 5:
                viewHolder.image.setImageResource(R.drawable.servidores);
                break;
        }
        viewHolder.itemView.setTag(course);
    }


    @Override
    public int getItemCount() {
        return courses.size();
    }

}