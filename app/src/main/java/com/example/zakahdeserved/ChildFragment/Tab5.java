package com.example.zakahdeserved.ChildFragment;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.Spinner;

import com.example.zakahdeserved.R;
import com.example.zakahdeserved.Utility.Constants;

public class Tab5 extends Fragment {


    public Tab5() {
        // Required empty public constructor
    }

    // TODO: Rename and change types and number of parameters


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view5 = inflater.inflate(R.layout.activity_tab5, container, false);
        Constants.view5 = view5;

        Button btn = view5.findViewById(R.id.btn_rec);

        btn.setOnClickListener(view -> {

            printViews(Constants.view1);
            printViews(Constants.view2);
            printViews(Constants.view3);
            printViews(Constants.view4);
            printViews(Constants.view5);

        });


        return view5;
    }

    void printViews(View view) {
        if(view == null)
            return;
        ViewGroup viewGroup = (ViewGroup) view;
        int count = viewGroup.getChildCount();
        for (int i = 0; i < count; i++) {
            View v = viewGroup.getChildAt(i);
            if (v instanceof EditText || v instanceof Spinner || v instanceof CheckBox)
                Log.d("TESTTAB", v.getResources().getResourceEntryName(v.getId()));
            else if (v instanceof LinearLayout || v instanceof ScrollView || v instanceof RelativeLayout) {
                printViews(v);
            }
        }
    }

}