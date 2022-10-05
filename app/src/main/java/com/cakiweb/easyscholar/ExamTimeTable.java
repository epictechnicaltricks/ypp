package com.cakiweb.easyscholar;

import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class ExamTimeTable extends AppCompatActivity {
    View clickCurrentExam,clickPastExam;
    RecyclerView recyclerView;
    ExamAdapter examAdapter;
    ArrayList<ExamData> examData=new ArrayList<ExamData>();
    String HttpURL = "http://192.168.1.212/erpapi.php";
    String id;
    LinearLayout noData;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_exam_time_table);
        noData = findViewById(R.id.noDataExam);
        noData.findViewById(R.id.noDataExam).setVisibility(View.VISIBLE);
        clickCurrentExam = findViewById(R.id.clickCurrentExam);
        clickPastExam = findViewById(R.id.clickPastExam);
        clickCurrentExam.findViewById(R.id.clickCurrentExam).setVisibility(View.VISIBLE);
        clickPastExam.findViewById(R.id.clickPastExam).setVisibility(View.GONE);

        recyclerView=findViewById(R.id.examRecycler);
        recyclerView.setHasFixedSize(true);

        examAdapter=new ExamAdapter(examData,ExamTimeTable.this);
        recyclerView.setAdapter(examAdapter);

        LinearLayoutManager linearLayoutManager=new LinearLayoutManager(ExamTimeTable.this,LinearLayoutManager.VERTICAL,false);
        recyclerView.setLayoutManager(linearLayoutManager);

//        for (int i=0; i<10; i++ ) {
//            ExamData examData1=new ExamData("20 Mar 2021","09-30am to 10.30am","English","80");
//            examData.add(examData1);
//        }

//        SharedPreferences sh = getSharedPreferences("MySharedPref", MODE_PRIVATE);
//        String json=sh.getString("json","");
//
//        try {
//            JSONObject jsonObj1 = new JSONObject(json);
//            JSONArray array = jsonObj1.getJSONArray("resultSet");
//            for (int i = 0; i < array.length(); i++) {
//                JSONObject ob1 = array.getJSONObject(i);
//                id=ob1.getString("student_id");
//            }
//        } catch (JSONException e) {
//            e.printStackTrace();
//        }
//        ProgressDialog loading = ProgressDialog.show(this,"Please wait...","Fetching...",false,false);
//        String url = HttpURL+"?method=exam&userId="+id;
//        //String url = "https://cakitech.com/soumya/index.php";
//        StringRequest stringRequest = new StringRequest(url, new Response.Listener<String>() {
//            @Override
//            public void onResponse(String response) {
//                loading.dismiss();
//                showJSON(response);
//                //Toast.makeText(NoticeActivity.this,""+response,Toast.LENGTH_LONG).show();
//            }
//        },
//                new Response.ErrorListener() {
//                    @Override
//                    public void onErrorResponse(VolleyError error) {
//                        Toast.makeText(ExamTimeTable.this,error.getMessage().toString(),Toast.LENGTH_LONG).show();
//                    }
//                });
//
//        RequestQueue requestQueue = Volley.newRequestQueue(this);
//        requestQueue.add(stringRequest);

    }
    private void showJSON(String response) {

        // Toast.makeText(PaymentActivity.this,""+response,Toast.LENGTH_LONG).show();
        if (response.contains("200")) {

            noData.findViewById(R.id.noDataExam).setVisibility(View.GONE);
            try {
                JSONObject jsonObject = new JSONObject(response);
                JSONArray result = jsonObject.getJSONArray("resultSet");
                for (int i = 0; i < result.length(); i++) {
                    JSONObject ob = result.getJSONObject(i);
                    ExamData examData1 = new ExamData(ob.getString("date"), ob.getString("date"), ob.getString("subject_name"), "na","0");
                    examData.add(examData1);
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
            recyclerView.setAdapter(examAdapter);
            examAdapter.notifyDataSetChanged();
        }
        else {
            noData.findViewById(R.id.noDataExam).setVisibility(View.VISIBLE);
        }
    }

    public void onPastExam(View view) {
        clickCurrentExam.findViewById(R.id.clickCurrentExam).setVisibility(View.GONE);
        clickPastExam.findViewById(R.id.clickPastExam).setVisibility(View.VISIBLE);
//        examData.clear();
//        examAdapter.notifyDataSetChanged();
//
//        ProgressDialog loading = ProgressDialog.show(this,"Please wait...","Fetching...",false,false);
//        String url = HttpURL+"?method=result&userId="+id;
//        //String url = "https://cakitech.com/soumya/index.php";
//        StringRequest stringRequest = new StringRequest(url, new Response.Listener<String>() {
//            @Override
//            public void onResponse(String response) {
//                loading.dismiss();
//
//                if (response.contains("200")) {
//                    noData.findViewById(R.id.noDataExam).setVisibility(View.GONE);
//                    try {
//                        JSONObject jsonObject = new JSONObject(response);
//                        JSONArray result = jsonObject.getJSONArray("resultSet");
//                        for (int i = 0; i < result.length(); i++) {
//                            JSONObject ob = result.getJSONObject(i);
//                            ExamData examData1 = new ExamData(ob.getString("created_on"), ob.getString("created_on"), ob.getString("exam_title"), ob.getString("total_marks"),ob.getString("scored_marks"));
//                            examData.add(examData1);
//                        }
//                    } catch (JSONException e) {
//                        e.printStackTrace();
//                    }
//                    recyclerView.setAdapter(examAdapter);
//                    examAdapter.notifyDataSetChanged();
//                }
//                else {
//                    noData.findViewById(R.id.noDataExam).setVisibility(View.VISIBLE);
//                }
//            }
//        },
//                new Response.ErrorListener() {
//                    @Override
//                    public void onErrorResponse(VolleyError error) {
//                        Toast.makeText(ExamTimeTable.this,error.getMessage().toString(),Toast.LENGTH_LONG).show();
//                    }
//                });
//
//        RequestQueue requestQueue = Volley.newRequestQueue(this);
//        requestQueue.add(stringRequest);
//

    }

    public void onCurrentExam(View view) {
        clickCurrentExam.findViewById(R.id.clickCurrentExam).setVisibility(View.VISIBLE);
        clickPastExam.findViewById(R.id.clickPastExam).setVisibility(View.GONE);
//        examData.clear();
//        examAdapter.notifyDataSetChanged();
//        ProgressDialog loading = ProgressDialog.show(this,"Please wait...","Fetching...",false,false);
//        String url = HttpURL+"?method=exam&userId="+id;
//        //String url = "https://cakitech.com/soumya/index.php";
//        StringRequest stringRequest = new StringRequest(url, new Response.Listener<String>() {
//            @Override
//            public void onResponse(String response) {
//                loading.dismiss();
//                showJSON(response);
//                //Toast.makeText(NoticeActivity.this,""+response,Toast.LENGTH_LONG).show();
//            }
//        },
//                new Response.ErrorListener() {
//                    @Override
//                    public void onErrorResponse(VolleyError error) {
//                        Toast.makeText(ExamTimeTable.this,error.getMessage().toString(),Toast.LENGTH_LONG).show();
//                    }
//                });
//
//        RequestQueue requestQueue = Volley.newRequestQueue(this);
//        requestQueue.add(stringRequest);
    }
    public void onBack(View view) {
        finish();
    }
}