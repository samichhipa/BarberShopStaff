package com.example.barbershopstaff.Adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.barbershopstaff.BranchActivity;
import com.example.barbershopstaff.Model.Common;
import com.example.barbershopstaff.Model.Salons;
import com.example.barbershopstaff.R;

import java.util.List;

public class SalonAdapter extends RecyclerView.Adapter<SalonAdapter.ViewHolder> {

    Context context;
    List<Salons> salonsList;

    public SalonAdapter(Context context, List<Salons> salonsList) {
        this.context = context;
        this.salonsList = salonsList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
       View view= LayoutInflater.from(context).inflate(R.layout.salon_layout,parent,false);
       return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        Salons salons=salonsList.get(position);

        holder.txt_salon_name.setText(salons.getCity_name());


        final String salonid=salons.getCity_id();
        holder.txt_salon_name.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                Common.SALON_ID=salonid;
                Intent intent =new Intent(context, BranchActivity.class);
                context.startActivity(intent);

            }
        });

    }

    @Override
    public int getItemCount() {
        return salonsList.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder{

        TextView txt_salon_name;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            txt_salon_name=itemView.findViewById(R.id.city_name);
        }
    }
}
