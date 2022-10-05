package com.cakiweb.easyscholar;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class ResultAdapter extends RecyclerView.Adapter<ResultAdapter.viewHolder> {
    ArrayList<ResultData> resultData;
    final Context context;

    public ResultAdapter(ArrayList<ResultData> resultData, Context context) {
        this.resultData =resultData;
        this.context = context;
    }

    @NonNull
    @Override
    public ResultAdapter.viewHolder onCreateViewHolder(@NonNull  ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.resultlist, parent, false);
        return new ResultAdapter.viewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull  ResultAdapter.viewHolder holder, int position) {


        final ResultData resultData1=resultData.get(position);
        int pos1=resultData.indexOf(resultData1);
        holder.class_name.setText(resultData1.getClass_name());
        holder.exam_term.setText(resultData1.getExam_term());
        holder.grade.setText(resultData1.grade);
        holder.session_name.setText(resultData1.getSession_name());
        holder.teacher_remarks.setText(resultData1.getTeacher_remarks());

        holder.author.setOnClickListener(view -> {

            Intent in = new Intent(Intent.ACTION_VIEW);
            in.setData(Uri.parse(resultData1.getAuthor()));
            context.startActivity(in);

          /*  Intent intent=new Intent(context,pdfview.class);
            intent.putExtra("pdfURL",resultData1.getAuthor());
            context.startActivity(intent);*/
            Toast.makeText(context,resultData1.getAuthor() , Toast.LENGTH_SHORT).show();


        });

    }

    @Override
    public int getItemCount() {
        return resultData.size();
    }

    public class viewHolder extends RecyclerView.ViewHolder {
        TextView class_name,
                exam_term,
                grade,
                session_name,
                teacher_remarks,
                author;

        public viewHolder(@NonNull View itemView) {
            super(itemView);;
            class_name=itemView.findViewById(R.id.class_name);
            exam_term=itemView.findViewById(R.id.exam_term);
            grade=itemView.findViewById(R.id.grade);
            session_name=itemView.findViewById(R.id.session2);
            teacher_remarks=itemView.findViewById(R.id.remark);
            author=itemView.findViewById(R.id.view);
        }
    }
}
