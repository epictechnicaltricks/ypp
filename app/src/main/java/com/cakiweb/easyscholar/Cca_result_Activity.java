package com.cakiweb.easyscholar;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;

public class Cca_result_Activity extends AppCompatActivity {
    LinearLayout noData;
    RecyclerView recyclerView;
    Cca_result_Adapter ccaAdapter;
    ArrayList<Cca_result_Data> Cca_data=new ArrayList<Cca_result_Data>();
    String HttpURL ;
    String id;
    String url = "http://erpdis.dooninternational.in/erp/index.php/Api_request/api_list?";
    ProgressDialog progressDialog;
    HashMap<String, String> hashMap = new HashMap<>();
    HttpParse httpParse = new HttpParse();
    JsonHttpParse jsonHttpParse = new JsonHttpParse();
    String finalResult,class_id,session,student_id;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cca);


        recyclerView =findViewById(R.id.noticeRecycler2);
        recyclerView.setHasFixedSize(true);
        noData = findViewById(R.id.noDataNotification2);
        noData.findViewById(R.id.noDataNotification2).setVisibility(View.GONE);

        ccaAdapter=new Cca_result_Adapter(Cca_data,Cca_result_Activity.this);
        recyclerView.setAdapter(ccaAdapter);


        LinearLayoutManager linearLayoutManager=new LinearLayoutManager(Cca_result_Activity.this,LinearLayoutManager.VERTICAL,false);
        recyclerView.setLayoutManager(linearLayoutManager);

        SharedPreferences sh = getSharedPreferences("MySharedPref", MODE_PRIVATE);
        class_id =sh.getString("class_id","");
        student_id = sh.getString("student_id","");
        HttpURL=sh.getString("api","");

//        for (int i=0; i<10; i++ ) {
//            NoticeData history=new NoticeData("notice_title","notice_description","created_on");
//            noticeData.add(history);
//        }


       // https://yppschool.com/erp/index.php/Api_request/api_list?method=ccaresult&student_id=643

        ProgressDialog loading = ProgressDialog.show(this,"Please wait...","Fetching...",false,false);
        String url = HttpURL+"method=ccaresult&student_id="+student_id;

        Toast.makeText(this, url, Toast.LENGTH_LONG).show();

        //String url = "https://cakitech.com/soumya/index.php";
        StringRequest stringRequest = new StringRequest(url, response -> {
            loading.dismiss();
            showJSON(response);
            //Toast.makeText(NoticeActivity.this,""+response,Toast.LENGTH_LONG).show();
        },
                error -> Toast.makeText(Cca_result_Activity.this, error.getMessage() ,Toast.LENGTH_LONG).show());

        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);



    }

    @SuppressLint("NotifyDataSetChanged")
    private void showJSON(String response) {

        //Toast.makeText(NoticeActivity.this,""+response+"  "+class_id,Toast.LENGTH_LONG).show();
        if (response.contains("200")) {
            noData.findViewById(R.id.noDataNotification2).setVisibility(View.GONE);
            try {
                JSONObject jsonObject = new JSONObject(response);
                JSONArray result = jsonObject.getJSONArray("resultSet");
                for (int i = 0; i < result.length(); i++) {
                    JSONObject ob = result.getJSONObject(i);
//                    NoticeData history = new NoticeData("Notice Id "+ob.getString("id"), ob.getString("notice"), " ");
//                    noticeData.add(history);

                    Cca_result_Data history = new Cca_result_Data(
                            ob.getString("session_name"),
                            ob.getString("class_name"),
                            ob.getString("cca_name"),
                            ob.getString("position_name"),
                            ob.getString("cca_date"),
                            ob.getString("viewaction"));
                       Cca_data.add(history);
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }

          Collections.reverse(Cca_data);
            recyclerView.setAdapter(ccaAdapter);
            ccaAdapter.notifyDataSetChanged();
        }
        else {
            noData.findViewById(R.id.noDataNotification2).setVisibility(View.VISIBLE);
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