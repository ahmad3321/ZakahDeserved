package com.example.zakahdeserved.ChildFragment;

import android.app.DatePickerDialog;
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

import com.example.zakahdeserved.Connection.DAL;
import com.example.zakahdeserved.Connection.DBHelper;
import com.example.zakahdeserved.R;
import com.example.zakahdeserved.Utility.Constants;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;


public class Programms_Tab5Student extends Fragment implements View.OnClickListener {

    Button button_Insert_Student;

    public Programms_Tab5Student() {
    }

    View myView;
    Calendar myCalendar;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.activity_programms_tab5student, container, false);
        myView = view;


        button_Insert_Student = view.findViewById(R.id.button_Insert_Student);
        button_Insert_Student.setOnClickListener(this);

        ((EditText) view.findViewById(R.id.ZakatID)).setText(Constants.ZakatID);

        myCalendar = Calendar.getInstance();

        EditText txtReceivedDate = view.findViewById(R.id.ReceivedDate);

        DatePickerDialog.OnDateSetListener date = (view1, year, monthOfYear, dayOfMonth) -> {
            myCalendar.set(Calendar.YEAR, year);
            myCalendar.set(Calendar.MONTH, monthOfYear);
            myCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
            updateLabel(txtReceivedDate);
        };

        txtReceivedDate.setOnClickListener(v -> new DatePickerDialog(getContext(), date, myCalendar
                .get(Calendar.YEAR), myCalendar.get(Calendar.MONTH),
                myCalendar.get(Calendar.DAY_OF_MONTH)).show());

        //Constants.SQLITEDAL.fillSpinner(getContext(), view.findViewById(R.id.lst_Universities));

        return view;
    }

    private void updateLabel(EditText txtDate) {
        String myFormat = "yyyy-MM-dd HH:mm";
        SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);
        txtDate.setText(sdf.format(myCalendar.getTime()));
    }

    public void onClick(View v) {

        switch (v.getId()) {

            case R.id.button_Insert_Student:
                getData();
                if (!DAL.executeQueries(insertQuery.toString()))
                    Constants.SQLITEDAL.addQuery(insertQuery.toString());

                break;


        }
    }

    String[] tablesNames;
    HashMap<String, HashMap<String, Object>> allItemsTable = new HashMap<>();
    StringBuilder insertQuery = new StringBuilder();

    void getData() {
        DBHelper.StudentsProgramTable.put("PersonID", Constants.ZakatID + "_" + Constants.PersonID);
        tablesNames = new String[]{"students_program"};

        allItemsTable.put(tablesNames[0], DBHelper.StudentsProgramTable);

        getAllControlsNamesAndData(myView);

        insertQuery.append(getInsertQuery(tablesNames, allItemsTable));
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