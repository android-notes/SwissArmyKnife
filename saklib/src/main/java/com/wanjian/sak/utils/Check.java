package com.wanjian.sak.utils;

/**
 * Created by wanjian on 2017/2/20.
 */

public class Check {

    public static void isNull(Object obj, String what) {
        if (obj == null) {
            throw new NullPointerException(what + " cannot be null !");
        }
    }

    public static void throwException(String msg) {
        throw new RuntimeException(msg);
    }
}
