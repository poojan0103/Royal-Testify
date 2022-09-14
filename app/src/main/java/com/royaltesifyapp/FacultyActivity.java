package com.royaltesifyapp;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.ListView;
import android.widget.Toast;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.royaltesifyapp.utils.Utils;
import com.royaltesifyapp.utils.VolleySingleton;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;

public class FacultyActivity extends AppCompatActivity {
    ArrayList<FacultyModel> LangModelArrayList;
    private ListView listView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_faculty);
        listView = findViewById(R.id.list1_view);

        facultyApi();
    }

    private void facultyApi() {
        ArrayList<FacultyModel> facultyModelArrayList = new ArrayList<FacultyModel>();
        StringRequest stringRequest = new StringRequest(Request.Method.GET, Utils.FACULTY_URL, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                Log.e("Login Response ===", "onResponse: " + response);

                try {
                    JSONArray jsonArray = new JSONArray(response);
                    for (int i = 0; i < jsonArray.length(); i++) {

                        JSONObject jsonObject = jsonArray.getJSONObject(i);
                        int Id = jsonObject.getInt("Id");
                        String FirstName = jsonObject.getString("FirstName");
                        String LastName = jsonObject.getString("LastName");
                        String Email = jsonObject.getString("Email");
                        FacultyModel facultyModel = new FacultyModel();
                        facultyModel.setId(Id);
                        facultyModel.setFirstName(FirstName);
                        facultyModel.setLastName(LastName);
                        facultyModel.setEmail(Email);

                        facultyModelArrayList.add(facultyModel);
                    }
                    FacultyListAdapter facultyListAdapter = new FacultyListAdapter(FacultyActivity.this, facultyModelArrayList);
                    listView.setAdapter(facultyListAdapter);


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