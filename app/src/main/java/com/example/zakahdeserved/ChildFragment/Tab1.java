package com.example.zakahdeserved.ChildFragment;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.Spinner;

import androidx.fragment.app.Fragment;

import com.example.zakahdeserved.Connection.DBHelper;
import com.example.zakahdeserved.R;
import com.example.zakahdeserved.Utility.Constants;
import com.example.zakahdeserved.Utility.ValidationController;


public class Tab1 extends Fragment {

    public Tab1() {

    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.activity_tab1, container, false);
        Constants.view1 = view;


        Spinner spnGender = view.findViewById(R.id.Gender);
        spnGender.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                ValidationController.ENABLE_FEMALE_TAB = i == 0;
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        EditText txtExisitStatusAbout = view.findViewById(R.id.ExisitStatusAbout);

        Spinner spnExisitStatus = view.findViewById(R.id.ExisitStatus);
        spnExisitStatus.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                if (i == 0) {   //في حالة موجود
                    ValidationController.ENABLE_ALL_TABS = true;
                    txtExisitStatusAbout.setEnabled(false);
                } else {    //في حالة غير موجود, غير معروف, عنوان خاطئ .....
                    ValidationController.ENABLE_ALL_TABS = false;
                    txtExisitStatusAbout.setEnabled(true);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        if (Constants.loadingData)
            DBHelper.loadDataToControls(view, Constants.familyInfo);

        return view;
    }
}