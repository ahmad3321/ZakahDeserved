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

public class Tab8 extends Fragment implements View.OnClickListener {

    LinearLayout layoutWife;
    Button buttonAdd;
    Button buttonSubmitList;

    public Tab8() {
        // Required empty public constructor
    }

    // TODO: Rename and change types and number of parameters


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.activity_tab8, container, false);

        buttonAdd = view.findViewById(R.id.button_add_Wifes);
        buttonSubmitList = view.findViewById(R.id.button_submit_list_Wifes);

        layoutWife = view.findViewById(R.id.layout_list_Wifes);


        buttonAdd.setOnClickListener(this);
        buttonSubmitList.setOnClickListener(this);


        return view;
    }
    public void onClick(View v) {

        switch (v.getId()){

            case R.id.button_add_Wifes:

                addView(R.layout.row_add_wifes,R.id.image_remove_Wife,layoutWife);

                break;
                case R.id.button_submit_list:

                /*if(checkIfValidAndRead()){

                   /* Intent intent = new Intent(MainActivity.this,ActivityCricketers.class);
                    Bundle bundle = new Bundle();
                    bundle.putSerializable("list",cricketersList);
                    intent.putExtras(bundle);
                    startActivity(intent);
                }*/
                break;
        }
    }

    private void addView(int id,int imageID,LinearLayout linearLayout) {

        final View WifeView = getLayoutInflater().inflate(id,null,false);

        ImageView imageClose = (ImageView)WifeView.findViewById(imageID);

        imageClose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                removeView(WifeView,linearLayout);
            }
        });

        linearLayout.addView(WifeView);

    }

    private void removeView(View view,LinearLayout linear){

        linear.removeView(view);

    }
}