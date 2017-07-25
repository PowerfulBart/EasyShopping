package com.bart.easyshopping.activity;

import android.os.Bundle;
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

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private LayoutInflater inflate;
    private FragmentTabHost tabhost;
    private List<Tab> tabLists;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        tabLists = new ArrayList<>(5);
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


}
