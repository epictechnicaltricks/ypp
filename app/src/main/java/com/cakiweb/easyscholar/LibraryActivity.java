package com.cakiweb.easyscholar;

import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class LibraryActivity extends AppCompatActivity {
    LinearLayout noData;
    RecyclerView recyclerView;
    ArrayList<BookData> bookData=new ArrayList<BookData>() ;
    BookAdapter bookAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_library);
        recyclerView=findViewById(R.id.bookRecycler);
        recyclerView.setHasFixedSize(true);
        noData = findViewById(R.id.noDataLibrary);
        noData.findViewById(R.id.noDataLibrary).setVisibility(View.GONE);
        bookAdapter=new BookAdapter(bookData,LibraryActivity.this);
        recyclerView.setAdapter(bookAdapter);


        LinearLayoutManager linearLayoutManager=new LinearLayoutManager(LibraryActivity.this,LinearLayoutManager.VERTICAL,false);
        recyclerView.setLayoutManager(linearLayoutManager);

        for (int i=0; i<10; i++ ) {
            BookData bookData1=new BookData("Let us C","1234","12 Mar 2021","11 Apr 2021","15","50");
            bookData.add(bookData1);
        }
    }

    public void onBack(View view) {
        onBackPressed();
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }

}