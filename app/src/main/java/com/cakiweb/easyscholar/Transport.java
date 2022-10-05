package com.cakiweb.easyscholar;

import android.app.ProgressDialog;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;

public class Transport extends AppCompatActivity {
    TextView root,stoppsgr;
    String url = "https://yppschool.com/erp/index.php/Api_request/api_list";
    ProgressDialog progressDialog;
    HashMap<String, String> hashMap = new HashMap<>();
    HttpParse httpParse = new HttpParse();
    JsonHttpParse jsonHttpParse = new JsonHttpParse();
    String finalResult,stu_id;
    CardView transport;
    LinearLayout noData;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_transport);
        root=findViewById(R.id.root);
        stoppsgr=findViewById(R.id.stoppage);
        transport=findViewById(R.id.layoutTransport);
        noData=findViewById(R.id.noDataTransport);
        noData.findViewById(R.id.noDataTransport).setVisibility(View.GONE);
        transport.findViewById(R.id.layoutTransport).setVisibility(View.VISIBLE);
        SharedPreferences sh = getSharedPreferences("MySharedPref", MODE_PRIVATE);
        stu_id = sh.getString("student_id", "");
        UserLoginFunction(stu_id);
    }
    public void UserLoginFunction(final String stu_id) {

        class UserLoginClass extends AsyncTask<String, Void, String> {

            @Override
            protected void onPreExecute() {
                super.onPreExecute();

                progressDialog = ProgressDialog.show(Transport.this, "Loading Data", null, true, true);
            }

            @Override
            protected void onPostExecute(String httpResponseMsg) {

                super.onPostExecute(httpResponseMsg);
                progressDialog.dismiss();
                 //Toast.makeText(Transport.this, httpResponseMsg, Toast.LENGTH_SHORT).show();

                if (httpResponseMsg.contains("200")) {

                   // noData.findViewById(R.id.noDataEvent).setVisibility(View.GONE);
                    try {
                        JSONObject jsonObject = new JSONObject(httpResponseMsg);
                        JSONObject result = jsonObject.getJSONObject("resultSet");

                        stoppsgr.setText(result.getString("stoppage_name"));
                        root.setText(result.getString("route_name"));


                    } catch (JSONException e) {
                        e.printStackTrace();
                    }

                } else {
                   // noData.findViewById(R.id.noDataEvent).setVisibility(View.VISIBLE);
                    noData.findViewById(R.id.noDataTransport).setVisibility(View.VISIBLE);
                    transport.findViewById(R.id.layoutTransport).setVisibility(View.GONE);
                    Toast.makeText(Transport.this, "no data found", Toast.LENGTH_SHORT).show();
                }

            }

            @RequiresApi(api = Build.VERSION_CODES.KITKAT)
            @Override
            protected String doInBackground(String... params) {
                String jsonInputString = "{\"method\":\"transportdetails\",\"student_id\":\""+stu_id+"\"}";
                finalResult = jsonHttpParse.postRequest(jsonInputString, url);
                return finalResult;
            }
        }
        UserLoginClass userLoginClass = new UserLoginClass();
        userLoginClass.execute();
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