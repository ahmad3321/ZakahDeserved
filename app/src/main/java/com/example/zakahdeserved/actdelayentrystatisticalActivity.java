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
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class actdelayentrystatisticalActivity extends AppCompatActivity {
    EditText LastVisitDate;
    Calendar myCalendar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.act_delay_entry_statistical);
        //Toolbar toolbar = findViewById(R.id.toolbar);
        //setSupportActionBar(toolbar);
        LastVisitDate = this.findViewById(R.id.LastVisitDate);

        Intent intent = getIntent();
        String JobTitle = intent.getStringExtra("JobTitle");
        switch (JobTitle) {
            case "احصائي":
                LastVisitDate.setVisibility(View.GONE);
                break;
            case "توزيع":
                LastVisitDate.setVisibility(View.VISIBLE);
                break;
        }

        DAL.fillSpinner(this, findViewById(R.id.lst_Directorates));

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
            ArrayList<String> empData = DAL.getEmployeeDate(empCode);
            ((EditText) findViewById(R.id.AdminEmployeeCode)).setText(empCode);
            ((EditText) findViewById(R.id.LastVisitDate)).setText(DAL.getMaxID("daily_staff_entries", "AutomaticVisitDate", empCode));
            ((EditText) findViewById(R.id.AdminEmployeeFullName)).setText(empData.get(0));
            ((Spinner) findViewById(R.id.lst_Directorates)).setEnabled(false);
            ((Spinner) findViewById(R.id.lst_Directorates)).setSelection(Integer.parseInt(empData.get(1)) - 1);


            findViewById(R.id.btnSend).setOnClickListener(view -> {
                Date c = Calendar.getInstance().getTime();
                SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd", Locale.US);
                String autoDate = df.format(c);
                Date ManualDate = null;
                String ManualDateStr = "";
                try {
                    ManualDate = df.parse(((EditText) findViewById(R.id.ManualVisitDate)).getText().toString());
                    ManualDateStr = df.format(ManualDate);
                } catch (ParseException e) {
                    e.printStackTrace();
                }

                String entryID = DAL.executeAndGetID("INSERT INTO daily_staff_entries\n" +
                        "(EmployeeCode, EmployeeFullName, AdminEmployeeCode, AdminEmployeeFullName, lst_Directorates," +
                        "AutomaticVisitDate, ManualVisitDate, EntryType)" +
                        "VALUES ('" + ((EditText) findViewById(R.id.EmployeeCode)).getText().toString() + "'," +
                        "'" + ((EditText) findViewById(R.id.EmployeeFullName)).getText().toString() + "'," +
                        "'" + ((EditText) findViewById(R.id.AdminEmployeeCode)).getText().toString() + "'," +
                        "'" + ((EditText) findViewById(R.id.AdminEmployeeFullName)).getText().toString() + "'," +
                        "'" + ((Spinner) findViewById(R.id.lst_Directorates)).getSelectedItemId() + 1 + "'," +
                        "'" + autoDate + "'," +
                        "'" + ManualDateStr + "'," +
                        JobTitle.equals("احصائي") + ");" +
                        "select max(EntryID) from link_entries_to_records;");

                if (entryID == null) {
                    Toast.makeText(getApplicationContext(), "لم يتم تسجيل الدخول", Toast.LENGTH_LONG).show();
                    return;
                }

                SharedPreferences.Editor myEdit = sharedPreferences.edit();
                myEdit.putString("entered_date", autoDate);
                myEdit.putString("entry_id", entryID);
                myEdit.apply();

                Intent intent1 = new Intent(getApplicationContext(), PackageView.class);
                intent1.putExtra("JobTitle", JobTitle); //احصاء أو توزيع
                startActivity(intent1);


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