package com.example.barbershopstaff.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.barbershopstaff.Model.Branches;
import com.example.barbershopstaff.R;

import org.w3c.dom.Text;

import java.util.List;

public class BranchAdapter extends RecyclerView.Adapter<BranchAdapter.ViewHolder> {


    List<Branches> branchesList;
    Context context;
    BranchAdapter.onClickListener Listener;

    public BranchAdapter(List<Branches> branchesList, Context context) {
        this.branchesList = branchesList;
        this.context = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(context).inflate(R.layout.branch_layout,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        Branches branches=branchesList.get(position);

        holder.txt_branch_name.setText(branches.getBranch_name());
        holder.txt_branch_address.setText(branches.getBranch_address());

    }

    @Override
    public int getItemCount() {
        return branchesList.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        TextView txt_branch_name,txt_branch_address;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            txt_branch_name=itemView.findViewById(R.id.branch_name);
            txt_branch_address=itemView.findViewById(R.id.branch_address);
            itemView.setOnClickListener(this);

        }


        @Override
        public void onClick(View v) {


            if (Listener != null) {

                int position = getAdapterPosition();

                Listener.onClick(position);
            }

        }
    }

    public interface onClickListener{

        void onClick(int position);


    }

    public void setOnClickListener(BranchAdapter.onClickListener onClickListener){

        Listener=onClickListener;



    }
}
