package com.wanjian.sak;

import android.graphics.drawable.Drawable;

public interface ILayer {
    String description();

    void enable(boolean enable);

    boolean isEnable();

    Drawable icon();
}
