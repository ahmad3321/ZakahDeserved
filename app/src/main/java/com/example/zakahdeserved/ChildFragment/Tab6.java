package com.example.zakahdeserved.ChildFragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.fragment.app.Fragment;

import com.example.zakahdeserved.R;

public class Tab6 extends Fragment {


Button FurnitureEvaluationIncrease,FurnitureEvaluationDecrease;
TextView FurnitureEvaluation;
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
}