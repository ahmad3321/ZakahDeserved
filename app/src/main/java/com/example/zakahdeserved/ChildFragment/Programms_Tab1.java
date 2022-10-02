package com.example.zakahdeserved.ChildFragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Spinner;

import androidx.fragment.app.Fragment;

import com.example.zakahdeserved.R;
import com.example.zakahdeserved.Utility.Constants;
import com.example.zakahdeserved.Utility.ValidationController;


public class Programms_Tab1 extends Fragment implements View.OnClickListener {
    LinearLayout layoutList;

    Button button_Insert_Ktlal;
    public Programms_Tab1() {

    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.activity_programms_tab1, container, false);

        button_Insert_Ktlal = view.findViewById(R.id.button_Insert_Ktlal);

        button_Insert_Ktlal.setOnClickListener(this);

        return view;
    }
    public void onClick(View v) {

        switch (v.getId()){

            case R.id.button_Insert_Ktlal:

                break;

        }
    }
}