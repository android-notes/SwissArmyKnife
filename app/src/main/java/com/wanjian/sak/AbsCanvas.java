package com.wanjian.sak;

import android.graphics.Canvas;
import android.graphics.Paint;
import android.view.View;
import android.view.ViewGroup;

/**
 * Created by wanjian on 2016/10/23.
 */

public abstract class AbsCanvas {

    protected abstract void onDraw(Canvas canvas, Paint paint, View view, int startLayer, int endLayer);

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }

        return obj.getClass() == getClass();
    }
}
