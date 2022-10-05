package com.cakiweb.easyscholar;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class NoticeAdapter extends RecyclerView.Adapter<NoticeAdapter.viewHolder> {
    final  Context context;
    ArrayList<NoticeData> noticeData;

    public NoticeAdapter(ArrayList<NoticeData> noticeData,Context context) {
        this.noticeData=noticeData;
        this.context = context;
    }

    @NonNull
    @Override
    public viewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.noticelist, parent, false);
        return new NoticeAdapter.viewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull viewHolder holder, int position) {
        final NoticeData noticeData1=noticeData.get(position);
        int pos1=noticeData.indexOf(noticeData1);
        holder.noticeTitle.setText("Notice " +noticeData1.getNoticeId());

        String removeTemp = noticeData1.getNoticeTitle().replaceAll("Notice-","");
        holder.noticeDescription.setText(removeTemp); // used to remove "notice" to " "
       // holder.noticeDescription.setText(noticeData1.getNoticeDescription());
        holder.noticeTime.setText("Show more");

        holder.noticeTime.setOnClickListener(view -> {


            Intent intent=new Intent(context,notice_view.class);
            intent.putExtra("noticeURL",noticeData1.getNoticeURL());
            context.startActivity(intent);
            //Toast.makeText(context,noticeData1.getNoticeURL() , Toast.LENGTH_SHORT).show();
        });

        holder.notice.setOnClickListener(v ->

                Toast.makeText(context,""+noticeData1.getNoticeDescription(),Toast.LENGTH_SHORT).show()

        );
    }

    @Override
    public int getItemCount() {
        return noticeData.size();
    }

    public class viewHolder extends RecyclerView.ViewHolder {
        TextView noticeTitle;
        TextView noticeDescription;
        TextView noticeTime;
        CardView notice;

        public viewHolder(@NonNull View itemView) {
            super(itemView);
            noticeTitle=itemView.findViewById(R.id.noticeTitle);
            noticeDescription=itemView.findViewById(R.id.noticeDescription);
            noticeTime=itemView.findViewById(R.id.noticeTime);
            notice=itemView.findViewById(R.id.notic);
        }
    }
}
