package com.example.lenovo.myapplication;


import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;


public class courseAdapter extends BaseAdapter {
    private LayoutInflater courseInflater;
    private List<course> courseList;

    public courseAdapter(Activity activity, List<course> courseList) {
        courseInflater = (LayoutInflater) activity.getSystemService(
                Context.LAYOUT_INFLATER_SERVICE);
        this.courseList = courseList;
    }

    @Override
    public int getCount() {
        return courseList.size();
    }

    @Override
    public Object getItem(int i) {
        return courseList.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        View lineView;
        lineView = courseInflater.inflate(R.layout.course_layout, null);
        TextView textViewCourseName = (TextView) lineView.findViewById(R.id.textViewcourseName);
        ImageView imageViewCoursePicture = (ImageView) lineView.findViewById(R.id.imageViewcoursePicture);

        course course = courseList.get(i);
        textViewCourseName.setText(course.getCourseName());
        imageViewCoursePicture.setImageResource(R.drawable.c);

        return lineView;
    }
}