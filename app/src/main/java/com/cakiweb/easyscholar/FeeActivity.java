package com.cakiweb.easyscholar;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.ColorStateList;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.coordinatorlayout.widget.CoordinatorLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.RecyclerView.Adapter;

import com.google.android.material.appbar.AppBarLayout;
import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayout.OnTabSelectedListener;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Timer;
import java.util.TimerTask;


public class FeeActivity extends  AppCompatActivity  { 
	
	private final Timer _timer = new Timer();
	
	private Toolbar _toolbar;
	private AppBarLayout _app_bar;
	private CoordinatorLayout _coordinator;
	private HashMap<String, Object> map = new HashMap<>();
	private String list = "";
	private double num = 0;
	private String api = "";
	private HashMap<String, Object> api_map = new HashMap<>();
	private String stu_id = "";
	private double amt_ = 0;
	private double pos = 0;
	
	private ArrayList<HashMap<String, Object>> results = new ArrayList<>();
	private final ArrayList<HashMap<String, Object>> listmap = new ArrayList<>();
	private final ArrayList<Double> add_position = new ArrayList<>();

	private final ArrayList<Integer> itemrow = new ArrayList<>();
	private HashMap<String, Object> itemrow_map = new HashMap<>();
	
	private LinearLayout linear1;
	private LinearLayout linear3;
	private LinearLayout pay;
	private LinearLayout linear5;
	private TabLayout tablayout1;
	private RecyclerView recyclerview1;
	private TextView msg;
	private TextView textview8;
	private TextView total_pending;
	private TextView textview9;
	private TextView textview6;
	
	private RequestNetwork in;
	private RequestNetwork.RequestListener _in_request_listener;
	private RequestNetwork.RequestListener this_is_2;
	private TimerTask time;

	String class_id,session_id;

	double amount = 0;

	TextView amount_text;

	private ArrayList<HashMap<String, Object>> selected_payment_pos = new ArrayList<>();

	private HashMap<String, Object> selected_payment_map = new HashMap<>();


	@Override
	protected void onCreate(Bundle _savedInstanceState) {
		super.onCreate(_savedInstanceState);
		setContentView(R.layout.fee);
		initialize(_savedInstanceState);
		initializeLogic();
	}
	
	private void initialize(Bundle _savedInstanceState) {
		


		linear1 = findViewById(R.id.linear1);
		linear3 = findViewById(R.id.linear3);
		pay = findViewById(R.id.pay);
		linear5 = findViewById(R.id.linear5);
		tablayout1 = findViewById(R.id.tablayout1);
		recyclerview1 = findViewById(R.id.recyclerview1);
		msg = findViewById(R.id.msg);
		textview8 = findViewById(R.id.textview8);
		total_pending = findViewById(R.id.total_pending);
		textview9 = findViewById(R.id.textview9);
		textview6 = findViewById(R.id.textview6);
		in = new RequestNetwork(this);

		amount_text = findViewById(R.id.amount_text);


		SharedPreferences sh = getSharedPreferences("MySharedPref", MODE_PRIVATE);
		stu_id = sh.getString("student_id", "");
		class_id= sh.getString("class_id", "");
		api=sh.getString("api","");
		session_id = sh.getString("session_id","");
		
		tablayout1.setOnTabSelectedListener(new OnTabSelectedListener() {
			@Override
			public void onTabSelected(TabLayout.Tab tab) {
				final int _position = tab.getPosition();
				switch(_position) {
					case (0): {
						time = new TimerTask() {
							@Override
							public void run() {
								runOnUiThread(new Runnable() {
									@Override
									public void run() {
										_request_api("tuitionfee");
										time.cancel();
									}
								});
							}
						};
						_timer.schedule(time, 1500);
						break;
					}
					case (1): {
						_request_api("transportfee");
						break;
					}
					case (2): {
						_request_api("hostelfee");
						break;
					}
					case (3): {
						_request_api("tuitionfee");
						break;
					}
					default: {
						in.startRequestNetwork(RequestNetworkController.GET, "https://yppschool.com/erp/index.php/Api_request/api_list?method=tuitionfee&student_id=845", "", _in_request_listener);
						break;
					}
				}
			}
			
			@Override
			public void onTabUnselected(TabLayout.Tab tab) {
				final int _position = tab.getPosition();
				
			}
			
			@Override
			public void onTabReselected(TabLayout.Tab tab) {
				final int _position = tab.getPosition();
				
			}
		});



		this_is_2 = new RequestNetwork.RequestListener() {
			@Override
			public void onResponse(String _param1, String _param2, HashMap<String, Object> _param3) {
				final String _tag = _param1;
				final String _response = _param2;
				final HashMap<String, Object> _responseHeaders = _param3;

				showMessage(_response);

			}

			@Override
			public void onErrorResponse(String _param1, String _param2) {
				final String _tag = _param1;
				final String _message = _param2;
				showMessage( _message);
				msg.setText("No internet!");
			}
		};

		_in_request_listener = new RequestNetwork.RequestListener() {
			@Override
			public void onResponse(String _param1, String _param2, HashMap<String, Object> _param3) {
				final String _tag = _param1;
				final String _response = _param2;
				final HashMap<String, Object> _responseHeaders = _param3;
				results.clear();
				selected_payment_pos.clear();
				selected_payment_map.clear();
				_refresh();

				try {
					if (_response.contains("200")) {

						// below for other option select option
						//if you not clear maybe if you select some
						// item on tutionfee it will also select on hostel fee at time


						for(int x = 0; x<100 ; ++x) {
							selected_payment_map = new HashMap<>();
							selected_payment_map.put("Select", "False");
							selected_payment_pos.add(selected_payment_map);


							/*itemrow_map = new HashMap<>();
							itemrow_map.put("fee", "");
							itemrow_map.put("id","" );
							listmap.add(itemrow_map);*/

						}


						/*for(int x = 0; x<5 ; ++x) {


							itemrow_map = new HashMap<>();
							itemrow_map.put("fee", "");
							itemrow_map.put("itemrow","" );
							listmap.add(itemrow_map);

						}*/

						map = new Gson().fromJson(_response, new TypeToken<HashMap<String, Object>>(){}.getType());
						list = (new Gson()).toJson(map.get("resultSet"), new TypeToken<ArrayList<HashMap<String, Object>>>(){}.getType());
						results = new Gson().fromJson(list, new TypeToken<ArrayList<HashMap<String, Object>>>(){}.getType());
						Collections.reverse(results);
						if (results.size() > 0) {
							_refresh();
							msg.setVisibility(View.GONE);
							total_pending.setText("₹".concat(String.valueOf((long)(_featch_pending_amt()))));
						}
						else {
							msg.setText("No data found.");
							total_pending.setText("No data");
						}
					}
					else {
						msg.setText("No data found.");
						total_pending.setText("No data");
					}
				} catch(Exception e) {
					showMessage( "Error ");
				}
			}
			
			@Override
			public void onErrorResponse(String _param1, String _param2) {
				final String _tag = _param1;
				final String _message = _param2;
				showMessage( _message);
				msg.setText("No internet!");
			}
		};
	}
	
	private void initializeLogic() {

		// this activity created by shubhamjeet at 21 oct 2022 , 3.23pm




		pay.setOnClickListener(view -> {

			if(amount>0)
			{
				Intent in = new Intent(Intent.ACTION_VIEW);
				in.setData(Uri.parse(_request_amount_api(amount+"")));
				startActivity(in);
			}else {

				pay.setVisibility(View.GONE);
				showMessage("Invalid payment");
				startActivity(new Intent(this,FeeActivity.class));
				finish();

			}



		});

		tablayout1.addTab(tablayout1.newTab().setText("Tuition"));
		tablayout1.addTab(tablayout1.newTab().setText("Hostel"));
		tablayout1.addTab(tablayout1.newTab().setText("Transport"));
		tablayout1.addTab(tablayout1.newTab().setText("Day Boarding"));
		tablayout1.setTabTextColors(0xFF757575, 0xFF1877F2);
		tablayout1.setTabRippleColor(new ColorStateList(new int[][]{new int[]{android.R.attr.state_pressed}},
		
		new int[] {0xFFBBDEFB}));
		tablayout1.setSelectedTabIndicatorColor(0xFF1877F2);
		tablayout1.setSelectedTabIndicatorHeight(10);
		pay.setVisibility(View.GONE);
		pay.setElevation((float)25);
		tablayout1.setElevation((float)25);





	}
	
	@Override
	protected void onActivityResult(int _requestCode, int _resultCode, Intent _data) {
		
		super.onActivityResult(_requestCode, _resultCode, _data);

	}
	
	public void _refresh () {
		recyclerview1.setAdapter(new Recyclerview1Adapter(results));
		recyclerview1.setLayoutManager(new LinearLayoutManager(this));
	}



	public String _request_amount_api (final String _amount) {

		String method = "method="+ "fee_payment" ;
		String stuid_ =  "&student_id="  + stu_id;
		String stu_sees = "&student_session=" + session_id;
		String stu_class ="&student_class=" +class_id;
		String fee =     "&fee" +"(\"32\",\"3\")" ;
		String itemrow = "&itemrow=" +"(\"35969\",\"35970\")" ;
		String amt =     "&amount_paid=" +_amount ;


		return api+method+stuid_+stu_sees+stu_class+amt+fee+itemrow;

		}



	public void _request_api (final String _method) {
		api_map = new HashMap<>();
		api_map.put("method", _method);
		api_map.put("student_id", stu_id);
		pay.setVisibility(View.GONE);
		msg.setVisibility(View.VISIBLE);
		msg.setText("Getting data");
		num = 0;
		amt_ = 0;      // this is check box count
		pos = 0;      // this is payable amount position
		amount = 0;  // this is money count



		in.setParams(api_map, RequestNetworkController.REQUEST_PARAM);
		in.startRequestNetwork(RequestNetworkController.GET, api, "no tag", _in_request_listener);


	}

	public void _request_api_send_server (final String _method) {
		api_map = new HashMap<>();
		api_map.put("method", _method);
		api_map.put("student_id", stu_id);
		pay.setVisibility(View.GONE);
		msg.setVisibility(View.VISIBLE);
		msg.setText("Getting data");
		num = 0;
		amt_ = 0;      // this is check box count
		pos = 0;      // this is payable amount position
		amount = 0;  // this is money count



		in.setParams(api_map, RequestNetworkController.REQUEST_PARAM);
		in.startRequestNetwork(RequestNetworkController.GET, api, "no tag", _in_request_listener);


	}



	public void _add_data_to_pos (final double _pos) {
		add_position.add(Double.valueOf(_pos));
	}
	
	
	public double _featch_pending_amt () {
		amt_ = 0;
		pos = 0;
		for(int _repeat10 = 0; _repeat10 < results.size(); _repeat10++) {
			if (results.get((int)pos).get("paid_status").toString().equals("Pending")) {
				amt_ = amt_ + Double.parseDouble(results.get((int)pos).get("fee_amount").toString());
			}
			pos++;
		}
		return (amt_);
	}
	
	
	public class Recyclerview1Adapter extends Adapter<Recyclerview1Adapter.ViewHolder> {
		ArrayList<HashMap<String, Object>> _data;
		public Recyclerview1Adapter(ArrayList<HashMap<String, Object>> _arr) {
			_data = _arr;
		}
		
		@Override
		public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
			LayoutInflater _inflater = (LayoutInflater)getBaseContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
			View _v = _inflater.inflate(R.layout.fees_cus, null);
			RecyclerView.LayoutParams _lp = new RecyclerView.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
			_v.setLayoutParams(_lp);
			return new ViewHolder(_v);
		}
		
		@Override
		public void onBindViewHolder(ViewHolder _holder, final int _position) {
			View _view = _holder.itemView;
			
			final LinearLayout bg = _view.findViewById(R.id.bg);
			final LinearLayout top_bg = _view.findViewById(R.id.top_bg);
			final LinearLayout date_layout = _view.findViewById(R.id.date_layout);
			final LinearLayout linear4 = _view.findViewById(R.id.linear4);
			final LinearLayout linear5 = _view.findViewById(R.id.linear5);
			final TextView status = _view.findViewById(R.id.status);
			final TextView date = _view.findViewById(R.id.date);
			final TextView amt = _view.findViewById(R.id.amt);
			final TextView fine = _view.findViewById(R.id.fine);
			final TextView payment_date = _view.findViewById(R.id.payment_date);
			final CheckBox select = _view.findViewById(R.id.select);
			
			try {
				payment_date.setText("Payment date : ".concat(results.get(_position).get("paid_date").toString()));
				amt.setText("₹".concat(results.get(_position).get("fee_amount").toString()).replace(".00", ""));
				date.setText(results.get(_position).get("month_year").toString());
				fine.setText("No fine");
				if (results.get(_position).get("paid_status").toString().equals("Paid")) {
					status.setText("Paid on");
					status.setTextColor(0xFF1B5E20);
					select.setVisibility(View.GONE);
					top_bg.setBackgroundColor(0xFFE8F5E9);
				}
				else {
					status.setTextColor(0xFFFF6F00);
					status.setText("Pending");
					payment_date.setText("");
					select.setVisibility(View.VISIBLE);
					top_bg.setBackgroundColor(0xFFFFF8E1);
				}
				bg.setElevation((float)20);




				select.setText(results.get(_position).get("id").toString());





			   /*	if (add_position.contains(_position)) {
					select.setText("Selected");
					pay.setVisibility(View.VISIBLE);
					date_layout.setBackgroundColor(0xFF388E3C);
				}
				else {
					date_layout.setBackgroundColor(0xFF3F51B5);
					select.setText("itemrow[ ]");
					if (num == 0) {
						pay.setVisibility(View.GONE);
					}
				}

				*/


				select.setOnClickListener(_view1 -> {



					try{

						if (select.isChecked()) {


							itemrow_map = new HashMap<>();
							itemrow_map.put("method", "fee_payment");
							itemrow_map.put("fee", results.get(_position).get("fee_amount").toString());
							itemrow_map.put("itemrow", results.get(_position).get("id").toString());
							listmap.add(itemrow_map);
							in.setParams(itemrow_map, RequestNetworkController.REQUEST_PARAM);
							in.startRequestNetwork(RequestNetworkController.POST, api, "no tag", this_is_2);





							num++;
							selected_payment_pos.get(_position).put("Select", "True");
							add_position.add(Double.valueOf(_position));




							//itemrow.add(Integer.parseInt(results.get(_position).get("id").toString()));



							select.setText("Selected");
							pay.setVisibility(View.VISIBLE);
							date_layout.setBackgroundColor(0xFF388E3C);

							amount = amount + Double.parseDouble(amt.getText().toString().replaceAll("₹",""));


						}
						else {
							num--;


							if (selected_payment_pos.get(_position).get("Select").toString().equals("True")) {
								selected_payment_pos.remove(_position);
							}



							//itemrow.remove(Double.parseDouble(results.get(_position).get("id").toString()));

							amount = amount - Double.parseDouble(amt.getText().toString().replaceAll("₹",""));
							add_position.remove((int)(num));
							date_layout.setBackgroundColor(0xFF3F51B5);
							select.setText("Not selected");

							if (num == 0) {

								pay.setVisibility(View.GONE);

							}

							//listmap.remove(_position);

						}

						//showMessage(String.valueOf(amount));
						amount_text.setText("₹"+(int)amount);


						total_pending.setText("₹".concat(String.valueOf((long)(_featch_pending_amt()-amount))));

						if(total_pending.getText().equals("₹0")){

							total_pending.setText("all clear");
						}


						if (selected_payment_pos.get(_position).get("Select").toString().equals("True")) {

							select.setText("Selected");
							select.setChecked(true);
							pay.setVisibility(View.VISIBLE);
							date_layout.setBackgroundColor(0xFF388E3C);

						}
						else {
							select.setChecked(false);
							date_layout.setBackgroundColor(0xFF3F51B5);
							select.setText("Not selected");


						}

						showMessage("itemrow[ ] value \n\n"+new Gson().toJson(listmap));


					}catch(Exception e)
					{
						showMessage(e +"\n\n467 LINE");
					}





				});
			} catch(Exception e) {
				showMessage( "Error on parameters ");
			}
		}
		
		@Override
		public int getItemCount() {
			return _data.size();
		}
		
		public class ViewHolder extends RecyclerView.ViewHolder{
			public ViewHolder(View v){
				super(v);
			}
		}
		
	}
	
	@Deprecated
	public void showMessage(String _s) {
		Toast.makeText(getApplicationContext(), _s, Toast.LENGTH_SHORT).show();
	}

	public void onBack(View view) {
		finish();
	}
}
