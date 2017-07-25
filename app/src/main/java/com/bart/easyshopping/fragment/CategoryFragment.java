package com.bart.easyshopping.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.bart.easyshopping.R;
import com.bart.easyshopping.adapter.BaseAdapter;
import com.bart.easyshopping.adapter.CategoryAdapter;
import com.bart.easyshopping.adapter.WaresAdapter;
import com.bart.easyshopping.adapter.decoration.DividerGridItemDecoration;
import com.bart.easyshopping.adapter.decoration.DividerItemDecoration;
import com.bart.easyshopping.bean.Banner;
import com.bart.easyshopping.bean.Category;
import com.bart.easyshopping.bean.Page;
import com.bart.easyshopping.bean.Wares;
import com.bart.easyshopping.common.Constants;
import com.bart.easyshopping.http.OkHttpHelper;
import com.bart.easyshopping.http.SpotsCallBack;
import com.cjj.MaterialRefreshLayout;
import com.cjj.MaterialRefreshListener;
import com.daimajia.slider.library.Animations.DescriptionAnimation;
import com.daimajia.slider.library.SliderLayout;
import com.daimajia.slider.library.SliderTypes.BaseSliderView;
import com.daimajia.slider.library.SliderTypes.TextSliderView;
import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.squareup.okhttp.Response;

import java.util.List;

/**
 * 作者：${Bart} on 2017/7/7 16:24
 * 邮箱：botao_zheng@163.com
 */

public class CategoryFragment extends BaseFragment {

    @ViewInject(R.id.recyclerview_category)
    private RecyclerView mCategoryRCView;

    @ViewInject(R.id.slider)
    private SliderLayout mSliderLayout;

    @ViewInject(R.id.refresh_layout)
    private MaterialRefreshLayout mRefreshLayout;

    @ViewInject(R.id.recyclerview_wares)
    private RecyclerView mWaresRCView;

    private OkHttpHelper mHelper = OkHttpHelper.getInstance();
    private CategoryAdapter mCategoryAdapter;
    private WaresAdapter mWaresAdapter;
    private List<Banner> mBanners;
    private List<Wares> datas;

    public static final int STATE_NORMAL = 0;
    public static final int STATE_REFRESH = 1;
    public static final int STATE_LOADMORE = 2;
    private int curState = STATE_NORMAL;

    private int curPage = 1;
    private int totalPage = 1;
    private int pageSize = 10;
    private long categoryId = 0;


    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.category_fragment_layout,container,false);
        ViewUtils.inject(this,view);

        requestCategoryData();
        requestBannerData();
        initRefreshLayout();

        return view;
    }


    private void requestCategoryData() {

        mHelper.get(Constants.API.CATEGORY_LIST, new SpotsCallBack<List<Category>>(getContext()) {
            @Override
            public void onSuccess(Response response, List<Category> categories) {

                showCategoryData(categories);

                if (categories != null && categories.size() >0){
                    // 进来"分类"后都是显示一级分类里面的详细数据
                    // 后面当用户点击一级分类中的其他Item时再去拿到那个Item的Id并进行二级分类数据的请求
                    categoryId = categories.get(0).getId();
                    requestWareDatas(categoryId);
                }

            }

            @Override
            public void onError(Response response, int code, Exception e) {
//                Looper.prepare();
//                Toast.makeText(getContext(), "cate=" + code  , Toast.LENGTH_SHORT).show();
//                Looper.loop();
            }
        });

    }

    private void showCategoryData(final List<Category> categoty) {

        mCategoryAdapter = new CategoryAdapter(getContext(),categoty);

        // 一级分类的点击事件：根据对应的Item的Id去请求对应的二级分类的商品数据
        mCategoryAdapter.setOnItemClickListener(new BaseAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                categoryId = categoty.get(position).getId();
//                Category category = mCategoryAdapter.getItem(position);
//                categoryId = category.getId();

                // 别忘了设置一下两行
                curPage = 1;
                curState = STATE_NORMAL;

                requestWareDatas(categoryId);
            }
        });

        mCategoryRCView.setAdapter(mCategoryAdapter);
        mCategoryRCView.setLayoutManager(new LinearLayoutManager(getContext()));
        mCategoryRCView.setItemAnimator(new DefaultItemAnimator());  // 设不设都可以，默认有的
        mCategoryRCView.addItemDecoration(new DividerItemDecoration(getContext(),DividerItemDecoration.VERTICAL_LIST));


    }


    private void requestBannerData() {

        mHelper.get(Constants.API.CATEGORY_BANNER, new SpotsCallBack<List<Banner>>(getContext()) {

                @Override
                public void onSuccess(Response response, List<Banner> banners) {

                    showBannerData(banners);
                }

                @Override
                public void onError(Response response, int code, Exception e) {

                    Toast.makeText(getContext(), "Category_banner_error:" + code, Toast.LENGTH_SHORT).show();
                }
            });

        }

    private void showBannerData(List<Banner> banners) {

        if (banners != null){

            for (Banner banner : banners){
                TextSliderView textSliderView = new TextSliderView(getActivity());
                textSliderView.description(banner.getName());
                textSliderView.image(banner.getImgUrl());
                textSliderView.setScaleType(BaseSliderView.ScaleType.Fit);
                mSliderLayout.addSlider(textSliderView);  //添加轮播的图片
            }
        }

        // 设置指示器、转场效果、动画等
//        mSliderLayout.setCustomIndicator(mPagerIndicator);
        mSliderLayout.setPresetIndicator(SliderLayout.PresetIndicators.Center_Bottom);
        mSliderLayout.setCustomAnimation(new DescriptionAnimation());
        mSliderLayout.setPresetTransformer(SliderLayout.Transformer.RotateDown);
//      mSliderLayout.setPresetTransformer(Transformer ts);
        mSliderLayout.setDuration(3000);  //设置转场的持续时长

        // 滑动 page 时的事件
//        mSliderLayout.addOnPageChangeListener(new ViewPagerEx.OnPageChangeListener() {
//            @Override
//            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
//
//            }
//
//            @Override
//            public void onPageSelected(int position) {
//
//            }
//
//            @Override
//            public void onPageScrollStateChanged(int state) {
//
//            }
//        });

    }


    private void initRefreshLayout() {

        mRefreshLayout.setLoadMore(true);
        mRefreshLayout.setMaterialRefreshListener(new MaterialRefreshListener() {
            @Override
            public void onRefresh(MaterialRefreshLayout materialRefreshLayout) {

                refreshData();
            }

            @Override
            public void onRefreshLoadMore(MaterialRefreshLayout materialRefreshLayout) {

                if (curPage <= totalPage){
                    loadMoreData();
                }else {
                    mRefreshLayout.finishRefreshLoadMore();
                }
            }
        });
    }



    private void refreshData() {

        curPage = 1;
        curState = STATE_REFRESH;
        requestWareDatas(categoryId);
    }

    private void loadMoreData() {

        curPage = ++curPage;
        curState = STATE_LOADMORE;
        requestWareDatas(categoryId);

    }

    private void requestWareDatas(long categoryId) {

//http://112.124.22.238:8081/course_api/wares/list?categoryId=1&curPage=1&pageSize=10

        String url = Constants.API.CATEGORY_WARES + "?categoryId=" + categoryId +
                                                    "&curPage=" + curPage +
                                                    "&pageSize=" + pageSize;

        mHelper.get(url, new SpotsCallBack<Page<Wares>>(getContext()) {

            @Override
            public void onSuccess(Response response, Page<Wares> wares) {

                curPage = wares.getCurrentPage();
                totalPage = wares.getTotalPage();
                pageSize = wares.getPageSize();
                showWaresData(wares.getList());
            }

            @Override
            public void onError(Response response, int code, Exception e) {

//                Toast.makeText(getContext(), "err code = " + code, Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void showWaresData(List<Wares> datas) {

        switch (curState){

            case STATE_NORMAL:
                mWaresAdapter = new WaresAdapter(getContext(),datas);
                mWaresRCView.setAdapter(mWaresAdapter);

                mWaresRCView.setLayoutManager(new GridLayoutManager(getContext(),2));
                mWaresRCView.setItemAnimator(new DefaultItemAnimator());
                mWaresRCView.addItemDecoration(new DividerGridItemDecoration(getContext()));
                break;

            case STATE_REFRESH:
                mWaresAdapter.clear();
                mWaresAdapter.addData(datas);

                mWaresRCView.scrollToPosition(0);
                mRefreshLayout.finishRefresh();
                break;

            case STATE_LOADMORE:
                mWaresAdapter.addData(mWaresAdapter.getData().size(),datas);
                mWaresRCView.scrollToPosition(mWaresAdapter.getData().size());
                mRefreshLayout.finishRefreshLoadMore();
                break;

            default:
                break;
        }
    }
}
