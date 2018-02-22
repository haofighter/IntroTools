package com.intro.hao.nCanander.adapter;

import android.content.Context;
import android.view.ViewGroup;


import com.intro.hao.nCanander.listener.OnClickWeekViewListener;
import com.intro.hao.nCanander.view.WeekView;

import org.joda.time.DateTime;

/**
 * Created by necer on 2017/8/30.
 * QQ群:127278900
 */

public class WeekAdapter extends CalendarAdapter {

    private OnClickWeekViewListener mOnClickWeekViewListener;

    public WeekAdapter(Context mContext, int count, int curr, DateTime dateTime, OnClickWeekViewListener onClickWeekViewListener) {
        super(mContext, count, curr, dateTime);
        this.mOnClickWeekViewListener = onClickWeekViewListener;
    }


    @Override
    public Object instantiateItem(ViewGroup container, int position) {

        WeekView nWeekView = (WeekView) mCalendarViews.get(position);
        if (nWeekView == null) {
            nWeekView = new WeekView(mContext, mDateTime.plusDays((position - mCurr) * 7), mOnClickWeekViewListener);
            mCalendarViews.put(position, nWeekView);
        }
        container.addView(mCalendarViews.get(position));
        return mCalendarViews.get(position);
    }
}
