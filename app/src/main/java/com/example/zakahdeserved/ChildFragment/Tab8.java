package com.example.zakahdeserved.ChildFragment;

import android.annotation.SuppressLint;
import android.app.DatePickerDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.provider.MediaStore;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Base64;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.zakahdeserved.Connection.DBHelper;
import com.example.zakahdeserved.Connection.SQLiteRecord;
import com.example.zakahdeserved.R;
import com.example.zakahdeserved.Utility.Constants;
import com.example.zakahdeserved.Utility.ValidationController;
import com.itextpdf.text.Document;
import com.itextpdf.text.Image;
import com.itextpdf.text.pdf.PdfWriter;

import java.io.ByteArrayOutputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Locale;
import java.util.Objects;

public class Tab8 extends Fragment implements View.OnClickListener {

    LinearLayout layoutWife, layout_list_Wifes_HealthStatus;
    Button buttonAdd;
    private static final int pic_id = 1;

    Document document;
    Calendar myCalendar;
    EditText txtFenmaleCount, txtMaleCount, txtAllCount;

    // this variable for binding the identityFile with its Number to know for whose person this file
    // and store the images data wile taking photos
    HashMap<String, ArrayList<byte[]>> personId_ImagesByte = new HashMap<>();
    String tempPersonID = "";
    //    ArrayList<String> lst_identityNumber = new ArrayList<>();
//    ArrayList<ArrayList<byte[]>> lst_ImagesByte = new ArrayList<>();
//    int tagForperson = 0;

    public Tab8() {
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.activity_tab8, container, false);

        Constants.view8 = view;
        document = new Document();

        txtAllCount = view.findViewById(R.id.FinalCount);
        txtFenmaleCount = view.findViewById(R.id.FeMaleCount);
        txtMaleCount = view.findViewById(R.id.MaleCount);

        buttonAdd = view.findViewById(R.id.button_add_Wifes);

        layoutWife = view.findViewById(R.id.layout_list_Wifes);


        buttonAdd.setOnClickListener(this);


        //Load data from family info (في حالة حزمة إضافة لن يكون هناك إلا بيانات أولية)
        //load persons
        ArrayList<SQLiteRecord> records = Constants.SQLITEDAL.getRecords("persons", DBHelper.PersonsColumns, "ZakatID", Constants.ZakatID);
        //لا نعرض رب الأسرة بين السجلات
        records.removeIf(myObject -> Objects.requireNonNull(myObject.getRecord().get("PersonID")).toString().endsWith("0"));

        for (SQLiteRecord record : records) {
            View v = addPersonView(R.layout.row_add_wifes, R.id.image_remove_Wife, layoutWife, false);
            DBHelper.loadDataToControls(v, record);

            //load health_statuses
            ArrayList<SQLiteRecord> health_statusesrecords = Constants.SQLITEDAL.getRecords("health_statuses", DBHelper.Helth_StatusesColumns, "PersonID", Objects.requireNonNull(record.getRecord().get("PersonID")).toString());
            for (SQLiteRecord health_statusesrecord : health_statusesrecords) {
                View health_statusesv = addHelthStatusView();
                DBHelper.loadDataToControls(health_statusesv, health_statusesrecord);
            }
        }

        return view;
    }

    public void onClick(View v) {

        switch (v.getId()) {

            case R.id.button_add_Wifes:

                addPersonView(R.layout.row_add_wifes, R.id.image_remove_Wife, layoutWife, true);

                break;
        }
    }

    @SuppressLint("SetTextI18n")
    private View addPersonView(int id, int imageID, LinearLayout linearLayout, boolean enableDeleteView) {

        final View WifeView = getLayoutInflater().inflate(id, null, false);
        Constants.allMembersCount++;

        ImageView imageClose = WifeView.findViewById(imageID);
        Button button_add_WifesHealthStatus = WifeView.findViewById(R.id.button_add_WifesHealthStatus);
        layout_list_Wifes_HealthStatus = WifeView.findViewById(R.id.layout_list_Wifes_HealthStatus);
        Button btn_Image_Document_Person = WifeView.findViewById(R.id.btn_Image_Document_Person);
        Button btn_Image_Document_Person_delete = WifeView.findViewById(R.id.btn_Image_Document_Person_delete);
        EditText txtPersonId = WifeView.findViewById(R.id.PersonID);

        //set person id
        txtPersonId.setText(Constants.ZakatID + "-" + Constants.IncrementPersonID++);

        ((EditText)WifeView.findViewById(R.id.IdentityNumber)).setText(Constants.Idintity_Number);

        //coin conversion
        EditText txtMonthlyIncome = WifeView.findViewById(R.id.MonthlyIncome);
        ((Spinner) WifeView.findViewById(R.id.CoinType)).setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                if (i == 0 && txtMonthlyIncome.getText().toString().length() > 0)    //tr
                {
                    txtMonthlyIncome.setText(String.valueOf(Double.parseDouble(txtMonthlyIncome.getText().toString()) * Constants.DollarPrise));
                    txtMonthlyIncome.setEnabled(false);
                } else
                    txtMonthlyIncome.setEnabled(true);
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });


        // this for persons already added, can't delete them.
        if (!enableDeleteView)
            imageClose.setVisibility(View.GONE);

        btn_Image_Document_Person.setOnClickListener(view1 -> {
            tempPersonID = txtPersonId.getText().toString();

            Intent camera_intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
            startActivityForResult(camera_intent, pic_id);
        });

        btn_Image_Document_Person_delete.setOnClickListener(view -> Objects.requireNonNull(personId_ImagesByte.get(txtPersonId.getText().toString())).clear());


        imageClose.setOnClickListener(v -> {
            Constants.allMembersCount--;

            //remove the pdf and the images associated to this person
            personId_ImagesByte.remove(txtPersonId.getText().toString());
            Constants.imagesFiles.remove(txtPersonId.getText().toString());

            if (((Spinner) WifeView.findViewById(R.id.Gender)).getSelectedItemId() == 0)  //أنثى
                Constants.femaleCount--;
            else
                Constants.maleCount--;
            removeView(WifeView, linearLayout);

            refreshFamilyMembersCount();
        });

        linearLayout.addView(WifeView);

        button_add_WifesHealthStatus.setOnClickListener(v -> addHelthStatusView());

        Spinner spnIsWorking = WifeView.findViewById(R.id.IsWorking);
        spnIsWorking.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                if (i == 0) {   //تعمل
                    WifeView.findViewById(R.id.MonthlyIncome).setVisibility(View.VISIBLE);
                    WifeView.findViewById(R.id.CoinType).setVisibility(View.VISIBLE);
                } else {   //لاتعمل
                    WifeView.findViewById(R.id.MonthlyIncome).setVisibility(View.GONE);
                    WifeView.findViewById(R.id.CoinType).setVisibility(View.GONE);

                    ((EditText) WifeView.findViewById(R.id.MonthlyIncome)).setText("");
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        Spinner spnWhoIs = WifeView.findViewById(R.id.WhoIs);
        spnWhoIs.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                if (i == 0) {   //زوجة
                    WifeView.findViewById(R.id.Relation).setVisibility(View.GONE);
                    ((EditText) WifeView.findViewById(R.id.Relation)).setText("");
                    ((Spinner) WifeView.findViewById(R.id.Gender)).setSelection(0);
                    WifeView.findViewById(R.id.Gender).setEnabled(false);

                } else if (i == 1) { //ابن
                    WifeView.findViewById(R.id.Relation).setVisibility(View.GONE);
                    ((EditText) WifeView.findViewById(R.id.Relation)).setText("");
                    WifeView.findViewById(R.id.Gender).setEnabled(true);
                } else {
                    WifeView.findViewById(R.id.Relation).setVisibility(View.VISIBLE);
                    WifeView.findViewById(R.id.Gender).setEnabled(true);
                }

            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        Spinner Gender = WifeView.findViewById(R.id.Gender);
        Gender.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                if (i == 0)    //أنثى
                {
                    if (Constants.allMembersCount == Constants.femaleCount + Constants.maleCount)
                        Constants.maleCount--;
                    Constants.femaleCount++;
                } else {   //ذكر
                    if (Constants.allMembersCount == Constants.femaleCount + Constants.maleCount)
                        Constants.femaleCount--;
                    Constants.maleCount++;
                }
                refreshFamilyMembersCount();
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        EditText txtBirthDate = WifeView.findViewById(R.id.BirthDate);

        myCalendar = Calendar.getInstance();

        DatePickerDialog.OnDateSetListener date = (view1, year, monthOfYear, dayOfMonth) -> {
            myCalendar.set(Calendar.YEAR, year);
            myCalendar.set(Calendar.MONTH, monthOfYear);
            myCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
            updateLabel(txtBirthDate);
        };

        txtBirthDate.setOnClickListener(v -> {
            new DatePickerDialog(getContext(), date, myCalendar
                    .get(Calendar.YEAR), myCalendar.get(Calendar.MONTH),
                    myCalendar.get(Calendar.DAY_OF_MONTH)).show();
        });


        Constants.SQLITEDAL.fillSpinner(getContext(), WifeView.findViewById(R.id.lst_IdentityTypes));

        return WifeView;
    }

    private void removeView(View view, LinearLayout linear) {
        linear.removeView(view);
    }

    public void refreshFamilyMembersCount() {
        txtFenmaleCount.setText(String.valueOf(Constants.femaleCount));
        txtMaleCount.setText(String.valueOf(Constants.maleCount));
        txtAllCount.setText(String.valueOf(Constants.allMembersCount));
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        try {
            if (requestCode == pic_id) {
                if (data != null) {
                    Bitmap bitmap = (Bitmap) data.getExtras().get("data");
                    ByteArrayOutputStream stream = new ByteArrayOutputStream();
                    bitmap.compress(Bitmap.CompressFormat.PNG, 90, stream); //compress to which format you want.
                    byte[] byte_arr = stream.toByteArray();

                    if (personId_ImagesByte.get(tempPersonID) != null)
                        personId_ImagesByte.get(tempPersonID).add(byte_arr);
                    else    // set a new arraylist if no arraylist founded
                        personId_ImagesByte.put(tempPersonID, new ArrayList<>() {{
                            add(byte_arr);
                        }});

                    Constants.imagesFiles.put(tempPersonID, ConvertImagesToPdf());
                }
            }
        } catch (Exception ex) {
            Toast.makeText(getContext(), "", Toast.LENGTH_LONG).show();
        }
    }

    private View addHelthStatusView() {

        final View healthstatusview = getLayoutInflater().inflate(R.layout.row_add_healthstatus, null, false);
        healthstatusview.findViewById(R.id.HealthStatusEvaluation).setEnabled(false);
        healthstatusview.findViewById(R.id.HealthStatusType).setEnabled(false);
        healthstatusview.findViewById(R.id.HealthStatusDescription).setEnabled(false);
        healthstatusview.findViewById(R.id.MonthlyCost).setEnabled(false);
        healthstatusview.findViewById(R.id.CoinType).setEnabled(false);
        ImageView imageClose = healthstatusview.findViewById(R.id.image_remove);

        //coin conversion
        EditText txtMonthlyCost = healthstatusview.findViewById(R.id.MonthlyCost);
        ((Spinner) healthstatusview.findViewById(R.id.CoinType)).setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                if (!((Spinner) healthstatusview.findViewById(R.id.HealthStatus)).getSelectedItem().equals("جيد")) {
                    if (i == 0 && txtMonthlyCost.getText().toString().length() > 0)    //tr
                    {
                        txtMonthlyCost.setText(String.valueOf(Double.parseDouble(txtMonthlyCost.getText().toString()) * Constants.DollarPrise));
                        txtMonthlyCost.setEnabled(false);
                    } else
                        txtMonthlyCost.setEnabled(true);
                }
            }
            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
        Spinner spnHealthStatus = healthstatusview.findViewById(R.id.HealthStatus);
        spnHealthStatus.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                if (i == 0) {   // good
                    //spnHealthStatus.findViewById(R.id.Relation).setV isibility(View.GONE);
                    healthstatusview.findViewById(R.id.HealthStatusEvaluation).setEnabled(false);
                    healthstatusview.findViewById(R.id.HealthStatusType).setEnabled(false);
                    healthstatusview.findViewById(R.id.HealthStatusDescription).setEnabled(false);
                    healthstatusview.findViewById(R.id.MonthlyCost).setEnabled(false);
                    ValidationController.ClearView(healthstatusview);
                } else if (i == 1) {
                    healthstatusview.findViewById(R.id.HealthStatusEvaluation).setEnabled(true);
                    healthstatusview.findViewById(R.id.HealthStatusType).setEnabled(false);
                    healthstatusview.findViewById(R.id.HealthStatusDescription).setEnabled(true);
                    healthstatusview.findViewById(R.id.MonthlyCost).setEnabled(true);
                    healthstatusview.findViewById(R.id.CoinType).setEnabled(true);
                } else if (i == 2) {
                    healthstatusview.findViewById(R.id.HealthStatusEvaluation).setEnabled(true);
                    healthstatusview.findViewById(R.id.HealthStatusType).setEnabled(true);
                    healthstatusview.findViewById(R.id.HealthStatusDescription).setEnabled(true);
                    healthstatusview.findViewById(R.id.MonthlyCost).setEnabled(true);
                    healthstatusview.findViewById(R.id.CoinType).setEnabled(true);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        imageClose.setOnClickListener(v -> removeView(healthstatusview));

        layout_list_Wifes_HealthStatus.addView(healthstatusview);
        return healthstatusview;
    }

    private void removeView(View view) {

        layout_list_Wifes_HealthStatus.removeView(view);

    }

    private void updateLabel(EditText txtDate) {
        String myFormat = "yyyy-MM-dd HH:mm";
        SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);
        txtDate.setText(sdf.format(myCalendar.getTime()));
    }

    public String ConvertImagesToPdf() {
        Image image1;
        ByteArrayOutputStream baos;
        String pdfString = "";
        try {
            for (int i = 0; i < personId_ImagesByte.get(tempPersonID).size(); i++) {
                baos = new ByteArrayOutputStream();
                PdfWriter pdfWriter = PdfWriter.getInstance(document, baos);
                image1 = Image.getInstance(personId_ImagesByte.get(tempPersonID).get(i));
                document.open();
                document.add(image1);
                document.close();
                pdfWriter.flush();
                byte[] pdfByteArray = baos.toByteArray();
                pdfString = Base64.encodeToString(pdfByteArray, Base64.DEFAULT);
            }
        } catch (Exception ex) {
            Toast.makeText(getContext(), ex.toString(), Toast.LENGTH_LONG).show();
        }
        return pdfString;
    }
}