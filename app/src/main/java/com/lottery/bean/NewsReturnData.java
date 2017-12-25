package com.lottery.bean;

import java.util.ArrayList;

/**
 * @author: LiuJinrui
 * @email: liujinrui@qdcftx.com
 * @time: 2017/12/23 18:12
 * @description:
 */
public class NewsReturnData {

    private String channel;
    private int channelid;
    private int total;
    private ArrayList<NewsReturnInfo> list;


    public String getChannel() {
        return channel;
    }

    public void setChannel(String channel) {
        this.channel = channel;
    }

    public int getChannelid() {
        return channelid;
    }

    public void setChannelid(int channelid) {
        this.channelid = channelid;
    }

    public int getTotal() {
        return total;
    }

    public void setTotal(int total) {
        this.total = total;
    }

    public ArrayList<NewsReturnInfo> getList() {
        return list;
    }

    public void setList(ArrayList<NewsReturnInfo> list) {
        this.list = list;
    }

    @Override
    public String toString() {
        return "NewsReturnData{" +
                "channel='" + channel + '\'' +
                ", channelid=" + channelid +
                ", total=" + total +
                ", list=" + list +
                '}';
    }
}
