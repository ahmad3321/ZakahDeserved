package com.example.zakahdeserved.ChildFragment;


import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.appcompat.widget.AppCompatSpinner;
import androidx.fragment.app.Fragment;

import com.example.zakahdeserved.Models.HealthStatuses;
import com.example.zakahdeserved.R;
import com.example.zakahdeserved.Utility.Constants;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class Tab4 extends Fragment implements View.OnClickListener {

    LinearLayout layoutList;

    Button buttonAdd;
    Button buttonSubmitList;
    Button buttonAdd1;

    ArrayList<HealthStatuses> HealthStatusesList = new ArrayList<>();
    public Tab4() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.activity_tab4, container, false);
        layoutList = view.findViewById(R.id.layout_list);
        buttonAdd = view.findViewById(R.id.button_add);
        buttonSubmitList = view.findViewById(R.id.button_submit_list);

        buttonAdd.setOnClickListener(this);
        buttonSubmitList.setOnClickListener(this);

        Constants.view4 = view;
        return view;
    }
    public void onClick(View v) {

        switch (v.getId()){

            case R.id.button_add:

                addView();

                break;

            case R.id.button_submit_list:
/*                if(checkIfValidAndRead()){

                   /* Intent intent = new Intent(MainActivity.this,ActivityCricketers.class);
                    Bundle bundle = new Bundle();
                    bundle.putSerializable("list",cricketersList);
                    intent.putExtras(bundle);
                    startActivity(intent);
                }
                break;*/
        }
    }
    /*private boolean checkIfValidAndRead() {
        HealthStatusesList.clear();
        boolean result = true;

        for(int i=0;i<layoutList.getChildCount();i++){

            View cricketerView = layoutList.getChildAt(i);

            EditText editTextName = (EditText)cricketerView.findViewById(R.id.HealthStatusType);
            Spinner spinnerTeam = (Spinner)cricketerView.findViewById(R.id.HealthStatus);

            HealthStatuses healthStatuses = new HealthStatuses();

            if(!editTextName.getText().toString().equals("")){
                healthStatuses.setCricketerName(editTextName.getText().toString());
            }else {
                result = false;
                break;
            }

            if(spinnerTeam.getSelectedItemPosition()!=0){
                //healthStatuses.setTeamName(teamList.get(spinnerTeam.getSelectedItemPosition()));
            }else {
                result = false;
                break;
            }

            HealthStatusesList.add(healthStatuses);

        }

        if(HealthStatusesList.size()==0){
            result = false;
            Toast.makeText(getActivity(), "Add Cricketers First!", Toast.LENGTH_SHORT).show();
        }else if(!result){
            Toast.makeText(getActivity(), "Enter All Details Correctly!", Toast.LENGTH_SHORT).show();
        }


        return result;
    }*/


    private void addView() {

        final View cricketerView = getLayoutInflater().inflate(R.layout.row_add_healthstatus,null,false);

        /*EditText editText = (EditText)cricketerView.findViewById(R.id.HealthStatusType);
        Spinner HealthStatus = (Spinner)cricketerView.findViewById(R.id.HealthStatus);*/
        ImageView imageClose = (ImageView)cricketerView.findViewById(R.id.image_remove);



        imageClose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                removeView(cricketerView);
            }
        });

        layoutList.addView(cricketerView);

    }

    private void removeView(View view){

        layoutList.removeView(view);

    }
}