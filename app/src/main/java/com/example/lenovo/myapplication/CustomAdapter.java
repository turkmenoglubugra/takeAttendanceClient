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

/**
 * Created by hasibezafer on 18.02.2017.
 */

public class CustomAdapter extends BaseAdapter {
    private LayoutInflater studentInflater;
    private List<student> studentList;

    public CustomAdapter(Activity activity, List<student> studentList) {
        studentInflater = (LayoutInflater) activity.getSystemService(
                Context.LAYOUT_INFLATER_SERVICE);
        this.studentList = studentList;
    }

    @Override
    public int getCount() {
        return studentList.size();
    }

    @Override
    public Object getItem(int i) {
        return studentList.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        View lineView;
        lineView = studentInflater.inflate(R.layout.custom_layout, null);
        TextView textViewUserName = (TextView) lineView.findViewById(R.id.textViewUserName);
        TextView textViewSystemClock = (TextView) lineView.findViewById(R.id.textViewSystemClock);
        ImageView imageViewUserPicture = (ImageView) lineView.findViewById(R.id.imageViewUserPicture);

        student student = studentList.get(i);
        textViewUserName.setText(student.getNumber());
        textViewSystemClock.setText(student.getState());
        imageViewUserPicture.setImageResource(R.drawable.st);

        return lineView;
    }
}