package com.example.elliotalderson.networkscanner;

import android.annotation.TargetApi;
import android.app.Activity;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.webkit.WebResourceError;
import android.webkit.WebResourceRequest;
import android.webkit.WebSettings;
import android.webkit.WebSettings.RenderPriority;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Toast;

public class ShowShodanActivity extends AppCompatActivity {
    private WebView mWebview;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_scans);
        mWebview  = new WebView(this);

        mWebview.getSettings().setJavaScriptEnabled(true); // enable javascript

        final Activity activity = this;

        mWebview.setWebViewClient(new WebViewClient() {
            @SuppressWarnings("deprecation")
            @Override
            public void onReceivedError(WebView view, int errorCode, String description, String failingUrl) {
                Toast.makeText(activity, description, Toast.LENGTH_SHORT).show();
            }
            /*
            For older Versions!
             */
            @TargetApi(android.os.Build.VERSION_CODES.M)
            @Override
            public void onReceivedError(WebView view, WebResourceRequest req, WebResourceError rerr) {
                onReceivedError(view, rerr.getErrorCode(), rerr.getDescription().toString(), req.getUrl().toString());
            }
        });
        /*
        Boost performance of Webview
        -   Activate Hardware acceleration
        -   Disable Cache
        -   Set Render Priority to high
         */
        mWebview.setLayerType(View.LAYER_TYPE_HARDWARE, null);
        mWebview.getSettings().setRenderPriority(RenderPriority.HIGH);
        mWebview.getSettings().setCacheMode(WebSettings.LOAD_NO_CACHE);

        mWebview .loadUrl("http://shodan.io");
        setContentView(mWebview );

    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        mWebview.destroy();
    }
    @Override
    protected void onStart() {

        super.onStart();
    }
}
