package com.example.barbershopstaff.Adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.example.barbershopstaff.Model.BookingInformation;
import com.example.barbershopstaff.Model.Common;
import com.example.barbershopstaff.R;
import com.example.barbershopstaff.ServiceActivity;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;
import java.util.List;

public class TimeSlotAdapter extends RecyclerView.Adapter<TimeSlotAdapter.ViewHolder> {

    Context context;
    List<BookingInformation> timeSlotList;
    List<CardView> cardViewList;


    public TimeSlotAdapter(Context context) {
        this.context = context;
        timeSlotList = new ArrayList<>();
        cardViewList = new ArrayList<>();

    }

    public TimeSlotAdapter(Context context, List<BookingInformation> timeSlotList) {
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
           // holder.cardView.setEnabled(true);
        } else {           //if all position is full(booked)//


            for (final BookingInformation timeSlot : timeSlotList) {

                final int slot = Integer.parseInt(timeSlot.getSlot().toString());
                if (slot == position) {

//we will set tag for all timeslot is full
                    //based on tag, we can set all remain card background without change full timeslot
                    holder.cardView.setTag(Common.DISABLE_TAG);

                    holder.cardView.setCardBackgroundColor(context.getResources().getColor(android.R.color.darker_gray));
                    holder.timeslot_status.setText("Booked");
                    holder.timeslot_status.setTextColor(context.getResources().getColor(android.R.color.black));
                    holder.timeslot.setTextColor(context.getResources().getColor(android.R.color.white));
                   // holder.cardView.setEnabled(false);

                    holder.cardView.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {

               FirebaseFirestore.getInstance().collection("AllSalons").document(Common.SALON_ID).collection("Branches")
                                    .document(Common.currenBranch.getBranch_id()).collection("Barber").document(Common.currenBarber.getBarber_id())
                                    .collection(Common.simpleDateFormat.format(Common.bookingDate.getTime())).document(timeSlot.getSlot().toString())
                       .get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                   @Override
                   public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                       if (task.isSuccessful()){

                           if (task.getResult().exists()){

                               Common.bookingInformation=task.getResult().toObject(BookingInformation.class);
                               context.startActivity(new Intent(context, ServiceActivity.class));



                           }



                       }
                   }
               }).addOnFailureListener(new OnFailureListener() {
                   @Override
                   public void onFailure(@NonNull Exception e) {

                       Toast.makeText(context,e.getMessage(),Toast.LENGTH_SHORT).show();
                   }
               });


                        }
                    });




                }else{

                    holder.cardView.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {

                        }
                    });

                }

            }


        }

        //add only available timeslot card to list
        if (!cardViewList.contains(holder.cardView)){

            cardViewList.add(holder.cardView);

        }





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
