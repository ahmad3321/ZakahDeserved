package com.example.zakahdeserved.ChildFragment;

import android.app.DatePickerDialog;
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

import com.example.zakahdeserved.Connection.DBHelper;
import com.example.zakahdeserved.Connection.SQLiteDAL;
import com.example.zakahdeserved.R;
import com.example.zakahdeserved.Utility.Constants;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

public class Tab8 extends Fragment implements View.OnClickListener {

    LinearLayout layoutWife, layout_list_Wifes_HealthStatus;
    Button buttonAdd, button_add_WifesHealthStatus;
    Button buttonSubmitList;

    Calendar myCalendar;

    public Tab8() {
        // Required empty public constructor
    }

    // TODO: Rename and change types and number of parameters


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.activity_tab8, container, false);

        Constants.view8 = view;

        buttonAdd = view.findViewById(R.id.button_add_Wifes);
        buttonSubmitList = view.findViewById(R.id.button_submit_list_Wifes);

        layoutWife = view.findViewById(R.id.layout_list_Wifes);


        buttonAdd.setOnClickListener(this);
        buttonSubmitList.setOnClickListener(this);


        if (Constants.loadingData)
            DBHelper.loadDataToControls(view, Constants.familyInfo);

        return view;
    }

    public void onClick(View v) {

        switch (v.getId()) {

            case R.id.button_add_Wifes:

                addView(R.layout.row_add_wifes, R.id.image_remove_Wife, layoutWife);

                break;
            case R.id.button_submit_list:

                /*if(checkIfValidAndRead()){

                   /* Intent intent = new Intent(MainActivity.this,ActivityCricketers.class);
                    Bundle bundle = new Bundle();
                    bundle.putSerializable("list",cricketersList);
                    intent.putExtras(bundle);
                    startActivity(intent);
                }*/
                break;
        }
    }

    private void addView(int id, int imageID, LinearLayout linearLayout) {

        final View WifeView = getLayoutInflater().inflate(id, null, false);

        ImageView imageClose = (ImageView) WifeView.findViewById(imageID);
        Button button_add_WifesHealthStatus = (Button) WifeView.findViewById(R.id.button_add_WifesHealthStatus);
        layout_list_Wifes_HealthStatus = (LinearLayout) WifeView.findViewById(R.id.layout_list_Wifes_HealthStatus);

        imageClose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                removeView(WifeView, linearLayout);
            }
        });

        linearLayout.addView(WifeView);


        button_add_WifesHealthStatus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addView();
            }
        });

        Spinner spnIsWorking = WifeView.findViewById(R.id.IsWorking);
        spnIsWorking.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                if (i == 0) {   //تعمل
                    WifeView.findViewById(R.id.MonthlyIncome).setVisibility(View.VISIBLE);
                    WifeView.findViewById(R.id.CoinType).setVisibility(View.VISIBLE);
                } else {   //لاتعمل
                    WifeView.findViewById(R.id.MonthlyIncome).setVisibility(View.GONE);
                    WifeView.findViewById(R.id.CoinType).setVisibility(View.GONE);

                    ((EditText) WifeView.findViewById(R.id.MonthlyIncome)).setText("");
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        Spinner spnWhoIs = WifeView.findViewById(R.id.WhoIs);
        spnWhoIs.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                if (i == 0 || i == 1) {   //زوجة أو ابن
                    WifeView.findViewById(R.id.Relation).setVisibility(View.GONE);
                    ((EditText) WifeView.findViewById(R.id.Relation)).setText("");
                } else
                    WifeView.findViewById(R.id.Relation).setVisibility(View.VISIBLE);
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });


        EditText txtBirthDate = WifeView.findViewById(R.id.BirthDate);

        myCalendar = Calendar.getInstance();

        DatePickerDialog.OnDateSetListener date = (view1, year, monthOfYear, dayOfMonth) -> {
            myCalendar.set(Calendar.YEAR, year);
            myCalendar.set(Calendar.MONTH, monthOfYear);
            myCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
            updateLabel(txtBirthDate);
        };

        txtBirthDate.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                new DatePickerDialog(getContext(), date, myCalendar
                        .get(Calendar.YEAR), myCalendar.get(Calendar.MONTH),
                        myCalendar.get(Calendar.DAY_OF_MONTH)).show();
            }
        });


        Spinner spnlst_IdentityTypes = WifeView.findViewById(R.id.lst_IdentityTypes);
        Constants.SQLITEDAL.fillSpinner(getContext(), spnlst_IdentityTypes);
    }

    private void removeView(View view, LinearLayout linear) {

        linear.removeView(view);

    }

    private void addView() {

        final View healthstatusview = getLayoutInflater().inflate(R.layout.row_add_healthstatus, null, false);

        ImageView imageClose = (ImageView) healthstatusview.findViewById(R.id.image_remove);


        imageClose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                removeView(healthstatusview);
            }
        });

        layout_list_Wifes_HealthStatus.addView(healthstatusview);

    }

    private void removeView(View view) {

        layout_list_Wifes_HealthStatus.removeView(view);

    }

    private void updateLabel(EditText txtDate) {
        String myFormat = "yyyy-MM-dd HH:mm";
        SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);
        txtDate.setText(sdf.format(myCalendar.getTime()));
    }
}