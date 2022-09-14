package com.royaltesifyapp;

import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;

import java.util.ArrayList;
import android.os.Bundle;
import android.widget.ListView;

public class DashBoardActivityAdmin extends AppCompatActivity {
    GridView gridView;
    String strData[] = {"Exam", "Question", "Subject","Faculty"};
    int imgData[] = {R.drawable.exam, R.drawable.question, R.drawable.google_plus,R.drawable.faculty};

    ArrayList<LangModel> langModelArrayList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dash_board_admin);

        gridView = findViewById(R.id.list_view);
        langModelArrayList = new ArrayList<LangModel>();

        for (int i = 0; i < strData.length; i++) {
            LangModel langModel = new LangModel(strData[i], imgData[i]);
            langModelArrayList.add(langModel);
        }

        MyBaseAdapter myBaseAdapter = new MyBaseAdapter(this, langModelArrayList);
        gridView.setAdapter(myBaseAdapter);
        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                if (i == 0) {
                    Intent intent = new Intent(DashBoardActivityAdmin.this, LoginActivity.class);
                    startActivity(intent);
                } else if (i == 1) {
                    Intent intent = new Intent(DashBoardActivityAdmin.this, AddSubjectActivity.class);
                    startActivity(intent);
                } else if (i == 2) {
                    Intent intent = new Intent(DashBoardActivityAdmin.this, SubjectDisplayActivity.class);
                    startActivity(intent);

                }else if (i == 3){
                    Intent intent = new Intent(DashBoardActivityAdmin.this,FacultyActivity.class);
                    startActivity(intent);
                }

            }
        });
    }
}