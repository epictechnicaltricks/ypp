package com.cakiweb.easyscholar;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class EventAdapter extends RecyclerView.Adapter<EventAdapter.viewHolder> {
    final Context context;
    ArrayList<NoticeData> eventData;

    public EventAdapter(ArrayList<NoticeData> eventData,Context context) {
        this.eventData=eventData;
        this.context = context;
    }

    @NonNull
    @Override
    public EventAdapter.viewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.events, parent, false);
        return new EventAdapter.viewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull EventAdapter.viewHolder holder, int position) {
        final NoticeData eventData1=eventData.get(position);
        int pos1=eventData.indexOf(eventData1);
        holder.title.setText(eventData1.getNoticeTitle());
        holder.description.setText(eventData1.getNoticeDescription());
        holder.date.setText(eventData1.getNoticeTime());

    }

    @Override
    public int getItemCount() {
        return eventData.size();
    }

    public class viewHolder extends RecyclerView.ViewHolder {
        TextView description,date,title;
        public viewHolder(@NonNull View itemView) {
            super(itemView);
            date=itemView.findViewById(R.id.eventDate);
            title=itemView.findViewById(R.id.eventTitle);
            description=itemView.findViewById(R.id.eventDescription);
        }
    }
}
