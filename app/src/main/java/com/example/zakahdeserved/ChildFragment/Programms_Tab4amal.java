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


public class Programms_Tab4amal extends Fragment implements View.OnClickListener {

    Button button_Insert_Amal;
    public Programms_Tab4amal() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.activity_programms_tab4amal, container, false);

        button_Insert_Amal = view.findViewById(R.id.button_Insert_Amal);

        button_Insert_Amal.setOnClickListener(this);

        return view;
    }
    public void onClick(View v) {

        switch (v.getId()){

            case R.id.button_Insert_Amal:

                break;

        }
    }
}