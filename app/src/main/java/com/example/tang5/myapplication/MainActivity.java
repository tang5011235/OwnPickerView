package com.example.tang5.myapplication;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.contrarywind.listener.OnItemSelectedListener;
import com.contrarywind.view.WheelView;
import com.example.tang5.adapter.DataWheelAdapter;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity extends AppCompatActivity {

    @BindView(R.id.wv_year)
    WheelView mWvYear;
    @BindView(R.id.wv_month)
    WheelView mWvMonth;
    @BindView(R.id.wv_day)
    WheelView mWvDay;
    @BindView(R.id.wv_period_of_time)
    WheelView mWvPeriodOfTime;


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
    private int currentSelectMoth;
    private int currentSelectDay;
    private String currentSelectPeriod;
    private Integer[] mCurrentDayOfMonth;
    private DataWheelAdapter<Integer> mDayAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        initYears();
        currentSelectMoth = getDateByFormate("MM");
        currentSelectDay = getDateByFormate("dd");
        currentSelectPeriod = isMorning() ? periodOfTime[0] : periodOfTime[1];

        mWvYear.setCyclic(false);
        mWvMonth.setCyclic(false);
        mWvDay.setCyclic(false);
        mWvPeriodOfTime.setCyclic(false);

        final DataWheelAdapter<Integer> yearAdapter = new DataWheelAdapter<>(year);
        final DataWheelAdapter<Integer> monthAdapter = new DataWheelAdapter(month);
        mCurrentDayOfMonth = getDayOfMonth(currentSelectYear, currentSelectMoth);
        mDayAdapter = new DataWheelAdapter(mCurrentDayOfMonth);
        DataWheelAdapter<String> periodOfTimeAdapter = new DataWheelAdapter(periodOfTime);

        mWvYear.setAdapter(yearAdapter);
        mWvMonth.setAdapter(monthAdapter);
        mWvDay.setAdapter(mDayAdapter);
        mWvPeriodOfTime.setAdapter(periodOfTimeAdapter);

        mWvYear.setOnItemSelectedListener(new OnItemSelectedListener() {
            @Override
            public void onItemSelected(int index) {
                currentSelectYear = yearAdapter.getItem(index);
                Integer[] dayOfMonth = getDayOfMonth(currentSelectYear, currentSelectMoth);
                if (mDayAdapter.indexOf(currentSelectDay) == mDayAdapter.getItemsCount()-1 && dayOfMonth.length-1 > mDayAdapter.indexOf(currentSelectDay)) {
                    mWvDay.setCurrentItem(dayOfMonth.length);
                }
                mDayAdapter.setYear(dayOfMonth);
                mWvDay.setAdapter(mDayAdapter);
            }
        });

        mWvMonth.setOnItemSelectedListener(new OnItemSelectedListener() {
            @Override
            public void onItemSelected(int index) {
                currentSelectMoth = monthAdapter.getItem(index);
                Integer[] dayOfMonth = getDayOfMonth(currentSelectYear, currentSelectMoth);
                if (mDayAdapter.indexOf(currentSelectDay) == mDayAdapter.getItemsCount()-1 && dayOfMonth.length-1 > mDayAdapter.indexOf(currentSelectDay)) {
                    mWvDay.setCurrentItem(dayOfMonth.length);
                }
                mDayAdapter.setYear(dayOfMonth);
                mWvDay.setAdapter(mDayAdapter);
            }
        });
        mWvDay.setOnItemSelectedListener(new OnItemSelectedListener() {
            @Override
            public void onItemSelected(int index) {
                currentSelectDay = mDayAdapter.getItem(index);
            }
        });
        mWvYear.setCurrentItem(yearAdapter.indexOf(currentSelectYear));
        mWvMonth.setCurrentItem(monthAdapter.indexOf(currentSelectMoth));
        mWvDay.setCurrentItem(mDayAdapter.indexOf(currentSelectDay));
        mWvPeriodOfTime.setCurrentItem(periodOfTimeAdapter.indexOf(currentSelectPeriod));
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


/*  System.out.println(getDayOfMonth(1904, 3).length);
        System.out.println(getDayOfMonth(1905, 4).length);
        System.out.println(getDayOfMonth(1896, 1).length);
        System.out.println(getDayOfMonth(1897, 3).length);
        System.out.println(getDayOfMonth(1892, 2).length);
        System.out.println(getDayOfMonth(1893, 2).length);
        System.out.println(getDayOfMonth(1892, 7).length);
        System.out.println(getDayOfMonth(1892, 8).length);*/

    /**
     * 获取某年某月的天数
     *
     * @param year
     * @param month
     * @return
     */
    private Integer[] getDayOfMonth(int year, int month) {
        if (month == 2) {
            if (isLeapYear(year)) { //二月并且为闰年
                return februaryOfLeapYear;
            } else {
                return februaryOfNormalYear;
            }
        } else if (month % 2 == 0) {
            if (month > 7) {//大的月份
                return dayOfMonthBig;
            } else {//小的月份
                return dayOfMonthSmall;
            }
        } else {
            if (month > 7) {//小的月份
                return dayOfMonthSmall;
            } else {//大的月份
                return dayOfMonthBig;
            }
        }
    }
}
