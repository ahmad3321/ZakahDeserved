package com.example.zakahdeserved.ChildFragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;

import androidx.fragment.app.Fragment;

import com.example.zakahdeserved.R;
import com.example.zakahdeserved.Utility.Constants;


public class Programms_Tab2Fidaa extends Fragment implements View.OnClickListener {
    LinearLayout layoutList;

    Button buttonAdd;
    Button buttonSubmitList;
    public Programms_Tab2Fidaa() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.activity_programms_tab2fidaa, container, false);
        Constants.view1 = view;

        layoutList = view.findViewById(R.id.layout_list_programms_Fidaa);
        buttonAdd = view.findViewById(R.id.button_add_program_Fidaa);
        buttonSubmitList = view.findViewById(R.id.button_submit_list);

        buttonAdd.setOnClickListener(this);
        buttonSubmitList.setOnClickListener(this);

        return view;
    }
    public void onClick(View v) {

        switch (v.getId()){

            case R.id.button_add_program_Fidaa:

                addView();

                break;

            case R.id.button_submit_list:

        }
    }
    private void addView() {

        final View ProgrammsView = getLayoutInflater().inflate(R.layout.row_add_group_fidaa_programm,null,false);
        ImageView imageClose = (ImageView)ProgrammsView.findViewById(R.id.image_remove_program_Fidaa);

        imageClose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                removeView(ProgrammsView);
            }
        });

        layoutList.addView(ProgrammsView);

    }

    private void removeView(View view){

        layoutList.removeView(view);

    }
}