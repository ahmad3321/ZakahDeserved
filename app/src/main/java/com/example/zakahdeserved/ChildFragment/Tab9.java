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

public class Tab9 extends Fragment implements View.OnClickListener {

    LinearLayout layoutSurveyConclusions;
    Button buttonAdd_SurveyConclusions;
    Button buttonSubmitList;

    public Tab9() {
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
        View view = inflater.inflate(R.layout.activity_tab9, container, false);

        buttonAdd_SurveyConclusions = view.findViewById(R.id.buttonAdd_SurveyConclusions);
        buttonSubmitList = view.findViewById(R.id.button_submit_list);

        layoutSurveyConclusions = view.findViewById(R.id.layoutSurveyConclusions);


        buttonAdd_SurveyConclusions.setOnClickListener(this);
        buttonSubmitList.setOnClickListener(this);


        return view;
    }
    public void onClick(View v) {

        switch (v.getId()){

            case R.id.buttonAdd_SurveyConclusions:

                addView(R.layout.row_add_surveyconclusions,R.id.image_remove_SurveyConclusions,layoutSurveyConclusions);

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

        final View surveyconclusionsView = getLayoutInflater().inflate(id,null,false);

        ImageView imageClose = (ImageView)surveyconclusionsView.findViewById(imageID);

        imageClose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                removeView(surveyconclusionsView,linearLayout);
            }
        });

        linearLayout.addView(surveyconclusionsView);

    }

    private void removeView(View view,LinearLayout linear){

        linear.removeView(view);

    }

}