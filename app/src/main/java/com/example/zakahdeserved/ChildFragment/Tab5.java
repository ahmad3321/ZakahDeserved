package com.example.zakahdeserved.ChildFragment;

import android.app.DatePickerDialog;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;

import com.example.zakahdeserved.Connection.DBHelper;
import com.example.zakahdeserved.R;
import com.example.zakahdeserved.Utility.Constants;
import com.example.zakahdeserved.Utility.ValidationController;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

public class Tab5 extends Fragment {

    boolean pageLocked = false;
    Calendar myCalendar;

    public Tab5() {
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
        View view5 = inflater.inflate(R.layout.activity_tab5, container, false);
        Constants.view5 = view5;
        EditText txtEventDate = view5.findViewById(R.id.EventDate);

        if (!ValidationController.ENABLE_FEMALE_TAB)
            ValidationController.lockThePage(view5);
//            view5.findViewById(R.id.FemaleScrollView).setEnabled(false);
        else
            ValidationController.UnlockThePage(view5);
        //view5.findViewById(R.id.FemaleScrollView).setEnabled(true);

        Spinner spnWifeSocialStatus = view5.findViewById(R.id.WifeSocialStatus);
        Spinner spnHusbandStatuses = view5.findViewById(R.id.Status);

        spnWifeSocialStatus.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {

                String[] listHusbandStatuses = new String[]{"سليم", "معاق", "متوفى", "معتقل", "مفقود", "مسافر"};

                //متزوجة
                if (i == 0) {
                    listHusbandStatuses = new String[]{"سليم", "معاق", "معتقل", "مفقود", "مسافر"};
                    if (pageLocked) {
                        ValidationController.UnlockThePage(view5);
                        pageLocked = false;
                    }
                }

                //,مهجورة, مطلقة, عزباء
                else if (i == 1 || i == 3 || i == 4) {
                    if (!pageLocked) {
                        ValidationController.lockThePage(view5);
                        spnWifeSocialStatus.setEnabled(true);   // بعد قفل الصفحة, تمكين قائمة نوع الزوجة
                        pageLocked = true;
                    }
                }

                //أرملة
                else if (i == 2) {
                    listHusbandStatuses = new String[]{"متوفى"};
                    if (pageLocked) {
                        ValidationController.UnlockThePage(view5);
                        pageLocked = false;
                    }
                }

                ArrayAdapter<String> adapter = new ArrayAdapter<>(view5.getContext(),
                        android.R.layout.simple_spinner_item, listHusbandStatuses);

                adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                spnHusbandStatuses.setAdapter(adapter);
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        spnHusbandStatuses.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                switch (adapterView.getSelectedItem().toString()) {

                    case "متوفى":
                    case "مفقود":
                        view5.findViewById(R.id.EventDate).setVisibility(View.VISIBLE);
                        view5.findViewById(R.id.Linear_Lockup).setVisibility(View.GONE);
                        view5.findViewById(R.id.Linear_Travel).setVisibility(View.GONE);

                        ValidationController.ClearView(view5.findViewById(R.id.Linear_Lockup));
                        ValidationController.ClearView(view5.findViewById(R.id.Linear_Travel));
                        break;

                    case "معتقل":
                        view5.findViewById(R.id.EventDate).setVisibility(View.GONE);
                        view5.findViewById(R.id.Linear_Lockup).setVisibility(View.VISIBLE);
                        view5.findViewById(R.id.Linear_Travel).setVisibility(View.GONE);

                        ((EditText)view5.findViewById(R.id.EventDate)).setText("");
                        ValidationController.ClearView(view5.findViewById(R.id.Linear_Travel));
                        break;

                    case "مسافر":
                        view5.findViewById(R.id.EventDate).setVisibility(View.GONE);
                        view5.findViewById(R.id.Linear_Lockup).setVisibility(View.GONE);
                        view5.findViewById(R.id.Linear_Travel).setVisibility(View.VISIBLE);

                        ((EditText)view5.findViewById(R.id.EventDate)).setText("");
                        ValidationController.ClearView(view5.findViewById(R.id.Linear_Lockup));
                        break;

                    default:
                        view5.findViewById(R.id.EventDate).setVisibility(View.GONE);
                        view5.findViewById(R.id.Linear_Lockup).setVisibility(View.GONE);
                        view5.findViewById(R.id.Linear_Travel).setVisibility(View.GONE);

                        ((EditText)view5.findViewById(R.id.EventDate)).setText("");
                        ValidationController.ClearView(view5.findViewById(R.id.Linear_Lockup));
                        ValidationController.ClearView(view5.findViewById(R.id.Linear_Travel));
                        break;
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });



        myCalendar = Calendar.getInstance();

        DatePickerDialog.OnDateSetListener date = (view, year, monthOfYear, dayOfMonth) -> {
            myCalendar.set(Calendar.YEAR, year);
            myCalendar.set(Calendar.MONTH, monthOfYear);
            myCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
            updateLabel(txtEventDate);
        };

        txtEventDate.setOnClickListener(v -> {
            new DatePickerDialog(view5.getContext(), date, myCalendar
                    .get(Calendar.YEAR), myCalendar.get(Calendar.MONTH),
                    myCalendar.get(Calendar.DAY_OF_MONTH)).show();

        });


        Spinner spnIfCondemnation = view5.findViewById(R.id.Ifcondemnation);
        spnIfCondemnation.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                //محكوم
                if (i == 0) {
                    view5.findViewById(R.id.ArrestDate).setVisibility(View.GONE);
                    view5.findViewById(R.id.CondemnationDuration).setVisibility(View.VISIBLE);

                    ((EditText)view5.findViewById(R.id.ArrestDate)).setText("");
                }
                //غير محكوم
                else if (i == 1) {
                    view5.findViewById(R.id.ArrestDate).setVisibility(View.VISIBLE);
                    view5.findViewById(R.id.CondemnationDuration).setVisibility(View.GONE);

                    ((EditText)view5.findViewById(R.id.CondemnationDuration)).setText("");
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        Constants.SQLITEDAL.fillSpinner(view5.getContext(), view5.findViewById(R.id.lst_IdentityTypes));

        //Load data from family info (في حالة حزمة إضافة لن يكون هناك إلا بيانات أولية)
            DBHelper.loadDataToControls(view5, Constants.familyInfo);

        return view5;
    }


    private void updateLabel(EditText txtDate) {
        String myFormat = "yyyy-MM-dd HH:mm";
        SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);
        txtDate.setText(sdf.format(myCalendar.getTime()));
    }
}