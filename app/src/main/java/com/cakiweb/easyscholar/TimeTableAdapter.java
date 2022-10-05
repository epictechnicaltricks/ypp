package com.cakiweb.easyscholar;

import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class TimeTableAdapter extends RecyclerView.Adapter<TimeTableAdapter.viewHolder> {
    ArrayList<Period> periods;
    final Context context;

    public TimeTableAdapter( ArrayList<Period> periods,Context context) {
        this.periods =periods;
        this.context = context;
    }

    @NonNull
    @Override
    public TimeTableAdapter.viewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.period, parent, false);
        return new TimeTableAdapter.viewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull TimeTableAdapter.viewHolder holder, int position) {
        final Period period=periods.get(position);
        int pos1=periods.indexOf(period);
        holder.periodTime.setText(period.getPeriodTime());
        holder.subName.setText(period.getSubName());
        holder.teacher.setText(period.getTeacher());
        holder.lecture.setText(period.getLecture());
        String url =period.getLecture();
        if(url.contains("https://")){
            if(url.length() == 36){
                url=period.getLecture();
            }
        }
        else {
            url="https://"+url;
        }
        String finalUrl = url;
        holder.lecture.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String url1 = finalUrl;
                Intent i = new Intent(Intent.ACTION_VIEW, Uri.parse(url1));
                i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                i.setPackage("com.android.chrome");
                try {
                    context.startActivity(i);
                } catch (ActivityNotFoundException e) {
                    i.setPackage(null);
                    context.startActivity(i);
                }
            }
        });

       // holder.topic.setText(period.getTopic());
    }

    @Override
    public int getItemCount() {
        return periods.size();
    }

    public class viewHolder extends RecyclerView.ViewHolder {

        TextView periodTime;
        TextView subName;
        TextView teacher;
        TextView lecture;
        TextView topic;
        public viewHolder(@NonNull View itemView) {
            super(itemView);
            periodTime=itemView.findViewById(R.id.periodTime);
            subName=itemView.findViewById(R.id.subName);
            teacher=itemView.findViewById(R.id.teacher);
            lecture=itemView.findViewById(R.id.lecture);
           // topic=itemView.findViewById(R.id.topic);
        }


    }
}
