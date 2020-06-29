package com.example.barbershopstaff;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.MenuItem;
import android.widget.Toast;

import com.example.barbershopstaff.Adapter.TimeSlotAdapter;
import com.example.barbershopstaff.Model.Common;
import com.example.barbershopstaff.Model.TimeSlot;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import devs.mulham.horizontalcalendar.HorizontalCalendar;
import devs.mulham.horizontalcalendar.utils.HorizontalCalendarListener;

import static com.example.barbershopstaff.Model.Common.simpleDateFormat;

public class HomeActivity extends AppCompatActivity {

    DrawerLayout drawerLayout;
    NavigationView navigationView;
    ActionBarDrawerToggle actionBarDrawerToggle;

    RecyclerView recyclerView;

    FirebaseFirestore firebaseFirestore;
    TimeSlotAdapter adapter;
    List<TimeSlot> timeSlotList;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        drawerLayout=findViewById(R.id.drawer);

        navigationView=findViewById(R.id.navigation_view);

        actionBarDrawerToggle=new ActionBarDrawerToggle(this,drawerLayout,R.string.open,R.string.close);

        drawerLayout.addDrawerListener(actionBarDrawerToggle);
        actionBarDrawerToggle.syncState();
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        firebaseFirestore=FirebaseFirestore.getInstance();





        Calendar date = Calendar.getInstance();
        date.add(Calendar.DATE, 0);  //Add Current Date

        LoadAvailableTimeSlotOfBarbers(Common.currenBarber.getBarber_id(),
                simpleDateFormat.format(date.getTime()));


        recyclerView = findViewById(R.id.timeslot_recyclerview);
        recyclerView.setHasFixedSize(true);
        GridLayoutManager linearLayoutManager = new GridLayoutManager(this, 2);
        linearLayoutManager.setOrientation(RecyclerView.VERTICAL);
        recyclerView.setLayoutManager(linearLayoutManager);



        //Calender
        Calendar startDate = Calendar.getInstance();
        startDate.add(Calendar.DATE, 0);

        Calendar endDate = Calendar.getInstance();
        endDate.add(Calendar.DATE, 2);


        HorizontalCalendar horizontalCalendar = new HorizontalCalendar.Builder(this, R.id.calender)
                .range(startDate, endDate)
                .datesNumberOnScreen(1)
                .mode(HorizontalCalendar.Mode.DAYS)
                .defaultSelectedDate(startDate)
                .build();

        horizontalCalendar.setCalendarListener(new HorizontalCalendarListener() {
            @Override
            public void onDateSelected(Calendar date, int position) {

                if (Common.bookingDate.getTimeInMillis() != date.getTimeInMillis()) {

                    Common.bookingDate= date; //this code will not load again if i select new day same with day selected
                    LoadAvailableTimeSlotOfBarbers(Common.currenBarber.getBarber_id(), simpleDateFormat.format(date.getTime()));
                }

            }
        });


    }

    private void LoadAvailableTimeSlotOfBarbers(final String barber_id, final String bookDate) {

        //  AllSalons/0wp6keG9ipLe7wrzyDPZ/Branches/9MXOvOSpAmtFMTSwJqNh/Barber/dD9fKGgWq4fDCkjCdZUD
        firebaseFirestore.collection("AllSalons").document(Common.SALON_ID).collection("Branches")
                .document(Common.currenBranch.getBranch_id()).collection("Barber").document(barber_id)
                .get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {

                if (task.isSuccessful()) {
                    final DocumentSnapshot documentSnapshot = task.getResult();
                    if (documentSnapshot.exists()) {


                        CollectionReference date = FirebaseFirestore.getInstance().collection("AllSalons").document(Common.SALON_ID).collection("Branches")
                                .document(Common.currenBranch.getBranch_id()).collection("Barber").document(barber_id)
                                .collection(bookDate);

                        date.get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                            @Override
                            public void onComplete(@NonNull Task<QuerySnapshot> task) {

                                if (task.isSuccessful()) {


                                    QuerySnapshot snapshots = task.getResult();
                                    if (snapshots.isEmpty()) {

                                        adapter = new TimeSlotAdapter(HomeActivity.this);
                                        recyclerView.setAdapter(adapter);



                                        Toast.makeText(HomeActivity.this, "Don't have any appointment", Toast.LENGTH_SHORT).show();


                                    } else {


                                        timeSlotList = new ArrayList<>();

                                        for (QueryDocumentSnapshot queryDocumentSnapshot : task.getResult()) {

                                            timeSlotList.add(queryDocumentSnapshot.toObject(TimeSlot.class));
                                        }

                                        adapter = new TimeSlotAdapter(HomeActivity.this, timeSlotList);
                                        recyclerView.setAdapter(adapter);
                                        adapter.notifyDataSetChanged();

                                    }


                                }

                            }
                        });

                    } else {

                        Toast.makeText(HomeActivity.this, "Barber Not Exists", Toast.LENGTH_SHORT).show();


                    }

                }

            }
        });


    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (actionBarDrawerToggle.onOptionsItemSelected(item)){

            return true;
        }


        return super.onOptionsItemSelected(item);
    }


}
