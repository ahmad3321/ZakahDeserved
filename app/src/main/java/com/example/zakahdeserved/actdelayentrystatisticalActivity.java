package com.example.zakahdeserved;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.security.crypto.EncryptedSharedPreferences;
import androidx.security.crypto.MasterKey;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.zakahdeserved.Connection.DAL;
import com.example.zakahdeserved.Utility.Constants;
import com.example.zakahdeserved.Utility.ValidationController;

import java.io.IOException;
import java.security.GeneralSecurityException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class actdelayentrystatisticalActivity extends AppCompatActivity {
    EditText LastVisitDate, DollarExchangeRate;
    Calendar myCalendar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.act_delay_entry_statistical);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        LastVisitDate = this.findViewById(R.id.LastVisitDate);
        DollarExchangeRate = this.findViewById(R.id.DollarExchangeRate);

        Intent intent = getIntent();
        String JobTitle = intent.getStringExtra("JobTitle");
        switch (JobTitle) {
            case "احصائي":
                LastVisitDate.setVisibility(View.GONE);
                DollarExchangeRate.setVisibility(View.VISIBLE);
                break;
            case "توزيع":
                LastVisitDate.setVisibility(View.VISIBLE);
                DollarExchangeRate.setVisibility(View.GONE);
                break;
        }

        Constants.SQLITEDAL.fillSpinner(this, findViewById(R.id.lst_Directorates));

        EditText txtVisitDate = findViewById(R.id.ManualVisitDate);

        myCalendar = Calendar.getInstance();

        DatePickerDialog.OnDateSetListener date = (view1, year, monthOfYear, dayOfMonth) -> {
            myCalendar.set(Calendar.YEAR, year);
            myCalendar.set(Calendar.MONTH, monthOfYear);
            myCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
            updateLabel(txtVisitDate);
        };

        txtVisitDate.setOnClickListener(v -> new DatePickerDialog(actdelayentrystatisticalActivity.this, date, myCalendar
                .get(Calendar.YEAR), myCalendar.get(Calendar.MONTH),
                myCalendar.get(Calendar.DAY_OF_MONTH)).show());

        try {
            SharedPreferences sharedPreferences = EncryptedSharedPreferences.create(
                    getApplicationContext(),
                    "MySharedPref",
                    Constants.SHAREDPREFERENCES_KEY, // masterKey created above
                    EncryptedSharedPreferences.PrefKeyEncryptionScheme.AES256_SIV,
                    EncryptedSharedPreferences.PrefValueEncryptionScheme.AES256_GCM);

            String empCode = sharedPreferences.getString("empCode", "");
            ((EditText) findViewById(R.id.AdminEmployeeCode)).setText(empCode);
            ((EditText) findViewById(R.id.LastVisitDate)).setText(DAL.getMaxID("daily_staff_entries", "AutomaticVisitDate", empCode));


            findViewById(R.id.btnSend).setOnClickListener(view -> {
                Date c = Calendar.getInstance().getTime();
                SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());
                String autoDate = df.format(c);

                boolean success = DAL.executeQueries("INSERT INTO daily_staff_entries\n" +
                        "(EmployeeCode, EmployeeFullName, AdminEmployeeCode, AdminEmployeeFullName, lst_Directorates," +
                        "AutomaticVisitDate, ManualVisitDate, DollarExchangeRate, EntryType)" +
                        "VALUES ('" + ((EditText) findViewById(R.id.EmployeeCode)).getText().toString() + "'," +
                        "'" + ((EditText) findViewById(R.id.EmployeeFullName)).getText().toString() + "'," +
                        "'" + ((EditText) findViewById(R.id.AdminEmployeeCode)).getText().toString() + "'," +
                        "'" + ((EditText) findViewById(R.id.AdminEmployeeFullName)).getText().toString() + "'," +
                        "'" + ((Spinner) findViewById(R.id.lst_Directorates)).getSelectedItemId() + "'," +
                        "'" + autoDate + "'," +
                        "'" + ((EditText) findViewById(R.id.ManualVisitDate)).getText().toString() + "'," +
                        "'" + ((EditText) findViewById(R.id.DollarExchangeRate)).getText().toString() + "'," +
                        JobTitle.equals("احصائي") + ");");

                if (!success) {
                    Toast.makeText(getApplicationContext(), "لم يتم تسجيل الدخول", Toast.LENGTH_LONG).show();
                    return;
                }

                Intent intent1 = new Intent(getApplicationContext(), PackageView.class);
                intent1.putExtra("JobTitle", JobTitle); //احصاء أو توزيع
                startActivity(intent1);

                SharedPreferences.Editor myEdit = sharedPreferences.edit();
                myEdit.putString("entered_date", autoDate);
                myEdit.apply();
            });


        } catch (GeneralSecurityException | IOException e) {
            e.printStackTrace();
        }
    }

    private void updateLabel(EditText txtDate) {
        String myFormat = "yyyy-MM-dd";
        SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);
        txtDate.setText(sdf.format(myCalendar.getTime()));
    }
}