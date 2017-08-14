package com.bart.easyshopping.fragment;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.TextView;

import com.bart.easyshopping.R;
import com.bart.easyshopping.activity.MainActivity;
import com.bart.easyshopping.adapter.CartAdapter;
import com.bart.easyshopping.adapter.decoration.DividerItemDecoration;
import com.bart.easyshopping.bean.ShoppingCart;
import com.bart.easyshopping.utils.CartProvider;
import com.bart.easyshopping.widget.MyToolBar;
import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.lidroid.xutils.view.annotation.event.OnClick;

import java.util.List;

/**
 * 作者：${Bart} on 2017/7/7 16:25
 * 邮箱：botao_zheng@163.com
 */

public class CartFragment extends BaseFragment implements View.OnClickListener {

    public static final int ACTION_EDIT = 1;
    public static final int ACTION_COMPLETED = 2;

    @ViewInject(R.id.cart_recycler_view)
    private RecyclerView cartRCV;

    @ViewInject(R.id.checkbox_all)
    private CheckBox mCheckBoxAll;

    @ViewInject(R.id.cart_total_price_tv)
    private TextView totalPrice;

    @ViewInject(R.id.btn_pay_now)
    private Button payNowBtn;

    @ViewInject(R.id.btn_del)
    private Button delBtn;

    private CartProvider mCartProvider;
    private CartAdapter mCartAdapter;
    private MyToolBar mToolBar;


    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.cart_fragment_layout,container,false);
        ViewUtils.inject(this,view);

        mCartProvider = new CartProvider(getContext());

        showCartData();


        return view;

    }

    private void showCartData() {

        List<ShoppingCart> carts = mCartProvider.getAll();


        mCartAdapter = new CartAdapter(getContext(),carts,mCheckBoxAll,totalPrice);
        cartRCV.setAdapter(mCartAdapter);
        cartRCV.setLayoutManager(new LinearLayoutManager(getContext()));
        cartRCV.addItemDecoration(new DividerItemDecoration(getContext(),DividerItemDecoration.VERTICAL_LIST));
    }

    public void refreshCartData(){

        mCartAdapter.clear();
        List<ShoppingCart> carts = mCartProvider.getAll();
        mCartAdapter.addData(carts);
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);  // 一定得写

        if (context instanceof MainActivity){

            MainActivity mainActivity = (MainActivity) context;
            mToolBar = (MyToolBar) mainActivity.findViewById(R.id.toolbar);
            changeToolbar();
        }
    }

    // 隐藏 搜索栏、展示 显示栏并做一些初始化
    public void changeToolbar() {

        mToolBar.hideSearchView();
        mToolBar.showTitleView();

        mToolBar.setTitle(R.string.cart_str);
        mToolBar.getRightBtn().setVisibility(View.VISIBLE);
        mToolBar.setRightButtonText("编辑");

        // 给 "编辑"按钮设置点击事件
        mToolBar.getRightBtn().setOnClickListener(this);
        mToolBar.getRightBtn().setTag(ACTION_EDIT);
    }


    // 删除商品的点击事件
    @OnClick(R.id.btn_del)
    public void delCart(View view){

        mCartAdapter.delCart();
    }

    @Override
    public void onClick(View v) {

        int action = (int) v.getTag();
        if (ACTION_EDIT == action){
            showDelControl();
        }
        else {
            hideDelControl();
        }
    }

    private void showDelControl(){

        mToolBar.getRightBtn().setText("完成");
        payNowBtn.setVisibility(View.GONE);
        totalPrice.setVisibility(View.GONE);
        delBtn.setVisibility(View.VISIBLE);

        mToolBar.getRightBtn().setTag(ACTION_COMPLETED);
        mCartAdapter.checkAllOrNot(false); // 默认用户点编辑时全部选
        mCheckBoxAll.setChecked(false); //全选框默认不勾选

    }

    private void hideDelControl(){

        mToolBar.getRightBtn().setText("编辑");
        delBtn.setVisibility(View.GONE);
        totalPrice.setVisibility(View.VISIBLE);
        payNowBtn.setVisibility(View.VISIBLE);

        mToolBar.getRightBtn().setTag(ACTION_EDIT);
        mCartAdapter.checkAllOrNot(true);
        mCartAdapter.showTotalPrice();
        mCheckBoxAll.setChecked(true);




    }


}
