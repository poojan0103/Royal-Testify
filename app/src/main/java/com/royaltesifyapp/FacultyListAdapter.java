package com.royaltesifyapp;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.royaltesifyapp.FacultyModel;
import com.royaltesifyapp.R;

import org.w3c.dom.Text;

import java.util.ArrayList;

public class FacultyListAdapter extends BaseAdapter {
    Context context;
    ArrayList<FacultyModel> facultyModelArrayList;
    public FacultyListAdapter(Context context,ArrayList<FacultyModel> LangModelArrayList){
        this.context = context;
        this.facultyModelArrayList = LangModelArrayList;
    }
    @Override
    public int getCount() {
        return facultyModelArrayList.size();
    }

    @Override
    public Object getItem(int i) {
        return facultyModelArrayList.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        LayoutInflater layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        view = layoutInflater.inflate(R.layout.raw_display1,null);

        TextView fname = view.findViewById(R.id.edt_fname);
        TextView lname = view.findViewById(R.id.edt_lname);

        fname.setText(facultyModelArrayList.get(i).getFirstname());
        lname.setText(facultyModelArrayList.get(i).getLastName());




        return view;
    }
}
