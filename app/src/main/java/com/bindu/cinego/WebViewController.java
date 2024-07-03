package com.bindu.cinego;

import android.content.Intent;
import android.webkit.WebResourceRequest;
import android.webkit.WebView;
import android.webkit.WebViewClient;


public class WebViewController extends WebViewClient
{

    public boolean shouldOverrideUrlLoading(WebView view, WebResourceRequest request)
    {
        view.loadUrl(request.getUrl().toString());
        return true;
    }
}
