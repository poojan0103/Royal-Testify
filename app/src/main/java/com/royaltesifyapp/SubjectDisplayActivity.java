package com.royaltesifyapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.royaltesifyapp.utils.Utils;
import com.royaltesifyapp.utils.VolleySingleton;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;


public class SubjectDisplayActivity extends AppCompatActivity {
    ArrayList<SubjectModel> LangModelArrayList;
    private ListView listView;
    FloatingActionButton btnAdd;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        listView = findViewById(R.id.list_view);
        btnAdd = findViewById(R.id.btn_add);

        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(SubjectDisplayActivity.this, AddSubjectActivity.class);
                startActivity(intent);
            }
        });
        loginApi();



    }

    private void loginApi() {

ArrayList<SubjectModel> subjectModelArrayList = new ArrayList<SubjectModel>();
        StringRequest stringRequest = new StringRequest(Request.Method.GET, Utils.SUBJECT_URL, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                Log.e("Login Response ===", "onResponse: " + response);

                try {
                    JSONArray jsonArray = new JSONArray(response);
                    for (int i = 0; i< jsonArray.length(); i++){

                        JSONObject jsonObject = jsonArray.getJSONObject(i);
                        int subjectId = jsonObject.getInt("subjectId");
                        String name = jsonObject.getString("name");
                        SubjectModel subjectModel  = new SubjectModel();
                        subjectModel.setSubjectId(subjectId);
                        subjectModel.setName(name);
                        subjectModelArrayList.add(subjectModel);

                    }
                    MyListAdapter myListAdapter = new MyListAdapter(SubjectDisplayActivity.this,subjectModelArrayList);
                    listView.setAdapter(myListAdapter);

                } catch (JSONException e) {
                    e.printStackTrace();
                }



            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                try {
                    if (error.networkResponse!= null) {


                        int statusCode = error.networkResponse.statusCode;
                        if (error.networkResponse.data != null) {

                            String body = new String(error.networkResponse.data, "UTF-8");
                            if (statusCode == 400) {


                                JSONObject obj = new JSONObject(body);
                                String errorMsg = obj.getString("message");

                                Toast.makeText(getApplicationContext(),errorMsg,Toast.LENGTH_SHORT).show();

                                Log.d("TAG", "error message" + errorMsg);
                            }
                        }
                    }
                } catch (JSONException | UnsupportedEncodingException e) {
                    e.printStackTrace();
                    Log.e("TAG", "UNKNOWN ERROR :" + e.getMessage());
                    Toast.makeText(getApplicationContext(),"Something went Wrong!",Toast.LENGTH_SHORT).show();
                }
            }
        } );

        stringRequest.setShouldCache(false);
        stringRequest.setRetryPolicy(new DefaultRetryPolicy(
                DefaultRetryPolicy.DEFAULT_TIMEOUT_MS * 2,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        VolleySingleton.getInstance(this).addToRequestQueue(stringRequest);


    }
}