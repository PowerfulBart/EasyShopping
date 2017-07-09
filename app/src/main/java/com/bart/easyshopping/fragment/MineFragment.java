package com.bart.easyshopping.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.bart.easyshopping.R;

/**
 * 作者：${Bart} on 2017/7/7 16:24
 * 邮箱：botao_zheng@163.com
 */

public class MineFragment extends BaseFragment {

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.mine_fragment_layout,container,false);
        return view;

    }
}
