package com.royaltesifyapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class UpdateSubjectActivity extends AppCompatActivity {

    EditText edtBatchName, edtDescription, edtDuration, edtFacultyName, edtSubjectName;
    Button btnClick;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_subject);

        edtBatchName = findViewById(R.id.edt_bname);

        btnClick = findViewById(R.id.btn_click);

        btnClick.setText("Update Subject");

        Intent i = getIntent();
        int subId = i.getIntExtra("SUB_ID",0);
        String strName = i.getStringExtra("SUB_NAME");
        edtBatchName.setText(strName);
        btnClick.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


            }
        });
    }


}