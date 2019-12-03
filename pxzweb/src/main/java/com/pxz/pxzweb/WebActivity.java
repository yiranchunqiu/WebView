package com.pxz.pxzweb;

import android.content.pm.ActivityInfo;
import android.graphics.drawable.ClipDrawable;
import android.graphics.drawable.GradientDrawable;
import android.graphics.drawable.LayerDrawable;
import android.os.Build;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.View;
import android.webkit.CookieManager;
import android.webkit.CookieSyncManager;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.franmontiel.persistentcookiejar.persistence.SharedPrefsCookiePersistor;

import java.util.List;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import okhttp3.Cookie;

/**
 * 类说明：web页面
 * 联系：530927342@qq.com
 *
 * @author peixianzhong
 * @date 2019/9/5 13:21
 */
public class WebActivity extends AppCompatActivity {
    private View vView;
    private RelativeLayout rlTitle;
    private ImageView ivLeft;
    private ImageView ivCha;
    private TextView tvTitle;
    private ProgressBar progressbar;
    private WebView webViewH5;
    private int progress = 100;
    private WebViewBean webViewBean = new WebViewBean();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_web);
        //设置透明状态栏
        StatusBarUtils.setStatusBarFullTransparent(this);
        // 设置为竖屏模式
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        initGetAdData();
        initView();
        initWeb();
        initOnClick();
    }

    private void initGetAdData() {
        if (getIntent() != null) {
            Bundle bundle = getIntent().getExtras();
            if (bundle != null) {
                webViewBean = bundle.getParcelable("webViewBean");
            }
        }
    }

    private void initView() {
        vView = findViewById(R.id.v_view);
        rlTitle = findViewById(R.id.rl_title);
        ivLeft = findViewById(R.id.iv_left);
        ivCha = findViewById(R.id.iv_cha);
        tvTitle = findViewById(R.id.tv_title);
        progressbar = findViewById(R.id.progressbar);
        webViewH5 = findViewById(R.id.web_view_h5);
    }

    private void initWeb() {
        initData();
        progressDrawable();
        webSetting();
        //进度条
        webViewH5.setWebChromeClient(new WebChromeClient() {
            @Override
            public void onProgressChanged(WebView view, int newProgress) {
                if (newProgress == progress) {
                    //加载完网页进度条消失
                    progressbar.setVisibility(View.GONE);
                } else {
                    //开始加载网页时显示进度条
                    progressbar.setVisibility(View.VISIBLE);
                    //设置进度值
                    progressbar.setProgress(newProgress);
                }
                super.onProgressChanged(view, newProgress);
            }
        });
        //如果不设置WebViewClient，请求会跳转系统浏览器
        webViewH5.setWebViewClient(new WebViewClient() {
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                webViewH5.loadUrl(url);
                return true;
            }
        });
        //清除缓存
        webViewH5.clearCache(true);
        removeAllCookie();
        setCookie();
        //设置网址
        webViewH5.loadUrl(webViewBean.getWebUrl());
    }

    /**
     * 设置数据
     */
    private void initData() {
        //状态栏
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.KITKAT) {
            vView.setVisibility(View.GONE);
        } else {
            vView.setVisibility(View.VISIBLE);
        }
        //是否显示结束按钮
        if (webViewBean.isIsleftFork()) {
            ivCha.setVisibility(View.GONE);
        } else {
            ivCha.setVisibility(View.VISIBLE);
        }
        ivLeft.setImageResource(webViewBean.getImgLeftArrow());
        ivCha.setImageResource(webViewBean.getImgleftFork());
        rlTitle.setBackgroundColor(ContextCompat.getColor(this, webViewBean.getTitleBackground()));
        vView.setBackgroundColor(ContextCompat.getColor(this, webViewBean.getTitleBackground()));
        tvTitle.setText(webViewBean.getTitleText());
        tvTitle.setTextColor(ContextCompat.getColor(this, webViewBean.getTitleTextColor()));
    }

    /**
     * 设置progressbar颜色
     */
    private void progressDrawable() {
        //获取progressBar的LayerDrawable,LayerDrawablle是我们写layer-list生成的多层级的drawable
        LayerDrawable drawable = (LayerDrawable) progressbar.getProgressDrawable();
        //新建一个GradientDrawable ，shape标签生成的drawable类型，此处用它是为了设置圆角，若不需要圆角，可以用ColorDrawable设置颜色
        GradientDrawable gradientDrawable = new GradientDrawable();
        //这里设置传过来的颜色
        gradientDrawable.setColor(ContextCompat.getColor(this, webViewBean.getProgressColor()));
        //ClipDrawable 是一个ClipDrawable是通过设置一个Drawable的当前显示比例来裁剪出另一张Drawable，
        //你可以通过调节这个比例来控制裁剪的宽高，以及裁剪内容占整个容器的权重，不是ClipDrawable的话会无法根据我们的progress显示长度，进度条显示起来总是满进度的
        ClipDrawable clipDrawable = new ClipDrawable(gradientDrawable, Gravity.START, ClipDrawable.HORIZONTAL);
        //重点，将LayerDrawable 里id为android.R.id.progress的Drawable层替换为我们新建的Drawable，这就达到了我们改变他的颜色的目的了
        drawable.setDrawableByLayerId(android.R.id.progress, clipDrawable);
    }

    /**
     * 设置web属性
     */
    private void webSetting() {
        WebSettings webSettings = webViewH5.getSettings();
        //支持javascript
        webSettings.setJavaScriptEnabled(true);
        // 设置可以支持缩放
        webSettings.setSupportZoom(true);
        // 设置出现缩放工具
        webSettings.setBuiltInZoomControls(true);
        //扩大比例的缩放
        webSettings.setUseWideViewPort(true);
        //自适应屏幕
        webSettings.setLayoutAlgorithm(WebSettings.LayoutAlgorithm.SINGLE_COLUMN);
        webSettings.setLoadWithOverviewMode(true);
        // 允许访问文件
        webSettings.setAllowFileAccess(true);
        //https+http混合图片问题
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            webSettings.setMixedContentMode(2);
        }
        //解除数据阻止
        webSettings.setBlockNetworkImage(false);
        //dom缓存
        webSettings.setDomStorageEnabled(true);
    }

    /**
     * 清除所有cookie
     */
    private void removeAllCookie() {
        CookieSyncManager cookieSyncManager = CookieSyncManager.createInstance(webViewH5.getContext());
        CookieManager cookieManager = CookieManager.getInstance();
        cookieManager.setAcceptCookie(true);
        cookieManager.removeSessionCookie();
        cookieManager.getCookie(webViewBean.getWebUrl());
        cookieManager.removeAllCookie();
        cookieSyncManager.sync();
    }

    /**
     * 设置cookie
     */
    private boolean setCookie() {
        SharedPrefsCookiePersistor sharedPrefsCookiePersistor = new SharedPrefsCookiePersistor(this);
        List<Cookie> cookies = sharedPrefsCookiePersistor.loadAll();
        CookieManager cookieManager = CookieManager.getInstance();
        for (Cookie cookie : cookies) {
            cookieManager.setCookie(webViewBean.getWebUrl(), cookie.name() + "=" + cookie.value());
        }
        String newCookie = cookieManager.getCookie(webViewBean.getWebUrl());
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.LOLLIPOP) {
            CookieSyncManager cookieSyncManager = CookieSyncManager.createInstance(this);
            cookieSyncManager.sync();
        }
        return TextUtils.isEmpty(newCookie) ? false : true;
    }

    private void initOnClick() {
        //返回
        ivLeft.setOnClickListener(view -> {
            if (webViewH5.canGoBack()) {
                webViewH5.goBack();
            } else {
                finish();
            }
        });
        //结束
        ivCha.setOnClickListener(view -> finish());
    }

    @Override
    public void onBackPressed() {
        if (webViewH5.canGoBack()) {
            webViewH5.goBack();
        } else {
            finish();
        }
    }
}