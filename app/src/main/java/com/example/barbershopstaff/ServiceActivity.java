package com.example.barbershopstaff;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatAutoCompleteTextView;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.barbershopstaff.Model.Common;
import com.example.barbershopstaff.Model.Services;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.chip.Chip;
import com.google.android.material.chip.ChipGroup;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashSet;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

public class ServiceActivity extends AppCompatActivity {

    TextView txtCustomerName, txtCustomerPhone;
    CircleImageView CustomerImage;

    AppCompatAutoCompleteTextView txt_services;

    Button finishBtn;

    ImageView service_image;

    ChipGroup service_chip_group, shopping_chip_group;
    HashSet<Services> servicesHashSet=new HashSet<>();

    LayoutInflater layoutInflater;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_service);


        init();
       loadCustomerInfo();


       
       loadAllServices();


    }

    private void loadAllServices() {

        FirebaseFirestore.getInstance().collection("AllSalons").document(Common.SALON_ID).collection("Branches")
                .document(Common.currenBranch.getBranch_id()).collection("Services")
                .get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {

                if (task.isSuccessful()) {
                    final List<Services> servicesList = new ArrayList<>();

                    for (DocumentSnapshot snapshot:task.getResult()){

                        Services services=snapshot.toObject(Services.class);
                        servicesList.add(services);
                    }

                    final List<String> nameServiceList=new ArrayList<>();

                    Collections.sort(servicesList, new Comparator<Services>() {
                        @Override
                        public int compare(Services services, Services t1) {
                            return services.getService_name().compareTo(t1.getService_name());
                        }
                    });

                    for (Services services:servicesList){
                        nameServiceList.add(services.getService_name());

                    }

                    ArrayAdapter<String> adapter=new ArrayAdapter<>(ServiceActivity.this,android.R.layout.select_dialog_item,nameServiceList);
                    txt_services.setThreshold(1);
                    txt_services.setAdapter(adapter);
                    txt_services.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                        @Override
                        public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

                            int index = nameServiceList.indexOf(txt_services.getText().toString().trim());

                            if (!servicesHashSet.contains(servicesList.get(index))) {

                                servicesHashSet.add(servicesList.get(index));
                                final Chip item = (Chip) layoutInflater.inflate(R.layout.chip_item, null);
                                item.setText(txt_services.getText().toString());
                                item.setTag(index);
                                txt_services.setText("");


                                item.setOnCloseIconClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View view) {
                                        service_chip_group.removeView(view);
                                        servicesHashSet.remove((int)item.getTag());
                                    }
                                });

                                service_chip_group.addView(item);


                            }else{

                                txt_services.setText("");
                            }
                        }
                    });

                }
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {

                Toast.makeText(ServiceActivity.this,e.getMessage(),Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void loadCustomerInfo() {

        txtCustomerName.setText(Common.bookingInformation.getCustomer_name());
        txtCustomerPhone.setText(Common.bookingInformation.getCustomer_phone());
    }

    private void init() {


        txtCustomerName = findViewById(R.id.txt_customerName);
        txtCustomerPhone = findViewById(R.id.txt_customerPhone);
        CustomerImage = findViewById(R.id.customerImage);

        txt_services = findViewById(R.id.txt_services);

        finishBtn = findViewById(R.id.finishBtn);

        service_image = findViewById(R.id.service_image);

        service_chip_group = findViewById(R.id.chip_group_services);

        shopping_chip_group = findViewById(R.id.chip_group_shopping);

        layoutInflater.from(ServiceActivity.this);

    }
}
