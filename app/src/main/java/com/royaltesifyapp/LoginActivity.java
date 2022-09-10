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
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.royaltesifyapp.utils.Utils;
import com.royaltesifyapp.utils.VolleySingleton;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.Map;


public class LoginActivity extends AppCompatActivity {

    TextView tvSignup;
    ImageView imgBtn;
    EditText edtEmail, edtPassword;
    Button btnLogin,fb,google;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        fb = findViewById(R.id.fb);
        google = findViewById(R.id.google);
        tvSignup = findViewById(R.id.tv_signup);
        edtEmail = findViewById(R.id.edt_email);
        edtPassword = findViewById(R.id.edt_password);
        btnLogin = findViewById(R.id.btn_click);

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

        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String stremail = edtEmail.getText().toString().trim();
                String strpassword = edtPassword.getText().toString().trim();

                if(strpassword.length()<6){
                    edtPassword.setError("Paasword must contain 6 charcters");
                    edtPassword.requestFocus();
                }
                if(TextUtils.isEmpty(strpassword)){
                    edtPassword.setError("Please enter Password");
                    edtPassword.requestFocus();

                }
                if (!Patterns.EMAIL_ADDRESS.matcher(stremail).matches()) {
                    edtEmail.setError("Please enter Valid email");
                    edtEmail.requestFocus();
                }

                if (TextUtils.isEmpty(stremail)) {
                    edtEmail.setError("Please enter Email ID");
                    edtEmail.requestFocus();
                }


                if (stremail.equals("admin@gmail.com") || strpassword.equals("admin123")){
                    Intent i = new Intent(LoginActivity.this, DashBoardActivityAdmin.class);
                    startActivity(i);
                }else {
                    loginApi(stremail, strpassword);
                }


            }
        });
        tvSignup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(LoginActivity.this,SignupActivity.class);
                startActivity(i);

            }
        });


        tvSignup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(LoginActivity.this, SignupActivity.class);
                startActivity(i);
            }
        });

    }

    private void loginApi(String stremail, String strpassword) {


        StringRequest stringRequest = new StringRequest(Request.Method.POST, Utils.DATA_URL, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                Log.e("Login Response ===", "onResponse: " + response);

                Intent i = new Intent(LoginActivity.this, DashBoardActivity.class);
                startActivity(i);
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
        } ) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {

                Map<String, String> map = new HashMap<String, String>();
                map.put("email", stremail);
                map.put("password", strpassword);

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

//get data in json format