package com.example.zakahdeserved.ChildFragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.fragment.app.Fragment;

import com.example.zakahdeserved.R;

public class Tab6 extends Fragment implements View.OnClickListener {


Button FurnitureEvaluationIncrease,FurnitureEvaluationDecrease;
TextView FurnitureEvaluation;
    LinearLayout layoutList,layoutListIncome;
    Button buttonAdd,buttonAddIncome;
    Button buttonSubmitList, buttonSubmitListIncome;

    public Tab6() {
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
        View view = inflater.inflate(R.layout.activity_tab6, container, false);
        FurnitureEvaluationIncrease=view.findViewById(R.id.FurnitureEvaluationIncrease);
        FurnitureEvaluationDecrease=view.findViewById(R.id.FurnitureEvaluationDecrease);
        FurnitureEvaluation=view.findViewById(R.id.FurnitureEvaluation);

        buttonAdd = view.findViewById(R.id.button_add_waterType);
        buttonSubmitList = view.findViewById(R.id.button_submit_list);
        buttonAddIncome = view.findViewById(R.id.button_add_Incomes);
        buttonSubmitList = view.findViewById(R.id.button_submit_list_Incomes);


        layoutList = view.findViewById(R.id.layout_list_waterType);
        layoutListIncome = view.findViewById(R.id.layout_list_Incomes);
        buttonAdd.setOnClickListener(this);
        buttonSubmitList.setOnClickListener(this);
        buttonAddIncome.setOnClickListener(this);
        buttonSubmitList.setOnClickListener(this);
        int value=0;
        FurnitureEvaluationIncrease.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    String currentvalue = FurnitureEvaluation.getText().toString();
                    int value = Integer.parseInt(currentvalue);
                    if (value < 5)
                        value++;
                    FurnitureEvaluation.setText(String.valueOf(value));
                }catch (Exception ex){}

            }
        });
        FurnitureEvaluationDecrease.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                String currentvalue = FurnitureEvaluation.getText().toString();
                int value=Integer.parseInt(currentvalue);
                if(value>-5)
                    value--;
                FurnitureEvaluation.setText(String.valueOf(value) );
                }catch (Exception ex){}
            }
        });

        return view;
    }
    public void onClick(View v) {

        switch (v.getId()){

            case R.id.button_add_waterType:

                addView();

                break;
            case R.id.button_add_Incomes:

                addViewIncome();

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
    private void addView() {

        final View cricketerView = getLayoutInflater().inflate(R.layout.row_add_water_type,null,false);

       // EditText editText = (EditText)cricketerView.findViewById(R.id.HealthStatusType);
        //Spinner HealthStatus = (Spinner)cricketerView.findViewById(R.id.WaterType);
        ImageView imageClose = (ImageView)cricketerView.findViewById(R.id.image_remove_waterType);



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
    private void addViewIncome() {

        final View IncomeCiew = getLayoutInflater().inflate(R.layout.row_add_incomes,null,false);

        // EditText editText = (EditText)cricketerView.findViewById(R.id.HealthStatusType);
        //Spinner HealthStatus = (Spinner)cricketerView.findViewById(R.id.WaterType);
        ImageView imageClose = (ImageView)IncomeCiew.findViewById(R.id.image_remove_Income);



        imageClose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                removeViewIncome(IncomeCiew);
            }
        });

        layoutListIncome.addView(IncomeCiew);

    }

    private void removeViewIncome(View view){

        layoutListIncome.removeView(view);

    }

}