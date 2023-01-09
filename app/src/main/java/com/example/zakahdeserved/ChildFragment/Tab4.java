package com.example.zakahdeserved.ChildFragment;


import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
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

import com.example.zakahdeserved.Connection.DBHelper;
import com.example.zakahdeserved.Connection.PackageRecord;
import com.example.zakahdeserved.Connection.SQLiteRecord;
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

    ArrayList<HealthStatuses> HealthStatusesList = new ArrayList<>();

    public Tab4() {
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

        buttonAdd.setOnClickListener(this);

        Constants.view4 = view;


        ArrayList<SQLiteRecord> records = Constants.SQLITEDAL.getHelthStatusForHeadFamily();
        for (SQLiteRecord record : records) {
            View v = addView();
            DBHelper.loadDataToControls(v, record);
        }
//        DBHelper.loadDataToControls(view, Constants.familyInfo);

        return view;
    }

    public void onClick(View v) {

        switch (v.getId()) {

            case R.id.button_add:

                addView();

                break;

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


    private View addView() {

        final View cricketerView = getLayoutInflater().inflate(R.layout.row_add_healthstatus, null, false);

        ImageView imageClose = cricketerView.findViewById(R.id.image_remove);

        //coin conversion
        EditText txtMonthlyCost = cricketerView.findViewById(R.id.MonthlyCost);
        ((Spinner) cricketerView.findViewById(R.id.CoinType)).setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                if (i == 0 && txtMonthlyCost.getText().toString().length() > 0)    //tr
                {
                    txtMonthlyCost.setText(String.valueOf(Double.parseDouble(txtMonthlyCost.getText().toString()) * Constants.DollarPrise));
                    txtMonthlyCost.setEnabled(false);
                } else
                    txtMonthlyCost.setEnabled(true);
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });


        imageClose.setOnClickListener(v -> removeView(cricketerView));
        if (layoutList.getChildCount() % 2 != 0) {
            cricketerView.setBackgroundColor(Color.WHITE);
        } else
            cricketerView.setBackgroundColor(Color.parseColor("#FFA5D3A6"));
        layoutList.addView(cricketerView);

        return cricketerView;
    }

    private void removeView(View view) {

        layoutList.removeView(view);

    }
}