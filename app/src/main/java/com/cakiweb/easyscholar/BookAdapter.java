package com.cakiweb.easyscholar;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class BookAdapter extends RecyclerView.Adapter<BookAdapter.viewHolder> {
    ArrayList<BookData> bookData;
    final Context context;

    public BookAdapter(ArrayList<BookData> bookData,Context context) {
        this.bookData=bookData;
        this.context = context;
    }

    @NonNull
    @Override
    public viewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.issue_book, parent, false);
        return new BookAdapter.viewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull viewHolder holder, int position) {
        final BookData bookData1=bookData.get(position);
        int pos1=bookData.indexOf(bookData1);
        holder.bookName.setText(bookData1.getBookName());
        holder.bookNo.setText(bookData1.getBookNo());
        holder.bookIssue.setText(bookData1.getBookIssue());
        holder.bookReturn.setText(bookData1.getBookReturn());
        holder.bookDue.setText(bookData1.getBookDue());
        holder.bookFine.setText(bookData1.getBookFine());
    }

    @Override
    public int getItemCount() {
        return bookData.size();
    }

    public class viewHolder extends RecyclerView.ViewHolder {

        TextView bookName,bookNo,bookIssue,bookReturn,bookDue,bookFine;
        public viewHolder(@NonNull View itemView) {
            super(itemView);
            bookName=itemView.findViewById(R.id.bookName);
            bookNo=itemView.findViewById(R.id.bookNo);
            bookIssue=itemView.findViewById(R.id.bookIssue);
            bookReturn=itemView.findViewById(R.id.bookReturn);
            bookDue=itemView.findViewById(R.id.bookDue);
            bookFine=itemView.findViewById(R.id.bookFine);
        }
    }
}
