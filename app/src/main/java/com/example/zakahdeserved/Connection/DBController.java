package com.example.zakahdeserved.Connection;

import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.Spinner;

import java.util.HashMap;
import java.util.Map;

public class DBController {
    public static String[] tablesNames = new String[]{"Husbands", "Families", "Incomes",
            "HousingInformations", "Persons", "Aids", "SurveyConclusions", "Assets", "HealthStatuses"};

    private static final String[] HusbandsColumns = new String[]{"WifeSocialStatus", "HusbandName", "HusbandLastName",
            "HusbandFatherName", "HusbandMotherFullName", "IdentityNumber", "IdentityType", "City", "Town",
            "Village", "BirthPlace", "BirthDate", "AcademicQualification", "Status", "EventDate", "Lockup",
            "TravelPlace", "TravelGoal", "Record", "Ifcondemnation", "CondemnationDuration", "ArrestDate"
    };

    public static HashMap<String, HashMap<String, Object>> allItemsTable = new HashMap<>();


    public static void init() {

        //Husbands
        HashMap<String, Object> HusbandsTable = new HashMap<>();
        for (String col : HusbandsColumns) {
            HusbandsTable.put(col, "");
        }
        allItemsTable.put(tablesNames[0], HusbandsTable);

    }

    public static void getAllControlsNamesAndData(View view) {
        final ViewGroup viewGroup = (ViewGroup) view;
        try {
            int count = viewGroup.getChildCount();
            for (int i = 0; i < count; i++) {
                View v = viewGroup.getChildAt(i);

                if (v instanceof EditText) {
                    Object ColumnValue;
                    String ColumnName = v.getResources().getResourceEntryName(v.getId());

                    ColumnValue = ((EditText) v).getText();
                    addColumnValue(ColumnName, ColumnValue);
                } else if (v instanceof Spinner) {
                    String ColumnName = v.getResources().getResourceEntryName(v.getId());

                    if (ColumnName.contains("1"))
                        ColumnName = ColumnName.substring(0, ColumnName.indexOf("1"));

                    Object ColumnValue = ((Spinner) v).getSelectedItemId();
                    addColumnValue(ColumnName, ColumnValue);
                } else if (v instanceof CheckBox) {
                    String ColumnName = v.getResources().getResourceEntryName(v.getId());
                    Object ColumnValue = ((CheckBox) v).isChecked();
                    addColumnValue(ColumnName, ColumnValue);
                } else if (v instanceof LinearLayout || v instanceof ScrollView || v instanceof RelativeLayout) {
                    getAllControlsNamesAndData(v);
                    Log.d("LinearLayout", v.getResources().getResourceEntryName(v.getId()));
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    static void addColumnValue(String colName, Object colValue) {
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