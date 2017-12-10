package com.lottery.model.net;


/**
 * @author: LiuJinrui
 * @email: liujinrui@qdcftx.com
 * @time: 2017/12/6 8:49
 * @description:
 */
public class BaseNetRetRequestPresenter {


        private BaseModeImp.PostBaseModeImp baseModeImp;
        private NetRequestView netRequestView;

        public BaseNetRetRequestPresenter(NetRequestView netRequestView) {
            this.netRequestView = netRequestView;
            baseModeImp = new BaseModeImp.PostBaseModeImp();
        }
        public void PostNetRetRequest() {
                baseModeImp.postBaseNetRequestModel(netRequestView.getPostJsonString(), new BaseNetRequestCallBack() {
                    @Override
                    public void SucceedCallBack(String data) {
                        netRequestView.NetInfoResponse(data);
                    }

                    @Override
                    public void OnNetError() {
                        netRequestView.showCordError("网络连接异常,请检查网络!");
                    }
                    @Override
                    public void CodeError(String msg) {
                        netRequestView.showCordError(msg);
                    }
                });
        }


}
