package com.example.zakahdeserved.ChildFragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Spinner;

import androidx.fragment.app.Fragment;

import com.example.zakahdeserved.R;
import com.example.zakahdeserved.Utility.Constants;
import com.example.zakahdeserved.Utility.ValidationController;


public class Programms_Tab1 extends Fragment implements View.OnClickListener {
    LinearLayout layoutList;

    Button buttonAdd;
    Button buttonSubmitList;
    public Programms_Tab1() {

    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.activity_Programms_tab1, container, false);
        Constants.view1 = view;

        layoutList = view.findViewById(R.id.layout_list_programms_ktlal);
        buttonAdd = view.findViewById(R.id.button_add_program_ktlal);
        buttonSubmitList = view.findViewById(R.id.button_submit_list);

        buttonAdd.setOnClickListener(this);
        buttonSubmitList.setOnClickListener(this);

        return view;
    }
    public void onClick(View v) {

        switch (v.getId()){

            case R.id.button_add:

                addView();

                break;

            case R.id.button_submit_list:
/*                if(checkIfValidAndRead()){

                   /* Intent intent = new Intent(MainActivity.this,ActivityCricketers.class);
                    Bundle bundle = new Bundle();
                    bundle.putSerializable("list",cricketersList);
                    intent.putExtras(bundle);
                    startActivity(intent);
                }
                break;*/
        }
    }
    private void addView() {

        final View ProgrammsView = getLayoutInflater().inflate(R.layout.row_add_group_programms,null,false);

        ImageView imageClose = (ImageView)ProgrammsView.findViewById(R.id.image_remove_program_ktlal);



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