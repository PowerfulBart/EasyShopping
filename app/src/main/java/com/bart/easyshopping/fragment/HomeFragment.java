package com.bart.easyshopping.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.bart.easyshopping.common.Constants;
import com.bart.easyshopping.R;
import com.bart.easyshopping.adapter.decoration.CardViewtemDecortion;
import com.bart.easyshopping.adapter.HomeCampaignAdapter;
import com.bart.easyshopping.bean.Banner;
import com.bart.easyshopping.bean.HomeCampaign;
import com.bart.easyshopping.http.BaseCallback;
import com.bart.easyshopping.http.OkHttpHelper;
import com.bart.easyshopping.http.SpotsCallBack;
import com.daimajia.slider.library.Animations.DescriptionAnimation;
import com.daimajia.slider.library.Indicators.PagerIndicator;
import com.daimajia.slider.library.SliderLayout;
import com.daimajia.slider.library.SliderTypes.BaseSliderView;
import com.daimajia.slider.library.SliderTypes.TextSliderView;
import com.daimajia.slider.library.Tricks.ViewPagerEx;
import com.google.gson.Gson;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;

import java.util.List;

/**
 * 作者：${Bart} on 2017/7/7 16:22
 * 邮箱：botao_zheng@163.com
 */

public class HomeFragment extends BaseFragment {

    private static final String TAG = "HomeFragment";
    private SliderLayout mSliderLayout;
    private PagerIndicator mPagerIndicator;
    private RecyclerView mRecyclerView;
    private List<Banner> mBanners;
    private Gson mGson = new Gson();
    private OkHttpHelper mHelper = OkHttpHelper.getInstance();
    private HomeCampaignAdapter mHomeCampaignAdapter;


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.home_fragment_layout,container,false);
        initView(view);

        requestImage();

        initRecyclerView(view);

        return view;
    }


    private void requestImage() {

//        String url = "http://112.124.22.238:8081/course_api/banner/query?type=1";
//        OkHttpClient client = new OkHttpClient();
//        Request request = new Request.Builder()
//                .url(url)
//                .build();
//
//        client.newCall(request).enqueue(new Callback() {
//            @Override
//            public void onFailure(Call call, IOException e) {
//
//                Log.i(TAG, "onFailure: " + e);
//            }
//
//            @Override
//            public void onResponse(Call call, Response response) throws IOException {
//
//                if (response.isSuccessful()) {
//
//                    String json = response.body().string();
//                    Type type = new TypeToken<List<Banner>>(){}.getType();
//                    mBanners = mGson.fromJson(json,type);
//
//                    initSlider();
//
//                }
//            };
//        });

        mHelper.get(Constants.API.HOME_BANNER, new SpotsCallBack<List<Banner>>(getContext()) {

            @Override
            public void onSuccess(Response response, List<Banner> banners) {

                mBanners = banners;
                initSlider();
            }

            @Override
            public void onError(Response response, int code, Exception e) {

            }


        });

    }

    private void initView(View view) {

        mSliderLayout = (SliderLayout) view.findViewById(R.id.home_image_slider);
        mPagerIndicator = (PagerIndicator) view.findViewById(R.id.custom_indicator);
        mRecyclerView = (RecyclerView) view.findViewById(R.id.home_recyclerview);
    }

    private void initSlider() {

        if (mBanners != null){

            for (Banner banner : mBanners){
                TextSliderView textSliderView = new TextSliderView(getActivity());
                textSliderView.description(banner.getName());
                textSliderView.image(banner.getImgUrl());
                textSliderView.setScaleType(BaseSliderView.ScaleType.Fit);
                mSliderLayout.addSlider(textSliderView);  //添加轮播的图片
            }
        }

        // 设置指示器、转场效果、动画等
        mSliderLayout.setCustomIndicator(mPagerIndicator);
//      mSliderLayout.setPresetIndicator(SliderLayout.PresetIndicators.Center_Bottom);
        mSliderLayout.setCustomAnimation(new DescriptionAnimation());
        mSliderLayout.setPresetTransformer(SliderLayout.Transformer.RotateDown);
//      mSliderLayout.setPresetTransformer(Transformer ts);
        mSliderLayout.setDuration(3000);  //设置转场的持续时长

        // 滑动 page 时的事件
        mSliderLayout.addOnPageChangeListener(new ViewPagerEx.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {

            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
        // 点击 SliderView 时的事件
//        textSliderView.setOnSliderClickListener(new BaseSliderView.OnSliderClickListener() {
//            @Override
//            public void onSliderClick(BaseSliderView slider) {
//
//            }
//
    }

    // 初始化 RecyclerView
    private void initRecyclerView(View view) {

        mRecyclerView = (RecyclerView) view.findViewById(R.id.home_recyclerview);

        mHelper.get(Constants.API.HOME_CAMPAIGN, new BaseCallback<List<HomeCampaign>>() {

            @Override
            public void onBeforeRequest(Request request) {

            }

            @Override
            public void onFailure(Request request, Exception e) {

            }

            @Override
            public void onResponse(Response response) {

            }

            @Override
            public void onSuccess(Response response, List<HomeCampaign> homeCampaigns) {

                initRCData(homeCampaigns);
            }

            @Override
            public void onError(Response response, int code, Exception e) {

            }
        });

    }

    // 初始化RecyclerView 的Data
    private void initRCData(List<HomeCampaign> homeCampaigns){


        mHomeCampaignAdapter = new HomeCampaignAdapter(homeCampaigns,getContext());
        mRecyclerView.setAdapter(mHomeCampaignAdapter);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this.getActivity()));
        mRecyclerView.addItemDecoration(new CardViewtemDecortion());

    }



    // 回收
    @Override
    public void onStop() {
        mSliderLayout.stopAutoCycle();
        super.onStop();
    }

}
