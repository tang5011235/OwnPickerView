package com.example.tang5.adapter;

import com.contrarywind.adapter.WheelAdapter;

import java.util.Arrays;

/**
 * 作者：thf on 2018/6/13 0013 21:53
 * <p>
 * 邮箱：tang5011235@163.com
 */
public class DataWheelAdapter<T> implements WheelAdapter<T> {
    private T[] year;

    public DataWheelAdapter(T[] year) {
        this.year = year;
    }

    @Override
    public int getItemsCount() {
        return year.length;
    }

    @Override
    public T getItem(int index) {
        return year[index];
    }


    @Override
    public int indexOf(T value) {
        return Arrays.binarySearch(year, value);
    }

    public void setYear(T[] year) {
        this.year = year;
    }
}
