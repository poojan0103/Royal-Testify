package com.royaltesifyapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.royaltesifyapp.utils.Utils;
import com.royaltesifyapp.utils.VolleySingleton;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.Map;

public class SignupActivity extends AppCompatActivity {


    TextView tvLogin;
    EditText edtFirstName, edtLastName, edtGender, edtEmail, edtPassword;
    Button btnClick,fb,google;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);
        edtFirstName = findViewById(R.id.edt_fname);
        edtLastName = findViewById(R.id.edt_lname);
        edtGender = findViewById(R.id.edt_gender);
        edtEmail = findViewById(R.id.edt_email);
        edtPassword = findViewById(R.id.edt_password);
        fb = findViewById(R.id.fb);
        google = findViewById(R.id.google);

        fb.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(Intent.ACTION_VIEW, Uri.parse("https://www.Facebook.com/"));
                startActivity(i);

            }
        });
        google.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(Intent.ACTION_VIEW, Uri.parse("https://www.google.com//"));
                startActivity(i);
            }
        });
        btnClick = findViewById(R.id.btn_click);
        tvLogin = findViewById(R.id.login);

        tvLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(SignupActivity.this,LoginActivity.class);
                startActivity(i);
            }
        });


        btnClick.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String strFn = edtFirstName.getText().toString().trim();
                String strLn = edtLastName.getText().toString().trim();
                String strGender = edtGender.getText().toString().trim();
                String strEmail = edtEmail.getText().toString().trim();
                String strPassword = edtPassword.getText().toString().trim();

                if (strFn.length()<3){
                    edtFirstName.setError("First Name Must contain 3 charcters");
                }
                if(TextUtils.isEmpty(strFn)){
                    edtFirstName.setError("Please enter First Name");
                    edtFirstName.requestFocus();

                }
                if (strLn.length()<3){
                    edtLastName.setError("Last Name Must contain 3 charcters");
                }
                if(TextUtils.isEmpty(strLn)){

                    edtLastName.setError("Please enter Last Name");
                    edtLastName.requestFocus();

                }
                if(TextUtils.isEmpty(strGender)){
                    edtGender.setError("Please enter Gender");
                    edtGender.requestFocus();

                }
                if(strPassword.length()<6){
                    edtPassword.setError("Paasword must contain 6 charcters");
                    edtPassword.requestFocus();
                }
                if(TextUtils.isEmpty(strPassword)){
                    edtPassword.setError("Please enter Password");
                    edtPassword.requestFocus();

                }
                if (!Patterns.EMAIL_ADDRESS.matcher(strEmail).matches()) {
                    edtEmail.setError("Please enter Valid email");
                    edtEmail.requestFocus();
                }

                if (TextUtils.isEmpty(strEmail)) {
                    edtEmail.setError("Please enter Email ID");
                    edtEmail.requestFocus();
                }




                loadData(strFn, strLn, strGender, strEmail, strPassword);



            }
        });
    }

    private void loadData(String strFn, String strLn, String strGender, String strEmail, String strPassword) {

        StringRequest stringRequest = new StringRequest(Request.Method.POST, Utils.DATA_URL, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                Log.e("Login Response ===", "onResponse: " + response);


                Intent i = new Intent(SignupActivity.this, LoginActivity.class);
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
                map.put("firstName", strFn);
                map.put("lastName", strLn);
                map.put("gender", strGender);
                map.put("email", strEmail);
                map.put("password", strPassword);

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