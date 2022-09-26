package com.example.zakahdeserved.ChildFragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;

import androidx.fragment.app.Fragment;

import com.example.zakahdeserved.R;
import com.example.zakahdeserved.Utility.Constants;


public class Programms_Tab2Fidaa extends Fragment implements View.OnClickListener {
    Button button_Insert_Fidaa;
    public Programms_Tab2Fidaa() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.activity_programms_tab2fidaa, container, false);
        Constants.view1 = view;

        button_Insert_Fidaa = view.findViewById(R.id.button_Insert_Fidaa);

        button_Insert_Fidaa.setOnClickListener(this);

        return view;
    }
    public void onClick(View v) {

        switch (v.getId()){

            case R.id.button_Insert_Fidaa:

                break;

        }
    }

}