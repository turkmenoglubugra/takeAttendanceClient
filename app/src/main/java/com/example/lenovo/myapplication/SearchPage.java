package com.example.lenovo.myapplication;

import android.content.Intent;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.Toast;

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
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class SearchPage extends AppCompatActivity {

    private RequestQueue mQueue;
    private ListView lv;
    private static ArrayList<String> dateslist = new ArrayList<String>();
    private static ArrayList<String> courseslist = new ArrayList<String>();
    private static ArrayList<String> courseslistid = new ArrayList<String>();
    private static ArrayList<String> studentslist = new ArrayList<String>();

    private ArrayAdapter<String> dataAdapterForIller1;
    private ArrayAdapter<String> dataAdapterForIller2;
    private Spinner dateSpinner;
    private Spinner courseSpinner;
    private Button searchPolling;
    static final List<student> students = new ArrayList<student>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_page);
        mQueue = Volley.newRequestQueue(this);
        dateSpinner = (Spinner) findViewById(R.id.comboDate);
        courseSpinner = (Spinner) findViewById(R.id.comboCourse);

        getCoursesid();
        getCourses();

        final Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                // Do something after 5s = 5000ms
                fillSpinner2(courseslist);            }
        }, 1000);


        courseSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position, long id) {
                //if (courseSpinner.getSelectedItemPosition() != 0) {
                    get_dates();
                    final Handler handler = new Handler();
                    handler.postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            // Do something after 5s = 5000ms
                            fillSpinner1(dateslist);            }
                    }, 2000);
                //}
            }

            @Override
            public void onNothingSelected(AdapterView<?> parentView) {
                // your code here
            }

        });

        searchPolling = (Button) findViewById(R.id.searchPolling);
        searchPolling.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                getpolling();
                final Handler handler = new Handler();
                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        // Do something after 5s = 5000ms
                        fillList();
                    }
                }, 1000);

                }
        });

    }


    private void fillList(){
        System.out.print(studentslist);
        //final ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
               // android.R.layout.simple_list_item_1, android.R.id.text1, studentslist);
        //lv.setAdapter(adapter);


    }

    private void get_dates(){
        dateslist.clear();
        /*Some example that you can pass your data through request body*/
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        JSONObject jsonBodyObj = new JSONObject();
        String url = "http://10.0.2.2:5000/get_dates";
        try{
            jsonBodyObj.put("key1", courseslistid.get(courseSpinner.getSelectedItemPosition()));

        }catch (JSONException e){
            e.printStackTrace();
        }
        final String requestBody = jsonBodyObj.toString();

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.POST,
                url, null, new Response.Listener<JSONObject>(){
            @Override    public void onResponse(JSONObject response) {
                JSONArray jsonArray = null;
                try {
                    jsonArray = response.getJSONArray("results");
                } catch (JSONException e) {
                    e.printStackTrace();
                }

                for (int i = 0; i < jsonArray.length(); i++) {
                    try {
                        dateslist.add(jsonArray.get(i).toString());
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
                //Log.i("Response",String.valueOf(response));
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
    }

    //datesSpinner
    private void fillSpinner1(ArrayList arrayList){
        ArrayList<String> strList = (ArrayList<String>)(ArrayList<?>)(arrayList);
        String[] stockArr = new String[strList.size()];
        stockArr = strList.toArray(stockArr);
        dataAdapterForIller1 = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, stockArr);
        //Listelenecek verilerin görünümünü belirliyoruz.
        dataAdapterForIller1.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        //Hazırladğımız Adapter'leri Spinner'lara ekliyoruz.
        dateSpinner.setAdapter(dataAdapterForIller1);
    }


    private void getCourses(){
        courseslist.clear();
        String url = "http://10.0.2.2:5000/get_courses";
        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(
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
                                courseslist.add(response.get(i).toString());
                            }
                        }catch (JSONException e){
                            e.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener(){
                    @Override
                    public void onErrorResponse(VolleyError error){
                        // Do something when error occurred
                    }
                }
        );
        mQueue.add(jsonArrayRequest);

    }
    //coursesSpinner
    private void fillSpinner2(ArrayList arrayList){
        ArrayList<String> strList = (ArrayList<String>)(ArrayList<?>)(arrayList);
        String[] stockArr = new String[strList.size()];
        stockArr = strList.toArray(stockArr);
        dataAdapterForIller2 = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, stockArr);
        //Listelenecek verilerin görünümünü belirliyoruz.
        dataAdapterForIller2.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        //Hazırladğımız Adapter'leri Spinner'lara ekliyoruz.
        courseSpinner.setAdapter(dataAdapterForIller2);
        courseSpinner.setSelected(false);
    }

    private void getpolling(){
        studentslist.clear();
        /*Some example that you can pass your data through request body*/
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        JSONObject jsonBodyObj = new JSONObject();
        String url = "http://10.0.2.2:5000/get_polling";
        try{
            jsonBodyObj.put("key1", dateSpinner.getSelectedItem());

        }catch (JSONException e){
            e.printStackTrace();
        }
        final String requestBody = jsonBodyObj.toString();

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.POST,
                url, null, new Response.Listener<JSONObject>(){
            @Override    public void onResponse(JSONObject response) {
                JSONArray jsonArray = null;
                try {
                    jsonArray = response.getJSONArray("results");
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                students.clear();
                for (int i = 0; i < jsonArray.length(); i++) {
                    try {
                        //String a = jsonArray.getJSONArray(i).get(0).toString() + "  " + jsonArray.getJSONArray(i).get(1).toString();
                        //studentslist.add(a);

                        students.add(new student(jsonArray.getJSONArray(i).get(0).toString(),jsonArray.getJSONArray(i).get(1).toString(),"asd"));

                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
                if(students.size() > 0){
                    open();
                }
                else
                    Toast.makeText(getApplicationContext(), "Yoklama Ait Herhangi Bir Kayıt Bulunamadı!", Toast.LENGTH_LONG).show();
                //Log.i("Response",String.valueOf(response));
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
    }

    public void open(){
        Intent intent = new Intent(this, list.class );
        startActivity(intent);
    }

    private void getCoursesid(){
        courseslistid.clear();
        String url = "http://10.0.2.2:5000/get_coursesid";
        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(
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
                                courseslistid.add(response.get(i).toString());
                            }
                        }catch (JSONException e){
                            e.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener(){
                    @Override
                    public void onErrorResponse(VolleyError error){
                        // Do something when error occurred
                    }
                }
        );
        mQueue.add(jsonArrayRequest);

    }
}
