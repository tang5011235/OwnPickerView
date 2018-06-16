package com.example.tang5.myapplication;

import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.contrarywind.listener.OnItemSelectedListener;
import com.contrarywind.view.WheelView;
import com.example.tang5.adapter.DataWheelAdapter;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * @author：thf on 2018/6/14 0014 09:04
 * <p>
 * 邮箱：tang5011235@163.com
 * <p>
 * name:OwnPickerView
 * <p>
 * version:
 * @description:
 */
public class FourParametersTimeWheelView {
    private View view;
    private WheelView mWvYear;
    private WheelView mWvMonth;
    private WheelView mWvDay;
    private WheelView mWvPeriodOfTime;
    private TextView mTvCancle;
    private TextView mTvSure;
    private TextView mTvDate;


    private Integer[] year = new Integer[40];
    private Integer[] month = new Integer[]{1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12};
    /**
     * 闰年二月
     */
    private Integer[] februaryOfLeapYear = new Integer[]{1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14
            , 15, 16, 17, 18, 19, 20, 21, 22, 23, 24, 25, 26, 27, 28, 29,};
    /**
     * 平年二月
     */
    private Integer[] februaryOfNormalYear = new Integer[]{1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14
            , 15, 16, 17, 18, 19, 20, 21, 22, 23, 24, 25, 26, 27, 28};
    private Integer[] dayOfMonthBig = new Integer[]{1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14
            , 15, 16, 17, 18, 19, 20, 21, 22, 23, 24, 25, 26, 27, 28, 29, 30, 31};
    private Integer[] dayOfMonthSmall = new Integer[]{1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14
            , 15, 16, 17, 18, 19, 20, 21, 22, 23, 24, 25, 26, 27, 28, 29, 30};
    private String[] periodOfTime = new String[]{"上午", "下午"};

    private int currentSelectYear;
    private int currentSelectMonth;
    private int currentSelectDay;
    private String currentSelectPeriod;
    private Integer[] mCurrentDayOfMonth;
    private DataWheelAdapter<Integer> mDayAdapter;


    public FourParametersTimeWheelView(View view) {
        this.view = view;
    }

    public void setDate() {
        mWvYear = view.findViewById(R.id.wv_year);
        mWvMonth = view.findViewById(R.id.wv_month);
        mWvDay = view.findViewById(R.id.wv_day);
        mTvSure = view.findViewById(R.id.tv_sure);
        mTvCancle = view.findViewById(R.id.tv_cancel);
        mTvDate = view.findViewById(R.id.tv_date);
        mWvPeriodOfTime = view.findViewById(R.id.wv_period_of_time);

        setCircle(false);

        initYears();
        currentSelectMonth = getDateByFormate("MM");
        currentSelectDay = getDateByFormate("dd");
        currentSelectPeriod = isMorning() ? periodOfTime[0] : periodOfTime[1];

        final DataWheelAdapter<Integer> yearAdapter = new DataWheelAdapter<>(year);
        final DataWheelAdapter<Integer> monthAdapter = new DataWheelAdapter(month);
        //获取当前某年某月的天数
        mCurrentDayOfMonth = getDayOfMonth(currentSelectYear, currentSelectMonth);
        mDayAdapter = new DataWheelAdapter(mCurrentDayOfMonth);
        DataWheelAdapter<String> periodOfTimeAdapter = new DataWheelAdapter(periodOfTime);

        mWvYear.setAdapter(yearAdapter);
        mWvMonth.setAdapter(monthAdapter);
        mWvDay.setAdapter(mDayAdapter);
        mWvPeriodOfTime.setAdapter(periodOfTimeAdapter);

        //设置默认选中值
        mWvYear.setCurrentItem(yearAdapter.indexOf(currentSelectYear));
        mWvMonth.setCurrentItem(monthAdapter.indexOf(currentSelectMonth));
        mWvDay.setCurrentItem(mDayAdapter.indexOf(currentSelectDay));
        mWvPeriodOfTime.setCurrentItem(periodOfTimeAdapter.indexOf(currentSelectPeriod));

        mWvYear.setOnItemSelectedListener(new OnItemSelectedListener() {
            @Override
            public void onItemSelected(int index) {
                currentSelectYear = yearAdapter.getItem(index);
                int preSelectIndexOfDay = mDayAdapter.indexOf(currentSelectDay);
                Integer[] preSelectDayOfMonth = mCurrentDayOfMonth;
                mCurrentDayOfMonth = getDayOfMonth(currentSelectYear, currentSelectMonth);
                if (currentSelectMonth == 2) {
                    //当天数选中的位置处于末尾 并且 原来天选中的值小于当前选中这个月的天数
                    if (mDayAdapter.indexOf(currentSelectDay) == (mDayAdapter.getItemsCount() - 1) || preSelectIndexOfDay >= mCurrentDayOfMonth.length) {
                        mDayAdapter.setDate(mCurrentDayOfMonth);
                        mWvDay.setAdapter(mDayAdapter);
                        mWvDay.setCurrentItem(mCurrentDayOfMonth.length - 1);
                        currentSelectDay = mCurrentDayOfMonth.length;
                    }
                }
                if (mDateListener != null) {
                    mDateListener.onDateSelected(currentSelectYear, currentSelectMonth, currentSelectDay, currentSelectPeriod);
                }
                showWeek();
                Log.e("年月日:", currentSelectYear + "" + currentSelectMonth + "" + currentSelectDay);
            }
        });

        mWvMonth.setOnItemSelectedListener(new OnItemSelectedListener() {
            @Override
            public void onItemSelected(int index) {
                currentSelectMonth = monthAdapter.getItem(index);
                int preSelectIndexOfDay = mDayAdapter.indexOf(currentSelectDay);
                Integer[] dayOfMonth = getDayOfMonth(currentSelectYear, currentSelectMonth);
                if (mDayAdapter.indexOf(currentSelectDay) == (mDayAdapter.getItemsCount() - 1)) {
                    mDayAdapter.setDate(dayOfMonth);
                    mWvDay.setAdapter(mDayAdapter);
                    mWvDay.setCurrentItem(dayOfMonth.length - 1);
                    currentSelectDay = dayOfMonth.length;
                } else {//如果不处于 尾部 那么保持位置不变
                    mDayAdapter.setDate(dayOfMonth);
                    mWvDay.setAdapter(mDayAdapter);
                    mWvDay.setCurrentItem(preSelectIndexOfDay);
                    currentSelectDay = mDayAdapter.getItem(preSelectIndexOfDay);
                }
                if (mDateListener != null) {
                    mDateListener.onDateSelected(currentSelectYear, currentSelectMonth, currentSelectDay, currentSelectPeriod);
                }
                showWeek();
                Log.e("年月日:", currentSelectYear + "" + currentSelectMonth + "" + currentSelectDay);
            }

        });
        mWvDay.setOnItemSelectedListener(new OnItemSelectedListener() {
            @Override
            public void onItemSelected(int index) {
                currentSelectDay = mDayAdapter.getItem(index);
                if (mDateListener != null) {
                    mDateListener.onDateSelected(currentSelectYear, currentSelectMonth, currentSelectDay, currentSelectPeriod);
                }
                showWeek();
                Log.e("年月日:", currentSelectYear + "" + currentSelectMonth + "" + currentSelectDay);
            }
        });

        mWvPeriodOfTime.setOnItemSelectedListener(new OnItemSelectedListener() {
            @Override
            public void onItemSelected(int index) {
                currentSelectPeriod = periodOfTime[index];
                if (mDateListener != null) {
                    mDateListener.onDateSelected(currentSelectYear, currentSelectMonth, currentSelectDay, currentSelectPeriod);
                }
            }
        });

    }

    public void setCircle(boolean circle) {
        mWvYear.setCyclic(circle);
        mWvMonth.setCyclic(circle);
        mWvDay.setCyclic(circle);
        mWvPeriodOfTime.setCyclic(circle);
    }

    String[] weekDays = {"星期日", "星期一", "星期二", "星期三", "星期四", "星期五", "星期六"};

    /**
     * 显示星期几
     */
    public void showWeek() {
        StringBuilder builder = new StringBuilder();
        builder.append(currentSelectYear)
                .append("-")
                .append(currentSelectMonth)
                .append("-")
                .append(currentSelectDay);
        Calendar calendar = Calendar.getInstance();
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
        try {
            Date date = simpleDateFormat.parse(builder.toString());
            calendar.setTime(date);
            calendar.get(Calendar.DAY_OF_WEEK);
            mTvDate.setText(weekDays[calendar.get(Calendar.DAY_OF_WEEK)-1]);
        } catch (ParseException e) {
            e.printStackTrace();
        }

    }

    /**
     * 初始化年份
     */
    private void initYears() {
        Calendar calendar = Calendar.getInstance();
        Date currentTime = calendar.getTime();
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy");
        String currentYear = simpleDateFormat.format(currentTime);
        currentSelectYear = Integer.parseInt(currentYear);
        int upRangeYear = Integer.parseInt(currentYear) + 20;
        int i = 0;
        for (int downRangeYear = Integer.parseInt(currentYear) - 20; downRangeYear < upRangeYear; downRangeYear++) {
            year[i] = downRangeYear;
            i++;
        }
    }

    private int getDateByFormate(String formate) {
        Calendar calendar = Calendar.getInstance();
        Date currentTime = calendar.getTime();
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(formate);
        return Integer.parseInt(simpleDateFormat.format(currentTime));
    }

    private boolean isMorning() {
        Calendar calendar = Calendar.getInstance();
        Date currentTime = calendar.getTime();
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("HH");
        return Integer.parseInt(simpleDateFormat.format(currentTime)) <= 12;
    }

    private boolean isLeapYear(int year) {
        return year % 4 == 0 && year % 100 != 0 || year % 400 == 0;
    }

    /**
     * 获取某年某月的天数
     *
     * @param year
     * @param month
     * @return
     */
    private Integer[] getDayOfMonth(int year, int month) {
        if (month == 2) {
            if (isLeapYear(year)) {
                //二月并且为闰年
                return februaryOfLeapYear;
            } else {
                return februaryOfNormalYear;
            }
        } else if (month % 2 == 0) {
            if (month > 7) {
                //大的月份
                return dayOfMonthBig;
            } else {
                //小的月份
                return dayOfMonthSmall;
            }
        } else {
            if (month > 7) {
                //小的月份
                return dayOfMonthSmall;
            } else {
                //大的月份
                return dayOfMonthBig;
            }
        }
    }

    private DateListener mDateListener;

    public interface DateListener {
        void onDateSelected(int year, int month, int day, String period);
    }

    public void setDateListener(DateListener dateListener) {
        mDateListener = dateListener;
    }
}
