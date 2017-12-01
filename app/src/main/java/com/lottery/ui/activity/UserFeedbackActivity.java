package com.lottery.ui.activity;

import android.os.Bundle;
import android.os.Handler;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.lottery.R;
import com.lottery.base.BaseActivity;
import com.lottery.utils.ToastUtils;
import com.lottery.widget.CustomEditText;


import butterknife.BindView;
import butterknife.OnClick;

public class UserFeedbackActivity extends BaseActivity  {

    @BindView(R.id.feedback_opinion_et)//用户反馈信息
            EditText opinionEt;
    @BindView(R.id.feedback_input_number_tv)//反馈信息输入数统计
            TextView inputNumTv;
    @BindView(R.id.feedback_contact_information_et)//联系方式
            CustomEditText contactInformationEt;
    @BindView(R.id.feedback_submit_btn)//反馈提交
            Button submitBtn;
    private String feedbackMsg;//反馈信息
    private String feedbackPhone;//联系方式

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_feedback);

        initToolbar("意见反馈",this,true);
        opinionEt.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                inputNumTv.setText(s.length() + "/400");
            }
        });
    }

    @OnClick({R.id.feedback_submit_btn})
    public void OnClick(View view) {
        checkSubmitMsg();
    }

    /**
     * 校验上传信息
     */
    private void checkSubmitMsg() {
        feedbackMsg = opinionEt.getText().toString().trim();
        feedbackPhone = contactInformationEt.getCustomEditText().trim();

        if (TextUtils.isEmpty(feedbackMsg)) {
            ToastUtils.getInstance().showShortToast("请输入您要反馈的问题或建议");
            return;
        }
        progressShow();
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                progressCancel();
                ToastUtils.getInstance().showShortToast("您的反馈信息我们已收到，我们将会尽快改进！！！");
                finish();
            }
        }, 1000);
    }
}