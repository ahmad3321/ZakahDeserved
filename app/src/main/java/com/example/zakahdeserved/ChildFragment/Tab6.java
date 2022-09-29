package com.example.zakahdeserved.ChildFragment;

import android.graphics.Color;
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
import android.widget.TextView;

import androidx.fragment.app.Fragment;

import com.example.zakahdeserved.Connection.DBHelper;
import com.example.zakahdeserved.R;
import com.example.zakahdeserved.Utility.Constants;

public class Tab6 extends Fragment implements View.OnClickListener {


    Button FurnitureEvaluationIncrease, FurnitureEvaluationDecrease;
    TextView FurnitureEvaluation;
    public LinearLayout layoutWaterTypeList, layoutListIncome, layoutListAids, Linear_CoinType;
    Button buttonAdd, buttonAddIncome, buttonAddAids;
    Button buttonSubmitList;
    EditText RentValue;

    public Tab6() {
        // Required empty public constructor
    }

    // TODO: Rename and change types and number of parameters


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View view6 = inflater.inflate(R.layout.activity_tab6, container, false);
        FurnitureEvaluationIncrease = view6.findViewById(R.id.FurnitureEvaluationIncrease);
        FurnitureEvaluationDecrease = view6.findViewById(R.id.FurnitureEvaluationDecrease);
        FurnitureEvaluation = view6.findViewById(R.id.FurnitureEvaluation);

        buttonAdd = view6.findViewById(R.id.button_add_waterType);
        buttonSubmitList = view6.findViewById(R.id.button_submit_list);
        buttonAddIncome = view6.findViewById(R.id.button_add_Incomes);
        buttonSubmitList = view6.findViewById(R.id.button_submit_list_Incomes);
        buttonAddAids = view6.findViewById(R.id.button_add_Aids);

        RentValue = view6.findViewById(R.id.RentValue);

        layoutWaterTypeList = view6.findViewById(R.id.layout_list_waterType);
        layoutListIncome = view6.findViewById(R.id.layout_list_Incomes);
        layoutListAids = view6.findViewById(R.id.layout_list_Aids);
        Linear_CoinType = view6.findViewById(R.id.Linear_CoinType);

        buttonAdd.setOnClickListener(this);
        buttonSubmitList.setOnClickListener(this);
        buttonAddIncome.setOnClickListener(this);
        buttonSubmitList.setOnClickListener(this);
        buttonAddAids.setOnClickListener(this);

        FurnitureEvaluationIncrease.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    String currentvalue = FurnitureEvaluation.getText().toString();
                    int value = Integer.parseInt(currentvalue);
                    if (value < 5)
                        value++;
                    FurnitureEvaluation.setText(String.valueOf(value));
                } catch (Exception ex) {
                }

            }
        });
        FurnitureEvaluationDecrease.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    String currentvalue = FurnitureEvaluation.getText().toString();
                    int value = Integer.parseInt(currentvalue);
                    if (value > -5)
                        value--;
                    FurnitureEvaluation.setText(String.valueOf(value));
                } catch (Exception ex) {
                }
            }
        });
        Constants.view6 = view6;


        Spinner spnHousingNature = view6.findViewById(R.id.HousingNature);
        spnHousingNature.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                //آجار
                if (i == 1) {
                    view6.findViewById(R.id.Linear_CoinType).setVisibility(View.VISIBLE);
                    view6.findViewById(R.id.RentValue).setVisibility(View.VISIBLE);
                } else {
                    view6.findViewById(R.id.Linear_CoinType).setVisibility(View.GONE);
                    view6.findViewById(R.id.RentValue).setVisibility(View.GONE);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });


        Spinner spnCookingGasOther = view6.findViewById(R.id.CookingGas);
        spnCookingGasOther.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                //غاز
                if (i == 0)
                    view6.findViewById(R.id.CookingGasOther).setVisibility(View.GONE);

                    //آخر
                else
                    view6.findViewById(R.id.CookingGasOther).setVisibility(View.VISIBLE);
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });


        if (Constants.loadingData)
            DBHelper.loadDataToControls(view6, Constants.familyInfo);
        return view6;
    }

    public void onClick(View v) {

        switch (v.getId()) {

            case R.id.button_add_waterType:

                addWaterTypeView();

                break;
            case R.id.button_add_Incomes:

                addView(R.layout.row_add_incomes, R.id.image_remove_Income, layoutListIncome);

                break;
            case R.id.button_add_Aids:

                addView(R.layout.row_add_aids, R.id.image_remove_Aids, layoutListAids);

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

    private void addWaterTypeView() {

        final View cricketerView = getLayoutInflater().inflate(R.layout.row_add_water_type, null, false);

        // EditText editText = (EditText)cricketerView.findViewById(R.id.HealthStatusType);
        //Spinner HealthStatus = (Spinner)cricketerView.findViewById(R.id.WaterType);
        ImageView imageClose = (ImageView) cricketerView.findViewById(R.id.image_remove_waterType);


        imageClose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                removeWaterTypeView(cricketerView);
            }
        });
        if(layoutWaterTypeList.getChildCount()%2!=0){
            cricketerView.setBackgroundColor(Color.WHITE);
        }
        else
            cricketerView.setBackgroundColor(Color.parseColor("#FFA5D3A6"));
        layoutWaterTypeList.addView(cricketerView);

    }

    private void removeWaterTypeView(View view) {
        layoutWaterTypeList.removeView(view);
    }

    private void addView(int id, int imageID, LinearLayout linearLayout) {

        final View IncomeView = getLayoutInflater().inflate(id, null, false);

        // EditText editText = (EditText)cricketerView.findViewById(R.id.HealthStatusType);
        //Spinner HealthStatus = (Spinner)cricketerView.findViewById(R.id.WaterType);
        //ImageView imageCloseIncome = (ImageView)IncomeView.findViewById(R.id.image_remove_Income);
        //ImageView imageCloseAids = (ImageView)IncomeView.findViewById(R.id.image_remove_Aids);
        ImageView imageClose = (ImageView) IncomeView.findViewById(imageID);


        imageClose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                removeView(IncomeView, linearLayout);
            }
        });
        if(linearLayout.getChildCount()%2!=0){
            IncomeView.setBackgroundColor(Color.WHITE);
        }
        else
            IncomeView.setBackgroundColor(Color.parseColor("#FFA5D3A6"));
        linearLayout.addView(IncomeView);


        if (id == R.layout.row_add_incomes) {
            Spinner spnIfIncome = IncomeView.findViewById(R.id.IfIncome);
            spnIfIncome.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                    if (i == 0) {
                        IncomeView.findViewById(R.id.IncomeWorkType).setEnabled(false);
                        IncomeView.findViewById(R.id.IncomeWorkTime).setEnabled(false);
                        IncomeView.findViewById(R.id.IncomeValue).setEnabled(false);
                        IncomeView.findViewById(R.id.spnIncomeCoinType).setEnabled(false);
                    } else {
                        IncomeView.findViewById(R.id.IncomeWorkType).setEnabled(true);
                        IncomeView.findViewById(R.id.IncomeWorkTime).setEnabled(true);
                        IncomeView.findViewById(R.id.IncomeValue).setEnabled(true);
                        IncomeView.findViewById(R.id.spnIncomeCoinType).setEnabled(true);
                    }
                }

                @Override
                public void onNothingSelected(AdapterView<?> adapterView) {

                }
            });
        }

    }

    private void removeViewIncome(View view) {

        layoutListIncome.removeView(view);

    }

    private void removeViewAids(View view) {

        layoutListAids.removeView(view);

    }

    private void removeView(View view, LinearLayout linear) {

        linear.removeView(view);

    }
}