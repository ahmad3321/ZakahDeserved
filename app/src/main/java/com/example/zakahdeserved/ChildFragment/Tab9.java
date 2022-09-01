package com.example.zakahdeserved.ChildFragment;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.Spinner;

import androidx.fragment.app.Fragment;

import com.example.zakahdeserved.R;
import com.example.zakahdeserved.Utility.Constants;

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
        return view;
    }

    public void onClick(View v) {

        switch (v.getId()) {

            case R.id.buttonAdd_SurveyConclusions:

                addView(R.layout.row_add_surveyconclusions, R.id.image_remove_SurveyConclusions, layoutSurveyConclusions);

                break;
            case R.id.button_submit_list:

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

    HashMap<String, Object> PersonsTable = new HashMap<>();
    HashMap<String, Object> FamiliesTable = new HashMap<>();
    HashMap<String, Object> Helth_StatusesTable = new HashMap<>();
    HashMap<String, Object> HusbandsTable = new HashMap<>();
    HashMap<String, Object> HousingInformaionTable = new HashMap<>();
    HashMap<String, Object> WaterTypesTable = new HashMap<>();
    HashMap<String, Object> IncomesTable = new HashMap<>();
    HashMap<String, Object> AidsTable = new HashMap<>();
    HashMap<String, Object> AssetsTable = new HashMap<>();


    String[] PersonsColumns = new String[]{"ZakatID", "Name", "LastName", "FatherName", "MotherFullName", "Gender",
            "IdentityNumber", "lst_IdentityTypes", "IdentityFile", "BirthPlace", "BirthDate", "AcademicQualification", "Relation",
            "WhoIs", "IsWorking", "Record", "MonthlyIncome", "CoinType"};

    String[] FamiliesColumns = new String[]{"ZakatID", "OrginalCity", "OrginalTown", "OrginalVillage", "City", "Town", "Village",
            "Neighborhood", "BuldingSymbol", "BuldingNumber", "AdressDetails", "KnownBy", "IfSmokers", "SmokersCount", "Job",
            "OrginalJob", "WantedJob", "Nationality", "ResidenceStatus", "ContactNumber1", "ContactNumber2",
            "RelationWithContact2", "Deserved", "Reson", "ExisitStatus", "ExisitStatusAbout"};

    String[] Helth_StatusesColumns = new String[]{"HealthStatusID", "PersonID", "HealthStatus",
            "HealthStatusEvaluation", "HealthStatusType", "HealthStatusDescription", "CoinType", "MonthlyCost"};

    String[] HusbandsColumns = new String[]{"ZakatID", "WifeSocialStatus", "HusbandName", "HusbandLastName", "HusbandFatherName",
            "HusbandMotherFullName", "IdentityNumber", "lst_IdentityTypes", "City", "Town", "Village", "BirthPlace",
            "BirthDate", "AcademicQualification", "Status", "EventDate", "Lockup", "TravelPlace", "TravelGoal", "Record",
            "Ifcondemnation", "CondemnationDuration", "ArrestDate"};

    String[] HousingInformationColumns = new String[]{"ZakatID", "HousingNature", "RentValueCoinType", "RentValue",
            "CoveredSpace", "RoomsCount", "FloorType", "RoofType", "WC", "CookingGas", "Mobiles", "Routers", "TVs", "Fridges", "Cars",
            "Motorcycles", "FurnitureEvaluation", "Sanitation", "Location", "GeneralDescription", "SolarPanelsCount", "SolarPanelsAmpCount",
            "AmpCount", "AmpValueCoinType", "OneAmpValue", "ConsumptionValueCoinType", "ConsumptionValue", "CookingGasOther"};

    String[] IncomesColumns = new String[]{"ZakatID", "IfIncome", "IncomeType", "IncomeTime", "IncomeValue", "CoinType"};

    String[] WaterTypesColumns = new String[]{"WaterTypeID", "ZakatID", "WaterType", "CoinType", "MonthlyValue"};

    String[] AidsColumns = new String[]{"AidID", "ZakatID", "AidType", "CoinType", "AidValue", "ReceivingTime", "From"};

    String[] AssetsColumns = new String[]{"ZakatID", "AssetType", "AssetAdress", "BenefitType", "BenefitValue",
            "GroundSpace", "ValueTime", "CoinType", "GroundNature", "MachineType", "AnimalType", "AnimalCount"};

    void getData() {
        for (String col : PersonsColumns)
            PersonsTable.put(col, "");

        for (String col : FamiliesColumns)
            FamiliesTable.put(col, "");

        for (String col : Helth_StatusesColumns)
            Helth_StatusesTable.put(col, "");

        for (String col : HusbandsColumns)
            HusbandsTable.put(col, "");

        for (String col : HousingInformationColumns)
            HousingInformaionTable.put(col, "");

        for (String col : WaterTypesColumns)
            WaterTypesTable.put(col, "");

        for (String col : IncomesColumns)
            IncomesTable.put(col, "");

        for (String col : AidsColumns)
            AidsTable.put(col, "");

        for (String col : AssetsColumns)
            AssetsTable.put(col, "");

        FamiliesTable.put("ZakatID", Constants.ZakatID);
        PersonsTable.put("ZakatID", Constants.ZakatID);
        Helth_StatusesTable.put("PersonID", Constants.PersonID);
        HusbandsTable.put("ZakatID", Constants.ZakatID);
        HousingInformaionTable.put("ZakatID", Constants.ZakatID);
        WaterTypesTable.put("ZakatID", Constants.ZakatID);
        IncomesTable.put("ZakatID", Constants.ZakatID);
        AidsTable.put("ZakatID", Constants.ZakatID);
        AssetsTable.put("ZakatID", Constants.ZakatID);

        getFromView1();
        getFromView2();
        getFromView4();
        getFromView5();
        getFromView6();
        getFromView7();

    }

    //معلومات المرشح والعائلة  Person and Family
    void getFromView1() {
        tablesNames = new String[]{"persons", "families"};

        allItemsTable.put(tablesNames[0], PersonsTable);
        allItemsTable.put(tablesNames[1], FamiliesTable);

        getAllControlsNamesAndData(Constants.view1);

        insertQuery.append(getInsertQuery(tablesNames, allItemsTable));
    }

    //معلومات العائلة family
    void getFromView2() {
        tablesNames = new String[]{"families"};

        allItemsTable.put(tablesNames[0], FamiliesTable);

        getAllControlsNamesAndData(Constants.view2);

        insertQuery.append(getInsertQuery(tablesNames, allItemsTable));
    }

    //الحالة الصحية لرب الأسرة Helth Status
    void getFromView4() {
        tablesNames = new String[]{"health_statuses"};

        allItemsTable.put(tablesNames[0], Helth_StatusesTable);

        LinearLayout Helth_Statuses_List = Constants.view4.findViewById(R.id.layout_list);

        for (int i = 0; i < Helth_Statuses_List.getChildCount(); i++) {

            getAllControlsNamesAndData(Helth_Statuses_List.getChildAt(i));

            insertQuery.append(getInsertQuery(tablesNames, allItemsTable));
        }
    }

    //معلومات الزوج (المستفيد أنثى) Husband Info
    void getFromView5() {
        tablesNames = new String[]{"husbands"};

        allItemsTable.put(tablesNames[0], HusbandsTable);

        getAllControlsNamesAndData(Constants.view5);

        insertQuery.append(getInsertQuery(tablesNames, allItemsTable));
    }

    // معلومات السكن HousingInformations
    void getFromView6() {
        tablesNames = new String[]{"housing_informations"};

        allItemsTable.put(tablesNames[0], HousingInformaionTable);

        getAllControlsNamesAndData(Constants.view6);

        insertQuery.append(getInsertQuery(tablesNames, allItemsTable));


        //مصادر الماء WaterTypes
        tablesNames = new String[]{"water_types"};
        allItemsTable.put(tablesNames[0], WaterTypesTable);
        LinearLayout Water_Types_List = Constants.view6.findViewById(R.id.layout_list_waterType);
        for (int i = 0; i < Water_Types_List.getChildCount(); i++) {

            getAllControlsNamesAndData(Water_Types_List.getChildAt(i));

            insertQuery.append(getInsertQuery(tablesNames, allItemsTable));
        }

        //مصادر الدخل Incomes
        tablesNames = new String[]{"incomes"};
        allItemsTable.put(tablesNames[0], IncomesTable);
        LinearLayout IncomesList = Constants.view6.findViewById(R.id.layout_list_Incomes);
        for (int i = 0; i < IncomesList.getChildCount(); i++) {

            getAllControlsNamesAndData(IncomesList.getChildAt(i));

            insertQuery.append(getInsertQuery(tablesNames, allItemsTable));
        }


        //مصادر الإغاثة Aids
        tablesNames = new String[]{"aids"};
        allItemsTable.put(tablesNames[0], AidsTable);
        LinearLayout AidsList = Constants.view6.findViewById(R.id.layout_list_Aids);
        for (int i = 0; i < AidsList.getChildCount(); i++) {

            getAllControlsNamesAndData(AidsList.getChildAt(i));

            insertQuery.append(getInsertQuery(tablesNames, allItemsTable));
        }


    }

    //الممتلكات والأصول Assets
    void getFromView7() {
        tablesNames = new String[]{"assets"};

        allItemsTable.put(tablesNames[0], AssetsTable);

        LinearLayout AssetsList= Constants.view7.findViewById(R.id.layout_list_Asset);

        for (int i = 0; i < AssetsList.getChildCount(); i++) {

            getAllControlsNamesAndData(AssetsList.getChildAt(i));

            insertQuery.append(getInsertQuery(tablesNames, allItemsTable));
        }
    }


    void getAllControlsNamesAndData(View view) {
        final ViewGroup viewGroup = (ViewGroup) view;
        try {
            int count = viewGroup.getChildCount();
            for (int i = 0; i < count; i++) {
                View v = viewGroup.getChildAt(i);

                if (v instanceof EditText) {
                    String ColumnName = v.getResources().getResourceEntryName(v.getId());
                    Object ColumnValue = ((EditText) v).getText();
                    putColumnValue(ColumnName, ColumnValue);

                } else if (v instanceof Spinner) {
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
                } else if (v instanceof LinearLayout || v instanceof ScrollView || v instanceof RelativeLayout) {
                    getAllControlsNamesAndData(v);
                    Log.d("LinearLayout", v.getResources().getResourceEntryName(v.getId()));
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


    String getInsertQuery(String[] tablesName, HashMap<String, HashMap<String, Object>> allTables) {
        StringBuilder insert_query = new StringBuilder();

        for (String tableName : tablesName) {
            if (tableName.equals("") || tableName == null)
                continue;
            StringBuilder strKeys = new StringBuilder("(");
            StringBuilder strValues = new StringBuilder("(");
            HashMap<String, Object> tableData = allTables.get(tableName);

            for (Map.Entry<String, Object> entry : tableData.entrySet()) {
                strKeys.append(entry.getKey()).append(",");
                strValues.append('\'').append(entry.getValue()).append('\'').append(",");
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