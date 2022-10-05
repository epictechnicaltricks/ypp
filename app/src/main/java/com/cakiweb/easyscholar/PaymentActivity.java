package com.cakiweb.easyscholar;

import android.app.ProgressDialog;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.Toast;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.razorpay.Checkout;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;

public class PaymentActivity extends AppCompatActivity {
    View clickTuition, clickHostel, clickTransport, clickDay;
    String id, stu_name, stu_class, stu_session, date;
    String url = "https://eazyscholar.com/dis/index.php/Api_request/api_list";
    String url1;
    ProgressDialog progressDialog;
    LinearLayout noData;
    RecyclerView recyclerView;
    FeeAdapter feeAdapter;
    ArrayList<FeeData> feeData = new ArrayList<FeeData>();

    HashMap<String, String> hashMap = new HashMap<>();
    HttpParse httpParse = new HttpParse();
    JsonHttpParse jsonHttpParse = new JsonHttpParse();
    String finalResult,stu_id,session,api;


    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_payment);
        Checkout.preload(getApplicationContext());
        clickTransport = findViewById(R.id.clickTransport);
        clickTuition = findViewById(R.id.clickTuition);
        clickHostel = findViewById(R.id.clickHostel);
        clickDay = findViewById(R.id.clickDayBoarding);
        clickTuition.findViewById(R.id.clickTuition).setVisibility(View.VISIBLE);
        clickTransport.findViewById(R.id.clickTransport).setVisibility(View.GONE);
        clickHostel.findViewById(R.id.clickHostel).setVisibility(View.GONE);
        clickDay.findViewById(R.id.clickDayBoarding).setVisibility(View.GONE);
        recyclerView = findViewById(R.id.paymentRecycler);
        noData = findViewById(R.id.noData);
        noData.findViewById(R.id.noData).setVisibility(View.GONE);

        recyclerView.setHasFixedSize(true);

        feeAdapter = new FeeAdapter(feeData, PaymentActivity.this);
        recyclerView.setAdapter(feeAdapter);


        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(PaymentActivity.this, LinearLayoutManager.VERTICAL, false);
        recyclerView.setLayoutManager(linearLayoutManager);

        Calendar c = Calendar.getInstance();
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM");
        date = df.format(c.getTime());
        //Toast.makeText(PaymentActivity.this,"date="+date,Toast.LENGTH_LONG).show();

        SharedPreferences sh = getSharedPreferences("MySharedPref", MODE_PRIVATE);
        stu_id = sh.getString("student_id", "");
        session = sh.getString("session_id", "");
        api=sh.getString("api","");
        url1=api;
        log("tuitionfee");

    }


    public void onBack(View view) {
        finish();
    }

    public void onTuition(View view) {

        clickTuition.findViewById(R.id.clickTuition).setVisibility(View.VISIBLE);
        clickTransport.findViewById(R.id.clickTransport).setVisibility(View.GONE);
        clickHostel.findViewById(R.id.clickHostel).setVisibility(View.GONE);
        clickDay.findViewById(R.id.clickDayBoarding).setVisibility(View.GONE);
        feeData.clear();
        feeAdapter.notifyDataSetChanged();
        log("tuitionfee");
    }

    public void onTransport(View view) {
        clickTuition.findViewById(R.id.clickTuition).setVisibility(View.GONE);
        clickTransport.findViewById(R.id.clickTransport).setVisibility(View.VISIBLE);
        clickHostel.findViewById(R.id.clickHostel).setVisibility(View.GONE);
        clickDay.findViewById(R.id.clickDayBoarding).setVisibility(View.GONE);
        feeData.clear();
        feeAdapter.notifyDataSetChanged();
        log("transportfee");
    }

    public void log(final String method){
        ProgressDialog loading = ProgressDialog.show(this,"Please wait...","Fetching...",false,false);
        //String url = HttpURL+"?method=exam&userId="+id;
        String url = url1+"method="+method+"&student_id="+stu_id;

        Toast.makeText(this, url, Toast.LENGTH_LONG).show();

        StringRequest stringRequest = new StringRequest(url, new Response.Listener<String>() {
            @Override
            public void onResponse(String httpResponseMsg) {
                loading.dismiss();
                //showJSON(response);
                //Toast.makeText(LoginActivity.this,httpResponseMsg,Toast.LENGTH_LONG).show();
                if (httpResponseMsg.contains("200")) {

                    noData.findViewById(R.id.noData).setVisibility(View.GONE);
                    try {
                        JSONObject jsonObject = new JSONObject(httpResponseMsg);
                        JSONArray result = jsonObject.getJSONArray("resultSet");
                        for (int i = 0; i < result.length(); i++) {
                            JSONObject ob = result.getJSONObject(i);
                            FeeData history = new FeeData(ob.getString("id"), ob.getString("session_id"), ob.getString("month_year"), ob.getString("fee_amount"), ob.getString("paid_status"), ob.getString("paid_date"), ob.getString("paid_amount"), "na");
                            feeData.add(history);
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                    recyclerView.setAdapter(feeAdapter);
                    feeAdapter.notifyDataSetChanged();
                } else {
                    noData.findViewById(R.id.noData).setVisibility(View.VISIBLE);
                }
            }
        },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(PaymentActivity.this,error.getMessage().toString(),Toast.LENGTH_LONG).show();
                    }
                });

        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);
    }

    public void onHostel(View view) {
        clickTuition.findViewById(R.id.clickTuition).setVisibility(View.GONE);
        clickTransport.findViewById(R.id.clickTransport).setVisibility(View.GONE);
        clickHostel.findViewById(R.id.clickHostel).setVisibility(View.VISIBLE);
        clickDay.findViewById(R.id.clickDayBoarding).setVisibility(View.GONE);
        feeData.clear();
        feeAdapter.notifyDataSetChanged();
       // noData.findViewById(R.id.noData).setVisibility(View.VISIBLE);
        log("hostelfee");
    }

    public void onDayBoarding(View view) {
        clickTuition.findViewById(R.id.clickTuition).setVisibility(View.GONE);
        clickTransport.findViewById(R.id.clickTransport).setVisibility(View.GONE);
        clickHostel.findViewById(R.id.clickHostel).setVisibility(View.GONE);
        clickDay.findViewById(R.id.clickDayBoarding).setVisibility(View.VISIBLE);
        feeData.clear();
        feeAdapter.notifyDataSetChanged();
        //noData.findViewById(R.id.noData).setVisibility(View.VISIBLE);
        UserLoginFunction(session, stu_id);
    }

    public void UserLoginFunction(final String session, final String stu_id) {

        class UserLoginClass extends AsyncTask<String, Void, String> {

            @Override
            protected void onPreExecute() {
                super.onPreExecute();

                progressDialog = ProgressDialog.show(PaymentActivity.this, "Loading Data", null, true, true);
            }

            @Override
            protected void onPostExecute(String httpResponseMsg) {

                super.onPostExecute(httpResponseMsg);
                progressDialog.dismiss();
                Toast.makeText(PaymentActivity.this, httpResponseMsg, Toast.LENGTH_SHORT).show();

                if (httpResponseMsg.contains("200")) {

                    noData.findViewById(R.id.noData).setVisibility(View.GONE);
                    try {
                        JSONObject jsonObject = new JSONObject(httpResponseMsg);
                        JSONArray result = jsonObject.getJSONArray("resultSet");
                        for (int i = 0; i < result.length(); i++) {
                            JSONObject ob = result.getJSONObject(i);
                            FeeData history = new FeeData(ob.getString("receipt_id"), ob.getString("session_name"), ob.getString("month_year"), ob.getString("fee_amount"), ob.getString("paid_status"), ob.getString("paid_date"), ob.getString("paid_amount"), "na");
                            feeData.add(history);
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                    recyclerView.setAdapter(feeAdapter);
                    feeAdapter.notifyDataSetChanged();
                } else {
                    noData.findViewById(R.id.noData).setVisibility(View.VISIBLE);
                }

            }

            @RequiresApi(api = Build.VERSION_CODES.KITKAT)
            @Override
            protected String doInBackground(String... params) {


               // String jsonInputString = "{\"method\":\"dayboardingfees\",\"student_id\":\""+stu_id+"\",\"session_id\":\""+session+"\"}";
                String jsonInputString = "{\"method\":\"dayboardingfees\",\"student_id\":\""+stu_id+"\",\"session_id\":\""+session+"\"}";
               // String jsonInputString = "{\"method\":\"studymaterial\",\"class_id\":\"8\",\"session_id\":\"\",\"subject_id\":\"\",\"chapter_id\":\"\"}";
                finalResult = jsonHttpParse.postRequest(jsonInputString, url);
                return finalResult;
            }
        }
        UserLoginClass userLoginClass = new UserLoginClass();
        userLoginClass.execute();
    }
}