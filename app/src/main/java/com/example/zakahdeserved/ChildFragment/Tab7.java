package com.example.zakahdeserved.ChildFragment;

import android.graphics.Color;
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
import android.widget.TextView;

import androidx.fragment.app.Fragment;

import com.example.zakahdeserved.Connection.DBHelper;
import com.example.zakahdeserved.R;
import com.example.zakahdeserved.Utility.Constants;
import com.example.zakahdeserved.Utility.ValidationController;

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
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.activity_tab7, container, false);
        Constants.view7 = view;

        buttonAdd = view.findViewById(R.id.button_add_Asset);
        buttonSubmitList = view.findViewById(R.id.button_submit_list_Asset);

        layoutAsset = view.findViewById(R.id.layout_list_Asset);


        buttonAdd.setOnClickListener(this);
        buttonSubmitList.setOnClickListener(this);



        //Load data from family info (في حالة حزمة إضافة لن يكون هناك إلا بيانات أولية)
            DBHelper.loadDataToControls(view, Constants.familyInfo);

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
        Spinner spnAssetType = AssetView.findViewById(R.id.AssetType);
        spnAssetType.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {

                //clear all controls
                ValidationController.ClearView(AssetView.findViewById(R.id.LinearHousrAndStore));
                ValidationController.ClearView(AssetView.findViewById(R.id.LinearFarm));
                ValidationController.ClearView(AssetView.findViewById(R.id.LinearAnimals));
               ((EditText)AssetView.findViewById(R.id.MachineType)).setText("");
               ((EditText)AssetView.findViewById(R.id.ValueTime)).setText("");

                switch (i){
                    case 0: //محل
                    case 1: //منزل
                        AssetView.findViewById(R.id.LinearHousrAndStore).setVisibility(View.VISIBLE);
                        AssetView.findViewById(R.id.LinearFarm).setVisibility(View.GONE);
                        AssetView.findViewById(R.id.LinearAnimals).setVisibility(View.GONE);
                        AssetView.findViewById(R.id.ValueTime).setVisibility(View.GONE);
                        AssetView.findViewById(R.id.MachineType).setVisibility(View.GONE);
                        break;

                    case 2: //أرض زراعية
                        AssetView.findViewById(R.id.LinearHousrAndStore).setVisibility(View.GONE);
                        AssetView.findViewById(R.id.LinearFarm).setVisibility(View.VISIBLE);
                        AssetView.findViewById(R.id.LinearAnimals).setVisibility(View.GONE);
                        AssetView.findViewById(R.id.ValueTime).setVisibility(View.VISIBLE);
                        AssetView.findViewById(R.id.MachineType).setVisibility(View.GONE);
                        break;

                    case 3: //آليات
                        AssetView.findViewById(R.id.LinearHousrAndStore).setVisibility(View.GONE);
                        AssetView.findViewById(R.id.LinearFarm).setVisibility(View.GONE);
                        AssetView.findViewById(R.id.LinearAnimals).setVisibility(View.GONE);
                        AssetView.findViewById(R.id.ValueTime).setVisibility(View.VISIBLE);
                        AssetView.findViewById(R.id.MachineType).setVisibility(View.VISIBLE);
                        break;

                    case 4: //ثروة حيوانية
                        AssetView.findViewById(R.id.LinearHousrAndStore).setVisibility(View.GONE);
                        AssetView.findViewById(R.id.LinearFarm).setVisibility(View.GONE);
                        AssetView.findViewById(R.id.LinearAnimals).setVisibility(View.VISIBLE);
                        AssetView.findViewById(R.id.ValueTime).setVisibility(View.VISIBLE);
                        AssetView.findViewById(R.id.MachineType).setVisibility(View.GONE);
                        break;
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        ImageView imageClose = (ImageView)AssetView.findViewById(imageID);

        imageClose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                removeView(AssetView,linearLayout);
            }
        });
        if(linearLayout.getChildCount()%2!=0){
            AssetView.setBackgroundColor(Color.WHITE);
        }
        else
            AssetView.setBackgroundColor(Color.parseColor("#FFA5D3A6"));
        linearLayout.addView(AssetView);

    }

    private void removeView(View view,LinearLayout linear){

        linear.removeView(view);

    }
}