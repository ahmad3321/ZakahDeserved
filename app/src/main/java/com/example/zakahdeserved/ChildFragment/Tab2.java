package com.example.zakahdeserved.ChildFragment;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;

import androidx.fragment.app.Fragment;

import com.example.zakahdeserved.Connection.DBHelper;
import com.example.zakahdeserved.R;
import com.example.zakahdeserved.Utility.Constants;
import com.example.zakahdeserved.Utility.ValidationController;

import java.util.Objects;

public class Tab2 extends Fragment {

    public Tab2() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.activity_tab2, container, false);
        Constants.view2 = view;


        CheckBox chkIfSmokers = view.findViewById(R.id.IfSmokers);
        chkIfSmokers.setOnCheckedChangeListener((compoundButton, b) -> {
            if (b)
                view.findViewById(R.id.SmokersCount).setVisibility(View.VISIBLE);
            else {
                view.findViewById(R.id.SmokersCount).setVisibility(View.GONE);
                ((EditText) view.findViewById(R.id.SmokersCount)).setText("");
            }
        });


        //Load data from family info (في حالة حزمة إضافة لن يكون هناك إلا بيانات أولية)
        DBHelper.loadDataToControls(view, Constants.familyInfo);

        return view;
    }

}