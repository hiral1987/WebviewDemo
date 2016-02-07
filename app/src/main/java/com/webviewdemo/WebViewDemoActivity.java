package com.webviewdemo;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.graphics.Bitmap;
import android.net.http.SslError;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.view.Window;
import android.webkit.JavascriptInterface;
import android.webkit.SslErrorHandler;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Toast;

public class WebViewDemoActivity extends Activity {

    WebView webView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_web_view_demo);

        webView = (WebView)findViewById(R.id.webview);
        WebSettings webSettings = webView.getSettings();
        webSettings.setJavaScriptEnabled(true);

        webView.addJavascriptInterface(new WebAppInterface(this), "Android");
        //webView.loadUrl("file:///android_asset/www/yr1955.html");
        webView.setWebViewClient(new MyWebViewClient());
        webView.loadUrl("https://in.yahoo.com");

        findViewById(R.id.previous_btn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (webView.canGoBack()) webView.goBack();
                else Toast.makeText(WebViewDemoActivity.this , "No back history available",Toast.LENGTH_SHORT).show();
            }
        });

        findViewById(R.id.next_btn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(webView.canGoForward())webView.goForward();
                else Toast.makeText(WebViewDemoActivity.this , "No forward history available",Toast.LENGTH_SHORT).show();
            }
        });

        findViewById(R.id.pdown_btn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                webView.pageDown(true);
            }
        });

        findViewById(R.id.pup_btn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                webView.pageUp(true);
            }
        });

        findViewById(R.id.refresh_btn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                webView.reload();
            }
        });
    }

    private class MyWebViewClient extends WebViewClient
    {
        ProgressDialog progressDialog;
        @Override
        public void onPageStarted(WebView view, String url, Bitmap favicon) {
            super.onPageStarted(view, url, favicon);
            progressDialog = ProgressDialog.show(WebViewDemoActivity.this , "" , "Please wait..." , false , false);
        }

        @Override
        public void onPageFinished(WebView view, String url) {
            super.onPageFinished(view, url);
            if(progressDialog!=null && progressDialog.isShowing())progressDialog.cancel();
        }

        @Override
        public void onReceivedSslError(WebView view, SslErrorHandler handler, SslError error) {
            super.onReceivedSslError(view, handler, error);
            view.loadUrl("https://in.yahoo.com");
            handler.proceed();
        }
    }

    public class WebAppInterface {
        Context mContext;

        /** Instantiate the interface and set the context */
        WebAppInterface(Context c) {
            mContext = c;
        }

        /** Show a toast from the web page */
        @JavascriptInterface
        public void showToast(String toast) {
            Toast.makeText(mContext, toast, Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event)
    {
        super.onKeyDown(keyCode, event);
        if(keyCode == KeyEvent.KEYCODE_BACK)
        {
            if(webView.canGoBack())
            {
                webView.goBack();
            }
            else
                finish();
        }
        return false;
    }
}
