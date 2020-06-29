package com.example.barbershopstaff.Adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.barbershopstaff.Model.Common;
import com.example.barbershopstaff.Model.TimeSlot;
import com.example.barbershopstaff.R;

import java.util.ArrayList;
import java.util.List;

public class TimeSlotAdapter extends RecyclerView.Adapter<TimeSlotAdapter.ViewHolder> {

    Context context;
    List<TimeSlot> timeSlotList;
    List<CardView> cardViewList;


    public TimeSlotAdapter(Context context) {
        this.context = context;
        timeSlotList = new ArrayList<>();
        cardViewList = new ArrayList<>();

    }

    public TimeSlotAdapter(Context context, List<TimeSlot> timeSlotList) {
        this.context = context;
        this.timeSlotList = timeSlotList;
        cardViewList = new ArrayList<>();

    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.timeslot_layout, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final ViewHolder holder, int position) {


        holder.timeslot.setText(new StringBuilder(Common.TimeSlotToString(position)).toString());
        if (timeSlotList.size() == 0) {                  //if all position is available, just show list//

            holder.timeslot_status.setText("Available");
            holder.timeslot_status.setTextColor(context.getResources().getColor(android.R.color.black));
            holder.timeslot.setTextColor(context.getResources().getColor(android.R.color.black));
            holder.cardView.setCardBackgroundColor(context.getResources().getColor(android.R.color.white));
            holder.cardView.setEnabled(true);
        } else {           //if all position is full(booked)//


            for (TimeSlot timeSlot : timeSlotList) {

                int slot = Integer.parseInt(timeSlot.getSlot().toString());
                if (slot == position) {

//we will set tag for all timeslot is full
                    //based on tag, we can set all remain card background without change full timeslot
                    holder.cardView.setTag(Common.DISABLE_TAG);

                    holder.cardView.setCardBackgroundColor(context.getResources().getColor(android.R.color.darker_gray));
                    holder.timeslot_status.setText("Booked");
                    holder.timeslot_status.setTextColor(context.getResources().getColor(android.R.color.black));
                    holder.timeslot.setTextColor(context.getResources().getColor(android.R.color.white));
                    holder.cardView.setEnabled(false);

                }

            }


        }

        //add only available timeslot card to list
        if (!cardViewList.contains(holder.cardView)){

            cardViewList.add(holder.cardView);

        }


holder.cardView.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View v) {


        for (CardView cardView:cardViewList){

            if (cardView.getTag()==null)
            {

                cardView.setCardBackgroundColor(context.getResources().getColor(android.R.color.white));

            }
        }

        holder.cardView.setCardBackgroundColor(context.getResources().getColor(android.R.color.holo_orange_dark));

       /* Intent intent=new Intent(Common.KEY_ENABLE_BUTTON_NEXT);
        intent.putExtra(Common.KEY_TIME_SLOT,position);
        intent.putExtra(Common.KEY_STEP,3);
        localBroadcastManager.sendBroadcast(intent);


        */

    }
});


    }

    @Override
    public int getItemCount() {
        return Common.TOTAl_TIMESLOT;
    }


    class ViewHolder extends RecyclerView.ViewHolder{

        TextView timeslot, timeslot_status;
        CardView cardView;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            timeslot = itemView.findViewById(R.id.txt_timeslot);
            timeslot_status = itemView.findViewById(R.id.timeslot_status);

            cardView = itemView.findViewById(R.id.timeslot_cardview);
        }
    }
}
