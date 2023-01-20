package com.example.zakahdeserved;

import androidx.appcompat.app.AppCompatActivity;
import androidx.security.crypto.EncryptedSharedPreferences;
import androidx.security.crypto.MasterKey;

import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.example.zakahdeserved.BroadCast.BroadCastClass;
import com.example.zakahdeserved.Connection.DAL;
import com.example.zakahdeserved.Connection.DBHelper;
import com.example.zakahdeserved.Connection.SQLiteDAL;

import com.example.zakahdeserved.Utility.Constants;
import com.example.zakahdeserved.Utility.ValidationController;

import java.io.IOException;
import java.security.GeneralSecurityException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;


public class MainActivity extends AppCompatActivity {
    TextView login;
    EditText username, password;
    String ExceptionQuery = "";
    BroadCastClass broadCastClass = new BroadCastClass();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //check if user is already logged in
        try {
            Constants.SHAREDPREFERENCES_KEY = new MasterKey.Builder(this, MasterKey.DEFAULT_MASTER_KEY_ALIAS)
                    .setKeyScheme(MasterKey.KeyScheme.AES256_GCM)
                    .build();

            SharedPreferences sharedPreferences = EncryptedSharedPreferences.create(
                    getApplicationContext(),
                    "MySharedPref",
                    Constants.SHAREDPREFERENCES_KEY, // masterKey created above
                    EncryptedSharedPreferences.PrefKeyEncryptionScheme.AES256_SIV,
                    EncryptedSharedPreferences.PrefValueEncryptionScheme.AES256_GCM);

            if (sharedPreferences.getBoolean("login", false)) {
                loginToActivity(sharedPreferences.getString("entered_date:" + sharedPreferences.getString("empCode", ""), ""), sharedPreferences.getInt("empDepartment", -1));
            }
        } catch (GeneralSecurityException | IOException e) {
            ValidationController.GetException(e.toString().replace("\"", ""), "", getApplicationContext() != null ? getApplicationContext().toString() : "", "Constants.SHAREDPREFERENCES_KEY");
            e.printStackTrace();
        }

        setContentView(R.layout.activity_main);

        String[] perms = {"android.permission.INTERNET",
                "android.permission.ACCESS_NETWORK_STATE",
                "android.permission.WRITE_EXTERNAL_STORAGE",
                "android.permission.MANAGE_EXTERNAL_STORAGE",
                "android.permission.READ_EXTERNAL_STORAGE"};
        int permsRequestCode = 200;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            requestPermissions(perms, permsRequestCode);
        }


        Constants.SQLITEDAL = new SQLiteDAL(getApplicationContext());

        DBHelper.initDatabaseTables();

        //BroadCastClass broadCastClass = new BroadCastClass();

        //registerNetworkBroadcastForNougat();

        login = findViewById(R.id.btn_login);
        username = findViewById(R.id.edit_username);
        password = findViewById(R.id.edit_password);
        Constants.SQLITEDAL.clearQueries();
        login.setOnClickListener(view -> {
            try {
                Boolean isSuccess = DAL.pdrUsernameTest(MainActivity.this, username.getText().toString(), password.getText().toString());
                if (isSuccess) {
                    int empDepartment = DAL.getDepartment(username.getText().toString());

                    try {
                        SharedPreferences sharedPreferences = EncryptedSharedPreferences.create(
                                getApplicationContext(),
                                "MySharedPref",
                                Constants.SHAREDPREFERENCES_KEY, // masterKey created above
                                EncryptedSharedPreferences.PrefKeyEncryptionScheme.AES256_SIV,
                                EncryptedSharedPreferences.PrefValueEncryptionScheme.AES256_GCM);
                        SharedPreferences.Editor myEdit = sharedPreferences.edit();
                        // Storing the key and its value as the data fetched from edittext
                        myEdit.putString("empCode", username.getText().toString());
                        myEdit.putBoolean("login", true);
                        myEdit.putInt("empDepartment", empDepartment);
                        myEdit.apply();

                        loginToActivity(sharedPreferences.getString("entered_date:" + sharedPreferences.getString("empCode", ""), ""), empDepartment);

                    } catch (GeneralSecurityException | IOException e) {

                        e.printStackTrace();
                    }
                } else {
                    Toast.makeText(getApplicationContext(), "خطأ في اسم المستخدم أو كلمة المرور", Toast.LENGTH_SHORT).show();
                }
            } catch (Exception ex) {
                ValidationController.GetException(ex.toString().replace("\"", ""), "", getApplicationContext().toString(), "");
                Toast.makeText(getApplicationContext(), "خطأ..." + ex, Toast.LENGTH_SHORT).show();
            }

        });
    }

    void loginToActivity(String lastEnterDate, int EmpDepartment) throws GeneralSecurityException, IOException {
        Date c = Calendar.getInstance().getTime();
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());
        String autoDate = df.format(c);
        boolean isEntered = autoDate.equals(lastEnterDate);

        //if not entered check entry from server
        //this could be done if employee entered from a device and tried to enter from another one
        if (!isEntered) {
            SharedPreferences sharedPreferences = EncryptedSharedPreferences.create(
                    getApplicationContext(),
                    "MySharedPref",
                    Constants.SHAREDPREFERENCES_KEY, // masterKey created above
                    EncryptedSharedPreferences.PrefKeyEncryptionScheme.AES256_SIV,
                    EncryptedSharedPreferences.PrefValueEncryptionScheme.AES256_GCM);

            lastEnterDate = DAL.getMaxID("daily_staff_entries", "AutomaticVisitDate", sharedPreferences.getString("empCode", ""));
            isEntered = autoDate.equals(lastEnterDate);
        }

        Intent intent1;

        if (isEntered)
            intent1 = new Intent(getApplicationContext(), PackageView.class);
        else
            intent1 = new Intent(getApplicationContext(), actdelayentrystatisticalActivity.class);

        if (EmpDepartment == Constants.STATISTICAL_JOB_TITLE)
            intent1.putExtra("JobTitle", "احصائي"); //احصاء أو توزيع
        else if (EmpDepartment == Constants.DISTRIBUTION_JOcB_TITLE)
            intent1.putExtra("JobTitle", "توزيع"); //احصاء أو توزيع
        else {
            Toast.makeText(getApplicationContext(), "المستخدم ليس له صلاحية في الدخول إلى التطبيق", Toast.LENGTH_SHORT).show();
            return;
        }
        startActivity(intent1);
    }

    private void registerNetworkBroadcastForNougat() {
        try {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                registerReceiver(broadCastClass, new IntentFilter(ConnectivityManager.CONNECTIVITY_ACTION));
            }
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                registerReceiver(broadCastClass, new IntentFilter(ConnectivityManager.CONNECTIVITY_ACTION));
            }
        } catch (Exception ignored) {
        }
    }

    protected void unregisterNetworkChanges() {
        try {
            unregisterReceiver(broadCastClass);
        } catch (IllegalArgumentException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        unregisterNetworkChanges();
    }


}