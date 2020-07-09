package com.wanjian.sak.view;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.widget.FrameLayout;

import com.wanjian.sak.R;

public class RootContainerView extends FrameLayout {
    private SAKEntranceView entranceView;

    public RootContainerView(@NonNull Context context) {
        this(context, null);
    }

    public RootContainerView(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
//        setFitsSystemWindows(false);
    }



}
