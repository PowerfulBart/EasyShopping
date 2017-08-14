package com.bart.easyshopping.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.bart.easyshopping.R;
import com.bart.easyshopping.adapter.HWAdapter;
import com.bart.easyshopping.adapter.HotWaresAdapter;
import com.bart.easyshopping.adapter.decoration.DividerItemDecoration;
import com.bart.easyshopping.bean.Page;
import com.bart.easyshopping.bean.Wares;
import com.bart.easyshopping.common.Constants;
import com.bart.easyshopping.http.OkHttpHelper;
import com.bart.easyshopping.http.SpotsCallBack;
import com.cjj.MaterialRefreshLayout;
import com.cjj.MaterialRefreshListener;
import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.squareup.okhttp.Response;

import java.util.List;

/**
 * 作者：${Bart} on 2017/7/7 16:24
 * 邮箱：botao_zheng@163.com
 */

public class HotFragment extends BaseFragment {

    private OkHttpHelper mHelper = OkHttpHelper.getInstance();
    private HotWaresAdapter mHotWaresAdapter;  // 被封装后的adapter取代
    private HWAdapter mHWAdapter;
    private List<Wares> datas;

    private int currentPage = 1;  // 当前Page
    private int pageSize = 7;  // 每个Page中的数据量
    private int totalPages = 1;  // 总Page数
    private int totalCount;     // 总数据量

    public static final String TAG = "HotFragment";

    public static final int STATE_NORMAL = 0;
    public static final int STATE_REFRESH = 1;
    public static final int STATE_LOADMORE = 2;

    public int currentState = STATE_NORMAL;

    @ViewInject(R.id.material_refresh_layout)
    private MaterialRefreshLayout mRefreshLayout;

    @ViewInject(R.id.hot_recycler_view)
    private RecyclerView mRecyclerView;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.hot_fragment_layout,container,false);
        ViewUtils.inject(this,view);

        getData();
        initRefreshLayout();


        return view;
    }

    private void initRefreshLayout() {

        mRefreshLayout.setLoadMore(true);  // 若要加载更多需要设置
        mRefreshLayout.setMaterialRefreshListener(new MaterialRefreshListener() {
            @Override
            public void onRefresh(MaterialRefreshLayout materialRefreshLayout) {

                refreshData();
            }

            @Override
            public void onRefreshLoadMore(MaterialRefreshLayout materialRefreshLayout) {
//                super.onRefreshLoadMore(materialRefreshLayout);

                if (currentPage <= totalPages){
                    loadMoreData();
                }else {
                    mRefreshLayout.finishRefreshLoadMore();
                    Toast.makeText(getContext(), "没有数据了亲爱的.", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private void refreshData() {

        currentPage = 1;
        currentState = STATE_REFRESH;
        getData();
    }

    private void loadMoreData() {
        currentPage = ++currentPage;
        currentState = STATE_LOADMORE;
        getData();
    }


//    http://112.124.22.238:8081/course_api/wares/hot?curPage=1&pageSize=10

    private void getData() {

        String url = Constants.API.HOT_WARES + "?curPage=" + currentPage + "&pageSize=" + pageSize; // 写少了个"="

        mHelper.get(url, new SpotsCallBack<Page<Wares>>(getContext()) {



            @Override
            public void onSuccess(Response response, Page<Wares> waresPage) {

                datas = waresPage.getList();
                currentPage = waresPage.getCurrentPage();
                totalPages = waresPage.getTotalPage();

                Log.d(TAG, "onSuccess:curPage= " + waresPage.getCurrentPage() +
                           "  onSuccess:pageSize= " + waresPage.getPageSize() +
                           "  onSuccess:totlaPage = " + waresPage.getTotalCount());

                showData();
            }

            @Override
            public void onError(Response response, int code, Exception e) {

            }
        });
    }

    private void showData() {

        switch (currentState){
            case STATE_NORMAL:
                mHWAdapter = new HWAdapter(getContext(),datas);
                // 为布局设置点击事件
//                mHWAdapter.setOnItemClickListener(new BaseAdapter.OnItemClickListener() {
//                    @Override
//                    public void onItemClick(View view, int position) {
//
//                    }
//                });
                mRecyclerView.setAdapter(mHWAdapter);

                mRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
                mRecyclerView.setItemAnimator(new DefaultItemAnimator());
                mRecyclerView.addItemDecoration(new DividerItemDecoration(getActivity(),DividerItemDecoration.VERTICAL_LIST));
                break;

            case STATE_REFRESH:
                mHWAdapter.clear();
                mHWAdapter.addData(datas);
                Log.d(TAG, "showData: " + datas);
                mRecyclerView.scrollToPosition(0);
                mRefreshLayout.finishRefresh();
                break;

            case STATE_LOADMORE:
                mHWAdapter.addData(mHWAdapter.getData().size(),datas);
                mRecyclerView.scrollToPosition(mHWAdapter.getData().size());
                mRefreshLayout.finishRefreshLoadMore();  // 这里不要用成finishRefresh，不然会一直处于加载更多的状态
                break;
            default:
                break;
        }
    }


}
