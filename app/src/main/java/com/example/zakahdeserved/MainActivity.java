package com.example.zakahdeserved;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.example.zakahdeserved.Connection.SQLiteDAL;
import com.google.android.material.tabs.TabLayout;

public class MainActivity extends AppCompatActivity {
    TextView btn_login ;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        SQLiteDAL sqLiteDAL = new SQLiteDAL(this);
        btn_login = (TextView) findViewById(R.id.btn_login);
    }
    public void onClick(View v) {
        Intent mainTabs = new Intent (this, MainTabs.class);
        this.startActivity(mainTabs);
    }
}