package com.cretin.www.calendarviewdemo.views;

import android.annotation.TargetApi;
import android.content.Context;
import android.graphics.Color;
import android.os.Build;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.cretin.www.calendarviewdemo.R;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * Created by cretin on 16/9/21.
 */
public class CalendarView extends LinearLayout {
    private Context mContext;
    private String[] weeks = new String[]{"S", "M", "T", "W", "T", "F", "S"};
    private int firstDay = 1;
    private int firstDayCopy = 1;
    private int currYear;
    private int currMonth;
    private int currDay;
    private int mTitleColor = Color.parseColor("#071d41");
    private int mWeekColor = Color.parseColor("#929292");
    private int mDaysColor = Color.parseColor("#1d1d26");

    private int defaultWidth = 100;

    private String[] mPaintDays;

    //创建一个二维数组来存放显示日期的控件
    private TextView[][] days = new TextView[5][7];
    private TextView time;
    private ImageView ivLeft;
    private ImageView ivRight;

    //记录最开始的落点
    private int currX;
    private int currY;

    //容器宽度
    private int mPanelWidth;

    public CalendarView(Context context) {
        super(context);
        init(context);
    }

    public CalendarView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    public CalendarView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    public CalendarView(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        init(context);
    }

    //初始化
    private void init(Context context) {
        mContext = context;
        setOrientation(VERTICAL);
    }

    //填充数据
    private void putData() {
        time.setText(currYear + "年" + currMonth + "月");
        for (int i = 0; i < 5; i++) {
            for (int j = 0; j < 7; j++) {
                if (i == 0) {
                    if (j >= getFisrtDayOfMonth(currYear, currMonth) - 1) {
                        days[i][j].setText((firstDay++) + "");
                    } else {
                        days[i][j].setText("");
                    }
                } else {
                    if (firstDay <= getDaysOfMonth(currYear, currMonth)) {
                        days[i][j].setText((firstDay++) + "");
                    }
                }
            }
        }
        drawOval(mPaintDays);
    }

    public String[] getmPaintDays() {
        return mPaintDays;
    }

    public void setmPaintDays(String[] mPaintDays) {
        this.mPaintDays = mPaintDays;
    }

    private void drawOval(String[] mPaintDays) {
        if (mPaintDays != null)
            for (String s : mPaintDays) {
                int year = Integer.parseInt(s.split(" ")[0]);
                int month = Integer.parseInt(s.split(" ")[1]);
                int day = Integer.parseInt(s.split(" ")[2]);
                HH:
                for (int i = 0; i < 5; i++) {
                    for (int j = 0; j < 7; j++) {
                        if (i == 0) {
                            if (j >= getFisrtDayOfMonth(currYear, currMonth) - 1) {
                                if (firstDayCopy == day && month == currMonth && year == currYear) {
                                    days[i][j].setBackgroundDrawable(getResources().getDrawable(R.mipmap.day_bg));
                                    days[i][j].setTextColor(Color.WHITE);
                                    firstDayCopy = 1;
                                    break HH;
                                }
                                firstDayCopy++;
                            }
                        } else {
                            if (firstDayCopy <= getDaysOfMonth(currYear, currMonth)) {
                                if (firstDayCopy == day && month == currMonth && year == currYear) {
                                    days[i][j].setBackgroundDrawable(getResources().getDrawable(R.mipmap.day_bg));
                                    days[i][j].setTextColor(Color.WHITE);
                                    firstDayCopy = 1;
                                    break HH;
                                }
                                firstDayCopy++;
                            }
                        }
                    }
                }
            }
    }

    //初始化整体布局
    private void initBodyView(Context context) {
        int margin = (mPanelWidth - 7 * defaultWidth) / 14;
        setPadding(0, 50, 0, 50 - margin);
        for (int i = 0; i < 5; i++) {
            LinearLayout llWeek = new LinearLayout(context);
            llWeek.setLayoutParams(new LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
            llWeek.setOrientation(HORIZONTAL);
            for (int j = 0; j < 7; j++) {
                days[i][j] = new TextView(context);
                LayoutParams lpWeek = new LayoutParams(defaultWidth, defaultWidth);
                lpWeek.weight = 1;
                lpWeek.setMargins(margin, margin, margin, margin);
                days[i][j].setLayoutParams(new LayoutParams(defaultWidth, defaultWidth));
                days[i][j].setTextColor(mDaysColor);
                days[i][j].setTextSize(14);
                days[i][j].setGravity(Gravity.CENTER);
                llWeek.addView(days[i][j], lpWeek);
            }
            addView(llWeek);
        }
    }

    //初始化头部布局
    private void initHeadView(Context context) {
        //获取当前时间
        Date dt = new Date();
        SimpleDateFormat matter = new SimpleDateFormat("yyyy MM dd");
        currYear = Integer.parseInt(matter.format(dt).split(" ")[0]);
        currMonth = Integer.parseInt(matter.format(dt).split(" ")[1]);
        currDay = Integer.parseInt(matter.format(dt).split(" ")[2]);

        int margin = (mPanelWidth - 7 * defaultWidth) / 14;
        LinearLayout llHead = new LinearLayout(context);
        LayoutParams lp = new LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        lp.gravity = Gravity.CENTER;
        llHead.setLayoutParams(new LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
        ivLeft = new ImageView(context);
        ivLeft.setImageResource(R.mipmap.calendar_left);
        ivLeft.setPadding(margin + 35, 10, margin + 35, 10);
        llHead.addView(ivLeft, lp);
        time = new TextView(context);
        LayoutParams lpTv = new LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        lpTv.weight = 1;
        time.setLayoutParams(new LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT));
        time.setTextColor(mTitleColor);
        time.setTextSize(16);
        time.setGravity(Gravity.CENTER);
        llHead.addView(time, lpTv);
        ivRight = new ImageView(context);
        ivRight.setImageResource(R.mipmap.calendar_right);
        ivRight.setPadding(margin + 35, 10, margin + 35, 10);
        llHead.addView(ivRight, lp);
        addView(llHead);

        LinearLayout llWeek = new LinearLayout(context);
        llWeek.setPadding(0, 60, 0, 0);
        llWeek.setLayoutParams(new LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
        llWeek.setOrientation(HORIZONTAL);
        for (int i = 0; i < 7; i++) {
            TextView week = new TextView(context);
            LayoutParams lpWeek = new LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
            lpWeek.weight = 1;
            week.setLayoutParams(new LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT));
            week.setText(weeks[i]);
            week.setTextColor(mWeekColor);
            week.setTextSize(14);
            week.setGravity(Gravity.CENTER);
            llWeek.addView(week, lpWeek);
        }
        addView(llWeek);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        if (mPanelWidth == 0) {
            int widthSize = MeasureSpec.getSize(widthMeasureSpec);
            int widthMode = MeasureSpec.getMode(widthMeasureSpec);

            int heightSize = MeasureSpec.getSize(heightMeasureSpec);
            int heightMode = MeasureSpec.getMode(heightMeasureSpec);

            int width = Math.min(widthSize, heightSize);
            if (widthMode == MeasureSpec.UNSPECIFIED) {
                width = heightSize;
            } else if (heightMode == MeasureSpec.UNSPECIFIED) {
                width = widthSize;
            }
            mPanelWidth = width;

            initHeadView(mContext);

            initBodyView(mContext);

            putData();

            ivLeft.setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View view) {
                    backMonth();
                }
            });

            ivRight.setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View view) {
                    preMonth();
                }
            });
        }
    }


    //开启手势
    @Override
    public boolean onTouchEvent(MotionEvent event) {
        switch (event.getAction()) {
            case MotionEvent.ACTION_UP:
                int x = (int) event.getX();
                int y = (int) event.getY();
                if (Math.abs(x - currX) > Math.abs(y - currY) + 50) {
                    //在水平方向上
                    if (x > currX) {
                        //右滑动
                        backMonth();
                    } else {
                        //左滑动
                        preMonth();
                    }
                }
                break;
            case MotionEvent.ACTION_DOWN:
                currX = (int) event.getX();
                currY = (int) event.getY();
                break;
        }
        return true;
    }

    //往前计算一个月
    private void backMonth() {
        clearData();
        if (currMonth != 1) {
            currMonth--;
        } else {
            currYear--;
            currMonth = 12;
        }
        putData();
    }

    //清除数据
    private void clearData() {
        firstDay = 1;
        firstDayCopy = 1;
        for (int i = 0; i < 5; i++) {
            for (int j = 0; j < 7; j++) {
                days[i][j].setText("");
                days[i][j].setBackgroundDrawable(null);
                days[i][j].setTextColor(mDaysColor);
            }
        }
    }

    //往后计算一个月
    private void preMonth() {
        clearData();
        if (currMonth != 12) {
            currMonth++;
        } else {
            currYear++;
            currMonth = 1;
        }
        putData();
    }

    //获取指定年份指定月份的第一天的位置
    private int getFisrtDayOfMonth(int year, int month) {
        Calendar cal = Calendar.getInstance();
        // 设置年份
        cal.set(Calendar.YEAR, year);
        // 设置月份
        cal.set(Calendar.MONTH, month - 1);
        // 获取某月最小天数
        int firstDay = cal.getActualMinimum(Calendar.DAY_OF_MONTH);
        // 设置日历中月份的最小天数
        cal.set(Calendar.DAY_OF_MONTH, firstDay);
        return cal.get(Calendar.DAY_OF_WEEK);
    }

    //获取指定年份指定月份的第一天的位置
    private int getDaysOfMonth(int year, int month) {
        Calendar cal = Calendar.getInstance();
        cal.set(Calendar.YEAR, year);
        cal.set(Calendar.MONTH, month - 1);
        return cal.getActualMaximum(Calendar.DATE);
    }
}
