package com.cakiweb.easyscholar;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class DayAdapter extends RecyclerView.Adapter<DayAdapter.viewHolder> {
    ArrayList<Day> days;
    final Context context;

    public DayAdapter(ArrayList<Day> days, Context context) {
        this.days=days;
        this.context = context;
    }

    @NonNull
    @Override
    public DayAdapter.viewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.day,
                parent, false);
        return new DayAdapter.viewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull DayAdapter.viewHolder holder, int position) {
//        if(position == 4){
//           position= position+2 ;
//        }
        Day day = days.get(position);
        holder.day.setText(day.getDate());
       // holder.week.setText(day.getDay());
        if(!day.getDate().equals(" ")) {
            holder.layout.setBackground((ContextCompat.getDrawable(context, R.drawable.col_back)));

            if (day.getStatus().equals("1")) {
                holder.layout.setBackground((ContextCompat.getDrawable(context, R.drawable.circle)));
            }
            if (day.getStatus().equals("0")) {
                holder.layout.setBackground((ContextCompat.getDrawable(context, R.drawable.circlered)));
            }
            if (Integer.valueOf(day.getDay()) % 7 == 0) {
                holder.layout.setBackground((ContextCompat.getDrawable(context, R.drawable.circleyellow)));
            }
        }

    }

    @Override
    public int getItemCount() {
        return days.size();
    }

    public class viewHolder extends RecyclerView.ViewHolder {
        RelativeLayout layout,color;
        TextView day;
        TextView week;
        public viewHolder(@NonNull View itemView) {
            super(itemView);
            layout=itemView.findViewById(R.id.col);
            day=itemView.findViewById(R.id.status);
            color=itemView.findViewById(R.id.c);

        }
    }
}
