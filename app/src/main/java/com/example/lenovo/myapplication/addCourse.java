package com.example.lenovo.myapplication;

import android.content.Context;
import android.content.Intent;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;


public class addCourse extends AppCompatActivity {
    private Button btnCourseList, btnAddCourse;
    private EditText insertCourseName;
    static final List<course> courseList = new ArrayList<course>();
    private RequestQueue mQueue;
    private static final int REQUEST_CODE = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mQueue = Volley.newRequestQueue(this);

        setContentView(R.layout.activity_add_course);
        btnCourseList = (Button) findViewById(R.id.courseList);
        btnAddCourse = (Button) findViewById(R.id.addCourse);
        insertCourseName = (EditText) findViewById(R.id.editText1) ;

        btnCourseList.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                getCourseList();
                final Handler handler = new Handler();
                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        // Do something after 5s = 5000ms
                        open();
                    }
                }, 1000);

            }
        });

        btnAddCourse.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v){
                insert();
            }
        });

    }
    public void open(){
        Intent intent = new Intent(this, courseList.class );
        startActivity(intent);
    }

    public void getCourseList(){
        courseList.clear();
        String url = "http://10.0.2.2:5000/get_courses";
        JsonArrayRequest jsonArrayRequest1 = new JsonArrayRequest(
                Request.Method.GET,
                url,
                null,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        // Do something with response
                        // Process the JSON
                        try{
                            // Loop through the array elements
                            for(int i=0;i<response.length();i++){
                                courseList.add(new course(response.get(i).toString()));
                            }
                        }catch (Exception e){
                            System.out.println(e.getMessage());
                            e.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener(){
                    @Override
                    public void onErrorResponse(VolleyError error){
                        System.out.println(error.getMessage());
                        // Do something when error occurred
                    }
                }
        );
        mQueue.add(jsonArrayRequest1);
    }

    public void insert(){
            /*Some example that you can pass your data through request body*/
            RequestQueue requestQueue = Volley.newRequestQueue(this);
            JSONObject jsonBodyObj = new JSONObject();
            String url = "http://10.0.2.2:5000/insert_delete_course";
            try{
                jsonBodyObj.put("key1", insertCourseName.getText());
                //jsonBodyObj.put("key2", spinnerCourses.getSelectedItemPosition());

            }catch (JSONException e){
                e.printStackTrace();
            }
            final String requestBody = jsonBodyObj.toString();

            JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.POST,
                    url, null, new Response.Listener<JSONObject>(){
                @Override    public void onResponse(JSONObject response) {
                    Log.i("Response",String.valueOf(response));
                }
            }, new Response.ErrorListener() {
                @Override    public void onErrorResponse(VolleyError error) {
                    VolleyLog.e("Error: ", error.getMessage());
                }
            }){
                @Override    public Map<String, String> getHeaders() throws AuthFailureError {
                    HashMap<String, String> headers = new HashMap<String, String>();
                    headers.put("Content-Type", "application/json");
                    return headers;
                }


                @Override    public byte[] getBody() {
                    try {
                        return requestBody == null ? null : requestBody.getBytes("utf-8");
                    } catch (UnsupportedEncodingException uee) {
                        VolleyLog.wtf("Unsupported Encoding while trying to get the bytes of %s using %s",
                                requestBody, "utf-8");
                        return null;
                    }
                }
            };

            requestQueue.add(jsonObjectRequest);
            insertCourseName.setText("");
        View view = this.getCurrentFocus();
        if (view != null) {
            InputMethodManager imm = (InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
        }

    }
}
