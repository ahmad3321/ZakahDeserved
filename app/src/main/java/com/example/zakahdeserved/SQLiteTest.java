package com.example.zakahdeserved;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatSpinner;

import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.Spinner;

import com.example.zakahdeserved.Connection.DBHelper;
import com.example.zakahdeserved.Connection.SQLiteRecord;
import com.example.zakahdeserved.Utility.Constants;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Optional;

public class SQLiteTest extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sqlite_test);

        DBHelper.initDatabaseTables();

//        HashMap<String, Object> hashMap = new HashMap<>();
//        hashMap.put("name", "ahmad");
//        familyInfo.add(new SQLiteRecord("testRecord", hashMap));
        EditText txtZakatID = findViewById(R.id.txtZakatID);
        Button btnLoadDataToFragments = findViewById(R.id.btnLoadDataFromSQLite);
        Spinner spinner = findViewById(R.id.spinner);
        btnLoadDataToFragments.setOnClickListener(view -> {
//                familyInfo = Constants.SQLITEDAL.getFamilyInfo(txtZakatID.getText().toString());
//                spinner.setSelection(((ArrayAdapter<String>) spinner.getAdapter()).getPosition("إعانة مالية"));
//                txtZakatID.setText(getValueOfControl("name", true).toString());


            //get data from localDB
            DBHelper.getfamilyFormFromSQLite(txtZakatID.getText().toString());

            // set to inquiry
            Constants.loadingData = true;


//            DBHelper.loadDataToControls(findViewById(R.id.mainLayout), );
        });
    }

}