package com.pxz.pxzweb;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * 类说明：webview实体类
 * 联系：530927342@qq.com
 *
 * @author peixianzhong
 * @date 2019/12/2 13:21
 */
public class WebViewBean implements Parcelable {
    /**
     * 标题栏背景颜色
     */
    private int titleBackground;
    /**
     * 标题栏返回按钮
     */
    private int imgLeftArrow;
    /**
     * 标题栏结束按钮
     */
    private int imgleftFork;
    /**
     * 标题栏文字
     */
    private String titleText;
    /**
     * 标题栏文字颜色
     */
    private int titleTextColor;
    /**
     * 标题栏是否需要结束按钮
     */
    private boolean isleftFork;
    /**
     * 进度颜色
     */
    private int progressColor;
    /**
     * url
     */
    private String webUrl;

    public WebViewBean(int titleBackground, int imgLeftArrow, int imgleftFork, String titleText, int titleTextColor, boolean isleftFork, int progressColor, String webUrl) {
        this.titleBackground = titleBackground;
        this.imgLeftArrow = imgLeftArrow;
        this.imgleftFork = imgleftFork;
        this.titleText = titleText;
        this.titleTextColor = titleTextColor;
        this.isleftFork = isleftFork;
        this.progressColor = progressColor;
        this.webUrl = webUrl;
    }

    public WebViewBean() {
    }

    public int getTitleBackground() {
        return titleBackground;
    }

    public void setTitleBackground(int titleBackground) {
        this.titleBackground = titleBackground;
    }

    public int getImgLeftArrow() {
        return imgLeftArrow;
    }

    public void setImgLeftArrow(int imgLeftArrow) {
        this.imgLeftArrow = imgLeftArrow;
    }

    public int getImgleftFork() {
        return imgleftFork;
    }

    public void setImgleftFork(int imgleftFork) {
        this.imgleftFork = imgleftFork;
    }

    public String getTitleText() {
        return titleText;
    }

    public void setTitleText(String titleText) {
        this.titleText = titleText;
    }

    public int getTitleTextColor() {
        return titleTextColor;
    }

    public void setTitleTextColor(int titleTextColor) {
        this.titleTextColor = titleTextColor;
    }

    public boolean isIsleftFork() {
        return isleftFork;
    }

    public void setIsleftFork(boolean isleftFork) {
        this.isleftFork = isleftFork;
    }

    public int getProgressColor() {
        return progressColor;
    }

    public void setProgressColor(int progressColor) {
        this.progressColor = progressColor;
    }

    public String getWebUrl() {
        return webUrl;
    }

    public void setWebUrl(String webUrl) {
        this.webUrl = webUrl;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(this.titleBackground);
        dest.writeInt(this.imgLeftArrow);
        dest.writeInt(this.imgleftFork);
        dest.writeString(this.titleText);
        dest.writeInt(this.titleTextColor);
        dest.writeByte(this.isleftFork ? (byte) 1 : (byte) 0);
        dest.writeInt(this.progressColor);
        dest.writeString(this.webUrl);
    }

    protected WebViewBean(Parcel in) {
        this.titleBackground = in.readInt();
        this.imgLeftArrow = in.readInt();
        this.imgleftFork = in.readInt();
        this.titleText = in.readString();
        this.titleTextColor = in.readInt();
        this.isleftFork = in.readByte() != 0;
        this.progressColor = in.readInt();
        this.webUrl = in.readString();
    }

    public static final Creator<WebViewBean> CREATOR = new Creator<WebViewBean>() {
        @Override
        public WebViewBean createFromParcel(Parcel source) {
            return new WebViewBean(source);
        }

        @Override
        public WebViewBean[] newArray(int size) {
            return new WebViewBean[size];
        }
    };
}