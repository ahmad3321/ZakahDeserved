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
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.example.zakahdeserved.BroadCast.BroadCastClass;
import com.example.zakahdeserved.Connection.DAL;
import com.example.zakahdeserved.Connection.SQLiteDAL;
import com.example.zakahdeserved.Utility.Constants;

import java.io.IOException;
import java.security.GeneralSecurityException;
import java.util.ArrayList;
import java.util.HashMap;

public class MainActivity extends AppCompatActivity {
    TextView login;
    EditText username, password;
    BroadCastClass broadCastClass = new BroadCastClass();
    ImageButton btn_Sync;

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
        requestPermissions(perms, permsRequestCode);

        try {
            Constants.SHAREDPREFERENCES_KEY = new MasterKey.Builder(this, MasterKey.DEFAULT_MASTER_KEY_ALIAS)
                    .setKeyScheme(MasterKey.KeyScheme.AES256_GCM)
                    .build();
        } catch (GeneralSecurityException | IOException e) {
            e.printStackTrace();
        }

        Constants.SQLITEDAL = new SQLiteDAL(getApplicationContext());

        BroadCastClass broadCastClass = new BroadCastClass();

        registerNetworkBroadcastForNougat();

        login = findViewById(R.id.btn_login);
        username = findViewById(R.id.edit_username);
        password = findViewById(R.id.edit_password);
        btn_Sync = findViewById(R.id.btn_Sync);

        login.setOnClickListener(view -> {
            Intent intent = new Intent(getApplicationContext(), SQLiteTest.class);
            startActivity(intent);
//            try {
//                Boolean isSuccess = DAL.pdrUsernameTest(MainActivity.this, username.getText().toString(), password.getText().toString());
//                if (isSuccess) {
//                    EmpDepartment = DAL.getDepartment(username.getText().toString());
//                    if (EmpDepartment == Constants.STATISTICAL_JOB_TITLE) {
//                        Intent intent = new Intent(getApplicationContext(), actdelayentrystatisticalActivity.class);
//                        startActivity(intent);
//                    } else if (EmpDepartment == Constants.DISTRIBUTION_JOB_TITLE) {
//
//                    } else {
//                        Toast.makeText(getApplicationContext(), "المستخدم ليس له صلاحية في الدخول إلى التطبيق", Toast.LENGTH_SHORT).show();
//                    }
//
//                    // Encrypted Shared Preferences
//                    try {
//                        SharedPreferences sharedPreferences = EncryptedSharedPreferences.create(
//                                getApplicationContext(),
//                                "MySharedPref",
//                                Constants.SHAREDPREFERENCES_KEY, // masterKey created above
//                                EncryptedSharedPreferences.PrefKeyEncryptionScheme.AES256_SIV,
//                                EncryptedSharedPreferences.PrefValueEncryptionScheme.AES256_GCM);
//
//                        SharedPreferences.Editor myEdit = sharedPreferences.edit();
//                        // Storing the key and its value as the data fetched from edittext
//                        myEdit.putString("username", username.getText().toString());
//                        myEdit.apply();
//                    } catch (GeneralSecurityException | IOException e) {
//                        e.printStackTrace();
//                    }
//                } else {
//                    Toast.makeText(getApplicationContext(), "خطأ في اسم المستخدم أو كلمة المرور", Toast.LENGTH_SHORT).show();
//                }
//            }
//
//            catch (Exception ex)
//            {
//                Toast.makeText(getApplicationContext(), "خطأ..." +ex, Toast.LENGTH_SHORT).show();
//            }

        });

        btn_Sync.setOnClickListener(view -> {
            try {
                new TestAsync().execute();
            } catch (Exception ex) {
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

    class TestAsync extends AsyncTask<Void, Integer, String> {
        String TAG = getClass().getSimpleName();

        protected void onPreExecute() {
            super.onPreExecute();
            Toast.makeText(MainActivity.this, "بدأت عملية المزامنة .. انتظر قليلا", Toast.LENGTH_SHORT).show();
            Log.d(TAG + " PreExceute", "On pre Exceute......");
        }

        protected String doInBackground(Void... arg0) {
            try {
                Constants.SQLITEDAL.clearSpinners();
                for (String spinner : Constants.dynamisLists) {
                    HashMap<String, String> spinnerItems = DAL.getSpinnerItems(spinner);
                    Constants.SQLITEDAL.addSpinner(spinner, spinnerItems);
                }
                return "true";
            } catch (Exception ex) {
                return "false";
            }

        }

        protected void onProgressUpdate(Integer... a) {
            super.onProgressUpdate(a);
            Log.d(TAG + " onProgressUpdate", "You are in progress update ... " + a[0]);
        }

        protected void onPostExecute(String result) {
            super.onPostExecute(result);
            if (result.equalsIgnoreCase("true")) {
                Toast.makeText(MainActivity.this, "تمت عملية المزامنة بنجاح", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(MainActivity.this, "لم تتم عملية المزامنة", Toast.LENGTH_SHORT).show();
            }
            Log.d(TAG + " onPostExecute", "" + result);
        }
    }

}