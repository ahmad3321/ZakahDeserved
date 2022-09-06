package com.example.zakahdeserved.ChildFragment;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.Spinner;

import com.example.zakahdeserved.MainActivity;
import com.example.zakahdeserved.R;
import com.example.zakahdeserved.Utility.Constants;
import com.example.zakahdeserved.Utility.ValidationController;

import java.util.List;

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

        Spinner spnWifeSocialStatus = view5.findViewById(R.id.WifeSocialStatus);

        spnWifeSocialStatus.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
//                String[] listHusbandStatuses = null;
//
//                if (i == 0)
//                    listHusbandStatuses = new String[]{"سليم", "معاق", "معتقل", "مفقود", "مسافر"};
//                else if(i==1)
//
//
//                Spinner spnHusbandStatuses = view5.findViewById(R.id.Status);
//
//                ArrayAdapter<String> adapter = new ArrayAdapter<String>(view5.getContext(),
//                        android.R.layout.simple_spinner_item, listHusbandStatuses);
//
//                adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
//                spnHusbandStatuses.setAdapter(adapter);
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        return view5;
    }
}