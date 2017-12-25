package com.lottery.ui.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.lottery.R;
import com.lottery.base.BaseRecyclerAdapter;
import com.lottery.base.SmartViewHolder;
import com.lottery.bean.NewsBean;
import com.lottery.bean.NewsReturnData;
import com.lottery.bean.NewsReturnInfo;
import com.lottery.constant.Common;
import com.lottery.model.net.BaseNetRetRequestPresenter;
import com.lottery.model.net.NetRequestView;
import com.lottery.ui.NewsInfoActivity.NewsInfoActivity;
import com.lottery.utils.GlideUtils;
import com.lottery.utils.GsonUtil;
import com.lottery.utils.HttpUtil;
import com.lottery.utils.ToastUtils;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnLoadmoreListener;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * @author: LiuJinrui
 * @email: liujinrui@qdcftx.com
 * @time: 2017/12/22 16:51
 * @description:
 */
public class NewsAmountFragment extends Fragment implements NetRequestView {

    private View rootView = null;

    @BindView(R.id.fragment_amount_rlv)
    RecyclerView recyclerView;

    @BindView(R.id.fragment_amount_smartRefreshLayout)
    SmartRefreshLayout smartRefreshLayout;
    private int countNum = 0;

    private BaseRecyclerAdapter mBaseRecyclerAdapter;
    private NewsBean newsBean;

    private ArrayList<NewsReturnInfo> newsReturnInfoList = new ArrayList<>();


    public static NewsAmountFragment newInstance(String title) {
        NewsAmountFragment newFragment = new NewsAmountFragment();
        Bundle bundle = new Bundle();
        bundle.putSerializable("title", title);
        newFragment.setArguments(bundle);
        return newFragment;
    }


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        String title = getArguments().getString("title");
        newsBean = new NewsBean();
        newsBean.setChannel(title);
        newsBean.setStart(countNum);
        newsBean.setNum(20);
        rootView = inflater.inflate(R.layout.fragment_new_amount, container, false);
        ButterKnife.bind(this, rootView);
        initEvent();
        smartRefreshLayout.autoRefresh();
        return rootView;
    }

    private void initEvent() {

        smartRefreshLayout.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(RefreshLayout refreshlayout) {
                countNum = 0;
                new BaseNetRetRequestPresenter(NewsAmountFragment.this).PostNetRetRequest();

            }
        });

        smartRefreshLayout.setOnLoadmoreListener(new OnLoadmoreListener() {
            @Override
            public void onLoadmore(RefreshLayout refreshlayout) {
                countNum = countNum + newsBean.getNum();
                new BaseNetRetRequestPresenter(NewsAmountFragment.this).PostNetRetRequest();
            }
        });
    }

    private void initNetView(List<NewsReturnInfo> newsReturnInfoArrayList) {
        recyclerView.setNestedScrollingEnabled(false);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setHasFixedSize(true);
        mBaseRecyclerAdapter = new BaseRecyclerAdapter<NewsReturnInfo>(newsReturnInfoArrayList, R.layout.news_rlv_item) {
            @Override
            protected void onBindViewHolder(SmartViewHolder holder, final NewsReturnInfo model, int position) {
                if (!"".equals(model.getPic())) {
                    GlideUtils.getInstance().loadNetImage(model.getPic(), (ImageView) holder.itemView.findViewById(R.id.news_rlv_item_iv));
                }else {
                    holder.itemView.findViewById(R.id.news_rlv_item_iv).setVisibility(View.INVISIBLE);
                }
                holder.text(R.id.news_rlv_item_tv_title, model.getTitle());
                holder.text(R.id.news_rlv_item_tv_data, model.getTime());
                holder.text(R.id.news_rlv_item_tv_laiyuan, model.getWeixinname());
                holder.itemView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent=new Intent(getContext(), NewsInfoActivity.class);
                        intent.putExtra("content",model.getContent());
                        startActivity(intent);
                    }
                });

            }
        };
        recyclerView.setAdapter(mBaseRecyclerAdapter);

    }

    @Override
    public void showCordError(String msg) {
        ToastUtils.getInstance().showLongToast(msg);
    }

    @Override
    public String getPostJsonString() {
        newsBean.setStart(countNum);
        return Common.NEWS_appKey + HttpUtil.encodeNoUrl(GsonUtil.beanToMap(newsBean));
    }

    @Override
    public void NetInfoResponse(String data, int code) {
        try {
            JSONObject jsonObject = new JSONObject(data);
            if (jsonObject.getString("status").equals("0")) {
                String str = jsonObject.getString("result");
                NewsReturnData newsReturnData = GsonUtil.getInstance().fromJson(str, NewsReturnData.class);

                ArrayList<NewsReturnInfo> newsReturnInfoArrayList=newsReturnData.getList();

                for (int i = 0; i < newsReturnInfoArrayList.size(); i++) {
                    if (newsReturnInfoArrayList.get(i).getPic()==null||"".equals(newsReturnInfoArrayList.get(i).getPic())){
                        newsReturnInfoArrayList.remove(i);
                    }
                }

                if (smartRefreshLayout.isRefreshing() && mBaseRecyclerAdapter != null) {
                    initNetView(newsReturnInfoList);
                    mBaseRecyclerAdapter.refresh(newsReturnInfoArrayList);
                    smartRefreshLayout.finishRefresh();

                } else if (smartRefreshLayout.isLoading()) {
                    mBaseRecyclerAdapter.loadmore(newsReturnInfoArrayList);
                    smartRefreshLayout.finishLoadmore();
                } else {
                    if (mBaseRecyclerAdapter == null) {
                        initNetView(newsReturnInfoArrayList);
                        smartRefreshLayout.finishRefresh();

                    } else {
                        newsReturnInfoList.addAll(newsReturnInfoArrayList);
                        initNetView(newsReturnInfoList);
                    }

                }
            }

        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    @Override
    public int code() {
        return 0;
    }
}
