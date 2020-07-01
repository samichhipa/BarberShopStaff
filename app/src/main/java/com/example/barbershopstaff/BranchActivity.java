package com.example.barbershopstaff;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatEditText;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.barbershopstaff.Adapter.BranchAdapter;
import com.example.barbershopstaff.Model.Barber;
import com.example.barbershopstaff.Model.Branches;
import com.example.barbershopstaff.Model.Common;
import com.example.barbershopstaff.Model.Token;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.firebase.iid.FirebaseInstanceId;

import java.util.ArrayList;
import java.util.List;

public class BranchActivity extends AppCompatActivity implements BranchAdapter.onClickListener {
    TextView all_Salon_Count;

    RecyclerView recyclerView;
    BranchAdapter adapter;
    List<Branches> branchesList = new ArrayList<>();

    int branch_count;


    AppCompatEditText dialog_email, dialog_password;
    Button dialog_login_btn, dialog_cancel_btn;


    FirebaseFirestore firebaseFirestore;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_branch);

        firebaseFirestore = FirebaseFirestore.getInstance();

        all_Salon_Count = findViewById(R.id.all_salon_count);
        recyclerView = findViewById(R.id.branch_recyclerview);
        GridLayoutManager gridLayoutManager = new GridLayoutManager(this, 2);
        gridLayoutManager.setOrientation(RecyclerView.VERTICAL);
        recyclerView.setLayoutManager(gridLayoutManager);
        adapter = new BranchAdapter(branchesList, this);
        adapter.setOnClickListener(this);

        getAllBranches();


    }



    private void getAllBranches() {

        firebaseFirestore.collection("AllSalons").document(Common.SALON_ID).collection("Branches").get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {

                branchesList.clear();
                for (DocumentSnapshot snapshot : task.getResult()) {

                    Branches branches = snapshot.toObject(Branches.class);

                    branchesList.add(branches);
                    branch_count = branchesList.size();

                }
                all_Salon_Count.setText(String.valueOf(branch_count));


                recyclerView.setAdapter(adapter);
                adapter.notifyDataSetChanged();

            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {


                Toast.makeText(BranchActivity.this, e.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public void onClick(int position) {
        Branches branches = branchesList.get(position);
        Common.currenBranch=branches;
        String branch_id = branches.getBranch_id();

        LoginDialog(branch_id);
    }

    private void LoginDialog(final String branch_id) {


        AlertDialog.Builder builder = new AlertDialog.Builder(BranchActivity.this);
        View view = LayoutInflater.from(BranchActivity.this).inflate(R.layout.login_layout, null);

        dialog_email = view.findViewById(R.id.dialog_txt_name);
        dialog_password = view.findViewById(R.id.dialog_txt_password);
        dialog_login_btn = view.findViewById(R.id.dialog_login);
        dialog_cancel_btn = view.findViewById(R.id.dialog_cancel);

        builder.setView(view);
        final AlertDialog alertDialog = builder.create();

        dialog_login_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String email, pass;
                email = dialog_email.getText().toString().trim();
                pass = dialog_password.getText().toString().trim();


                if (TextUtils.isEmpty(email)) {

                    dialog_email.setError("Enter Email");
                } else if (TextUtils.isEmpty(pass)) {

                    dialog_password.setError("Enter Password");


                } else {

                    Login(email, pass, branch_id, alertDialog);

                }


            }
        });

        dialog_cancel_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                alertDialog.dismiss();

            }
        });


        alertDialog.show();


    }

    private void Login(final String email, final String pass, String branch_id, final AlertDialog alertDialog) {


        firebaseFirestore.collection("AllSalons").document(Common.SALON_ID).collection("Branches")
                .document(branch_id).collection("Barber").whereEqualTo("barber_email", email)
                .whereEqualTo("barber_password", pass).get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {

                if (task.isSuccessful()) {

                    if (task.getResult().size() > 0) {

                        for (DocumentSnapshot snapshot : task.getResult()) {

                            Barber barber = snapshot.toObject(Barber.class);
                            if (barber.getBarber_email().equals(email) && barber.getBarber_password().equals(pass)) {

                                Common.currenBarber = barber;
                            }


                        }


                        alertDialog.dismiss();

                        Intent intent = new Intent(BranchActivity.this, HomeActivity.class);
                        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                        startActivity(intent);


                    } else {

                        Toast.makeText(BranchActivity.this, "Invalid Email Or Password", Toast.LENGTH_SHORT).show();

                        dialog_email.setText("");
                        dialog_password.setText("");
                    }

                }

            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {

                Toast.makeText(BranchActivity.this, e.getMessage(), Toast.LENGTH_SHORT).show();

            }
        });


    }


}
