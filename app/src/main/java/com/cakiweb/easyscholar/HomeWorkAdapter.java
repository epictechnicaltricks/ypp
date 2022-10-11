package com.cakiweb.easyscholar;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class HomeWorkAdapter extends RecyclerView.Adapter<HomeWorkAdapter.viewHolder>{
    ArrayList<HomeWorkData> homeWorkData;
    final Context context;

    public HomeWorkAdapter(ArrayList<HomeWorkData> homeWorkData,Context context) {
        this.homeWorkData=homeWorkData;
        this.context = context;
    }

    @NonNull
    @Override
    public HomeWorkAdapter.viewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.home_work, parent, false);
        return new HomeWorkAdapter.viewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull HomeWorkAdapter.viewHolder holder, int position) {
        final HomeWorkData homeWorkData1=homeWorkData.get(position);
        int pos1=homeWorkData.indexOf(homeWorkData1);



        if(homeWorkData1.getMonth().contains("00:00:00"))
        {
            String x = homeWorkData1.getMonth().replaceAll("00:00:00", "");

            holder.month.setText(x);
            holder.submitBy.setText(x);

        }else {
            holder.month.setText(homeWorkData1.getMonth());
            holder.submitBy.setText(homeWorkData1.getMonth());
            // this same dates
        }


        // this visibility is temp
       // holder.submittedDate.setVisibility(View.GONE);
       // holder.partExam.setVisibility(View.GONE);


        holder.subName.setText(homeWorkData1.getSubName());
        holder.topic.setText(homeWorkData1.getTopic());
        //holder.submitBy.setText(homeWorkData1.getSubmitBy());
        holder.chapter.setText(homeWorkData1.getChapter());

        holder.partExam.setText(homeWorkData1.getPartExam());
//        holder.doc.setText(homeWorkData1.getDoc());
        holder.homeWorkStatus.setText(homeWorkData1.getHomeWorkStatus());
//        holder.doc.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Toast.makeText(context,"no Attachments are available ",Toast.LENGTH_SHORT).show();
//            }
//        });
        if(homeWorkData1.getHomeWorkStatus().equals("pending")){
            holder.homeWorkStatus.setTextColor(Color.parseColor("#510101"));
        }
    }

    @Override
    public int getItemCount() {
        return homeWorkData.size();
    }

    public class viewHolder extends RecyclerView.ViewHolder {
        TextView month;
        TextView subName;
        TextView topic;
        TextView submitBy;
        TextView chapter;
        TextView submittedDate;
        TextView partExam;
        //TextView doc;
        TextView homeWorkStatus;
        public viewHolder(@NonNull View itemView) {
            super(itemView);
            month=itemView.findViewById(R.id.homeworkDate);
            subName=itemView.findViewById(R.id.homeworkSub);
            topic=itemView.findViewById(R.id.homeworkTopic);
            submitBy=itemView.findViewById(R.id.homeworkSubmitBy);
            chapter=itemView.findViewById(R.id.homeworkChapter);
            submittedDate=itemView.findViewById(R.id.homeworkSubmitDate);
            partExam=itemView.findViewById(R.id.homeworkExam);
           // doc=itemView.findViewById(R.id.homeworkDoc);
            homeWorkStatus=itemView.findViewById(R.id.homeworkStatus);
        }
    }
}
