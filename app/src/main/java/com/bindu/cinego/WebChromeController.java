package com.bindu.cinego;

import android.app.Activity;
import android.content.Context;
import android.content.pm.ActivityInfo;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.widget.FrameLayout;


public class WebChromeController extends WebChromeClient
{

    private Activity m_activity;
    private Context m_context;
    private View m_customView;
    private int  m_originalSystemUiVisibility;
    private int  m_originalOrientation;
    private WebChromeClient.CustomViewCallback m_customViewCallback;
    protected FrameLayout m_fullscreenContainer;


    public WebChromeController(Activity activity, Context context)
    {
        m_activity = activity;
        m_context = context;
        m_fullscreenContainer = activity.findViewById(R.id.fullscreenContainer);
    }

    @Override
    public void onHideCustomView()
    {
        if(m_customView == null)
            return;

        m_fullscreenContainer.setVisibility(View.GONE);

        m_activity.getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_VISIBLE);
        m_activity.setRequestedOrientation(m_originalOrientation);
        m_customView = null;
        m_customViewCallback.onCustomViewHidden();
    }

    @Override
    public void onShowCustomView(View view, WebChromeClient.CustomViewCallback callback)
    {
        if(m_customView != null)
        {
            onHideCustomView();
            return;
        }

        m_customView = view;
        m_customViewCallback = callback;

        m_activity.getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_FULLSCREEN);
        m_activity.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);

        m_fullscreenContainer.addView(m_customView,new FrameLayout.LayoutParams(
                FrameLayout.LayoutParams.MATCH_PARENT,
                FrameLayout.LayoutParams.MATCH_PARENT
        ));


        m_fullscreenContainer.setVisibility(View.VISIBLE);
        m_fullscreenContainer.setSystemUiVisibility(View.SYSTEM_UI_FLAG_FULLSCREEN);

       m_originalOrientation =  m_activity.getRequestedOrientation();
       m_originalSystemUiVisibility = m_activity.getWindow().getDecorView().getSystemUiVisibility();

    }

}
