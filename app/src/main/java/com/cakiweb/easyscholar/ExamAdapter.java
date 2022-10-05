package com.cakiweb.easyscholar;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class ExamAdapter extends RecyclerView.Adapter<ExamAdapter.viewHolder> {
    final Context context;
    ArrayList<ExamData> examData;

    public ExamAdapter(ArrayList<ExamData> examData,Context context) {
        this.examData=examData;
        this.context = context;
    }

    @NonNull
    @Override
    public ExamAdapter.viewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.exam, parent, false);
        return new ExamAdapter.viewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ExamAdapter.viewHolder holder, int position) {
        final ExamData examData1=examData.get(position);
        int pos1=examData.indexOf(examData1);
        holder.date.setText(examData1.getDate());
        holder.time.setText(examData1.getTime());
        holder.subject.setText(examData1.getSubject());
        holder.maxMark.setText(examData1.getMaxMark());
        holder.scored.setText(examData1.getPast());
        if(!examData1.getPast().equals("0")){
            holder.mark.findViewById(R.id.linearMark).setVisibility(View.VISIBLE);

        }
    }

    @Override
    public int getItemCount() {
        return examData.size();
    }

    public class viewHolder extends RecyclerView.ViewHolder {
        TextView date;
        TextView time;
        TextView subject;
        TextView maxMark;
        TextView scored;
        LinearLayout mark;
        LinearLayout max;
        public viewHolder(@NonNull View itemView) {
            super(itemView);
             date=itemView.findViewById(R.id.examDate);
             time=itemView.findViewById(R.id.examTime);
            subject=itemView.findViewById(R.id.examSub);
            maxMark=itemView.findViewById(R.id.examMark);
            mark=itemView.findViewById(R.id.linearMark);
            mark.findViewById(R.id.linearMark).setVisibility(View.GONE);
            max=itemView.findViewById(R.id.linearMax);
            scored=itemView.findViewById(R.id.examMarkScored);
        }
    }
}
