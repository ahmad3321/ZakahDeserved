package com.example.zakahdeserved.ChildFragment;

import static com.itextpdf.text.Annotation.FILE;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Base64;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.zakahdeserved.Connection.DBHelper;
import com.example.zakahdeserved.R;
import com.example.zakahdeserved.Utility.Constants;
import com.example.zakahdeserved.Utility.ValidationController;
import com.itextpdf.text.BadElementException;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Image;
import com.itextpdf.text.pdf.PdfWriter;

import java.io.ByteArrayOutputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.MalformedURLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;


public class Tab1 extends Fragment {

    public Tab1() {

    }

    Calendar myCalendar;
    Button btn_Image_Document, btn_Image_Document_delete;
    private static final int pic_id = 1;
    ArrayList<byte[]> ImagesByte = new ArrayList<>();
    Document document;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.activity_tab1, container, false);
        Constants.view1 = view;
        document = new Document();


        btn_Image_Document = view.findViewById(R.id.btn_Image_Document);
        btn_Image_Document_delete = view.findViewById(R.id.btn_Image_Document_delete);

        btn_Image_Document.setOnClickListener(view12 -> {
            Intent camera_intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
            startActivityForResult(camera_intent, pic_id);
        });

        btn_Image_Document_delete.setOnClickListener(view13 -> ImagesByte.clear());

        Spinner spnGender = view.findViewById(R.id.Gender);
        spnGender.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                ValidationController.ENABLE_FEMALE_TAB = i == 0;    //في حالة المستفيد أنثى
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        EditText txtExisitStatusAbout = view.findViewById(R.id.ExisitStatusAbout);

        Spinner spnExisitStatus = view.findViewById(R.id.ExisitStatus);
        spnExisitStatus.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                if (i == 0) {   //في حالة موجود
                    ValidationController.ENABLE_ALL_TABS = true;
                    txtExisitStatusAbout.setVisibility(View.GONE);
                    txtExisitStatusAbout.setText("");
                } else {    //في حالة غير موجود, غير معروف, عنوان خاطئ .....
                    ValidationController.ENABLE_ALL_TABS = false;
                    txtExisitStatusAbout.setVisibility(View.VISIBLE);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        Spinner lst_IdentityTypes = view.findViewById(R.id.lst_IdentityTypes);
        //Constants.SQLITEDAL.fillSpinner(view.getContext(), lst_IdentityTypes);


        EditText txtBirthDate = view.findViewById(R.id.BirthDate);

        myCalendar = Calendar.getInstance();

        DatePickerDialog.OnDateSetListener date = (view1, year, monthOfYear, dayOfMonth) -> {
            myCalendar.set(Calendar.YEAR, year);
            myCalendar.set(Calendar.MONTH, monthOfYear);
            myCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
            updateLabel(txtBirthDate);
        };

        txtBirthDate.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                new DatePickerDialog(getContext(), date, myCalendar
                        .get(Calendar.YEAR), myCalendar.get(Calendar.MONTH),
                        myCalendar.get(Calendar.DAY_OF_MONTH)).show();
            }
        });


        //Load data from family info (في حالة حزمة إضافة لن يكون هناك إلا بيانات أولية)
        DBHelper.loadDataToControls(view, Constants.familyInfo);

        return view;
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
                    }
                    break;
            }
        } catch (Exception ex) {
            Toast.makeText(getContext(), "", Toast.LENGTH_LONG).show();
        }
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