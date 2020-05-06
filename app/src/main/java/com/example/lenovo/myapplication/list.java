package com.example.lenovo.myapplication;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ListView;

public class list extends AppCompatActivity {
    private ListView lv1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list);

        lv1 = (ListView)  findViewById(R.id.listView2);

        CustomAdapter adapter = new CustomAdapter(list.this, SearchPage.students);
        lv1.setAdapter(adapter);

    }
}
