package com.lottery.model.net;

/**
 * @author: LiuJinrui
 * @email: liujinrui@qdcftx.com
 * @time: 2017/12/5 17:39
 * @description:
 */
public interface BaseNetRequestCallBack {


    void SucceedCallBack(String data);
    /**
     * 网络错误回调
     */
    void OnNetError();
    /**
     * @param msg 参数错误
     */
    void CodeError(String msg);



}
