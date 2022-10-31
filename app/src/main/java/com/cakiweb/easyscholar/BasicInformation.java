package com.cakiweb.easyscholar;

import android.app.ProgressDialog;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.bumptech.glide.Glide;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class BasicInformation extends AppCompatActivity {
    int i=2;
    String api;
    TextView id,name,dob,gender,email,presentaddress,fathersname,mothername,stuClass,section,phone,aadhar,session,SLno,addNo,cast,fProf,mProf,mCon,mEmail;
    String student_id,strid,strname,strlast,strdate,strgender,stremail,strpresentadd,strfather,strmother,strClassname,strSession,strphone,strAadhar,strCast,strMcon,strMProf,strFProf,strSlno,strAdd;
   ImageView profileImg;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_basic_information);

        id=(TextView) findViewById(R.id.stuId);
        name=(TextView) findViewById(R.id.studentName);
        SLno=findViewById(R.id.slNo);
        addNo=findViewById(R.id.admissionNo);
        cast=findViewById(R.id.caste);
        mProf=findViewById(R.id.motherProfession);
        fProf=findViewById(R.id.fatherProfession);
        mEmail=findViewById(R.id.motherEmail);
        mCon=findViewById(R.id.motherConNo);
        dob=(TextView) findViewById(R.id.dateOfBirth);
        gender=(TextView) findViewById(R.id.gender);
        email=(TextView) findViewById(R.id.fatherEmail);
        presentaddress=(TextView) findViewById(R.id.address);
        fathersname=(TextView) findViewById(R.id.fatherName);
        mothername=(TextView) findViewById(R.id.motherName);
        stuClass=(TextView) findViewById(R.id.stuClass);
        section=(TextView) findViewById(R.id.section);
        phone=(TextView) findViewById(R.id.fatherConNo);
        aadhar=(TextView) findViewById(R.id.adhar);
        session=(TextView) findViewById(R.id.session);

        profileImg = findViewById(R.id.profileImg);


        SharedPreferences sh = getSharedPreferences("MySharedPref", MODE_PRIVATE);
        student_id =sh.getString("student_id","");
        api=sh.getString("api","");
        //Toast.makeText(BasicInformation.this,student_id,Toast.LENGTH_LONG).show();
        log();
    }
    public void log(){
        ProgressDialog loading = ProgressDialog.show(this,"Please wait...","Fetching...",false,false);
        //String url = HttpURL+"?method=exam&userId="+id;
        String url = api+"method=getStudentData&student_id="+student_id;
        StringRequest stringRequest = new StringRequest(url, new Response.Listener<String>() {
            @Override
            public void onResponse(String httpResponseMsg) {
                loading.dismiss();
                //showJSON(response);
                //Toast.makeText(BasicInformation.this,httpResponseMsg,Toast.LENGTH_LONG).show();
                if(httpResponseMsg.contains("200")){
                    try {
                        JSONObject jsonObject = new JSONObject(httpResponseMsg);
                        JSONArray array = jsonObject.getJSONArray("resultSet");
                        for (int i = 0; i < array.length(); i++) {
                            JSONObject ob = array.getJSONObject(0);
                            //Toast.makeText(this,""+ob,Toast.LENGTH_SHORT).show();
                            strid=ob.getString("student_id");
                            strname=ob.getString("student_first_name");
                            strlast=ob.getString("student_last_name");
                            strfather=ob.getString("father_name");
                            strmother=ob.getString("mother_name");
                            strpresentadd=ob.getString("present_address");
                            strphone=ob.getString("student_mobile");
                            strdate=ob.getString("student_dob");
                            stremail=ob.getString("student_email");
                            strgender=ob.getString("student_gender");
                            strClassname=ob.getString("class_name");
                            strAadhar=ob.getString("id_proof");
                            strSession=ob.getString("session_name");
                            strCast=ob.getString("caste");
                            strMcon=ob.getString("mother_contact_no");
                            strFProf=ob.getString("father_income_source");
                            strMProf=ob.getString("mother_income_source");
                            strSlno = ob.getString("student_serial_no");
                            strAdd = ob.getString("admission_id");

                            Glide.with(getApplicationContext()).load(
                                    Uri.parse(ob.getString("student_profile_photo")))
                                    .error(R.drawable.avatar)
                                    .placeholder(R.color.selected_gray)
                                    .thumbnail(0.01f)
                                    .into(profileImg);


                            SharedPreferences sharedPreferences = getSharedPreferences("MySharedPref", MODE_PRIVATE);
                            SharedPreferences.Editor myEdit = sharedPreferences.edit();

                            myEdit.putString("student_name",strname+" "+strlast);
                            myEdit.putString("profile_IMG",ob.getString("student_profile_photo"));
                            myEdit.apply();

                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                    name.setText(strname+" "+strlast);
                    id.setText(strid);
                    fathersname.setText(strfather);
                    mothername.setText(strmother);
                    presentaddress.setText(strpresentadd);
                    phone.setText(strphone);
                    email.setText(stremail);
                    dob.setText(strdate);
                    gender.setText(strgender);
                    stuClass.setText(strClassname);
                    aadhar.setText(strAadhar);
                    session.setText(strSession);
                    cast.setText(strCast);
                    mCon.setText(strMcon);
                    mProf.setText(strMProf);
                    fProf.setText(strFProf);
                    SLno.setText(strSlno);
                    addNo.setText(strAdd);
                }
                else{
                    Toast.makeText(BasicInformation.this, httpResponseMsg, Toast.LENGTH_SHORT).show();
                }
            }
        },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(BasicInformation.this,error.getMessage().toString(),Toast.LENGTH_LONG).show();
                    }
                });

        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);
    }



    public void onBack(View view) {
        finish();
    }
}