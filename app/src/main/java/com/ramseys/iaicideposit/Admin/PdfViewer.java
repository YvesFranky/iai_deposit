package com.ramseys.iaicideposit.Admin;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ProgressBar;

import com.ramseys.iaicideposit.R;

public class PdfViewer extends AppCompatActivity {
    WebView webview;
    ProgressBar progressbar;
    Bundle bundle;
    private String url;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pdf_viewer);

        bundle = getIntent().getExtras();
        if(bundle.get("url")!=null){
            url = bundle.get("url").toString();
        }else url="google.com";

        webview = (WebView)findViewById(R.id.webview);
        progressbar = (ProgressBar) findViewById(R.id.progressbar);
        webview.getSettings().setJavaScriptEnabled(true);
        webview.loadUrl("https://docs.google.com/gview?embedded=true&url=" + url);

        webview.setWebViewClient(new WebViewClient(){
            public void onPageFinished(WebView view, String url){
                progressbar.setVisibility(View.GONE);
            }
        });

    }
}