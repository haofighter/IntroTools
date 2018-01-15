package com.intro.hao.mytools.Utils.view;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.RelativeLayout;

import com.intro.hao.mytools.R;


public class LoadingView {
    RelativeLayout bgLayout;
    protected Context mContext;
    protected View mView;

    public LoadingView(Context context, boolean isCustom) {
        mContext = context;
        mView = LayoutInflater.from(mContext).inflate(getLayoutID(isCustom), null);
        initView();
    }


    protected int getLayoutID(boolean isCustom) {
        if (!isCustom) {
            return R.layout.view_loading_layout;
        } else {
            return R.layout.image_view;
        }
    }


    public View getView() {
        return mView;
    }


    public void setVisable(int state) {
        mView.setVisibility(state);
    }


    protected void initView() {
        bgLayout = mView.findViewById(R.id.bg_layout);
        bgLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View arg0) {

            }
        });
    }

}
