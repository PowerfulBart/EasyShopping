package com.bart.easyshopping.adapter;

import android.content.Context;

import com.bart.easyshopping.R;
import com.bart.easyshopping.bean.Category;

import java.util.List;

/**
 * 作者：${Bart} on 2017/7/25 16:47
 * 邮箱：botao_zheng@163.com
 */

public class CategoryAdapter extends SimpleAdapter<Category> {

    public CategoryAdapter(Context context, List<Category> datas) {
        super(context, R.layout.template_single_text, datas);
    }

    @Override
    public void convert(BaseViewHolder viewHolder, Category item) {

        viewHolder.getTextView(R.id.categoty_tv).setText(item.getName());
    }
}
