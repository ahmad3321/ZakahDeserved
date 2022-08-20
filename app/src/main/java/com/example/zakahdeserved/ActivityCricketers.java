package com.example.zakahdeserved;

import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.zakahdeserved.Models.HealthStatuses;

import java.util.ArrayList;

public class ActivityCricketers extends AppCompatActivity {

    RecyclerView recyclerHealthStatuses;
    ArrayList<HealthStatuses> HealthStatusesList = new ArrayList<>();
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_healthstatuses);

        recyclerHealthStatuses = findViewById(R.id.recycler_healthstatus);

        LinearLayoutManager layoutManager = new LinearLayoutManager(this,RecyclerView.VERTICAL,false);
        recyclerHealthStatuses.setLayoutManager(layoutManager);

        HealthStatusesList = (ArrayList<HealthStatuses>) getIntent().getExtras().getSerializable("list");

        recyclerHealthStatuses.setAdapter(new HealthStatusesAdapter(HealthStatusesList));

    }
}
