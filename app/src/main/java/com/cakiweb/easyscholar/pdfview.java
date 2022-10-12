package com.cakiweb.easyscholar;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.util.ArrayList;
import java.util.HashMap;


public class pdfview extends  Activity {
	
	
	private String pdfURL = "";
	
	private RelativeLayout linear1;
	private LinearLayout webLayout;
	private LinearLayout progress;
	private WebView webview1;
	private ProgressBar progressbar1;
	
	private RequestNetwork in;
	private RequestNetwork.RequestListener _in_request_listener;


	private HashMap<String, Object> map = new HashMap<>();
	private String list = "";
	private String url = "";

	private ArrayList<HashMap<String, Object>> results = new ArrayList<>();


	private RequestNetwork req;
	private RequestNetwork.RequestListener _req_request_listener;

	String id,stu_id,class_id,api,session_id;

	@Override
	protected void onCreate(Bundle _savedInstanceState) {
		super.onCreate(_savedInstanceState);
		setContentView(R.layout.pdfview_layout);
		initialize(_savedInstanceState);
		initializeLogic();
	}
	
	@SuppressLint("SetJavaScriptEnabled")
	private void initialize(Bundle _savedInstanceState) {
		
		linear1 = (RelativeLayout) findViewById(R.id.linear1);
		webLayout = (LinearLayout) findViewById(R.id.webLayout);
		progress = (LinearLayout) findViewById(R.id.progress);
		webview1 = (WebView) findViewById(R.id.webview1);
		webview1.getSettings().setJavaScriptEnabled(true);
		webview1.getSettings().setSupportZoom(true);
		progressbar1 = (ProgressBar) findViewById(R.id.progressbar1);




		in = new RequestNetwork(this);
		req = new RequestNetwork(this);





		SharedPreferences sh = getSharedPreferences("MySharedPref", MODE_PRIVATE);
		stu_id = sh.getString("student_id", "");
		class_id= sh.getString("class_id", "");
		api=sh.getString("api","");
		session_id = sh.getString("session_id","");






		webview1.setWebViewClient(new WebViewClient() {
			@Override
			public void onPageStarted(WebView _param1, String _param2, Bitmap _param3) {
				final String _url = _param2;
				if (!_url.contains("embedded=true")) {
					_Open_url(_url);
					webview1.goBack();
				}
				progress.setVisibility(View.VISIBLE);
				in.startRequestNetwork(RequestNetworkController.GET, "http://example.com/", "check_connection", _in_request_listener);
				super.onPageStarted(_param1, _param2, _param3);
			}
			
			@Override
			public void onPageFinished(WebView _param1, String _param2) {
				final String _url = _param2;
				progress.setVisibility(View.GONE);
				if (!_url.contains("embedded=true")) {
					_Open_url(_url);
					webview1.goBack();
				}
				super.onPageFinished(_param1, _param2);
			}
		});
		
		_in_request_listener = new RequestNetwork.RequestListener() {
			@Override
			public void onResponse(String _param1, String _param2, HashMap<String, Object> _param3) {
				final String _tag = _param1;
				final String _response = _param2;
				final HashMap<String, Object> _responseHeaders = _param3;



				webLayout.setVisibility(View.VISIBLE);
			}
			
			@Override
			public void onErrorResponse(String _param1, String _param2) {
				final String _tag = _param1;
				final String _message = _param2;
				webLayout.setVisibility(View.GONE);
				showMessage( "No internet !");
			}
		};





		_req_request_listener = new RequestNetwork.RequestListener() {
			@Override
			public void onResponse(String _param1, String _param2, HashMap<String, Object> _param3) {
				final String _tag = _param1;
				final String _response = _param2;
				final HashMap<String, Object> _responseHeaders = _param3;
				try {
					if (_response.contains("200")) {
						map = new Gson().fromJson(_response, new TypeToken<HashMap<String, Object>>(){}.getType());
						list = (new Gson()).toJson(map.get("resultSet"), new TypeToken<ArrayList<HashMap<String, Object>>>(){}.getType());
						results = new Gson().fromJson(list, new TypeToken<ArrayList<HashMap<String, Object>>>(){}.getType());

						url = results.get((int)0).get("attachment").toString();

						loadPDF(url);

						Toast.makeText(pdfview.this, results.get((int)0).get("session_name").toString(), Toast.LENGTH_SHORT).show();
					}
				} catch(Exception e) {
					Toast.makeText(pdfview.this, "Error on Fetch", Toast.LENGTH_SHORT).show();
				}
			}

			@Override
			public void onErrorResponse(String _param1, String _param2) {
				final String _tag = _param1;
				final String _message = _param2;
				showMessage( "No internet !");
			}
		};






	}
	
	private void initializeLogic() {


if(getIntent().getStringExtra("request").equals("calender")) {

	API_request("academic_calendar", class_id,session_id,api);

}

if(getIntent().getStringExtra("request").equals("syllabus")) {

	API_request("syllabus", class_id,session_id,api);

}





		//https://yppschool.com/erp/index.php/Api_request/api_list?method=attendance&student_id=643

		//https://yppschool.com/erp/index.php/Api_request/api_list?method=syllabus&session_id=25&class_id=11


		//https://yppschool.com/erp/index.php/Api_request/api_list?method=academic_calendar&session_id=25&class_id=11


		webview1.setDownloadListener((url, userAgent, contentDisposition, mimetype, contentLength) -> _Open_url(url));
		
		_ZoomWebView(webview1, true);
	}

	@Override
	protected void onActivityResult(int _requestCode, int _resultCode, Intent _data) {
		
		super.onActivityResult(_requestCode, _resultCode, _data);
		

	}

	private  void API_request(String _method,String _class_id, String _session_id , String _api)
	{
		HashMap<String, Object> map2 = new HashMap<>();
		map2.put("method", _method);
		map2.put("session_id", _session_id);
		map2.put("class_id", _class_id);

		req.setParams(map2, RequestNetworkController.REQUEST_PARAM);
		req.startRequestNetwork(
				RequestNetworkController.GET,
				_api,
				"tag", _req_request_listener);

	}

	private void loadPDF(String url)
	{
		//pdfURL = getIntent().getStringExtra("pdfURL");
		pdfURL = url;
		String prefix = "https://docs.google.com/gview?embedded=true&url=";
		webview1.loadUrl(prefix.concat(pdfURL));


	}
	
	@Override
	public void onBackPressed() {
		if (webview1.canGoBack()) {
			webview1.goBack();
		}
		else {
			finish();
		}
	}
	public void _transparent_satus () {
		Window w = this.getWindow();w.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
		getWindow().getDecorView().setSystemUiVisibility( View.SYSTEM_UI_FLAG_LAYOUT_STABLE | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN); if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) { getWindow().setStatusBarColor(Color.TRANSPARENT); }
	}
	
	
	public void _ZoomWebView (final WebView _web, final boolean _ballon) {
		_web.getSettings().setBuiltInZoomControls(_ballon);_web.getSettings().setDisplayZoomControls(!_ballon);
	}
	
	
	public void _Open_url (final String _url) {

		Intent i = new Intent(Intent.ACTION_VIEW); i.setData(Uri.parse(_url)); startActivity(i);
		
	}
	
	
	@Deprecated
	public void showMessage(String _s) {
		Toast.makeText(getApplicationContext(), _s, Toast.LENGTH_SHORT).show();
	}
	

}
