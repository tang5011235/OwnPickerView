package com.example.tang5.adapter;

import com.contrarywind.adapter.WheelAdapter;

import java.util.Arrays;

/**
 * 作者：thf on 2018/6/13 0013 21:53
 * <p>
 * 邮箱：tang5011235@163.com
 */
public class DataWheelAdapter<T> implements WheelAdapter<T> {
    private T[] date;

    public DataWheelAdapter(T[] date) {
        this.date = date;
    }

    @Override
    public int getItemsCount() {
        return date.length;
    }

    @Override
    public T getItem(int index) {
        return date[index];
    }


    @Override
    public int indexOf(T value) {
        return Arrays.binarySearch(date, value);
    }

    public void setDate(T[] date) {
        this.date = date;
    }
}
