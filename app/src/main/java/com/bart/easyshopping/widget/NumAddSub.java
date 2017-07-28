package com.bart.easyshopping.widget;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.support.v7.widget.TintTypedArray;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bart.easyshopping.R;

import static android.R.attr.x;
import static java.lang.reflect.Array.getInt;

/**
 * 作者：${Bart} on 2017/7/28 14:57
 * 邮箱：botao_zheng@163.com
 */

public class NumAddSub extends LinearLayout implements View.OnClickListener{

    private OnButtonClickListener mListener;
    private LayoutInflater mInflater;
    private TextView inputNum;
    private Button addBtn;
    private Button subBtn;

    private int minValue;
    private int maxValue;
    private int value;

    // 自定义 监听接口 并声明 控件点击事件
    public interface OnButtonClickListener{
        void onBtnAddClick(View view,int value);
        void onBtnSubClick(View view,int value);
    }

    public NumAddSub(Context context) {
        this(context,null);
    }

    public NumAddSub(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs,0);
    }

    public NumAddSub(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {

        super(context, attrs, defStyleAttr);

        mInflater = LayoutInflater.from(context);
        initView();

        if (attrs != null){
            // TintTypedArray 类用于读取自定义属性
            TintTypedArray array = TintTypedArray.obtainStyledAttributes(context,attrs,R.styleable.NumAddSubStyleable,defStyleAttr,0);

            // 逐个将 attr中定义的样式读取进来(这样能读到xml中给定的值)
            int val = array.getInt(R.styleable.NumAddSubStyleable_value,0);
            setValue(val);

            int minVal = array.getInt(R.styleable.NumAddSubStyleable_minValue,0);
            setMinValue(minVal);

            int maxVal = array.getInt(R.styleable.NumAddSubStyleable_maxValue,0);
            setMaxValue(maxVal);

            Drawable drawableBtnAdd = array.getDrawable(R.styleable.NumAddSubStyleable_btnAddBackground);
            Drawable drawableBtnSub = array.getDrawable(R.styleable.NumAddSubStyleable_btnSubBackground);
            Drawable drawableTextView = array.getDrawable(R.styleable.NumAddSubStyleable_textViewBackground);
            setBtnAddBackground(drawableBtnAdd);
            setBtnSubBackground(drawableBtnSub);
            setTextViewBackground(drawableTextView);

            // 千万记得回收
            array.recycle();
        }
    }

    @Override
    public void onClick(View v) {

        if (v.getId() == R.id.btn_add){

            addNum();

            if (mListener != null){
                mListener.onBtnAddClick(v,value);
            }

        }else if (v.getId() == R.id.btn_sub){

            subNum();

            if (mListener != null){
                mListener.onBtnSubClick(v,value);
            }
        }

    }

    private void subNum() {

        if (value < maxValue)
            ++value;

        inputNum.setText("" + value);
    }

    private void addNum() {

        if (value > minValue)
            --value;

        inputNum.setText("" + value);
    }

    // Getter、Setter
    public int getValue() {

        String val = inputNum.getText().toString();

        // val引用不为空 且 val值不为空
        if (val != null && !"".equals(val))
            this.value = Integer.parseInt(val);

        return value;
    }

    public void setValue(int value) {

        inputNum.setText(value + "");
        this.value = value;
    }

    public int getMinValue() {
        return minValue;
    }

    public void setMinValue(int minValue) {
        this.minValue = minValue;
    }

    public int getMaxValue() {
        return maxValue;
    }

    public void setMaxValue(int maxValue) {
        this.maxValue = maxValue;
    }

    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
    public void setBtnAddBackground(Drawable drawable){
        addBtn.setBackground(drawable);
    }

    public void setBtnSubBackground(Drawable drawable){
        subBtn.setBackground(drawable);
    }

    // 直接 传资源
    public void setTextViewBackground(Drawable drawable){
        inputNum.setBackground(drawable);
    }

    // 通过 id 形式获取
    public void setTextViewBackground(int resId){
        inputNum.setBackground(getResources().getDrawable(resId,null));
    }


    private void initView() {

        // 因为 继承了LinearLayout,其本身是个ViewGroup，所以这里可以用 this
        View view = mInflater.inflate(R.layout.widget_num_add_sub,this,false);

        addBtn = (Button) view.findViewById(R.id.btn_add);
        subBtn = (Button) view.findViewById(R.id.btn_sub);
        inputNum = (TextView) view.findViewById(R.id.input_num);

        addBtn.setOnClickListener(this);
        subBtn.setOnClickListener(this);
    }
}
