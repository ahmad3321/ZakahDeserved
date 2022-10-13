package com.example.zakahdeserved;

import androidx.appcompat.app.AppCompatActivity;
import androidx.security.crypto.EncryptedSharedPreferences;
import androidx.security.crypto.MasterKey;

import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.example.zakahdeserved.BroadCast.BroadCastClass;
import com.example.zakahdeserved.Connection.DAL;
import com.example.zakahdeserved.Connection.DBHelper;
import com.example.zakahdeserved.Connection.PackageRecord;
import com.example.zakahdeserved.Connection.SQLiteDAL;
import com.example.zakahdeserved.Connection.SQLiteRecord;
import com.example.zakahdeserved.Connection.ShowRecord;
import com.example.zakahdeserved.Utility.Constants;

import java.io.IOException;
import java.security.GeneralSecurityException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

public class MainActivity extends AppCompatActivity {
    TextView login;
    EditText username, password;
    BroadCastClass broadCastClass = new BroadCastClass();
    ImageButton btn_Sync;
    int EmpDepartment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        String[] perms = {"android.permission.INTERNET",
                "android.permission.ACCESS_NETWORK_STATE",
                "android.permission.WRITE_EXTERNAL_STORAGE",
                "android.permission.MANAGE_EXTERNAL_STORAGE",
                "android.permission.READ_EXTERNAL_STORAGE"};
        int permsRequestCode = 200;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            requestPermissions(perms, permsRequestCode);
        }
        try {
            Constants.SHAREDPREFERENCES_KEY = new MasterKey.Builder(this, MasterKey.DEFAULT_MASTER_KEY_ALIAS)
                    .setKeyScheme(MasterKey.KeyScheme.AES256_GCM)
                    .build();
        } catch (GeneralSecurityException | IOException e) {
            e.printStackTrace();
        }

        Constants.SQLITEDAL = new SQLiteDAL(getApplicationContext());

        DBHelper.initDatabaseTables();

        BroadCastClass broadCastClass = new BroadCastClass();

        registerNetworkBroadcastForNougat();

        login = findViewById(R.id.btn_login);
        username = findViewById(R.id.edit_username);
        password = findViewById(R.id.edit_password);
        btn_Sync = findViewById(R.id.btn_Sync);

        login.setOnClickListener(view -> {
//            Intent intent = new Intent(getApplicationContext(), MainTabs.class);
            //startActivity(intent);
            try {
                Boolean isSuccess = DAL.pdrUsernameTest(MainActivity.this, username.getText().toString(), password.getText().toString());
                if (isSuccess) {
                    EmpDepartment = DAL.getDepartment(username.getText().toString());
                    if (EmpDepartment == Constants.STATISTICAL_JOB_TITLE) {
                        Intent intent1 = new Intent(getApplicationContext(), actdelayentrystatisticalActivity.class);
                        intent1.putExtra("JobTitle", "احصائي"); //احصاء أو توزيع
                        startActivity(intent1);
                    } else if (EmpDepartment == Constants.DISTRIBUTION_JOcB_TITLE) {
                        Intent intent1 = new Intent(getApplicationContext(), actdelayentrystatisticalActivity.class);
                        intent1.putExtra("JobTitle", "توزيع"); //احصاء أو توزيع
                        startActivity(intent1);
                    } else {
                        Toast.makeText(getApplicationContext(), "المستخدم ليس له صلاحية في الدخول إلى التطبيق", Toast.LENGTH_SHORT).show();
                    }

                    // Encrypted Shared Preferences
                    try {
                        SharedPreferences sharedPreferences = EncryptedSharedPreferences.create(
                                getApplicationContext(),
                                "MySharedPref",
                                Constants.SHAREDPREFERENCES_KEY, // masterKey created above
                                EncryptedSharedPreferences.PrefKeyEncryptionScheme.AES256_SIV,
                                EncryptedSharedPreferences.PrefValueEncryptionScheme.AES256_GCM);
                        SharedPreferences.Editor myEdit = sharedPreferences.edit();
                        // Storing the key and its value as the data fetched from edittext
                        myEdit.putString("empCode", username.getText().toString());
                        myEdit.apply();
                    } catch (GeneralSecurityException | IOException e) {
                        e.printStackTrace();
                    }
                } else {
                    Toast.makeText(getApplicationContext(), "خطأ في اسم المستخدم أو كلمة المرور", Toast.LENGTH_SHORT).show();
                }
            } catch (Exception ex) {
                Toast.makeText(getApplicationContext(), "خطأ..." + ex, Toast.LENGTH_SHORT).show();
            }

        });

        btn_Sync.setOnClickListener(view -> {
            try {
                new TestAsync().execute();
            } catch (Exception ex) {
            }
        });
    }

    private void registerNetworkBroadcastForNougat() {
        try {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                registerReceiver(broadCastClass, new IntentFilter(ConnectivityManager.CONNECTIVITY_ACTION));
            }
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                registerReceiver(broadCastClass, new IntentFilter(ConnectivityManager.CONNECTIVITY_ACTION));
            }
        } catch (Exception ignored) {
        }
    }

    protected void unregisterNetworkChanges() {
        try {
            unregisterReceiver(broadCastClass);
        } catch (IllegalArgumentException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        unregisterNetworkChanges();
    }

    class TestAsync extends AsyncTask<Void, Integer, String> {
        String TAG = getClass().getSimpleName();

        protected void onPreExecute() {
            super.onPreExecute();
            Toast.makeText(MainActivity.this, "بدأت عملية المزامنة .. انتظر قليلا", Toast.LENGTH_SHORT).show();
            Log.d(TAG + " PreExceute", "On pre Exceute......");
        }

        protected String doInBackground(Void... arg0) {
            try {

                // Synchronize Spinners
                Constants.SQLITEDAL.clearSpinners();
                for (String spinner : Constants.dynamisLists) {
                    HashMap<String, String> spinnerItems = DAL.getSpinnerItems(spinner);
                    Constants.SQLITEDAL.addSpinner(spinner, spinnerItems);
                }


                //Synchronize packages

                // get empCode
                SharedPreferences sharedPreferences = EncryptedSharedPreferences.create(
                        getApplicationContext(),
                        "MySharedPref",
                        Constants.SHAREDPREFERENCES_KEY, // masterKey created above
                        EncryptedSharedPreferences.PrefKeyEncryptionScheme.AES256_SIV,
                        EncryptedSharedPreferences.PrefValueEncryptionScheme.AES256_GCM);
                String empCode = sharedPreferences.getString("empCode", "");


                //get packages info from server
                ArrayList<PackageRecord> lstPackages = DAL.getPackeges("SELECT ZakatID, PersonID, Program," +
                        " FromEmployeeCode, ToEmployeeCode, Package\n" +
                        "FROM zakatraising.packages \n" +
                        "INNER JOIN zakatraising.package_contents \n" +
                        "on zakatraising.packages.packageID = zakatraising.package_contents.packageID\n" +
                        " where packages.ToEmployeeCode = " + empCode + " and package_contents.PackageStatus = 'قيد العمل';");

                //store packages info locally
                Constants.SQLITEDAL.RefreshPackages(lstPackages);


                // Clear all stored informations about families
                Constants.SQLITEDAL.ClearAllRecords();
                Constants.ShwoRecords.clear();

                // get families informations from server
                for (int i = 0; i < lstPackages.size(); i++) {
                    String zakatId = lstPackages.get(i).ZakatID;
                    ArrayList<String> PersonsIDs = new ArrayList<>();
                    ArrayList<SQLiteRecord> AllFamilyRecords = new ArrayList<>();

                    //get from persons table
                    AllFamilyRecords.addAll(DAL.getTableData("persons", Arrays.asList(DBHelper.PersonsColumns),
                            "select " + String.join(",", DBHelper.PersonsColumns) + " from persons where ZakatID like '" + zakatId + "';",
                            List.of(9)));

                    // get how many persons in the family
                    for (int j = 0; j < AllFamilyRecords.size(); j++)
                        PersonsIDs.add(Objects.requireNonNull(AllFamilyRecords.get(i).getRecord().get("PersonID")).toString());


                    //get from families table
                    AllFamilyRecords.addAll(DAL.getTableData("families", Arrays.asList(DBHelper.FamiliesColumns),
                            "select " + String.join(",", DBHelper.FamiliesColumns) + " from families where ZakatID like '" + zakatId + "';",
                            List.of()));


                    // if this Form is for collecting data
                    if (lstPackages.get(i).Package.equals("إضافة")) {
                        Constants.SQLITEDAL.insertAllRecords(AllFamilyRecords);


                        // add info to show
                        Optional<SQLiteRecord> familyRecord = AllFamilyRecords.stream().filter(x -> x.getTableName().equals("families")).findFirst();
                        Optional<SQLiteRecord> fatherRecord = AllFamilyRecords.stream().filter(x -> x.getTableName().equals("persons")
                                && Objects.requireNonNull(x.getRecord().get("PersonID")).toString().endsWith("0")).findFirst(); //get the record of father

                        if (familyRecord.isPresent() && fatherRecord.isPresent())
                            Constants.ShwoRecords.add(new ShowRecord(zakatId,
                                    Objects.requireNonNull(familyRecord.get().getRecord().get("City")).toString(),
                                    Objects.requireNonNull(familyRecord.get().getRecord().get("Town")).toString(),
                                    Objects.requireNonNull(fatherRecord.get().getRecord().get("Name")).toString(),
                                    lstPackages.get(i).Package));
                        continue;   //don't get more information
                    }


                    //get from health_statuses table
//                    ArrayList<HashMap<String, Object>> health_statusesRecords = new ArrayList<>();
                    for (String personID : PersonsIDs) {
                        List<String> health_statusesColumns = Arrays.asList(DBHelper.Helth_StatusesColumns);
                        health_statusesColumns.add(0, "HealthStatusID");
                        AllFamilyRecords.addAll(DAL.getTableData("health_statuses", health_statusesColumns,
                                "select " + String.join(",", health_statusesColumns) + " from health_statuses where PersonID like '" + personID + "';",
                                List.of()));
                    }


                    //get from husbands table
                    AllFamilyRecords.addAll(DAL.getTableData("husbands", Arrays.asList(DBHelper.HusbandsColumns),
                            "select " + String.join(",", DBHelper.HusbandsColumns) + " from husbands where ZakatID like '" + zakatId + "';",
                            List.of()));


                    //get from housing_informations table
                    AllFamilyRecords.addAll(DAL.getTableData("housing_informations", Arrays.asList(DBHelper.HousingInformationColumns),
                            "select " + String.join(",", DBHelper.HousingInformationColumns) + " from housing_informations where ZakatID like '" + zakatId + "';",
                            List.of()));

                    //get from incomes table
                    List<String> incomesColumns = Arrays.asList(DBHelper.IncomesColumns);
                    incomesColumns.add(0, "ID");
                    AllFamilyRecords.addAll(DAL.getTableData("incomes", incomesColumns,
                            "select " + String.join(",", incomesColumns) + " from incomes where ZakatID like '" + zakatId + "';",
                            List.of()));


                    //get from water_types table
                    List<String> waterTypesColumns = Arrays.asList(DBHelper.WaterTypesColumns);
                    waterTypesColumns.add(0, "WaterTypeID");
                    AllFamilyRecords.addAll(DAL.getTableData("water_types", waterTypesColumns,
                            "select " + String.join(",", waterTypesColumns) + " from water_types where ZakatID like '" + zakatId + "';",
                            List.of()));


                    //get from aids table
                    List<String> aidsColumns = Arrays.asList(DBHelper.AidsColumns);
                    aidsColumns.add(0, "AidID");
                    AllFamilyRecords.addAll(DAL.getTableData("aids", aidsColumns,
                            "select " + String.join(",", aidsColumns) + " from aids where ZakatID like '" + zakatId + "';",
                            List.of()));


                    //get from assets table
                    List<String> assetsColumns = Arrays.asList(DBHelper.AssetsColumns);
                    assetsColumns.add(0, "AssetID");
                    AllFamilyRecords.addAll(DAL.getTableData("assets", assetsColumns,
                            "select " + String.join(",", assetsColumns) + " from assets where ZakatID like '" + zakatId + "';",
                            List.of()));


                    //get from survey_conclusions table
                    List<String> survey_conclusionsColumns = Arrays.asList(DBHelper.SurveyConclusionColumns);
                    survey_conclusionsColumns.add(0, "ID");
                    AllFamilyRecords.addAll(DAL.getTableData("survey_conclusions", survey_conclusionsColumns,
                            "select " + String.join(",", survey_conclusionsColumns) + " from survey_conclusions where ZakatID like '" + zakatId + "';",
                            List.of()));


                    // Insert all data on SQLite
                    Constants.SQLITEDAL.insertAllRecords(AllFamilyRecords);

                    // add info to show
                    Optional<SQLiteRecord> familyRecord = AllFamilyRecords.stream().filter(x -> x.getTableName().equals("families")).findFirst();
                    Optional<SQLiteRecord> fatherRecord = AllFamilyRecords.stream().filter(x -> x.getTableName().equals("persons")
                            && Objects.requireNonNull(x.getRecord().get("PersonID")).toString().endsWith("0")).findFirst(); //get the record of father

                    if (familyRecord.isPresent() && fatherRecord.isPresent())
                        Constants.ShwoRecords.add(new ShowRecord(zakatId,
                                Objects.requireNonNull(familyRecord.get().getRecord().get("City")).toString(),
                                Objects.requireNonNull(familyRecord.get().getRecord().get("Town")).toString(),
                                Objects.requireNonNull(fatherRecord.get().getRecord().get("Name")).toString(),
                                lstPackages.get(i).Package));
                }

                return "true";
            } catch (Exception ex) {
                return "false";
            }
        }

        protected void onProgressUpdate(Integer... a) {
            super.onProgressUpdate(a);
            Log.d(TAG + " onProgressUpdate", "You are in progress update ... " + a[0]);
        }

        protected void onPostExecute(String result) {
            super.onPostExecute(result);
            if (result.equalsIgnoreCase("true")) {
                Toast.makeText(MainActivity.this, "تمت عملية المزامنة بنجاح", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(MainActivity.this, "لم تتم عملية المزامنة", Toast.LENGTH_SHORT).show();
            }
            Log.d(TAG + " onPostExecute", "" + result);
        }
    }

}