package com.intro.hao.nCanander.calendar;

import android.animation.Animator;
import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.Rect;
import android.os.Parcelable;
import android.support.annotation.Nullable;
import android.support.v4.view.NestedScrollingParent;
import android.util.AttributeSet;
import android.util.Log;
import android.view.animation.Animation;
import android.widget.FrameLayout;


import com.intro.hao.mytools.Utils.animalUtil.AnimalUtils;
import com.intro.hao.mytools.Utils.animalUtil.AnimalUtilsModel;
import com.intro.hao.mytools.base.BackCall;
import com.intro.hao.nCanander.listener.OnCalendarChangedListener;
import com.intro.hao.nCanander.listener.OnMonthCalendarChangedListener;
import com.intro.hao.nCanander.listener.OnWeekCalendarChangedListener;
import com.intro.hao.nCanander.utils.Attrs;
import com.intro.hao.nCanander.view.MonthView;

import org.joda.time.DateTime;

import java.util.List;

/**
 * Created by necer on 2017/8/25.
 * QQ群:127278900
 */

public class NCalendar extends FrameLayout implements NestedScrollingParent, ValueAnimator.AnimatorUpdateListener, OnWeekCalendarChangedListener, OnMonthCalendarChangedListener {

    private WeekCalendar weekCalendar;
    private MonthCalendar monthCalendar;
    public static final int MONTH = 100;
    public static final int WEEK = 200;
    private static int STATE = 100;//默认月
    private int weekHeigh;//周日历的高度
    private int monthHeigh;//月日历的高度,是日历整个的高度，并非是月日历绘制区域的高度

    private int monthCalendarTop; //月日历的getTop

    private int duration;//动画时间
    private int monthCalendarOffset;//月日历需要滑动的距离
    private ValueAnimator monthValueAnimator;//月日历动画
    private ValueAnimator childViewValueAnimator;//childView动画

    private Rect monthRect;//月日历大小的矩形
    private Rect weekRect;//周日历大小的矩形 ，用于判断点击事件是否在日历的范围内

    private OnCalendarChangedListener onCalendarChangedListener;


    public NCalendar(Context context) {
        this(context, null);
    }

    public NCalendar(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public NCalendar(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        //禁止多点触摸
        setMotionEventSplittingEnabled(false);

        monthCalendar = new MonthCalendar(context, attrs);
        weekCalendar = new WeekCalendar(context, attrs);

        duration = Attrs.duration;
        monthHeigh = Attrs.monthCalendarHeight;
        STATE = Attrs.defaultCalendar;

        weekHeigh = monthHeigh / 5;
        monthCalendar.setLayoutParams(new FrameLayout.LayoutParams(FrameLayout.LayoutParams.MATCH_PARENT, monthHeigh));
        weekCalendar.setLayoutParams(new FrameLayout.LayoutParams(FrameLayout.LayoutParams.MATCH_PARENT, weekHeigh));

        addView(monthCalendar);
        addView(weekCalendar);

        monthCalendar.setOnMonthCalendarChangedListener(this);
        weekCalendar.setOnWeekCalendarChangedListener(this);

        post(new Runnable() {
            @Override
            public void run() {
                weekCalendar.setVisibility(STATE == MONTH ? INVISIBLE : VISIBLE);

                monthRect = new Rect(0, monthCalendar.getTop(), monthCalendar.getWidth(), monthCalendar.getHeight());
                weekRect = new Rect(0, weekCalendar.getTop(), weekCalendar.getWidth(), weekCalendar.getHeight());


            }
        });

        monthValueAnimator = new ValueAnimator();
        childViewValueAnimator = new ValueAnimator();

        monthValueAnimator.addUpdateListener(this);
        childViewValueAnimator.addUpdateListener(this);
        childViewValueAnimator.addListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animation) {

            }

            @Override
            public void onAnimationEnd(Animator animation) {
                if (STATE == MONTH) {
                    STATE = WEEK;
                    weekCalendar.setVisibility(VISIBLE);
                    monthCalendar.setVisibility(INVISIBLE);
                } else {
                    STATE = MONTH;
                    weekCalendar.setVisibility(INVISIBLE);
                    monthCalendar.setVisibility(VISIBLE);
                }
            }

            @Override
            public void onAnimationCancel(Animator animation) {
            }

            @Override
            public void onAnimationRepeat(Animator animation) {
            }
        });
    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {

        if (STATE == MONTH) {
            monthCalendarTop = monthCalendar.getTop();
        } else {
//            monthCalendarTop = -getMonthCalendarOffset();
        }

        monthCalendar.layout(0, monthCalendarTop, r, monthHeigh + monthCalendarTop);
        weekCalendar.layout(0, 0, r, weekHeigh);

    }


    //月日历需要滑动的距离，
    private int getMonthCalendarOffset() {
        MonthView currectMonthView = monthCalendar.getCurrectMonthView();
        //该月有几行
        int rowNum = currectMonthView.getRowNum();
        //现在选中的是第几行
        int selectRowIndex = currectMonthView.getSelectRowIndex();
        //month需要移动selectRowIndex*h/rowNum ,计算时依每个行的中点计算
        int monthCalendarOffset = selectRowIndex * currectMonthView.getDrawHeight() / rowNum;
        return monthCalendarOffset;
    }

    //自动滑动
    private void autoScroll(int startMonth, int endMonth, int startChild, int endChild) {
        monthValueAnimator.setIntValues(startMonth, endMonth);
        monthValueAnimator.setDuration(duration);
        monthValueAnimator.start();

        childViewValueAnimator.setIntValues(startChild, endChild);
        childViewValueAnimator.setDuration(duration);
        childViewValueAnimator.start();
    }

    public void setOnCalendarChangedListener(OnCalendarChangedListener onCalendarChangedListener) {
        this.onCalendarChangedListener = onCalendarChangedListener;
    }


    @Override
    public void onAnimationUpdate(ValueAnimator animation) {
        if (animation == monthValueAnimator) {
            int animatedValue = (int) animation.getAnimatedValue();
            int top = monthCalendar.getTop();
            int i = animatedValue - top;
            monthCalendar.offsetTopAndBottom(i);
        }
    }

    @Override
    public void onWeekCalendarChanged(DateTime dateTime) {
        if (STATE == WEEK) {
            monthCalendar.setDateTime(dateTime);
            requestLayout();
            if (onCalendarChangedListener != null) {
                onCalendarChangedListener.onCalendarChanged(dateTime);
            }
        }
    }

    @Override
    public void onMonthCalendarChanged(DateTime dateTime) {
        //monthCalendarOffset在这里赋值，月日历改变的时候
//        monthCalendarOffset = getMonthCalendarOffset();

        if (STATE == MONTH) {
            weekCalendar.setDateTime(dateTime);
            if (onCalendarChangedListener != null) {
                onCalendarChangedListener.onCalendarChanged(dateTime);
            }
        }
    }


    /**
     * 点击事件是否在日历的范围内
     *
     * @param x
     * @param y
     * @return
     */
    private boolean isInCalendar(int x, int y) {
        if (STATE == MONTH) {
            return monthRect.contains(x, y);
        } else {
            return weekRect.contains(x, y);
        }
    }


    /**
     * 跳转制定日期
     *
     * @param formatDate yyyy-MM-dd
     */
    public void setDate(String formatDate) {
        DateTime dateTime = new DateTime(formatDate);
        if (STATE == MONTH) {
            monthCalendar.setDateTime(dateTime);
        } else {
            weekCalendar.setDateTime(dateTime);
        }
    }

    /**
     * 由周日历转换到月日历
     */
    public void toMonth(final BackCall backCall) {
        if (STATE == WEEK) {
            Log.i("", "转成月份显示");
//            monthCalendarTop = monthCalendar.getTop();
//            weekCalendar.setVisibility(INVISIBLE);
//            int monthCalendarOffset = getMonthCalendarOffset();
//            autoScroll(monthCalendarTop, 0, 0, monthHeigh);

            new AnimalUtils().setAnimalModel(new AnimalUtilsModel(weekCalendar, AnimalUtils.AnimalType.top)).addTranAnimal(false).setAnimationListener(
                    new Animation.AnimationListener() {
                        @Override
                        public void onAnimationStart(Animation animation) {

                        }

                        @Override
                        public void onAnimationEnd(Animation animation) {
                            weekCalendar.setVisibility(INVISIBLE);
                            monthCalendar.setVisibility(VISIBLE);
                            STATE = MONTH;
                            new AnimalUtils().setAnimalModel(new AnimalUtilsModel(monthCalendar, AnimalUtils.AnimalType.top)).addTranAnimal(true).setAnimationListener(new Animation.AnimationListener() {
                                @Override
                                public void onAnimationStart(Animation animation) {

                                }

                                @Override
                                public void onAnimationEnd(Animation animation) {
                                    monthCalendar.setVisibility(VISIBLE);
                                    backCall.deal();
                                }

                                @Override
                                public void onAnimationRepeat(Animation animation) {

                                }
                            }).startAnimal();
                        }

                        @Override
                        public void onAnimationRepeat(Animation animation) {

                        }
                    }
            ).startAnimal();
        }
    }

    /**
     * 由月日历到周日历
     */

    public void toWeek(final BackCall backCall) {
        if (STATE == MONTH) {
            Log.i("", "转成星期显示");
//            int monthCalendarOffset = getMonthCalendarOffset();
//            autoScroll(0, -monthCalendarOffset, monthHeigh, weekHeigh);
            new AnimalUtils().setAnimalModel(new AnimalUtilsModel(weekCalendar, AnimalUtils.AnimalType.top)).addTranAnimal(false).setAnimationListener(
                    new Animation.AnimationListener() {
                        @Override
                        public void onAnimationStart(Animation animation) {

                        }

                        @Override
                        public void onAnimationEnd(Animation animation) {
                            weekCalendar.setVisibility(INVISIBLE);
                            monthCalendar.setVisibility(VISIBLE);
                            STATE = MONTH;
                            new AnimalUtils().setAnimalModel(new AnimalUtilsModel(monthCalendar, AnimalUtils.AnimalType.top)).addTranAnimal(true).setAnimationListener(new Animation.AnimationListener() {
                                @Override
                                public void onAnimationStart(Animation animation) {

                                }

                                @Override
                                public void onAnimationEnd(Animation animation) {
                                    monthCalendar.setVisibility(VISIBLE);
                                    backCall.deal();
                                }

                                @Override
                                public void onAnimationRepeat(Animation animation) {

                                }
                            }).startAnimal();
                        }

                        @Override
                        public void onAnimationRepeat(Animation animation) {

                        }
                    }
            ).startAnimal();
        }
    }

    /**
     * 回到今天
     */
    public void toToday() {
        if (STATE == MONTH) {
            monthCalendar.toToday();
        } else {
            weekCalendar.toToday();
        }
    }

    /**
     * 设置指示圆点
     *
     * @param pointList
     */
    public void setPoint(List<String> pointList) {
        monthCalendar.setPointList(pointList);
        weekCalendar.setPointList(pointList);
    }

    public void toNextPager() {
        if (STATE == MONTH) {
            monthCalendar.toNextPager();
        } else {
            weekCalendar.toNextPager();
        }
    }

    public void toLastPager() {
        if (STATE == MONTH) {
            monthCalendar.toLastPager();
        } else {
            weekCalendar.toLastPager();
        }
    }

    /**
     * 设置日期区间
     *
     * @param startString
     * @param endString
     */
    public void setDateInterval(String startString, String endString) {
        monthCalendar.setDateInterval(startString, endString);
        weekCalendar.setDateInterval(startString, endString);
    }


    /**
     * 返回100是月，返回200是周
     *
     * @return
     */
    public int getState() {
        return STATE;
    }


    @Override
    protected void onRestoreInstanceState(Parcelable state) {
        try {
            super.onRestoreInstanceState(state);
        } catch (Exception e) {
            state = null;
        }
    }
}
