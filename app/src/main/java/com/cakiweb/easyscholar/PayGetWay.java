package com.cakiweb.easyscholar;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.atom.mpsdklibrary.PayActivity;
import com.razorpay.Checkout;
import com.sun.org.apache.xerces.internal.impl.dv.util.Base64;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.Calendar;

public class PayGetWay extends AppCompatActivity {
    String amount,strEmail,strFname,strPhone,strSession,strMonth,strFine,strId;
    TextView fatherName,email,phone,fee,fine,session,month,id;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pay_get_way);
        Checkout.preload(getApplicationContext());
        fatherName=findViewById(R.id.conName);
        email=findViewById(R.id.conEmail);
        phone=findViewById(R.id.conPhone);
        fee=findViewById(R.id.conAmount);
        fine=findViewById(R.id.conFine);
        session=findViewById(R.id.conSession);
        month=findViewById(R.id.conMonth);
        id=findViewById(R.id.invoiceId);

        SharedPreferences sh = getSharedPreferences("MySharedPref", MODE_PRIVATE);
        String json=sh.getString("json","");

        try {
            JSONObject jsonObj1 = new JSONObject(json);
            JSONArray array = jsonObj1.getJSONArray("result");
            for (int i = 0; i < array.length(); i++) {
                JSONObject ob = array.getJSONObject(i);
                strFname=ob.getString("fathersname");
                strPhone=ob.getString("phone");
                strEmail=ob.getString("email");
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }

        Intent intent = getIntent();
        amount = intent.getStringExtra("amount");
        strSession = intent.getStringExtra("session");
        strMonth = intent.getStringExtra("month");
        strFine= intent.getStringExtra("fine");
        strId = intent.getStringExtra("id");

        fatherName.setText(strFname);
        email.setText(strEmail);
        phone.setText(strPhone);
        fee.setText(amount);
        fine.setText(strFine);
        session.setText(strSession);
        month.setText(strMonth);
        id.setText(strId);

    }
    public void onConfirm(View view) {
        Calendar c = Calendar.getInstance();
        SimpleDateFormat df = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
        String date = df.format(c.getTime());
        int amt = 1000;
        Toast.makeText(this, "Total Amount:- " + amt + "/-", Toast.LENGTH_SHORT).show();

        String amt5 = amt + ".00";
        String Email = "abc@gmail.com";
        String Phone = "1234567890";
        Intent newPayIntent = new Intent(this, PayActivity.class);
        newPayIntent.putExtra("merchantId", "197");
//txnscamt Fixed. Must be 0
        newPayIntent.putExtra("txnscamt", "0");
        newPayIntent.putExtra("loginid", "197");
        newPayIntent.putExtra("password", "Test@123");
        newPayIntent.putExtra("prodid", "NSE");
//txncurr Fixed. Must be �INR�
        newPayIntent.putExtra("txncurr", "INR");
        newPayIntent.putExtra("clientcode", encodeBase64("007"));
        newPayIntent.putExtra("custacc", "100000036600");
        newPayIntent.putExtra("channelid", "INT");
//amt  Should be 2 decimal number i.e 1.00
        newPayIntent.putExtra("amt", amount);
        newPayIntent.putExtra("txnid", "013346723");
//Date Should be in same format
        newPayIntent.putExtra("date", date);
        newPayIntent.putExtra("signature_request", "KEY123657234");
        newPayIntent.putExtra("signature_response", "KEYRESP123657234");
        newPayIntent.putExtra("discriminator", "All");
        newPayIntent.putExtra("isLive", false);
//Optinal Parameters
//Only for Name
        newPayIntent.putExtra("customerName", "abcde");
//Only for Email ID
        newPayIntent.putExtra("customerEmailID", Email);
//Only for Mobile Number
        newPayIntent.putExtra("customerMobileNo", Phone);
//Only for Address
        newPayIntent.putExtra("billingAddress", "Pune");
// Can pass any data
        newPayIntent.putExtra("optionalUdf9", "OPTIONAL DATA 2");
// Pass data in XML format, only for Multi product
        newPayIntent.putExtra("mprod", "mprod");
        startActivityForResult(newPayIntent, 1);

    }
    public void onConform(View view) {
        Toast.makeText(PayGetWay.this, "can't process now", Toast.LENGTH_SHORT).show();
    }
    public String encodeBase64 (String encode)
    {
        String decode = null;

        try {


            decode = Base64.encode(encode.getBytes());
        } catch (Exception e) {
            System.out.println("Unable to decode : " + e);
        }
        return decode;
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }

    public void onBack(View view) {
        onBackPressed();
    }
}