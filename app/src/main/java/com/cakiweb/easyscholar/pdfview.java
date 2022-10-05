package com.cakiweb.easyscholar;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
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

import com.rajat.pdfviewer.PdfViewerActivity;

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
	}
	
	private void initializeLogic() {
		//_transparent_satus();




		pdfURL = getIntent().getStringExtra("pdfURL");
		Intent in = new Intent(Intent.ACTION_VIEW);
		in.setData(Uri.parse(pdfURL));
		startActivity(in);
	/*	startActivity(PdfViewerActivity.Companion.launchPdfFromUrl(this, pdfURL,
				"Result Details", "dir",true));
        finish();
*/



		//https://www.orimi.com/pdf-test.pdf
		String prefix = "https://docs.google.com/gview?embedded=true&url=";
		webview1.loadUrl(prefix.concat(pdfURL));
		
		webview1.setDownloadListener((url, userAgent, contentDisposition, mimetype, contentLength) -> _Open_url(url));
		
		_ZoomWebView(webview1, true);
	}

	@Override
	protected void onActivityResult(int _requestCode, int _resultCode, Intent _data) {
		
		super.onActivityResult(_requestCode, _resultCode, _data);
		

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
