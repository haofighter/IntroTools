package com.intro.hao.mytools.customview.CheckView;

import android.content.Context;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2017/7/13.
 */

public class CheckedBox extends android.support.v7.widget.AppCompatCheckBox {
    public CheckedBox(Context context) {
        super(context);
    }

    public CheckedBox(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public CheckedBox(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    List<OnClickListener> onClickListeners = new ArrayList<>();

    @Override
    public void setOnClickListener(@Nullable final OnClickListener l) {
        if (!onClickListeners.contains(l)) {
            onClickListeners.add(l);
        }
        OnClickListener onClickListener = new OnClickListener() {
            @Override
            public void onClick(View v) {
                for (int i = 0; i < onClickListeners.size(); i++) {
                    onClickListeners.get(i).onClick(v);
                }
            }
        };

        super.setOnClickListener(onClickListener);
    }
}
