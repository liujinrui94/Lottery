package com.lottery.bean;

/**
 * @author: LiuJinrui
 * @email: liujinrui@qdcftx.com
 * @time: 2017/12/23 20:19
 * @description:
 */
public class NewsReturnInfo {


    private String title;//标题

    private String time;//起始数量

    private String weixinname;// 	微信公众号名称

    private String weixinsummary;//微信公众号介绍

    private String pic;// 	图片

    private String content;//内容

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getWeixinname() {
        return weixinname;
    }

    public void setWeixinname(String weixinname) {
        this.weixinname = weixinname;
    }

    public String getWeixinsummary() {
        return weixinsummary;
    }

    public void setWeixinsummary(String weixinsummary) {
        this.weixinsummary = weixinsummary;
    }

    public String getPic() {
        return pic;
    }

    public void setPic(String pic) {
        this.pic = pic;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    @Override
    public String toString() {
        return "NewsReturnInfo{" +
                "title='" + title + '\'' +
                ", time='" + time + '\'' +
                ", weixinname='" + weixinname + '\'' +
                ", weixinsummary='" + weixinsummary + '\'' +
                ", pic='" + pic + '\'' +
                ", content='" + content + '\'' +
                '}';
    }
}
