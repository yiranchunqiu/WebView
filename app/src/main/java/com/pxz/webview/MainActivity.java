package com.pxz.webview;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

import com.pxz.pxzweb.WebActivity;
import com.pxz.pxzweb.WebViewBean;

/**
 * 类说明：主页
 * 联系：530927342@qq.com
 *
 * @author peixianzhong
 * @date 2019/6/3 10:48
 */
public class MainActivity extends AppCompatActivity {
    private TextView tvWeb;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();
        initClick();
    }

    private void initView() {
        tvWeb=findViewById(R.id.tv_web);
    }

    private void initClick() {
        tvWeb.setOnClickListener(v -> {
            WebViewBean webViewBean=new WebViewBean(R.color.colorPrimary, R.mipmap.title_back_white,
                    R.mipmap.title_close_white,"百度",R.color.white,
                    false,R.color.colorPrimary,"https://www.baidu.com");
            Bundle bundle = new Bundle();
            bundle.putParcelable("webViewBean", webViewBean);
            Intent intent = new Intent();
            intent.setClass(this, WebActivity.class);
            intent.putExtras(bundle);
            startActivity(intent);
        });
    }
}