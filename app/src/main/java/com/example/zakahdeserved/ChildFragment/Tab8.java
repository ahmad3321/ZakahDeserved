package com.example.zakahdeserved.ChildFragment;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.provider.MediaStore;
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
import com.itextpdf.text.Document;
import com.itextpdf.text.Image;
import com.itextpdf.text.pdf.PdfWriter;

import java.io.ByteArrayOutputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Iterator;
import java.util.Locale;
import java.util.Objects;

public class Tab8 extends Fragment implements View.OnClickListener {

    LinearLayout layoutWife, layout_list_Wifes_HealthStatus;
    Button buttonAdd, button_add_WifesHealthStatus;
    private static final int pic_id = 1;
    ArrayList<byte[]> ImagesByte = new ArrayList<>();
    Document document;
    Calendar myCalendar;
    EditText txtFenmaleCount, txtMaleCount, txtAllCount;

    // this variable for binding the identityFile with its Number to know for whose person this file
    String identityNumber = "";

    int femaleCount = 0, maleCount = 0, allMembersCount = 0;

    public Tab8() {
        // Required empty public constructor
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
//        DBHelper.loadDataToControls(view, Constants.familyInfo);
        //load persons
        ArrayList<SQLiteRecord> records = Constants.SQLITEDAL.getRecords("persons", DBHelper.PersonsColumns, "ZakatID", Constants.ZakatID);
        //لا نعرض رب الأسرة بين السجلات
        records.removeIf(myObject -> Objects.requireNonNull(myObject.getRecord().get("PersonID")).toString().endsWith("0"));

        for (SQLiteRecord record : records) {
            View v = addPersonView(R.layout.row_add_wifes, R.id.image_remove_Wife, layoutWife);
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

                addPersonView(R.layout.row_add_wifes, R.id.image_remove_Wife, layoutWife);

                break;
        }
    }

    private View addPersonView(int id, int imageID, LinearLayout linearLayout) {

        final View WifeView = getLayoutInflater().inflate(id, null, false);
        allMembersCount++;

        ImageView imageClose = WifeView.findViewById(imageID);
        Button button_add_WifesHealthStatus = WifeView.findViewById(R.id.button_add_WifesHealthStatus);
        layout_list_Wifes_HealthStatus = WifeView.findViewById(R.id.layout_list_Wifes_HealthStatus);
        Button btn_Image_Document_Person = WifeView.findViewById(R.id.btn_Image_Document_Person);
        Button btn_Image_Document_Person_delete = WifeView.findViewById(R.id.btn_Image_Document_Person_delete);
        EditText txtIdentityNumber = WifeView.findViewById(R.id.IdentityNumber);

        btn_Image_Document_Person.setOnClickListener(view1 -> {
            identityNumber = txtIdentityNumber.getText().toString();
            if (identityNumber.equals("")) {
                Toast.makeText(getContext(), "الرجاء إدخال رقم الوثيقة قبل تصوير الوثيقة", Toast.LENGTH_LONG).show();
                return;
            }

            Intent camera_intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
            startActivityForResult(camera_intent, pic_id);
        });

        btn_Image_Document_Person_delete.setOnClickListener(view -> ImagesByte.clear());


        imageClose.setOnClickListener(v -> {
            allMembersCount--;
            if (((Spinner) WifeView.findViewById(R.id.Gender)).getSelectedItemId() == 0)  //أنثى
                femaleCount--;
            else
                maleCount--;
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
                if (i == 0 || i == 1) {   //زوجة أو ابن
                    WifeView.findViewById(R.id.Relation).setVisibility(View.GONE);
                    ((EditText) WifeView.findViewById(R.id.Relation)).setText("");
                } else
                    WifeView.findViewById(R.id.Relation).setVisibility(View.VISIBLE);
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
                    if (allMembersCount == femaleCount + maleCount)
                        maleCount--;
                    femaleCount++;
                } else {   //ذكر
                    if (allMembersCount == femaleCount + maleCount)
                        femaleCount--;
                    maleCount++;
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

    void refreshFamilyMembersCount() {
        txtFenmaleCount.setText(String.valueOf(femaleCount));
        txtMaleCount.setText(String.valueOf(maleCount));
        txtAllCount.setText(String.valueOf(allMembersCount));
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        try {
            switch (requestCode) {
                case pic_id:
                    if (data != null) {
                        Bitmap bitmap = (Bitmap) data.getExtras().get("data");
                        ByteArrayOutputStream stream = new ByteArrayOutputStream();
                        bitmap.compress(Bitmap.CompressFormat.PNG, 90, stream); //compress to which format you want.
                        byte[] byte_arr = stream.toByteArray();
                        ImagesByte.add(byte_arr);
                        Constants.imagesFiles.put(identityNumber, ConvertImagesToPdf());
                    }
                    break;
            }
        } catch (Exception ex) {
            Toast.makeText(getContext(), "", Toast.LENGTH_LONG).show();
        }
    }

    private View addHelthStatusView() {

        final View healthstatusview = getLayoutInflater().inflate(R.layout.row_add_healthstatus, null, false);

        ImageView imageClose = healthstatusview.findViewById(R.id.image_remove);


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
        ByteArrayOutputStream baos = null;
        String pdfString = "";
        try {
            for (int i = 0; i < ImagesByte.size(); i++) {
                baos = new ByteArrayOutputStream();
                PdfWriter pdfWriter = PdfWriter.getInstance(document, baos);
                image1 = Image.getInstance(ImagesByte.get(i));
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