package com.example.zakahdeserved.ChildFragment;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.Spinner;

import androidx.appcompat.widget.AppCompatSpinner;
import androidx.fragment.app.Fragment;
import androidx.security.crypto.EncryptedSharedPreferences;

import com.example.zakahdeserved.Connection.DAL;
import com.example.zakahdeserved.Connection.DBHelper;
import com.example.zakahdeserved.Connection.PackageRecord;
import com.example.zakahdeserved.Connection.SQLiteRecord;
import com.example.zakahdeserved.PackageView;
import com.example.zakahdeserved.R;
import com.example.zakahdeserved.Utility.Constants;
import com.example.zakahdeserved.Utility.ValidationController;

import java.io.IOException;
import java.security.GeneralSecurityException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import java.util.Objects;

public class Tab9 extends Fragment implements View.OnClickListener {

    LinearLayout layoutSurveyConclusions;
    Button buttonAdd_SurveyConclusions;
    Button buttonSubmitList;
    String autoDate;

    public Tab9() {
        // Required empty public constructor
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.activity_tab9, container, false);

        buttonAdd_SurveyConclusions = view.findViewById(R.id.buttonAdd_SurveyConclusions);
        buttonSubmitList = view.findViewById(R.id.button_submit_list);

        layoutSurveyConclusions = view.findViewById(R.id.layoutSurveyConclusions);


        buttonAdd_SurveyConclusions.setOnClickListener(this);
        buttonSubmitList.setOnClickListener(this);


        Constants.view9 = view;

        //Load data from family info (في حالة حزمة إضافة لن يكون هناك إلا بيانات أولية)
        DBHelper.loadDataToControls(view, Constants.familyInfo);
        // load survey_conclusions
        ArrayList<SQLiteRecord> records = Constants.SQLITEDAL.getRecords("survey_conclusions", DBHelper.SurveyConclusionColumns, "ZakatID", Constants.ZakatID);
        for (SQLiteRecord record : records) {
            View v = addView(R.layout.row_add_surveyconclusions, R.id.image_remove_SurveyConclusions, layoutSurveyConclusions);
            DBHelper.loadDataToControls(v, record);
        }

        return view;
    }

    public void onClick(View v) {

        switch (v.getId()) {

            case R.id.buttonAdd_SurveyConclusions:
                addView(R.layout.row_add_surveyconclusions, R.id.image_remove_SurveyConclusions, layoutSurveyConclusions);
                break;
            case R.id.button_submit_list:
                AlertDialog.Builder alert = new AlertDialog.Builder(getContext());
                alert.setTitle("إدخال البيانات");
                alert.setMessage("هل أنت متأكد من ادخال البيانات ؟");
                alert.setPositiveButton("نعم", (dialogInterface, i) -> {
                    getData();
                    if (!DAL.executeQueries(insertQuery.toString()))
                        Constants.SQLITEDAL.addQuery(insertQuery.toString(), Constants.PackageID, Constants.ZakatID,Constants.PackagePersonID, "package");
                    dialogInterface.dismiss();
                });
                alert.setNegativeButton("لا", (dialog, which) -> dialog.dismiss());

                alert.show();
                break;
        }
    }

    private View addView(int id, int imageID, LinearLayout linearLayout) {

        final View surveyconclusionsView = getLayoutInflater().inflate(id, null, false);

        ImageView imageClose = surveyconclusionsView.findViewById(imageID);

        imageClose.setOnClickListener(v -> removeView(surveyconclusionsView, linearLayout));

        if (linearLayout.getChildCount() % 2 != 0) {
            surveyconclusionsView.setBackgroundColor(Color.WHITE);
        } else
            surveyconclusionsView.setBackgroundColor(Color.parseColor("#FFA5D3A6"));
        linearLayout.addView(surveyconclusionsView);

        return surveyconclusionsView;
    }

    private void removeView(View view, LinearLayout linear) {

        linear.removeView(view);

    }

    String[] tablesNames;
    HashMap<String, HashMap<String, Object>> allItemsTable = new HashMap<>();
    StringBuilder insertQuery = new StringBuilder();


    void getData() {
        DBHelper.FamiliesTable.put("ZakatID", Constants.ZakatID);
        DBHelper.PersonsTable.put("ZakatID", Constants.ZakatID);
        DBHelper.HusbandsTable.put("ZakatID", Constants.ZakatID);
        DBHelper.HousingInformaionTable.put("ZakatID", Constants.ZakatID);
        DBHelper.WaterTypesTable.put("ZakatID", Constants.ZakatID);
        DBHelper.IncomesTable.put("ZakatID", Constants.ZakatID);
        DBHelper.AidsTable.put("ZakatID", Constants.ZakatID);
        DBHelper.AssetsTable.put("ZakatID", Constants.ZakatID);
        DBHelper.SurveyConclusionTable.put("ZakatID", Constants.ZakatID);

        removeRecordsFromSubTables();

        getFromView1();
        getFromView2();
        getFromView4();
        if (ValidationController.ENABLE_FEMALE_TAB) //المستفيد أنثى
            getFromView5();
        getFromView6();
        getFromView7();
        getFromView8();
        getFromView9();

        addToPackage();
        linkRecordToEntry();
        //calculate family pointers
        insertQuery.append("call calculate_pointers('").append(Constants.ZakatID).append("', '").append(autoDate).append("');");

        // remove the package from local after end work with it.
        Constants.SQLITEDAL.deletePackage(Constants.ZakatID, Constants.PackagePersonID);
    }

    private void linkRecordToEntry() {
        try {
            SharedPreferences sharedPreferences = EncryptedSharedPreferences.create(
                    getContext(),
                    "MySharedPref",
                    Constants.SHAREDPREFERENCES_KEY, // masterKey created above
                    EncryptedSharedPreferences.PrefKeyEncryptionScheme.AES256_SIV,
                    EncryptedSharedPreferences.PrefValueEncryptionScheme.AES256_GCM);


            insertQuery.append("INSERT INTO link_entries_to_records (EntryID, ZakatID, PersonID, Program, Event, EntryType) VALUES ('")
                    .append(sharedPreferences.getString("entry_id", "")).append("', '").append(Constants.ZakatID).append("', '")
                    .append(Constants.PackagePersonID).append("', '").append(Constants.PackageProgram).append("', '")
                    .append(Constants.PackageType).append("', ").append(sharedPreferences.getInt("empDepartment", -1) == Constants.DISTRIBUTION_JOcB_TITLE).append(");");

        } catch (GeneralSecurityException | IOException e) {
            e.printStackTrace();
        }
    }

    // إزالة السجلات الموجودة في الجداول الفرعية من أجل إضافة السجلات الجديدة
    void removeRecordsFromSubTables() {
        insertQuery.append("DELETE FROM incomes  WHERE ZakatID = '").append(Constants.ZakatID).append("' and ID > -1;");
        insertQuery.append("DELETE FROM water_types  WHERE ZakatID = '").append(Constants.ZakatID).append("' and WaterTypeID > -1;");
        insertQuery.append("DELETE FROM survey_conclusions  WHERE ZakatID = '").append(Constants.ZakatID).append("' and ID > -1;");
        insertQuery.append("DELETE FROM assets  WHERE ZakatID = '").append(Constants.ZakatID).append("' and AssetID > -1;");
        insertQuery.append("DELETE FROM aids  WHERE ZakatID = '").append(Constants.ZakatID).append("' and AidID > -1;");
        insertQuery.append("DELETE FROM health_statuses  WHERE PersonID in (select PersonID from persons where ZakatID = '").append(Constants.ZakatID).append("') and HealthStatusID > -1;");
    }

    // Packages and Package_Contents تحديث حالة الحزمة
    void addToPackage() {

        /*Date c = Calendar.getInstance().getTime();
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());
        autoDate = df.format(c);*/
        autoDate = ValidationController.updateLabel(Calendar.getInstance().getTime());


        insertQuery.append("UPDATE package_contents SET PackageStatus = 'تم الانتهاء منها' , EndDate = '").append(autoDate)
                .append("' WHERE PackageID = '").append(Constants.PackageID)
                .append("'  and personid  = '").append(Constants.PackagePersonID).append("';");

        PackageRecord packageRecord = Constants.SQLITEDAL.getPackageRecord(Constants.PackageID, Constants.ZakatID, Constants.PackagePersonID);

        insertQuery.append("INSERT INTO packages (Package, FromEmployeeCode, ToEmployeeCode, SendDate, Program)" + "VALUES ('")
                .append(packageRecord.Package).append("', '").append(packageRecord.ToEmployeeCode).append("', '")
                .append(packageRecord.FromEmployeeCode).append("', '").append(autoDate).append("', '")
                .append(packageRecord.Program).append("');")
                .append("INSERT INTO package_contents (PackageID, PersonID, ZakatID, PackageStatus) ")
                .append("VALUES ((select max(PackageID) from packages), '").append(packageRecord.PersonID).append("', '")
                .append(packageRecord.ZakatID).append("', 'قيد العمل');");
    }

    //معلومات المرشح والعائلة  Person and Family
    void getFromView1() {
        if (Constants.view1 == null)
            return;

        tablesNames = new String[]{"persons", "families"};

        allItemsTable.put(tablesNames[0], DBHelper.PersonsTable);
        allItemsTable.put(tablesNames[1], DBHelper.FamiliesTable);

        DBHelper.PersonsTable.put("PersonID", Constants.ZakatID + "-0");
        DBHelper.PersonsTable.put("WhoIs", "رب الأسرة");

        getAllControlsNamesAndData(Constants.view1);

        //get the identity number for the person
        String identityNumber = Objects.requireNonNull(DBHelper.PersonsTable.get("IdentityNumber")).toString();
        //set the person pdfFile of his identity
        DBHelper.PersonsTable.put("IdentityFile", Constants.imagesFiles.get(identityNumber));

        //لم أضع جدول families هنا لأن الجدول لم نكنمل كل بياناته
        // ستكتمل بياناته في الفراغمنت الأخيرة view9 وعندها سنأخذ البيانات منه
        insertQuery.append(getInsertQuery(new String[]{"persons"}, allItemsTable));
    }

    //معلومات العائلة family
    void getFromView2() {
        if (Constants.view2 == null)
            return;
        tablesNames = new String[]{"families"};

        allItemsTable.put(tablesNames[0], DBHelper.FamiliesTable);

        getAllControlsNamesAndData(Constants.view2);

        // لم نأخذ بيانات جدول families من هنا لأم البيانات لم تكتمل بعد
        // سوف تكتمل البيانات في التاب الأخير
//        insertQuery.append(getInsertQuery(tablesNames, allItemsTable));
    }

    //الحالة الصحية لرب الأسرة Helth Status
    void getFromView4() {

        // قم بزيادة عداد IncrementPersonID في كل الحالتين سواء هناك حالة صحية لرب الأسرة أو لا
        DBHelper.Helth_StatusesTable.put("PersonID", Constants.ZakatID + "-0");

        if (Constants.view4 == null)
            return;

        tablesNames = new String[]{"health_statuses"};

        allItemsTable.put(tablesNames[0], DBHelper.Helth_StatusesTable);

        LinearLayout Helth_Statuses_List = Constants.view4.findViewById(R.id.layout_list);

        for (int i = 0; i < Helth_Statuses_List.getChildCount(); i++) {

            getAllControlsNamesAndData(Helth_Statuses_List.getChildAt(i));

            insertQuery.append(getInsertQuery(tablesNames, allItemsTable));
        }
    }

    //معلومات الزوج (المستفيد أنثى) Husband Info
    void getFromView5() {
        if (Constants.view5 == null)
            return;
        tablesNames = new String[]{"husbands"};

        allItemsTable.put(tablesNames[0], DBHelper.HusbandsTable);

        getAllControlsNamesAndData(Constants.view5);

        insertQuery.append(getInsertQuery(tablesNames, allItemsTable));
    }

    // معلومات السكن HousingInformations
    void getFromView6() {
        if (Constants.view6 == null)
            return;
        tablesNames = new String[]{"housing_informations"};

        allItemsTable.put(tablesNames[0], DBHelper.HousingInformaionTable);

        getAllControlsNamesAndData(Constants.view6);

        insertQuery.append(getInsertQuery(tablesNames, allItemsTable));


        //مصادر الماء WaterTypes
        tablesNames = new String[]{"water_types"};
        allItemsTable.put(tablesNames[0], DBHelper.WaterTypesTable);
        LinearLayout Water_Types_List = Constants.view6.findViewById(R.id.layout_list_waterType);
        for (int i = 0; i < Water_Types_List.getChildCount(); i++) {

            getAllControlsNamesAndData(Water_Types_List.getChildAt(i));

            insertQuery.append(getInsertQuery(tablesNames, allItemsTable));
        }

        //مصادر الدخل Incomes
        tablesNames = new String[]{"incomes"};
        allItemsTable.put(tablesNames[0], DBHelper.IncomesTable);
        LinearLayout IncomesList = Constants.view6.findViewById(R.id.layout_list_Incomes);
        for (int i = 0; i < IncomesList.getChildCount(); i++) {

            getAllControlsNamesAndData(IncomesList.getChildAt(i));

            insertQuery.append(getInsertQuery(tablesNames, allItemsTable));
        }


        //مصادر الإغاثة Aids
        tablesNames = new String[]{"aids"};
        allItemsTable.put(tablesNames[0], DBHelper.AidsTable);
        LinearLayout AidsList = Constants.view6.findViewById(R.id.layout_list_Aids);
        for (int i = 0; i < AidsList.getChildCount(); i++) {

            getAllControlsNamesAndData(AidsList.getChildAt(i));

            insertQuery.append(getInsertQuery(tablesNames, allItemsTable));
        }
    }

    //الممتلكات والأصول Assets
    void getFromView7() {
        if (Constants.view7 == null)
            return;
        tablesNames = new String[]{"assets"};

        allItemsTable.put(tablesNames[0], DBHelper.AssetsTable);

        LinearLayout AssetsList = Constants.view7.findViewById(R.id.layout_list_Asset);

        for (int i = 0; i < AssetsList.getChildCount(); i++) {

            getAllControlsNamesAndData(AssetsList.getChildAt(i));

            insertQuery.append(getInsertQuery(tablesNames, allItemsTable));
        }
    }

    //أفراد الأسرة (زوجة, أولاد, معالين) persons
    void getFromView8() {

        if (Constants.view8 == null)
            return;

        LinearLayout PersonsList = Constants.view8.findViewById(R.id.layout_list_Wifes);

        for (int i = 0; i < PersonsList.getChildCount(); i++) {

            tablesNames = new String[]{"persons"};

            allItemsTable.put(tablesNames[0], DBHelper.PersonsTable);

            View PersonInfo = PersonsList.getChildAt(i);
            String _PersonID = ((EditText) PersonInfo.findViewById(R.id.PersonID)).getText().toString();

            // if personid not empty, take the max person id
            if (!_PersonID.equals(""))
                Constants.IncrementPersonID = Integer.parseInt(_PersonID.substring(_PersonID.indexOf('-')));

                // if person id is empty
            else {
                Constants.IncrementPersonID++;
                _PersonID = Constants.ZakatID + "-" + Constants.IncrementPersonID;
                ((EditText) PersonInfo.findViewById(R.id.PersonID)).setText(_PersonID);
            }

            getAllControlsNamesAndData(PersonInfo);

            //get the identity number for the person
            String identityNumber = Objects.requireNonNull(DBHelper.PersonsTable.get("IdentityNumber")).toString();
            //set the person pdfFile of his identity
            if (Constants.imagesFiles.containsKey(identityNumber))
                DBHelper.PersonsTable.put("IdentityFile", Constants.imagesFiles.get(identityNumber));

            insertQuery.append(getInsertQuery(tablesNames, allItemsTable));


            //الحالة الصحية للفرد HelthStatus Of person
            // قم بزيادة عداد IncrementPersonID في كل الحالتين سواء هناك حالة صحية للفرد أو لا
            DBHelper.Helth_StatusesTable.put("PersonID", _PersonID);
            LinearLayout PersonsHelthStatusesList = PersonInfo.findViewById(R.id.layout_list_Wifes_HealthStatus);

            for (int j = 0; j < PersonsHelthStatusesList.getChildCount(); j++) {
                tablesNames = new String[]{"health_statuses"};

                allItemsTable.put(tablesNames[0], DBHelper.Helth_StatusesTable);
                getAllControlsNamesAndData(PersonsHelthStatusesList.getChildAt(j));

                insertQuery.append(getInsertQuery(tablesNames, allItemsTable));
            }

        }
    }

    //رأي الجوارSurvey Conclusons
    void getFromView9() {
        if (Constants.view9 == null)
            return;

        tablesNames = new String[]{"families"};

        allItemsTable.put(tablesNames[0], DBHelper.FamiliesTable);

        getAllControlsNamesAndData(Constants.view9);

        insertQuery.append(getInsertQuery(tablesNames, allItemsTable));


        //رأي الجوار
        tablesNames = new String[]{"survey_conclusions"};
        allItemsTable.put(tablesNames[0], DBHelper.SurveyConclusionTable);
        LinearLayout SurveyCoinclusionList = Constants.view9.findViewById(R.id.layoutSurveyConclusions);
        for (int i = 0; i < SurveyCoinclusionList.getChildCount(); i++) {

            getAllControlsNamesAndData(SurveyCoinclusionList.getChildAt(i));

            insertQuery.append(getInsertQuery(tablesNames, allItemsTable));
        }
    }

    private void getAllControlsNamesAndData(View view) {
        final ViewGroup viewGroup = (ViewGroup) view;
        try {
            int count = viewGroup.getChildCount();
            for (int i = 0; i < count; i++) {
                View v = viewGroup.getChildAt(i);

                if (v instanceof EditText) {
                    String ColumnName = v.getResources().getResourceEntryName(v.getId());
                    Object ColumnValue = ((EditText) v).getText();
                    putColumnValue(ColumnName, ColumnValue);

                } else if (v instanceof Spinner || v instanceof AppCompatSpinner) {
                    String ColumnName = v.getResources().getResourceEntryName(v.getId());

                    if (ColumnName.contains("1"))
                        ColumnName = ColumnName.substring(0, ColumnName.indexOf("1"));

                    Object ColumnValue;
                    if (Constants.dynamisLists.contains(ColumnName))
                        ColumnValue = ((Spinner) v).getSelectedItemId() + 1;
                    else
                        ColumnValue = ((Spinner) v).getSelectedItem().toString();
                    putColumnValue(ColumnName, ColumnValue);

                } else if (v instanceof CheckBox) {
                    String ColumnName = v.getResources().getResourceEntryName(v.getId());
                    Object ColumnValue = ((CheckBox) v).isChecked();
                    putColumnValue(ColumnName, ColumnValue);
                } else if (v instanceof LinearLayout || v instanceof ScrollView || v instanceof RelativeLayout || v instanceof FrameLayout) {
                    getAllControlsNamesAndData(v);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    void putColumnValue(String colName, Object colValue) {
        for (HashMap<String, Object> colKeyVal : allItemsTable.values()) {
            if (colKeyVal.containsKey(colName)) {
                colKeyVal.put(colName, colValue);
            }
        }
    }


    private String getInsertQuery(String[] tablesName, HashMap<String, HashMap<String, Object>> allTables) {
        StringBuilder insert_query = new StringBuilder();

        for (String tableName : tablesName) {
            if (tableName == null || tableName.equals(""))
                continue;
            StringBuilder strKeys = new StringBuilder("(");
            StringBuilder strValues = new StringBuilder("(");
            // update query used for on key duplicated, if so perform update instead of insert.
            StringBuilder update_query = new StringBuilder();
            HashMap<String, Object> tableData = allTables.get(tableName);

            for (Map.Entry<String, Object> entry : tableData.entrySet()) {
                strKeys.append(entry.getKey()).append(",");
                update_query.append(entry.getKey()).append(" = ");

                Object value = entry.getValue();
                if (value == null || value.toString().isEmpty()) {
                    strValues.append("null").append(",");
                    update_query.append("null").append(",");
                } else if (value.toString().equals("0") || value.toString().equals("1") ||
                        value.toString().equals("true") || value.toString().equals("false")) {
                    strValues.append(value).append(",");
                    update_query.append(value).append(",");
                } else {
                    strValues.append('\'').append(value).append('\'').append(",");
                    update_query.append('\'').append(value).append('\'').append(",");
                }
            }
            strKeys.deleteCharAt(strKeys.length() - 1);
            strValues.deleteCharAt(strValues.length() - 1);
            update_query.deleteCharAt(update_query.length() - 1);
            strKeys.append(")");
            strValues.append(")");

            insert_query.append("insert into ").append(tableName).append(" ")
                    .append(strKeys).append(" values ").append(strValues).append("\n");
            insert_query.append(" ON DUPLICATE KEY UPDATE ");
            insert_query.append(update_query).append(";");

        }
        return insert_query.toString();
    }

}