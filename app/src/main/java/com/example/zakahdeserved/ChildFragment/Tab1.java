package com.example.zakahdeserved.ChildFragment;

import static android.app.Activity.RESULT_OK;

import android.app.DatePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
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
import android.widget.Spinner;
import android.widget.Toast;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.ActivityResultRegistry;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.Nullable;
import androidx.core.content.FileProvider;
import androidx.fragment.app.Fragment;

import com.example.zakahdeserved.Connection.DBHelper;
import com.example.zakahdeserved.R;
import com.example.zakahdeserved.Utility.Constants;
import com.example.zakahdeserved.Utility.ValidationController;
import com.itextpdf.text.Document;
import com.itextpdf.text.Image;
import com.itextpdf.text.pdf.PdfWriter;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Locale;


public class Tab1 extends Fragment {

    public Tab1() {

    }

    Calendar myCalendar;
    Button btn_Image_Document, btn_Image_Document_delete;
    private static final int pic_id = 1;
    ArrayList<byte[]> ImagesByte = new ArrayList<>();
    Document document;
    Uri imageUri;
    String mCurrentPhotoPath;
    // this variable for binding the identityFile with its Number to know for whose person this file
    String identityNumber = "";

    //

    //
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
        EditText txtIdentityNumber = view.findViewById(R.id.IdentityNumber);

        txtIdentityNumber.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                if (txtIdentityNumber.getText().toString().equals("")) {
                    Toast.makeText(getContext(), "لا يمكن أن يكون حقل رقم الوثيقة فارغا", Toast.LENGTH_SHORT).show();
                    txtIdentityNumber.setText(identityNumber);
                    return;
                }

                //store the previous identity number
                String oldidentityNumber = identityNumber;

                //Update the key to new key
                identityNumber = txtIdentityNumber.getText().toString();

                if (Constants.imagesFiles.containsKey(identityNumber))
                    Constants.imagesFiles.put(identityNumber, "");

                //if pictures have taken and stored in hashmap
                if (ImagesByte.size() > 0) {
                    String value = Constants.imagesFiles.get(oldidentityNumber);
                    Constants.imagesFiles.remove(oldidentityNumber);
                    Constants.imagesFiles.put(identityNumber, value);
                }
            }
        });
        btn_Image_Document.setOnClickListener(view12 -> {
            identityNumber = txtIdentityNumber.getText().toString();
            if (identityNumber.equals("")) {
                Toast.makeText(getContext(), "الرجاء إدخال رقم الوثيقة قبل تصوير الوثيقة", Toast.LENGTH_LONG).show();
                return;
            }
            AddImage(getContext());
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
        Constants.SQLITEDAL.fillSpinner(view.getContext(), lst_IdentityTypes);

        EditText txtBirthDate = view.findViewById(R.id.BirthDate);

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


        //Load data from family info (في حالة حزمة إضافة لن يكون هناك إلا بيانات أولية)
        DBHelper.loadDataToControls(view, Constants.familyInfo);

        return view;
    }

    public void AddImage(Context context) {
        try {
            String filename = Constants.ZakatID;
            File Dir = context.getExternalFilesDir(Environment.DIRECTORY_PICTURES);

            File Imagefile = File.createTempFile(filename, ".jpg", Dir);
            mCurrentPhotoPath = Imagefile.getAbsolutePath();
            Intent camera_intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
            imageUri = FileProvider.getUriForFile(context, "com.example.zakahdeserved.FileProvider", Imagefile);
            camera_intent.putExtra(MediaStore.EXTRA_OUTPUT, imageUri);
            startActivityForResult(camera_intent, pic_id);
        } catch (Exception ex) {
            ex.printStackTrace();
            Toast.makeText(context, ex.toString(), Toast.LENGTH_LONG).show();
        }
    }
    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        try {
            switch (requestCode) {
                case pic_id:
                if(requestCode==1 && resultCode==RESULT_OK){
                    BitmapFactory.Options options = new BitmapFactory.Options();
                    options.inSampleSize=8; //4, 8, etc. the more value, the worst quality of image

                    Bitmap photo=BitmapFactory.decodeFile(mCurrentPhotoPath, options);
                    //Bitmap photo = BitmapFactory.decodeFile(mCurrentPhotoPath);
                    ByteArrayOutputStream stream = new ByteArrayOutputStream();
                    photo.compress(Bitmap.CompressFormat.PNG, 90, stream);
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