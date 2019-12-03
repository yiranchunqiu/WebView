# 介绍
## 自用项目，webview

### 图片
<div style="align: center">
       <img src="https://github.com/yiranchunqiu/WebView/blob/master/pic/%E5%9B%BE%E7%89%871.png" width="32%">
</div>


### 使用方法
### 添加

```
allprojects {
 		repositories {
 			maven { url 'https://jitpack.io' }
 		}
 	}
```

### 添加依赖

```
 	dependencies {
    	        implementation 'com.github.yiranchunqiu:WebView:1.1'
    	}
```

### 使用

```
            WebViewBean webViewBean=new WebViewBean(R.color.colorPrimary, R.mipmap.title_back_white,
                    R.mipmap.title_close_white,"百度",R.color.white,
                    false,R.color.colorPrimary,"https://www.baidu.com");
            Bundle bundle = new Bundle();
            bundle.putParcelable("webViewBean", webViewBean);
            Intent intent = new Intent();
            intent.setClass(this, WebActivity.class);
            intent.putExtras(bundle);
            startActivity(intent);
```