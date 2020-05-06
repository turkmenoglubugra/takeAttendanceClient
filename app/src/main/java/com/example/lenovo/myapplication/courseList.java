package com.example.lenovo.myapplication;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ListView;

public class courseList extends AppCompatActivity {
    private ListView lv3;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_course_list);
        lv3 = (ListView)  findViewById(R.id.listView3);

        courseAdapter adapter = new courseAdapter(courseList.this, addCourse.courseList);
        lv3.setAdapter(adapter);
    }
}
