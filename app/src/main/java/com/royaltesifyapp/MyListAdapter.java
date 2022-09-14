package com.royaltesifyapp;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

public class MyListAdapter extends BaseAdapter {
    Context context;
    ArrayList<SubjectModel> subjectModelArrayList;


    public MyListAdapter(Context context, ArrayList<SubjectModel> LangModelArrayList,ArrayList<FacultyModel> LangModel1ArrayList){
        this.context = context;
        this.subjectModelArrayList = LangModelArrayList;



    }

    public MyListAdapter(SubjectDisplayActivity context, ArrayList<SubjectModel> subjectModelArrayList) {
    }

    @Override
    public int getCount() {
        return subjectModelArrayList.size();
    }

    @Override
    public Object getItem(int i) {

        return subjectModelArrayList.get(i);
    }

    @Override
    public long getItemId(int i) {

        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {

        LayoutInflater layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        view = layoutInflater.inflate(R.layout.raw_display,null);

        TextView bname = view.findViewById(R.id.edt_bname);
        ImageView imgEdit = view.findViewById(R.id.img_edit);
        ImageView imgdelete = view.findViewById(R.id.img_delete);
        imgEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context,UpdateSubjectActivity.class);
                intent.putExtra("SUB_ID",subjectModelArrayList.get(i).getSubjectId());
                intent.putExtra("SUB_NAME",subjectModelArrayList.get(i).getName());

                context.startActivity(intent);

            }
        });
        imgdelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

        bname.setText(subjectModelArrayList.get(i).getName());

        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int id = subjectModelArrayList.get(i).subjectId;
                Intent i = new Intent(context,ExamActivity.class);
                i.putExtra("KEY_EXAMID",id);
                context.startActivity(i);

            }
        });


        return view;
    }
}
