package com.example.zakahdeserved;

import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.security.crypto.EncryptedSharedPreferences;

import com.example.zakahdeserved.Connection.DAL;
import com.example.zakahdeserved.Connection.DBHelper;
import com.example.zakahdeserved.Connection.PackageRecord;
import com.example.zakahdeserved.Connection.SQLiteRecord;
import com.example.zakahdeserved.Connection.ShowRecord;
import com.example.zakahdeserved.Utility.Constants;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

public class PackageView extends AppCompatActivity {

    Button btn_download, btn_upload, btn_refresh;
    ImageButton btn_Sync;
    ArrayList<PackageRecord> lstPackages = new ArrayList<>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.packageview);
        btn_Sync = findViewById(R.id.btn_Sync);
        btn_download = findViewById(R.id.btn_download);
        btn_upload = findViewById(R.id.btn_upload);
        btn_refresh = findViewById(R.id.btn_Refresh);


        btn_Sync.setOnClickListener(view -> {

        });
    }

    public void onClick_UDR(View view) {

        switch (view.getId()) {
            case R.id.btn_download:
                try {
                    (new TestAsync()).execute();
                } catch (Exception ignored) {
                }
                break;
            case R.id.btn_upload:
                break;
            case R.id.btn_Refresh:
                break;
        }
    }

    private void addView(ArrayList<PackageRecord> list) {

        LayoutInflater linf = LayoutInflater.from(PackageView.this);

        //Linear Layout on you want to add inflated View
        LinearLayout layout_list_Add = findViewById(R.id.layout_list_Add);
        LinearLayout layout_list_Update = findViewById(R.id.layout_list_Update);
        LinearLayout layout_list_Refresh = findViewById(R.id.layout_list_Refresh);
        LinearLayout layout_list_Program = findViewById(R.id.layout_list_Program);

        for (int i = 0; i < list.size(); i++) {

            View v = linf.inflate(R.layout.row_add_package, null);//Pass your lineraLayout
            String packageName = list.get(0).getPackage();
            ((EditText) v.findViewById(R.id.ZakatID)).setText(list.get(0).getZakatID());
            ((EditText) v.findViewById(R.id.PersonID)).setText(list.get(0).getPersonID());
            ((EditText) v.findViewById(R.id.Program)).setText(list.get(0).getProgram());

            switch (packageName) {
                case "إضافة":
                    layout_list_Add.addView(v);
                    break;
                case "تعديل":
                    layout_list_Update.addView(v);
                    break;
                case "تحديث":
                    layout_list_Refresh.addView(v);
                    break;
                case "توزيع":
                    layout_list_Program.addView(v);
                    break;
            }
        }
    }

    class TestAsync extends AsyncTask<Void, Integer, String> {
        String TAG = getClass().getSimpleName();

        protected void onPreExecute() {
            super.onPreExecute();
            Toast.makeText(PackageView.this, "بدأت عملية تنزيل الحزم .. انتظر قليلا", Toast.LENGTH_SHORT).show();
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
                lstPackages = DAL.getPackeges("SELECT packages.PackageID,ZakatID, PersonID, Program," +
                        " FromEmployeeCode, ToEmployeeCode, Package\n" +
                        "FROM zakatraising.packages \n" +
                        "INNER JOIN zakatraising.package_contents \n" +
                        "on zakatraising.packages.packageID = zakatraising.package_contents.packageID\n" +
                        " where packages.ToEmployeeCode = '" + empCode + "' and package_contents.PackageStatus = 'قيد العمل';");


                // Clear all stored informations about families
                Constants.SQLITEDAL.ClearAllRecords();
                Constants.ShwoRecords.clear();

                //store packages info locally
                Constants.SQLITEDAL.StorePackages(lstPackages);


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
                        PersonsIDs.add(Objects.requireNonNull(AllFamilyRecords.get(j).getRecord().get("PersonID")).toString());


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
                                    lstPackages.get(i).Package,
                                    lstPackages.get(i).Program));
                        continue;   //don't get more information
                    }


                    //get from health_statuses table
                    for (String personID : PersonsIDs) {
                        List<String> health_statusesColumns = new LinkedList<>(Arrays.asList(DBHelper.Helth_StatusesColumns));
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
                    List<String> incomesColumns = new LinkedList<>(Arrays.asList(DBHelper.IncomesColumns));
                    incomesColumns.add(0, "ID");
                    AllFamilyRecords.addAll(DAL.getTableData("incomes", incomesColumns,
                            "select " + String.join(",", incomesColumns) + " from incomes where ZakatID like '" + zakatId + "';",
                            List.of()));


                    //get from water_types table
                    List<String> waterTypesColumns = new LinkedList<>(Arrays.asList(DBHelper.WaterTypesColumns));
                    waterTypesColumns.add(0, "WaterTypeID");
                    AllFamilyRecords.addAll(DAL.getTableData("water_types", waterTypesColumns,
                            "select " + String.join(",", waterTypesColumns) + " from water_types where ZakatID like '" + zakatId + "';",
                            List.of()));


                    //get from aids table
                    List<String> aidsColumns = new LinkedList<>(Arrays.asList(DBHelper.AidsColumns));
                    aidsColumns.add(0, "AidID");
                    AllFamilyRecords.addAll(DAL.getTableData("aids", aidsColumns,
                            "select " + String.join(",", aidsColumns) + " from aids where ZakatID like '" + zakatId + "';",
                            List.of()));


                    //get from assets table
                    List<String> assetsColumns = new LinkedList<>(Arrays.asList(DBHelper.AssetsColumns));
                    assetsColumns.add(0, "AssetID");
                    AllFamilyRecords.addAll(DAL.getTableData("assets", assetsColumns,
                            "select " + String.join(",", assetsColumns) + " from assets where ZakatID like '" + zakatId + "';",
                            List.of()));


                    //get from survey_conclusions table
                    List<String> survey_conclusionsColumns = new LinkedList<>(Arrays.asList(DBHelper.SurveyConclusionColumns));
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
                                lstPackages.get(i).Package,
                                lstPackages.get(i).Program));
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

                ArrayList<String> packagesIDs = new ArrayList<>();
                for (PackageRecord packageRecord : lstPackages)
                    packagesIDs.add(packageRecord.PackageID);
                String packagesDoneQuery = "UPDATE 'package_contents' SET 'PackageStatus' = 'تم الانتهاء منها' " +
                        " WHERE 'PackageID' in (" + String.join(",", packagesIDs) + ") ;";

                if (!DAL.executeQueries(packagesDoneQuery))
                    Constants.SQLITEDAL.addQuery(packagesDoneQuery);


                runOnUiThread(() -> addView(lstPackages));

                Toast.makeText(PackageView.this, "تمت عملية تنزيل الحزم بنجاح", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(PackageView.this, "فشلت عملية التنزيل", Toast.LENGTH_SHORT).show();
            }
            Log.d(TAG + " onPostExecute", "" + result);
        }
    }

}