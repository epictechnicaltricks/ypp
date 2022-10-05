package com.cakiweb.easyscholar;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class StudyMaterialAdapter extends RecyclerView.Adapter<StudyMaterialAdapter.viewHolder> implements Filterable {
    List<StudyMaterialData> exampleList;
    List<StudyMaterialData> exampleListFull;
    final Context context;

    public StudyMaterialAdapter(List<StudyMaterialData> exampleList, Context context) {
        this.exampleList = exampleList;
        exampleListFull = new ArrayList<>(exampleList);
        this.context = context;
    }

    @NonNull
    @Override
    public viewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.studymaterial, parent, false);
        return new StudyMaterialAdapter.viewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull viewHolder holder, int position) {
        final StudyMaterialData studyMaterialData = exampleList.get(position);
        int pos1 = exampleList.indexOf(studyMaterialData);
        holder.subName.setText(studyMaterialData.getSubName());
        holder.chapter.setText(studyMaterialData.getChapterName());
        holder.topic.setText(studyMaterialData.getTopic());
        holder.month.setText(studyMaterialData.getMonth());
    }

    @Override
    public int getItemCount() {
        return exampleList.size();
    }


    @Override
    public Filter getFilter() {
        return exampleFilter;
    }

    private Filter exampleFilter = new Filter() {
        @Override
        protected FilterResults performFiltering(CharSequence constraint) {
            List<StudyMaterialData> filteredList = new ArrayList<>();
            if (constraint == null || constraint.length() == 0) {
                filteredList.addAll(exampleListFull);
            } else {
                String filterPattern = constraint.toString().toLowerCase().trim();
                for (StudyMaterialData item : exampleListFull) {
                    if (item.getSubName().toLowerCase().contains(filterPattern) || item.getTopic().toLowerCase().contains(filterPattern)) {
                        filteredList.add(item);
                    }
                }
            }
            FilterResults results = new FilterResults();
            results.values = filteredList;
            return results;
        }

        @Override
        protected void publishResults(CharSequence constraint, FilterResults results) {
            exampleList.clear();
            exampleList.addAll((List) results.values);
            notifyDataSetChanged();
        }
    };


    public class viewHolder extends RecyclerView.ViewHolder {
        TextView subName, topic, chapter, month;

        public viewHolder(@NonNull View itemView) {
            super(itemView);
            subName = itemView.findViewById(R.id.materialSub);
            chapter = itemView.findViewById(R.id.materialChapter);
            topic = itemView.findViewById(R.id.materialTopic);
            month = itemView.findViewById(R.id.materialMonth);


        }
    }
}