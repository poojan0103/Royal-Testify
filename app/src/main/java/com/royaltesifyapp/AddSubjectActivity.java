package com.royaltesifyapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.royaltesifyapp.utils.Utils;
import com.royaltesifyapp.utils.VolleySingleton;

import java.util.HashMap;
import java.util.Map;

public class   AddSubjectActivity extends AppCompatActivity {

    EditText edtBatchName;
    Button btnClick;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_subject);
        edtBatchName = findViewById(R.id.edt_bname);

        btnClick = findViewById(R.id.btn_click);


        btnClick.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String strbname = edtBatchName.getText().toString();
                Intent i = new Intent(AddSubjectActivity.this,SubjectDisplayActivity.class);
                startActivity(i);


                loadData(strbname);
            }

        });
    }

    private void loadData(String strbname) {
        StringRequest stringRequest = new StringRequest(Request.Method.POST, Utils.SUBJECT_URL, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                Log.e("Login Response ===", "onResponse: " + response);

                Intent i = new Intent(AddSubjectActivity.this, SubjectDisplayActivity.class);
                startActivity(i);


            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {

                Map<String, String> map = new HashMap<String, String>();
                map.put("batchName", strbname);
                return map;
            }

            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                HashMap<String, String> headers = new HashMap<>();

                headers.put("Content-Type", "application/json");
                return headers;
            }
        };

        stringRequest.setShouldCache(false);
        stringRequest.setRetryPolicy(new DefaultRetryPolicy(
                DefaultRetryPolicy.DEFAULT_TIMEOUT_MS * 2,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        VolleySingleton.getInstance(this).addToRequestQueue(stringRequest);

    }
}
