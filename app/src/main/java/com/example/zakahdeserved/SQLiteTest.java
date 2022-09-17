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
    ArrayList<SQLiteRecord> familyInfo = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sqlite_test);

        DBHelper.initDatabaseTables();

        HashMap<String, Object> hashMap = new HashMap<>();
        hashMap.put("name", "ahmad");
        familyInfo.add(new SQLiteRecord("testRecord", hashMap));
//        EditText txtZakatID = findViewById(R.id.txtZakatID);
        Button btnLoadDataToFragments = findViewById(R.id.btnLoadDataFromSQLite);
        Spinner spinner = findViewById(R.id.spinner);
        btnLoadDataToFragments.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                familyInfo = Constants.SQLITEDAL.getFamilyInfo(txtZakatID.getText().toString());
//                spinner.setSelection(((ArrayAdapter<String>) spinner.getAdapter()).getPosition("إعانة مالية"));
//                txtZakatID.setText(getValueOfControl("name", true).toString());
                loadDataToControls(findViewById(R.id.mainLayout));
            }
        });
    }

    private void loadDataToControls(View view) {
        final ViewGroup viewGroup = (ViewGroup) view;
        try {
            int count = viewGroup.getChildCount();
            for (int i = 0; i < count; i++) {
                View v = viewGroup.getChildAt(i);

                if (v instanceof EditText) {
                    String name = v.getResources().getResourceEntryName(v.getId());
                    ((EditText) v).setText(getValueOfControl(name, false).toString());

                } else if (v instanceof Spinner || v instanceof AppCompatSpinner) {
                    String name = v.getResources().getResourceEntryName(v.getId());
                    Object value = getValueOfControl(name, false);

                    if (Constants.dynamisLists.contains(name))
                        ((Spinner) v).setSelection(Integer.parseInt(value.toString()));
                    else
                        ((Spinner) v).setSelection(((ArrayAdapter<String>) ((Spinner) v).getAdapter()).getPosition(value.toString()));

                } else if (v instanceof CheckBox) {
                    String name = v.getResources().getResourceEntryName(v.getId());
                    Object value = getValueOfControl(name, false);
                    ((CheckBox) v).setChecked(Boolean.getBoolean(value.toString()));

                } else if (v instanceof LinearLayout || v instanceof ScrollView || v instanceof RelativeLayout || v instanceof FrameLayout) {
                    loadDataToControls(v);
//                    Log.d("LinearLayout", v.getResources().getResourceEntryName(v.getId()));
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private Object getValueOfControl(String controlName, boolean deleteRecord) {
        Object value;
        Optional<SQLiteRecord> row = familyInfo.stream()
                .filter(x -> x.getRecord().containsKey(controlName))
                .findFirst();
        if (row.isPresent()) {
            value = row.get().getRecord().get(controlName);

            if (deleteRecord)
                familyInfo.remove(row.get());
        } else
            value = 0;

        return value;
    }
}