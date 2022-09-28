package com.example.zakahdeserved.ChildFragment;

import android.os.Bundle;
import android.util.Log;
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

import com.example.zakahdeserved.Connection.DAL;
import com.example.zakahdeserved.Connection.DBHelper;
import com.example.zakahdeserved.R;
import com.example.zakahdeserved.Utility.Constants;
import com.example.zakahdeserved.Utility.ValidationController;

import java.util.HashMap;
import java.util.Map;

public class Tab9 extends Fragment implements View.OnClickListener {

    LinearLayout layoutSurveyConclusions;
    Button buttonAdd_SurveyConclusions;
    Button buttonSubmitList;

    public Tab9() {
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
        View view = inflater.inflate(R.layout.activity_tab9, container, false);

        buttonAdd_SurveyConclusions = view.findViewById(R.id.buttonAdd_SurveyConclusions);
        buttonSubmitList = view.findViewById(R.id.button_submit_list);

        layoutSurveyConclusions = view.findViewById(R.id.layoutSurveyConclusions);


        buttonAdd_SurveyConclusions.setOnClickListener(this);
        buttonSubmitList.setOnClickListener(this);


        Constants.view9 = view;

        if (Constants.loadingData)
            DBHelper.loadDataToControls(view, Constants.familyInfo);

        return view;
    }

    public void onClick(View v) {

        switch (v.getId()) {

            case R.id.buttonAdd_SurveyConclusions:

                addView(R.layout.row_add_surveyconclusions, R.id.image_remove_SurveyConclusions, layoutSurveyConclusions);

                break;
            case R.id.button_submit_list:
                getData();
//                if (!DAL.executeQueries(insertQuery.toString()))
//                    Constants.SQLITEDAL.addQuery(insertQuery.toString());
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

    private void addView(int id, int imageID, LinearLayout linearLayout) {

        final View surveyconclusionsView = getLayoutInflater().inflate(id, null, false);

        ImageView imageClose = (ImageView) surveyconclusionsView.findViewById(imageID);

        imageClose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                removeView(surveyconclusionsView, linearLayout);
            }
        });

        linearLayout.addView(surveyconclusionsView);

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

        getFromView1();
        getFromView2();
        getFromView4();
        if (ValidationController.ENABLE_FEMALE_TAB) //المستفيد أنثى
            getFromView5();
        getFromView6();
        getFromView7();
        getFromView8();
        getFromView9();
    }

    //معلومات المرشح والعائلة  Person and Family
    void getFromView1() {
        if (Constants.view1 == null)
            return;

        tablesNames = new String[]{"persons", "families"};

        allItemsTable.put(tablesNames[0], DBHelper.PersonsTable);
        allItemsTable.put(tablesNames[1], DBHelper.FamiliesTable);

        DBHelper.PersonsTable.put("PersonID", Constants.ZakatID + "_" + Constants.PersonID);

        getAllControlsNamesAndData(Constants.view1);


        //لم أضع جدول families هنا لأن الجدول لم نكنمل كل بياناته
        // ستكتمل بياناته في الفراغمنت التالية view2 وعندها سنأخذ البيانات منه
        insertQuery.append(getInsertQuery(new String[]{"persons"}, allItemsTable));
    }

    //معلومات العائلة family
    void getFromView2() {
        if (Constants.view2 == null)
            return;
        tablesNames = new String[]{"families"};

        allItemsTable.put(tablesNames[0], DBHelper.FamiliesTable);

        getAllControlsNamesAndData(Constants.view2);

        insertQuery.append(getInsertQuery(tablesNames, allItemsTable));
    }

    //الحالة الصحية لرب الأسرة Helth Status
    void getFromView4() {
        if (Constants.view4 == null)
            return;
        tablesNames = new String[]{"health_statuses"};

        DBHelper.Helth_StatusesTable.put("PersonID", Constants.ZakatID + "_" + Constants.PersonID++);

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
            DBHelper.PersonsTable.put("PersonID", Constants.ZakatID + "_" + Constants.PersonID);

            allItemsTable.put(tablesNames[0], DBHelper.PersonsTable);

            View PersonInfo = PersonsList.getChildAt(i);

            getAllControlsNamesAndData(PersonInfo);

            insertQuery.append(getInsertQuery(tablesNames, allItemsTable));


            //الحالة الصحية للفرد HelthStatus Of person
            LinearLayout PersonsHelthStatusesList = PersonInfo.findViewById(R.id.layout_list_Wifes_HealthStatus);
            for (int j = 0; j < PersonsHelthStatusesList.getChildCount(); j++) {
                tablesNames = new String[]{"health_statuses"};
                DBHelper.Helth_StatusesTable.put("PersonID", Constants.ZakatID + "_" + Constants.PersonID++); //increase personId after insert helth status for current person

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
                        ColumnValue = ((Spinner) v).getSelectedItemId();
                    else
                        ColumnValue = ((Spinner) v).getSelectedItem().toString();
                    putColumnValue(ColumnName, ColumnValue);

                } else if (v instanceof CheckBox) {
                    String ColumnName = v.getResources().getResourceEntryName(v.getId());
                    Object ColumnValue = ((CheckBox) v).isChecked();
                    putColumnValue(ColumnName, ColumnValue);
                } else if (v instanceof LinearLayout || v instanceof ScrollView || v instanceof RelativeLayout || v instanceof FrameLayout) {
                    getAllControlsNamesAndData(v);
//                    Log.d("LinearLayout", v.getResources().getResourceEntryName(v.getId()));
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
            if (tableName.equals("") || tableName == null)
                continue;
            StringBuilder strKeys = new StringBuilder("(");
            StringBuilder strValues = new StringBuilder("(");
            HashMap<String, Object> tableData = allTables.get(tableName);

            for (Map.Entry<String, Object> entry : tableData.entrySet()) {
                strKeys.append(entry.getKey()).append(",");

                Object value = entry.getValue();

                if (value.toString().equals("0") || value.toString().equals("1"))
                    strValues.append(value).append(",");
                else
                    strValues.append('\'').append(value).append('\'').append(",");

            }
            strKeys.deleteCharAt(strKeys.length() - 1);
            strValues.deleteCharAt(strValues.length() - 1);
            strKeys.append(")");
            strValues.append(")");
            insert_query.append("insert into ").append(tableName).append(" ")
                    .append(strKeys).append(" values ").append(strValues).append(";");

        }
        return insert_query.toString();
    }

}