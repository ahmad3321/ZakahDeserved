package com.example.zakahdeserved;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.zakahdeserved.Models.HealthStatuses;

import java.util.ArrayList;

public class HealthStatusesAdapter extends RecyclerView.Adapter<HealthStatusesAdapter.HealthStatusesView>{

    ArrayList<HealthStatuses> cricketersList = new ArrayList<>();

    public HealthStatusesAdapter(ArrayList<HealthStatuses> cricketersList) {
        this.cricketersList = cricketersList;
    }

    @NonNull
    @Override
    public HealthStatusesView onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.row_healthstatus,parent,false);

        return new HealthStatusesView(view);
    }

    @Override
    public void onBindViewHolder(@NonNull HealthStatusesView holder, int position) {

        HealthStatuses HealthStatusesView = cricketersList.get(position);
        holder.textCricketerName.setText(HealthStatusesView.getCricketerName());
        holder.textTeamName.setText(HealthStatusesView.getTeamName());


    }

    @Override
    public int getItemCount() {
        return cricketersList.size();
    }

    public class HealthStatusesView extends RecyclerView.ViewHolder{

        TextView textCricketerName,textTeamName;
        public HealthStatusesView(@NonNull View itemView) {
            super(itemView);

            textCricketerName = (TextView)itemView.findViewById(R.id.text_cricketer_name);
            textTeamName = (TextView)itemView.findViewById(R.id.text_team_name);

        }
    }
}
