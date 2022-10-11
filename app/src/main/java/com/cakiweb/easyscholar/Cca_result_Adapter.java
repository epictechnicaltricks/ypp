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

public class Cca_result_Adapter extends RecyclerView.Adapter<Cca_result_Adapter.viewHolder> {
    ArrayList<Cca_result_Data> Cca_result_data;
    final Context context;

    public Cca_result_Adapter(ArrayList<Cca_result_Data> Cca_result_data, Context context) {
        this.Cca_result_data = Cca_result_data;
        this.context = context;
    }

    @NonNull
    @Override
    public Cca_result_Adapter.viewHolder onCreateViewHolder(@NonNull  ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.cca_resultlist, parent, false);
        return new Cca_result_Adapter.viewHolder(view);
    }



    @Override
    public void onBindViewHolder(@NonNull  Cca_result_Adapter.viewHolder holder, int position) {


        final Cca_result_Data resultData1= Cca_result_data.get(position);
        int pos1=Cca_result_data.indexOf(resultData1);
        holder.session_name.setText(resultData1.getsession_name());
        holder.class_name.setText(resultData1.getclass_name());
        holder.cca_name.setText(resultData1.getCca_name());
        holder.exam_term.setText(resultData1.getposition_name());
        holder.teacher_remarks.setText(resultData1.getCca_date());

        holder.author.setOnClickListener(view -> {

            Intent in = new Intent(Intent.ACTION_VIEW);
            in.setData(Uri.parse(resultData1.getviewaction()));
            context.startActivity(in);

          /*  Intent intent=new Intent(context,pdfview.class);
            intent.putExtra("pdfURL",resultData1.getAuthor());
            context.startActivity(intent);*/
            Toast.makeText(context,resultData1.getviewaction() , Toast.LENGTH_SHORT).show();


        });

    }

    @Override
    public int getItemCount() {
        return Cca_result_data.size();
    }

    public class viewHolder extends RecyclerView.ViewHolder {
        TextView class_name,
                exam_term,
                cca_name,
                session_name,
                teacher_remarks,
                author;

        public viewHolder(@NonNull View itemView) {
            super(itemView);;
            class_name=itemView.findViewById(R.id.class_name);
            exam_term=itemView.findViewById(R.id.exam_term);
            cca_name =itemView.findViewById(R.id.cca_name);
            session_name=itemView.findViewById(R.id.session2);
            teacher_remarks=itemView.findViewById(R.id.remark);
            author=itemView.findViewById(R.id.view);
        }
    }
}
