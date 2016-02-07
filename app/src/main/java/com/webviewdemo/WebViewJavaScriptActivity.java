package com.webviewdemo;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.view.Window;
import android.view.inputmethod.InputMethodManager;
import android.webkit.JavascriptInterface;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.widget.TextView;
import android.widget.Toast;

public class WebViewJavaScriptActivity extends Activity {

    WebView webView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_web_view_java_script);

        webView = (WebView)findViewById(R.id.webview);
        WebSettings webSettings = webView.getSettings();
        webSettings.setJavaScriptEnabled(true);

        webView.addJavascriptInterface(new WebAppInterface(this), "Android");
        webView.loadUrl("file:///android_asset/www/yr1955.html");
        //webView.setWebViewClient(new MyWebViewClient());
        //webView.loadUrl("https://in.yahoo.com");
    }

    public class WebAppInterface {
        Context mContext;

        /** Instantiate the interface and set the context */
        WebAppInterface(Context c) {
            mContext = c;
        }

        /** Show a toast from the web page */
        @JavascriptInterface
        public void showToast(String fname,String lname)
        {
            Toast.makeText(mContext, fname+" "+lname, Toast.LENGTH_SHORT).show();
            setValues(fname , lname);
        }
    }

    private void setValues(final String fname,final String lname)
    {
        webView.post(new Runnable() {
            @Override
            public void run() {
                InputMethodManager inputMethodManager = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                inputMethodManager.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
                TextView fname1 = (TextView) findViewById(R.id.lastname_val);
                fname1.setText(lname);
                TextView lname1 = (TextView) findViewById(R.id.firstname_val);
                lname1.setText(fname);
            }
        });

    }
}
