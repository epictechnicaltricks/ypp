package com.cakiweb.easyscholar;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class   FeeAdapter extends RecyclerView.Adapter<FeeAdapter.viewHolder>  {
    final Context context;
    ArrayList<FeeData> feeData;

    public FeeAdapter(ArrayList<FeeData> feeData,Context context) {
        this.feeData=feeData;
        this.context = context;
    }

    @NonNull
    @Override
    public viewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.feedata, parent, false);
        return new FeeAdapter.viewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull viewHolder holder, int position) {
        final FeeData feeData1=feeData.get(position);
        int pos1=feeData.indexOf(feeData1);
        holder.id.setText(feeData1.getId());
       // holder.sessionid.setText(feeData1.getSessionid());
        holder.monthyear.setText(feeData1.getMonthyear());
        holder.feeamount.setText(feeData1.getFeeamount());
        holder.paidstatus.setText(feeData1.getPaidstatus());
        holder.feeMonth.setText(feeData1.getMonthyear());
       // holder.paidamount.setText(feeData1.getPaidamount());
        if (feeData1.getFinecollected().equals("0.00"))
        {
            holder.finecollected.setText("no fine");
        }
        else {
            holder.finecollected.setText(feeData1.getFinecollected());
        }
        if (feeData1.getPaidstatus().equals("Pending")){
            holder.feePay.setVisibility(View.VISIBLE);
            holder.feeBill.setVisibility(View.GONE);
        }
        if (feeData1.getPaidstatus().equals("Paid")){
            holder.feePay.setVisibility(View.GONE);
            holder.feeBill.setVisibility(View.VISIBLE);
        }
        holder.feePay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context,PayGetWay.class);
                intent.putExtra("amount",feeData1.getFeeamount());
                intent.putExtra("id",feeData1.getId());
                intent.putExtra("fine",feeData1.getFinecollected());
                intent.putExtra("session",feeData1.getSessionid());
                intent.putExtra("month",feeData1.getMonthyear());
                context.startActivity(intent);
            }
        });
        holder.feeBill.setOnClickListener(v -> Toast.makeText(context,"You cant download now",Toast.LENGTH_SHORT).show());


    }

    @Override
    public int getItemCount() {
        return feeData.size();
    }

    public class viewHolder extends RecyclerView.ViewHolder {
        TextView id;
        TextView monthyear;
        TextView feeamount;
        TextView paidstatus;
        TextView finecollected;
        Button feePay,feeBill;
        TextView feeMonth;
        public viewHolder(@NonNull View itemView) {
            super(itemView);
            id=itemView.findViewById(R.id.payId);
            monthyear=itemView.findViewById(R.id.payMonth);
            feeamount=itemView.findViewById(R.id.feeAmount);
            feeMonth=itemView.findViewById(R.id.feeMonth);
            paidstatus=itemView.findViewById(R.id.feeStatus);
            finecollected=itemView.findViewById(R.id.feeFine);
            feePay=itemView.findViewById(R.id.feePay);
            feeBill=itemView.findViewById(R.id.feeBill);
            feePay.findViewById(R.id.feePay).setVisibility(View.VISIBLE);
            feeBill.findViewById(R.id.feeBill).setVisibility(View.GONE);
        }
    }
}
