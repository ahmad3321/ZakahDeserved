package com.example.zakahdeserved;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Spinner;

import com.example.zakahdeserved.Utility.Constants;

public class actdelayentrystatisticalActivity extends AppCompatActivity {
EditText LastVisitDate,DollarExchangeRate;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.act_delay_entry_statistical);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        LastVisitDate=this.findViewById(R.id.LastVisitDate);
        DollarExchangeRate=this.findViewById(R.id.DollarExchangeRate);

        Intent intent = getIntent();
        String JobTitle = intent.getStringExtra("JobTitle");
        switch (JobTitle){
            case "احصائي":
                LastVisitDate.setVisibility(View.GONE);
                DollarExchangeRate.setVisibility(View.VISIBLE);
            case "توزيع":
                LastVisitDate.setVisibility(View.VISIBLE);
                DollarExchangeRate.setVisibility(View.GONE);
        }
        Spinner lst_Directorates = findViewById(R.id.lst_Directorates);
        Constants.SQLITEDAL.fillSpinner(this, lst_Directorates);
    }
}