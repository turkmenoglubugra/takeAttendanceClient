package com.example.lenovo.myapplication;

import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Base64;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;

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

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import android.graphics.Bitmap;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.Toast;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Base64;
import java.io.ByteArrayOutputStream;

public class MainActivity extends AppCompatActivity {

    private RequestQueue mQueue;
    private int GALLERY_REQUEST = 1;
    private static final int REQUEST_CODE = 1;
    private Bitmap bitmap;
    private ImageView imageView;
    private static String encodedString;
    private Spinner spinnerCourses;
    private Button buttonImage;
    private ArrayAdapter<String> dataAdapterForIller;
    private static final ArrayList<String> list = new ArrayList<String>();
    private static String[] courses  = { "Machine Learning", "Data Minig", "Artifical Intelligence"};
    private static ArrayList<String> courseslist = new ArrayList<String>();
    private static ArrayList<String> courseslistid = new ArrayList<String>();
    private static ArrayList<Integer> coursesindex = new ArrayList<Integer>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        mQueue = Volley.newRequestQueue(this);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        spinnerCourses = (Spinner) findViewById(R.id.spinner1);
        getCoursesid();
        getCourses();

        final Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                // Do something after 5s = 5000ms
                fillSpinner(courseslist);            }
        }, 1000);


        setContentView(R.layout.activity_main);
        spinnerCourses = (Spinner) findViewById(R.id.spinner1);
        fillSpinner(courseslist);

        buttonImage = (Button) findViewById(R.id.button2);

        buttonImage.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
              //  if(spinnerCourses.getSelectedItemPosition() != 0) {
                    ImgToBase64();
               // }
               // else
               //     Toast.makeText(getApplicationContext(), "Lütfen Bir Ders Seçiniz!", Toast.LENGTH_LONG).show();

            }
        });

    }

    @Override
    protected void onActivityResult(int reqCode, int resultCode, Intent data) {
        super.onActivityResult(reqCode, resultCode, data);


        if (resultCode == RESULT_OK) {
            try {
                final Uri imageUri = data.getData();
                /*final InputStream imageStream = getContentResolver().openInputStream(imageUri);
                final Bitmap selectedImage = BitmapFactory.decodeStream(imageStream);
                ByteArrayOutputStream baos = new ByteArrayOutputStream();
                selectedImage.compress(Bitmap.CompressFormat.PNG, 100, baos);
                byte[] b = baos.toByteArray();
                imageEncoded = Base64.encodeToString(b, Base64.DEFAULT);*/
                InputStream inputStream = getContentResolver().openInputStream(imageUri);//You can get an inputStream using any IO API
                byte[] bytes;
                byte[] buffer = new byte[8192];
                int bytesRead;
                ByteArrayOutputStream output = new ByteArrayOutputStream();
                try {
                    while ((bytesRead = inputStream.read(buffer)) != -1) {
                        output.write(buffer, 0, bytesRead);
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
                bytes = output.toByteArray();
                encodedString = Base64.encodeToString(bytes, Base64.DEFAULT);
                sendJson();
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
        }

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

    private void fillSpinner(ArrayList arrayList){
        ArrayList<String> strList = (ArrayList<String>)(ArrayList<?>)(arrayList);
        String[] stockArr = new String[strList.size()];
        stockArr = strList.toArray(stockArr);
        dataAdapterForIller = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, stockArr);
        //Listelenecek verilerin görünümünü belirliyoruz.
        dataAdapterForIller.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        //Hazırladğımız Adapter'leri Spinner'lara ekliyoruz.
        spinnerCourses.setAdapter(dataAdapterForIller);
    }

    private void getJson() {

        String url = "http://10.0.2.2:5000/get_user";
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
                                System.out.println(response.get(i));
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
        /*JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, url, null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            JSONArray jsonArray = response.getJSONArray("");

                            for (int i = 0; i < jsonArray.length(); i++) {
                                System.out.println(jsonArray.get(i));

                                //String firstName = employee.getString("city");
                                //String mail = employee.getString("name");

                                //System.out.println(firstName + ", " + mail + "\n\n");
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                error.printStackTrace();
            }
        });*/

        mQueue.add(jsonArrayRequest);
    }

    private void sendJson(){
        /*Some example that you can pass your data through request body*/
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        JSONObject jsonBodyObj = new JSONObject();
        String url = "http://10.0.2.2:5000/user";
        try{
            jsonBodyObj.put("key1", encodedString);
            jsonBodyObj.put("key2", courseslistid.get(spinnerCourses.getSelectedItemPosition()));

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
        Toast.makeText(getApplicationContext(), "Yoklama Alma Başarılı!", Toast.LENGTH_LONG).show();

    }

    private void ImgToBase64(){

        Intent photoPickerIntent = new Intent(Intent.ACTION_PICK);
        photoPickerIntent.setType("image/*");
        startActivityForResult(photoPickerIntent, GALLERY_REQUEST);

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