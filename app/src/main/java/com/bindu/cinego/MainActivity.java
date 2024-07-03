package com.bindu.cinego;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.net.ConnectivityManager;
import android.net.http.SslError;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.SslErrorHandler;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.FrameLayout;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.activity.OnBackPressedDispatcher;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class MainActivity extends AppCompatActivity {

    private WebView m_webView;

    @Override
    @SuppressLint("SetJavaScriptEnabled")
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });


        m_webView = (WebView) findViewById(R.id.mainWebView);

        WebSettings settings = m_webView.getSettings();
        settings.setJavaScriptEnabled(true);
        settings.setLoadWithOverviewMode(true);
        settings.setUseWideViewPort(true);
        settings.setJavaScriptCanOpenWindowsAutomatically(true);
        settings.setMinimumFontSize(1);
        settings.setMinimumLogicalFontSize(1);
        settings.setDomStorageEnabled(true);
        settings.setDatabaseEnabled(true);
        settings.setAllowFileAccess(true);



        m_webView.setWebViewClient(new WebViewController());
        m_webView.setWebChromeClient(new WebChromeController(this,MainActivity.this));

        if(IsNetworkConnected())
        {
            m_webView.loadUrl(getString(R.string.web_url));
        }

    }

    private static int m_timeInterval = 2000;
    private static long m_backPressTime = 0;

    @SuppressLint("MissingSuperCall")
    @Override
    public void onBackPressed()
    {
        if(!m_webView.canGoBack())
        {

            if(m_backPressTime + m_timeInterval > System.currentTimeMillis())
            {
                Intent intent = new Intent(Intent.ACTION_MAIN);
                intent.addCategory(Intent.CATEGORY_HOME);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);
            }
            else
            {
                Toast.makeText(getBaseContext(),"Press back again to exit", Toast.LENGTH_SHORT).show();
            }
            m_backPressTime = System.currentTimeMillis();
        }
        else
            m_webView.goBack();

    }

    private boolean IsNetworkAvailable()
    {
        ConnectivityManager cm = (ConnectivityManager) getBaseContext().getSystemService(Context.CONNECTIVITY_SERVICE);
        return cm.getActiveNetworkInfo() != null;
    }

    private boolean IsNetworkConnected()
    {
        try
        {
            String command = "ping -c 1 google.com";
            return (Runtime.getRuntime().exec(command).waitFor() == 0);
        }
        catch (Exception e) {
            return false;
        }
    }

}