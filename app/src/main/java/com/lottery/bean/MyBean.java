package com.lottery.bean;

public class MyBean {
    private String isshowwap;
    private String wapurl;
    private String url;
    private String show_url;
    private String status;

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getShow_url() {
        return show_url;
    }

    public void setShow_url(String show_url) {
        this.show_url = show_url;
    }

    public String getIsshowwap() {
        return isshowwap;
    }

    public void setIsshowwap(String isshowwap) {
        this.isshowwap = isshowwap;
    }

    public String getWapurl() {
        return wapurl;
    }

    public void setWapurl(String wapurl) {
        this.wapurl = wapurl;
    }

    @Override
    public String toString() {
        return "MyBean{" +
                "isshowwap='" + isshowwap + '\'' +
                ", wapurl='" + wapurl + '\'' +
                ", url='" + url + '\'' +
                ", showurl='" + show_url + '\'' +
                '}';
    }
}