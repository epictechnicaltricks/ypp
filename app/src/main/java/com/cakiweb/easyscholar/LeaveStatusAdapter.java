package com.cakiweb.easyscholar;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class LeaveStatusAdapter extends RecyclerView.Adapter<LeaveStatusAdapter.viewHolder> {
    final Context context;
    ArrayList<NoticeData> statusData;

    public LeaveStatusAdapter(ArrayList<NoticeData> statusData,Context context) {
        this.context = context;
        this.statusData=statusData;
    }

    @NonNull
    @Override
    public viewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.leave_status, parent, false);
        return new LeaveStatusAdapter.viewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull LeaveStatusAdapter.viewHolder holder, int position) {
        final NoticeData statusData1=statusData.get(position);
        int pos1=statusData.indexOf(statusData1);
        holder.description.setText(statusData1.getNoticeDescription());
        holder.date.setText(statusData1.getNoticeTime());
    }

    @Override
    public int getItemCount() {
            return statusData.size();
    }

    public class viewHolder extends RecyclerView.ViewHolder {
        TextView description,date;
        public viewHolder(@NonNull View itemView) {
            super(itemView);
            date=itemView.findViewById(R.id.leaveDate);
            description=itemView.findViewById(R.id.leaveRes);
        }
    }
}
