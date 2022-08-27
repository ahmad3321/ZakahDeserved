package com.example.zakahdeserved.ChildFragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.fragment.app.Fragment;

import com.example.zakahdeserved.R;

public class Tab7 extends Fragment implements View.OnClickListener {

    LinearLayout layoutAsset;
    Button buttonAdd,buttonAddIncome,buttonAddAids;
    Button buttonSubmitList;

    public Tab7() {
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
        View view = inflater.inflate(R.layout.activity_tab7, container, false);

        buttonAdd = view.findViewById(R.id.button_add_Asset);
        buttonSubmitList = view.findViewById(R.id.button_submit_list_Asset);

        layoutAsset = view.findViewById(R.id.layout_list_Asset);


        buttonAdd.setOnClickListener(this);
        buttonSubmitList.setOnClickListener(this);


        return view;
    }
    public void onClick(View v) {

        switch (v.getId()){

            case R.id.button_add_Asset:

                addView(R.layout.row_add_asset,R.id.image_remove_Asset,layoutAsset);

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

        final View AssetView = getLayoutInflater().inflate(id,null,false);

        // EditText editText = (EditText)cricketerView.findViewById(R.id.HealthStatusType);
        //Spinner HealthStatus = (Spinner)cricketerView.findViewById(R.id.WaterType);
        //ImageView imageCloseIncome = (ImageView)IncomeCiew.findViewById(R.id.image_remove_Income);
        //ImageView imageCloseAids = (ImageView)IncomeCiew.findViewById(R.id.image_remove_Aids);
        ImageView imageClose = (ImageView)AssetView.findViewById(imageID);

        imageClose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                removeView(AssetView,linearLayout);
            }
        });

        linearLayout.addView(AssetView);

    }

    private void removeView(View view,LinearLayout linear){

        linear.removeView(view);

    }
}