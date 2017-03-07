package com.wanjian.sak;

/**
 * Created by wanjian on 2017/2/20.
 */

public class Check {

    public static void isNull(Object obj, String what) {
        if (obj == null) {
            throw new NullPointerException(what + " can not be null !");
        }
    }

    public static void throwException(String msg) {
        throw new RuntimeException(msg);
    }
}
