package com.bart.easyshopping.activity;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTabHost;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TabHost;
import android.widget.TextView;

import com.bart.easyshopping.R;
import com.bart.easyshopping.bean.Tab;
import com.bart.easyshopping.fragment.CartFragment;
import com.bart.easyshopping.fragment.CategoryFragment;
import com.bart.easyshopping.fragment.HomeFragment;
import com.bart.easyshopping.fragment.HotFragment;
import com.bart.easyshopping.fragment.MineFragment;
import com.bart.easyshopping.widget.MyToolBar;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private LayoutInflater inflate;
    private FragmentTabHost tabhost;
    private List<Tab> tabLists = new ArrayList<>(5);
    private CartFragment mCartFragment;
    private MyToolBar mMyToolBar;


    // logt

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);



        initToolBar();
        initTab();

    }


    private void initTab() {

        Tab tab_home = new Tab(R.drawable.selector_home,R.string.home_str, HomeFragment.class);
        Tab tab_hot = new Tab(R.drawable.selector_hot,R.string.hot_str, HotFragment.class);
        Tab tab_category = new Tab(R.drawable.selector_category,R.string.category_str, CategoryFragment.class);
        Tab tab_cart = new Tab(R.drawable.selector_cart,R.string.cart_str, CartFragment.class);
        Tab tab_mine = new Tab(R.drawable.selector_mine,R.string.mine_str, MineFragment.class);

        tabLists.add(tab_home);
        tabLists.add(tab_hot);
        tabLists.add(tab_category);
        tabLists.add(tab_cart);
        tabLists.add(tab_mine);

        inflate = LayoutInflater.from(this);
        tabhost = (FragmentTabHost) this.findViewById(android.R.id.tabhost);
        tabhost.setup(this,getSupportFragmentManager(),R.id.real_tabhost);

        for (Tab tab : tabLists){

            TabHost.TabSpec tabSpec = tabhost.newTabSpec(getString(tab.getTitle()));
            tabSpec.setIndicator(buildIndicator(tab));
            tabhost.addTab(tabSpec,tab.getFragment(),null);

        }

        tabhost.setOnTabChangedListener(new TabHost.OnTabChangeListener() {
            @Override
            public void onTabChanged(String tabId) {
                if (tabId == getString(R.string.cart_str)){
                    //进入购物车则隐藏ToolBar，隐藏方法在以下方法中调用
                    refreshData();

                }
                else{
                    // 其他Fragment则显示 搜索栏
                    mMyToolBar.showSearchView();
                    mMyToolBar.hideTitleView();
                    mMyToolBar.getRightBtn().setVisibility(View.GONE);
                }
            }
        });

        tabhost.getTabWidget().setShowDividers(LinearLayout.SHOW_DIVIDER_NONE);  //去掉Tab之间的分割线
        tabhost.setCurrentTab(0);
//        tabhost.setOnTabChangedListener(new TabHost.OnTabChangeListener() {
//            @Override
//            public void onTabChanged(String tabId) {
//                switch (Integer.parseInt(tabId)){
//                    case R.string.home_str:
//                        Toast.makeText(MainActivity.this, "Home", Toast.LENGTH_SHORT).show();
//                        break;
//                    case R.string.hot_str:
//                        Toast.makeText(MainActivity.this, "Hot", Toast.LENGTH_SHORT).show();
//                        break;
//                    case R.string.category_str:
//                        Toast.makeText(MainActivity.this, "Category", Toast.LENGTH_SHORT).show();
//                        break;
//                    case R.string.cart_str:
//                        Toast.makeText(MainActivity.this, "Cart", Toast.LENGTH_SHORT).show();
//                        break;
//                    case R.string.mine_str:
//                        Toast.makeText(MainActivity.this, "Mine", Toast.LENGTH_SHORT).show();
//                        break;
//                }
//            }
//        });
    }


    private View buildIndicator(Tab tab) {

        View view = inflate.inflate(R.layout.tab_content_layout,null);
        ImageView img = (ImageView) view.findViewById(R.id.tab_iv);
        TextView title = (TextView) view.findViewById(R.id.tab_tx);

        img.setBackgroundResource(tab.getIcon());
        title.setText(tab.getTitle());

        return view;
    }


    private void refreshData(){

        if (mCartFragment == null){

            Fragment fragment = getSupportFragmentManager().findFragmentByTag(getString(R.string.cart_str));

            // 上面获取到的fragment可能是空的，因为只有前面点击过该fragment，该fragment才会被创建并加入
            // FragmentManager 中，所以判空是防止 刚打开应用还没加载fragment导致这里为空的情况
            if (fragment != null) {

                mCartFragment = (CartFragment) fragment;
                mCartFragment.refreshCartData();
                // Fragment的onAttach()里面对可见性的操作只有在碎片和活动绑定时有效，后面如果添新商品到购物车时，
                // 刷新数据之后变回来了，所以每次刷新数据时，就去设置一次可见性
                mCartFragment.changeToolbar();
            }
        }
        else {
            mCartFragment.refreshCartData();
            mCartFragment.changeToolbar();
        }
    }


    private void initToolBar() {

        mMyToolBar = (MyToolBar) findViewById(R.id.toolbar);
    }
}
