package com.example.zakahdeserved;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.os.Bundle;
import android.widget.Spinner;

import com.example.zakahdeserved.Utility.Constants;

public class actdelayentrystatisticalActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.act_delay_entry_statistical);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);


        Spinner lst_Directorates = findViewById(R.id.lst_Directorates);
        Constants.SQLITEDAL.fillSpinner(this, lst_Directorates);
    }
}