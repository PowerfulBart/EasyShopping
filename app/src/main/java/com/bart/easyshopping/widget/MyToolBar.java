package com.bart.easyshopping.widget;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.support.annotation.Nullable;
import android.support.annotation.StringRes;
import android.support.v7.widget.TintTypedArray;
import android.support.v7.widget.Toolbar;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.bart.easyshopping.R;

/**
 * 作者：${Bart} on 2017/7/9 17:26
 * 邮箱：botao_zheng@163.com
 *
 */

public class MyToolBar extends Toolbar {

    private LayoutInflater mInflater;

    private View mView;
    private EditText searchEt;
    private TextView titleTv;
    private Button rightBtn;

    public MyToolBar(Context context) {
        this(context,null);
    }

    public MyToolBar(Context context, @Nullable AttributeSet attrs) {
        this(context,attrs,0);
    }

    public MyToolBar(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context,attrs,defStyleAttr);

        initView();
        setContentInsetsRelative(10,10);  //设置ToolBar的边距


        /*
        以下实现了控件属性的两种写法：
        1.在xml中定义，因为是自定义控件，所以要在attrs文件中 声明 "自定义属性名",并设置该属性的取值范围，
            如 format="reference"
        2.在类文件中通过函数调用来实现属性设置，如下文的 isShowSearchView
            或者其他 set函数如setRightButtonIcon(Drawable icon)
         */

        // 如果自定义属性组为空，说明没有自定义属性
        // TintTypedArray 类用于读取自定义属性
        if (attrs != null){

            final TintTypedArray a = TintTypedArray.obtainStyledAttributes(getContext(), attrs,
                    R.styleable.MyToolBar, defStyleAttr, 0);

            // 将xml文件中设置的 rightButtonIcon 图片资源读进来，再调用set函数
            // 在xml里面设置实际上和在java里面调用 set函数 是一样的,xml属性最终还是调的 set方法
            // 当然xml多了一个解析的过程,放到java代码里调用会稍微好些(实际没什么区别)。
            // 所以在类文件中设置属性比xml中快一点，但是写在xml中更直观
            final Drawable rightIcon = a.getDrawable(R.styleable.MyToolBar_rightButtonIcon);
            if (rightIcon != null) {
                //setNavigationIcon(navIcon);  //原来设置导航图片资源的 set函数
                setRightButtonIcon(rightIcon);
            }

            // 设置显示 搜索框，默认是 false
            boolean isShowSearchView = a.getBoolean(R.styleable.MyToolBar_isShowSearchView,false);

            // 若要求显示搜索框，则隐藏标题
            if(isShowSearchView){

                showSearchView();
                hideTitleView();
            }

            a.recycle();
        }
    }


    // 右侧Button的 设背景、设点击事件、设Text、获得Button对象
    public void  setRightButtonIcon(Drawable icon){

        if(rightBtn !=null){
            rightBtn.setBackground(icon);  // 原来是setimageDrawable（）
            rightBtn.setVisibility(VISIBLE);
        }
    }

    public  void setRightButtonOnClickListener(OnClickListener li){

        rightBtn.setOnClickListener(li);
    }

    public void setRightButtonText(int resId){
        setRightButtonText(getResources().getString(resId));
    }
    //  CharSequence与String都能用于定义字符串，
    // 但CharSequence的值是可读可写序列，而String的值是只读序列。
    public void setRightButtonText(CharSequence text){
        if (rightBtn != null){
            rightBtn.setText(text);
            rightBtn.setVisibility(VISIBLE);
        }
    }

    public Button getRightBtn(){

        return this.rightBtn;

    }


    // 中间TextView的 设文字，由于父类已经有此方法，我们重写它即可
    @Override
    public void setTitle(@StringRes int resId) {
        setTitle(getContext().getText(resId));
    }

    @Override
    public void setTitle(CharSequence title) {

        //本身ToolBar是可以通过 title 属性来设置文字的,我们的ToolBar继承了ToolBar，
        // 所以会先初始化父类的构造方法，当读到setTitle时，因为我们重写了，所以会调用我们的setTitle，
        // 但发现我们此时TextView是null（父类里面会判断，若空就new一个TextView）
        // 所以这里我们要调用initView()
        initView();

        if (titleTv != null){

            titleTv.setText(title);
            showTitleView();
        }
    }

    // 中间文字 和 搜索框 的可见性
    public  void showSearchView(){

        if(searchEt !=null)
            searchEt.setVisibility(VISIBLE);
    }

    public void hideSearchView(){
        if(searchEt !=null)
            searchEt.setVisibility(GONE);
    }

    public void showTitleView(){
        if(titleTv !=null)
            titleTv.setVisibility(VISIBLE);
    }


    public void hideTitleView() {
        if (titleTv != null)
            titleTv.setVisibility(GONE);
    }

    private void initView() {

        //初始化ToolBar时会调用此方法，这里做一下判断，防止重复创建
        if (mView == null){

            mInflater = LayoutInflater.from(getContext());
            mView = mInflater.inflate(R.layout.widget_toolbar,null);

            searchEt = (EditText) mView.findViewById(R.id.toolbar_searchview);
            titleTv = (TextView) mView.findViewById(R.id.toolbar_title);
            rightBtn = (Button) mView.findViewById(R.id.toolbar_rightButton);

            LayoutParams lp = new LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                    ViewGroup.LayoutParams.WRAP_CONTENT,
                    Gravity.CENTER_HORIZONTAL);

            addView(mView,lp);
        }
    }
}
