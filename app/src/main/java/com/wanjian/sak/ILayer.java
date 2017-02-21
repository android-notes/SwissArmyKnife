package com.wanjian.sak;

import android.graphics.Canvas;
import android.view.View;

/**
 * Created by wanjian on 2017/2/20.
 */

public abstract class ILayer  {

    public abstract String description();

    public abstract void draw(Canvas canvas, View view, int startLayer, int endLayer);

    public abstract void enable(boolean enable);

}
