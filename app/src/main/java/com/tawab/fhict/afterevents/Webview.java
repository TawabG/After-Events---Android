package com.tawab.fhict.afterevents;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.webkit.WebView;
import android.webkit.WebViewClient;

public class Webview extends AppCompatActivity {

    private WebView wv1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_webview);

        WebView browser = (WebView) findViewById(R.id.webview);
        browser.loadUrl("https://www.thuisbezorgd.nl/eten-bestellen-eindhoven-5641");



        wv1=(WebView)findViewById(R.id.webview);
        wv1.getSettings().setJavaScriptEnabled(true);

        wv1.setWebViewClient(new MyBrowser());

    }

    private class MyBrowser extends WebViewClient {
        @Override
        public boolean shouldOverrideUrlLoading(WebView view, String url) {
            view.loadUrl(url);
            return true;
        }
    }
}
