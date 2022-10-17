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
import com.example.zakahdeserved.Connection.PackageRecord;
import com.example.zakahdeserved.Connection.SQLiteDAL;
import com.example.zakahdeserved.Connection.SQLiteRecord;
import com.example.zakahdeserved.Connection.ShowRecord;
import com.example.zakahdeserved.Utility.Constants;

import java.io.IOException;
import java.security.GeneralSecurityException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

public class MainActivity extends AppCompatActivity {
    TextView login;
    EditText username, password;
    BroadCastClass broadCastClass = new BroadCastClass();
    int EmpDepartment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
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
        try {
            Constants.SHAREDPREFERENCES_KEY = new MasterKey.Builder(this, MasterKey.DEFAULT_MASTER_KEY_ALIAS)
                    .setKeyScheme(MasterKey.KeyScheme.AES256_GCM)
                    .build();
        } catch (GeneralSecurityException | IOException e) {
            e.printStackTrace();
        }

        Constants.SQLITEDAL = new SQLiteDAL(getApplicationContext());

        DBHelper.initDatabaseTables();

        BroadCastClass broadCastClass = new BroadCastClass();

        registerNetworkBroadcastForNougat();

        login = findViewById(R.id.btn_login);
        username = findViewById(R.id.edit_username);
        password = findViewById(R.id.edit_password);

        login.setOnClickListener(view -> {
//            Intent intent = new Intent(getApplicationContext(), MainTabs.class);
            //startActivity(intent);
            try {
                Boolean isSuccess = DAL.pdrUsernameTest(MainActivity.this, username.getText().toString(), password.getText().toString());
                if (isSuccess) {
                    EmpDepartment = DAL.getDepartment(username.getText().toString());
                    if (EmpDepartment == Constants.STATISTICAL_JOB_TITLE) {
                        Intent intent1 = new Intent(getApplicationContext(), PackageView.class);
                        intent1.putExtra("JobTitle", "احصائي"); //احصاء أو توزيع
                        startActivity(intent1);
                    } else if (EmpDepartment == Constants.DISTRIBUTION_JOcB_TITLE) {
                        Intent intent1 = new Intent(getApplicationContext(), PackageView.class);
                        intent1.putExtra("JobTitle", "توزيع"); //احصاء أو توزيع
                        startActivity(intent1);
                    } else {
                        Toast.makeText(getApplicationContext(), "المستخدم ليس له صلاحية في الدخول إلى التطبيق", Toast.LENGTH_SHORT).show();
                    }

                    // Encrypted Shared Preferences
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
                        myEdit.apply();
                    } catch (GeneralSecurityException | IOException e) {
                        e.printStackTrace();
                    }
                } else {
                    Toast.makeText(getApplicationContext(), "خطأ في اسم المستخدم أو كلمة المرور", Toast.LENGTH_SHORT).show();
                }
            } catch (Exception ex) {
                Toast.makeText(getApplicationContext(), "خطأ..." + ex, Toast.LENGTH_SHORT).show();
            }

        });

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