package com.example.zakahdeserved.ChildFragment;

import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.fragment.app.Fragment;

import com.example.zakahdeserved.Connection.DBHelper;
import com.example.zakahdeserved.Connection.SQLiteRecord;
import com.example.zakahdeserved.R;
import com.example.zakahdeserved.Utility.Constants;
import com.example.zakahdeserved.Utility.ValidationController;

import java.util.ArrayList;
import java.util.Objects;

public class Tab6 extends Fragment implements View.OnClickListener {


    Button FurnitureEvaluationIncrease, FurnitureEvaluationDecrease;
    TextView FurnitureEvaluation;
    public LinearLayout layoutWaterTypeList, layoutListIncome, layoutListAids, Linear_CoinType;
    Button buttonAdd, buttonAddIncome, buttonAddAids;
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
        Constants.view6 = view6;

        FurnitureEvaluationIncrease = view6.findViewById(R.id.FurnitureEvaluationIncrease);
        FurnitureEvaluationDecrease = view6.findViewById(R.id.FurnitureEvaluationDecrease);
        FurnitureEvaluation = view6.findViewById(R.id.FurnitureEvaluation);

        buttonAdd = view6.findViewById(R.id.button_add_waterType);
        buttonAddIncome = view6.findViewById(R.id.button_add_Incomes);
        buttonAddAids = view6.findViewById(R.id.button_add_Aids);

        RentValue = view6.findViewById(R.id.RentValue);

        layoutWaterTypeList = view6.findViewById(R.id.layout_list_waterType);
        layoutListIncome = view6.findViewById(R.id.layout_list_Incomes);
        layoutListAids = view6.findViewById(R.id.layout_list_Aids);
        Linear_CoinType = view6.findViewById(R.id.Linear_CoinType);

        buttonAdd.setOnClickListener(this);
        buttonAddIncome.setOnClickListener(this);
        buttonAddAids.setOnClickListener(this);

        FurnitureEvaluationIncrease.setOnClickListener(view -> {
            try {
                String currentvalue = FurnitureEvaluation.getText().toString();
                int value = Integer.parseInt(currentvalue);
                if (value < 5)
                    value++;
                FurnitureEvaluation.setText(String.valueOf(value));
            } catch (Exception ex) {
            }

        });
        FurnitureEvaluationDecrease.setOnClickListener(view -> {
            try {
                String currentvalue = FurnitureEvaluation.getText().toString();
                int value = Integer.parseInt(currentvalue);
                if (value > -5)
                    value--;
                FurnitureEvaluation.setText(String.valueOf(value));
            } catch (Exception ex) {
            }
        });


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

                    ((EditText) view6.findViewById(R.id.RentValue)).setText("");
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
                if (i == 0) {
                    view6.findViewById(R.id.CookingGasOther).setVisibility(View.GONE);
                    ((EditText) view6.findViewById(R.id.CookingGasOther)).setText("");
                }

                //آخر
                else
                    view6.findViewById(R.id.CookingGasOther).setVisibility(View.VISIBLE);
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        //for coin type conversion
        coinTypeConversion(view6);

        //Load data from family info (في حالة حزمة إضافة لن يكون هناك إلا بيانات أولية)
        DBHelper.loadDataToControls(view6, Constants.familyInfo);

        // load water_types
        ArrayList<SQLiteRecord> records = Constants.SQLITEDAL.getRecords("water_types", DBHelper.WaterTypesColumns, "ZakatID", Constants.ZakatID);
        for (SQLiteRecord record : records) {
            View v = addWaterTypeView();
            DBHelper.loadDataToControls(v, record);
        }
        //load incomes
        records = Constants.SQLITEDAL.getRecords("incomes", DBHelper.IncomesColumns, "ZakatID", Constants.ZakatID);
        for (SQLiteRecord record : records) {
            View v = addView(R.layout.row_add_incomes, R.id.image_remove_Income, layoutListIncome);
            DBHelper.loadDataToControls(v, record);
        }
        //load aids
        records = Constants.SQLITEDAL.getRecords("aids", DBHelper.AidsColumns, "ZakatID", Constants.ZakatID);
        for (SQLiteRecord record : records) {
            View v = addView(R.layout.row_add_aids, R.id.image_remove_Aids, layoutListAids);
            DBHelper.loadDataToControls(v, record);
        }

        //تعديل
        if (Objects.equals(Constants.PackageType, "تعديل")) {
            ValidationController.lockThePage(Constants.view6);
            ValidationController.EnableOnlyToEditFields(Constants.view6, "housing_informations");
            ValidationController.EnableOnlyToEditFields(Constants.view6, "water_types");
            ValidationController.EnableOnlyToEditFields(Constants.view6, "incomes");
            ValidationController.EnableOnlyToEditFields(Constants.view6, "aids");

            buttonAdd.setEnabled(true);
            buttonAddIncome.setEnabled(true);
            buttonAddAids.setEnabled(true);

            view6.findViewById(R.id.RentValueCoinType).setEnabled(RentValue.isEnabled());
            view6.findViewById(R.id.AmpValueCoinType).setEnabled(view6.findViewById(R.id.OneAmpValue).isEnabled());
            view6.findViewById(R.id.ConsumptionValueCoinType).setEnabled(view6.findViewById(R.id.ConsumptionValue).isEnabled());
        }

        return view6;
    }

    private void coinTypeConversion(View view) {
        EditText txtOneAmpValue = view.findViewById(R.id.OneAmpValue);
        EditText txtConsumptionValue = view.findViewById(R.id.ConsumptionValue);

        ((Spinner) view.findViewById(R.id.RentValueCoinType)).setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                if (i == 0 && RentValue.getText().toString().length() > 0)    //tr
                {
                    RentValue.setText(String.valueOf(Integer.parseInt(RentValue.getText().toString()) * Constants.DollarPrise));
                    RentValue.setEnabled(false);
                } else
                    RentValue.setEnabled(true);
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
        ((Spinner) view.findViewById(R.id.AmpValueCoinType)).setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                if (i == 0 && txtOneAmpValue.getText().toString().length() > 0)    //tr
                {
                    txtOneAmpValue.setText(String.valueOf(Double.parseDouble(txtOneAmpValue.getText().toString()) * Constants.DollarPrise));
                    txtOneAmpValue.setEnabled(false);
                } else
                    txtOneAmpValue.setEnabled(true);
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
        ((Spinner) view.findViewById(R.id.ConsumptionValueCoinType)).setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                if (i == 0 && txtConsumptionValue.getText().toString().length() > 0)    //tr
                {
                    txtConsumptionValue.setText(String.valueOf(Double.parseDouble(txtConsumptionValue.getText().toString()) * Constants.DollarPrise));
                    txtConsumptionValue.setEnabled(false);
                } else
                    txtConsumptionValue.setEnabled(true);
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
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
        }
    }

    private View addWaterTypeView() {

        final View cricketerView = getLayoutInflater().inflate(R.layout.row_add_water_type, null, false);

        // EditText editText = (EditText)cricketerView.findViewById(R.id.HealthStatusType);
        //Spinner HealthStatus = (Spinner)cricketerView.findViewById(R.id.WaterType);
        ImageView imageClose = cricketerView.findViewById(R.id.image_remove_waterType);

        //coin conversion
        EditText txtMonthlyValue = cricketerView.findViewById(R.id.MonthlyValue);
        ((Spinner) cricketerView.findViewById(R.id.CoinType)).setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                if (i == 0 && txtMonthlyValue.getText().toString().length() > 0)    //tr
                {
                    txtMonthlyValue.setText(String.valueOf(Integer.parseInt(txtMonthlyValue.getText().toString()) * Constants.DollarPrise));
                    txtMonthlyValue.setEnabled(false);
                } else
                    txtMonthlyValue.setEnabled(true);
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });


        imageClose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                removeWaterTypeView(cricketerView);
            }
        });
        if (layoutWaterTypeList.getChildCount() % 2 != 0) {
            cricketerView.setBackgroundColor(Color.WHITE);
        } else
            cricketerView.setBackgroundColor(Color.parseColor("#FFA5D3A6"));
        layoutWaterTypeList.addView(cricketerView);

        return cricketerView;
    }

    private void removeWaterTypeView(View view) {
        layoutWaterTypeList.removeView(view);
    }

    private View addView(int id, int imageID, LinearLayout linearLayout) {

        final View IncomeView = getLayoutInflater().inflate(id, null, false);
        try{

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
        if (linearLayout.getChildCount() % 2 != 0) {
            IncomeView.setBackgroundColor(Color.WHITE);
        } else
            IncomeView.setBackgroundColor(Color.parseColor("#FFA5D3A6"));
        linearLayout.addView(IncomeView);


        if (id == R.layout.row_add_incomes) {
            Spinner spnIfIncome = IncomeView.findViewById(R.id.IfIncome);
            spnIfIncome.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                    if (i == 0) {
                        IncomeView.findViewById(R.id.IncomeType).setEnabled(true);
                        IncomeView.findViewById(R.id.IncomeTime).setEnabled(true);
                        IncomeView.findViewById(R.id.IncomeValue).setEnabled(true);
                        IncomeView.findViewById(R.id.CoinType).setEnabled(true);
                    } else {
                        IncomeView.findViewById(R.id.IncomeType).setEnabled(false);
                        IncomeView.findViewById(R.id.IncomeTime).setEnabled(false);
                        IncomeView.findViewById(R.id.IncomeValue).setEnabled(true);
                        IncomeView.findViewById(R.id.CoinType).setEnabled(false);
                    }
                }
                @Override
                public void onNothingSelected(AdapterView<?> adapterView) {

                }
            });
            //coin conversion
            EditText txtIncomeValue = IncomeView.findViewById(R.id.IncomeValue);
            ((Spinner) IncomeView.findViewById(R.id.CoinType)).setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                    if (i == 0 && txtIncomeValue.getText().toString().length() > 0)    //tr
                    {
                        txtIncomeValue.setText(String.valueOf(Double.parseDouble(txtIncomeValue.getText().toString()) * Constants.DollarPrise));
                        txtIncomeValue.setEnabled(false);
                    } else
                        txtIncomeValue.setEnabled(true);
                }

                @Override
                public void onNothingSelected(AdapterView<?> adapterView) {

                }
            });

        } else if (id == R.id.button_add_Aids) {
            //coin conversion
            EditText txtAidValue = IncomeView.findViewById(R.id.AidValue);
            ((Spinner) IncomeView.findViewById(R.id.CoinType)).setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                    if (i == 0 && txtAidValue.getText().toString().length() > 0)    //tr
                    {
                        txtAidValue.setText(String.valueOf(Double.parseDouble(txtAidValue.getText().toString()) * Constants.DollarPrise));
                        txtAidValue.setEnabled(false);
                    } else
                        txtAidValue.setEnabled(true);
                }

                @Override
                public void onNothingSelected(AdapterView<?> adapterView) {

                }
            });
        }
        return IncomeView;
    }catch (Exception ex){
            return null;
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