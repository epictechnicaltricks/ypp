package com.cakiweb.easyscholar;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;

public class LeaveApplication extends AppCompatActivity {

    RecyclerView recyclerView;
    LeaveStatusAdapter leaveStatusAdapter;
    ArrayList<NoticeData> statusData=new ArrayList<NoticeData>();
    LinearLayout appl,sta;
    EditText reason;
    TextView leaveFrom,leaveTo;
    String strReason,strForm,strTo;
    String finalResult,stu_id,session ;
    String url = "index.php/Api_request/api_list";
    Boolean CheckEditText ;
    ProgressDialog progressDialog;
    HashMap<String,String> hashMap = new HashMap<>();
    HttpParse httpParse = new HttpParse();
    JsonHttpParse jsonHttpParse = new JsonHttpParse();

    private Calendar calendar;
    private int year, month, day;
    String strYear,strMon,strDay,api;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_leave_application);

        recyclerView =findViewById(R.id.leaveStatusRecycler);
        recyclerView.setHasFixedSize(true);


        leaveStatusAdapter=new LeaveStatusAdapter(statusData,LeaveApplication.this);
        recyclerView.setAdapter(leaveStatusAdapter);


        LinearLayoutManager linearLayoutManager=new LinearLayoutManager(LeaveApplication.this,LinearLayoutManager.VERTICAL,false);
        recyclerView.setLayoutManager(linearLayoutManager);

        reason=findViewById(R.id.leaveReason);
        leaveFrom=findViewById(R.id.leave_form);
        leaveTo=findViewById(R.id.leave_to);

        appl = findViewById(R.id.appl);
        sta = findViewById(R.id.applSta);
        appl.findViewById(R.id.appl).setVisibility(View.VISIBLE);
        sta.findViewById(R.id.applSta).setVisibility(View.GONE);

        calendar = Calendar.getInstance();
        year = calendar.get(Calendar.YEAR);

        month = calendar.get(Calendar.MONTH);
        day = calendar.get(Calendar.DAY_OF_MONTH);
        //showDate(year, month+1, day);
        //showDate1(year, month+1, day);

        SharedPreferences sh = getSharedPreferences("MySharedPref", MODE_PRIVATE);
        stu_id = sh.getString("student_id", "");
        session = sh.getString("session", "");
        api=sh.getString("api1","");
        url=api+url;
//        for (int i=0; i<10; i++ ) {
//            NoticeData history=new NoticeData("Annual Sports","participation is mandatory","02 apr 2021");
//            statusData.add(history);
//        }

    }

    private void showDate(int year, int i, int day) {
        leaveFrom.setText(new StringBuilder().append(day).append("-")
                .append(i).append("-").append(year));
        strForm=leaveFrom.getText().toString().trim();

    }
    private void showDate1(int year, int i, int day) {
        leaveTo.setText(new StringBuilder().append(day).append("-")
                .append(i).append("-").append(year));
        strTo=leaveTo.getText().toString().trim();
    }

    @SuppressWarnings("deprecation")
    public void onLeaveForm(View view) {
        showDialog(999);
    }
    @SuppressWarnings("deprecation")
    public void onLeaveTo(View view) {
        showDialog(998);
    }

    @Override
    protected Dialog onCreateDialog(int id) {
        // TODO Auto-generated method stub
        if (id == 999) {
            return new DatePickerDialog(this,
                    myDateListener, year, month, day);
        }
        if (id == 998) {
            return new DatePickerDialog(this,
                    myDateListener1, year, month, day);
        }
        return null;
    }
    private DatePickerDialog.OnDateSetListener myDateListener = new
            DatePickerDialog.OnDateSetListener() {
                @Override
                public void onDateSet(DatePicker arg0,
                                      int arg1, int arg2, int arg3) {
                    // TODO Auto-generated method stub
                    strYear = Integer.toString(arg1 );
                    strMon = Integer.toString(arg2 );
                    strDay = Integer.toString(arg3 );

                    showDate(arg1, arg2+1, arg3);
                }
            };
    private DatePickerDialog.OnDateSetListener myDateListener1 = new
            DatePickerDialog.OnDateSetListener() {
                @Override
                public void onDateSet(DatePicker arg0,
                                      int arg1, int arg2, int arg3) {
                    // TODO Auto-generated method stub
                    strYear = Integer.toString(arg1 );
                    strMon = Integer.toString(arg2 );
                    strDay = Integer.toString(arg3 );

                    showDate1(arg1, arg2+1, arg3);
                }
            };

    public void onBack(View view) {
        onBackPressed();
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }

    public void onSubmit(View view) {
        CheckEditTextIsEmptyOrNot();

        if (CheckEditText) {
            UserLoginFunction(stu_id,strForm,strTo,strReason,session);
        } else {
            Toast.makeText(LeaveApplication.this, "Please fill all form fields.", Toast.LENGTH_LONG).show();
        }

    }
    public void CheckEditTextIsEmptyOrNot(){

        strReason = reason.getText().toString().trim();
        strForm = leaveFrom.getText().toString().trim();
        strTo = leaveTo.getText().toString().trim();

        if(TextUtils.isEmpty(strReason) || TextUtils.isEmpty(strForm) || TextUtils.isEmpty(strTo))
        {
            CheckEditText = false;
        }
        else {
            CheckEditText = true ;
        }
    }

    public void onStatus(View view) {
        appl.findViewById(R.id.appl).setVisibility(View.GONE);
        sta.findViewById(R.id.applSta).setVisibility(View.VISIBLE);
        UserLeave(stu_id,session);
    }
    public void UserLoginFunction(final String stu_id,final String leaveFrom,final String leaveTo,final String reason,final String session){

        class UserLoginClass extends AsyncTask<String,Void,String> {

            @Override
            protected void onPreExecute() {
                super.onPreExecute();

                progressDialog = ProgressDialog.show(LeaveApplication.this,"Loading Data",null,true,true);
            }

            @Override
            protected void onPostExecute(String httpResponseMsg) {

                super.onPostExecute(httpResponseMsg);
                progressDialog.dismiss();
                Toast.makeText(LeaveApplication.this, httpResponseMsg, Toast.LENGTH_SHORT).show();
                if(httpResponseMsg.contains("200")){
                    try {
                        JSONObject jsonObject = new JSONObject(httpResponseMsg);
                        Toast.makeText(LeaveApplication.this, jsonObject.getString("msg"), Toast.LENGTH_SHORT).show();


                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                    //finish();
                    //startActivity(new Intent(LoginActivity.this,HomeActivity.class));
                }
                else{
                    Toast.makeText(LeaveApplication.this, "error", Toast.LENGTH_SHORT).show();
                }

            }

            @Override
            protected String doInBackground(String... params) {

                //String jsonInputString = "{\"method\":\"applyleavestudent\",\"student_id\":\""+stu_id+"\",\"from_date\":\"2021-04-01\",\"to_date\":\"2021-04-02\",\"leave_reason\":\"Due to festival at home\",\"session\":\"2021-2022\"}";
                String jsonInputString = "{\"method\":\"applyleavestudent\",\"student_id\":\""+stu_id+"\",\"from_date\":\""+leaveFrom+"\",\"to_date\":\""+leaveTo+"\",\"leave_reason\":\""+reason+"\",\"session\":\""+session+"\"}";

                finalResult = jsonHttpParse.postRequest(jsonInputString, url);
                return finalResult;
            }
        }
        UserLoginClass userLoginClass = new UserLoginClass();
        userLoginClass.execute();
    }
    public void UserLeave(final String stu_id,final String session){

        class UserLeaveClass extends AsyncTask<String,Void,String> {

            @Override
            protected void onPreExecute() {
                super.onPreExecute();

                progressDialog = ProgressDialog.show(LeaveApplication.this,"Loading Data",null,true,true);
            }

            @Override
            protected void onPostExecute(String httpResponseMsg) {

                super.onPostExecute(httpResponseMsg);
                progressDialog.dismiss();
                Toast.makeText(LeaveApplication.this, httpResponseMsg, Toast.LENGTH_SHORT).show();
                if(httpResponseMsg.contains("200")){
                    try {
                        JSONObject jsonObject = new JSONObject(httpResponseMsg);
                        JSONArray result = jsonObject.getJSONArray("resultSet");
                        for (int i = 0; i < result.length(); i++) {
                            JSONObject ob = result.getJSONObject(i);
                            NoticeData history=new NoticeData(ob.getString("from_date"),ob.getString("leave_reason"),ob.getString("to_date"),"","");
                            statusData.add(history);
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                    recyclerView.setAdapter(leaveStatusAdapter);
                    leaveStatusAdapter.notifyDataSetChanged();
                }
                else{
                    Toast.makeText(LeaveApplication.this, "error", Toast.LENGTH_SHORT).show();
                }

            }

            @Override
            protected String doInBackground(String... params) {

                //String jsonInputString = "{\"method\":\"leavestatus\",\"student_id\":\""+stu_id+"\",\"session\":\"2021-2022\"}";
                String jsonInputString = "{\"method\":\"leavestatus\",\"student_id\":\""+stu_id+"\",\"session\":\""+session+"\"}";
                finalResult = jsonHttpParse.postRequest(jsonInputString, url);
                return finalResult;
            }
        }
        UserLeaveClass userLeaveClass = new UserLeaveClass();
        userLeaveClass.execute();
    }


}