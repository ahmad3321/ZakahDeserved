package com.example.zakahdeserved.ChildFragment;

import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;

import androidx.fragment.app.Fragment;

import com.example.zakahdeserved.R;

public class PackageView extends Fragment implements View.OnClickListener {

    LinearLayout layoutAsset;
    Button buttonAdd,buttonAddIncome,buttonAddAids;
    Button buttonSubmitList;

    public PackageView() {
        // Required empty public constructor
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.packageview, container, false);

        buttonSubmitList = view.findViewById(R.id.button_submit_list_Asset);

        layoutAsset = view.findViewById(R.id.layout_list_Asset);


        buttonAdd.setOnClickListener(this);
        buttonSubmitList.setOnClickListener(this);

        return view;
    }

    @Override
    public void onClick(View view) {

    }
}