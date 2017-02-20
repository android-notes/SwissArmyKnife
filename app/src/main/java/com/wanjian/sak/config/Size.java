package com.wanjian.sak.config;


import java.util.LinkedList;
import java.util.List;

/**
 * Created by wanjian on 2017/2/20.
 */

public class Size {

    private int length;
    private String unit;

    private Size() {
    }

    public Size(int length, String unit) {
        this.length = length;
        this.unit = unit;
    }

    public int getLength() {
        return length;
    }

    public void setLength(int length) {
        this.length = length;
    }

    public String getUnit() {
        return unit;
    }

    public void setUnit(String unit) {
        this.unit = unit;
    }


    private static List<Size> sList = new LinkedList<>();

    public static Size obtain() {
        if (sList.isEmpty()) {
            return new Size();
        }

        return sList.get(0);

    }

    public static void returnSize(Size size) {
        if (size == null) {
            throw new RuntimeException("size can not be null!");
        }
        if (sList.size() < 100) {
            sList.add(size);
        }
    }
}
