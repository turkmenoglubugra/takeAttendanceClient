package com.example.lenovo.myapplication;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainPage extends AppCompatActivity {

    private Button openPollingPage;
    private Button openSearhPage;
    private Button addCourse;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_page);

        openPollingPage = (Button) findViewById(R.id.openPollingPage);
        openPollingPage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openPpollingPage();
            }
        });

        openSearhPage = (Button) findViewById(R.id.openSearchPage);
        openSearhPage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openSearchPage();
            }
        });

        addCourse = (Button) findViewById(R.id.addCourse);
        addCourse.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addCoursePage();
            }
        });
    }

    public void openPpollingPage(){
        Intent intent = new Intent(this, MainActivity.class );
        startActivity(intent);
    }

    public void openSearchPage(){
        Intent intent = new Intent(this, SearchPage.class );
        //Intent intent = new Intent(this, listpage.class );
        startActivity(intent);
    }
    public void addCoursePage(){
        Intent intent = new Intent(this, addCourse.class );
        startActivity(intent);
    }
}
