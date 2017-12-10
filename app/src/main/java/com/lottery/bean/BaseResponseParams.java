package com.lottery.bean;

/**
 * @author: LiuJinrui
 * @email: liujinrui@qdcftx.com
 * @time: 2017/12/7 17:23
 * @description:
 */
public class BaseResponseParams {

    private  String respondeCode;
    private String responseMsg;
    private String info;


    public String getRespondeCode() {
        return respondeCode;
    }

    public void setRespondeCode(String respondeCode) {
        this.respondeCode = respondeCode;
    }

    public String getResponseMsg() {
        return responseMsg;
    }

    public void setResponseMsg(String responseMsg) {
        this.responseMsg = responseMsg;
    }

    public String getInfo() {
        return info;
    }

    public void setInfo(String info) {
        this.info = info;
    }
}
