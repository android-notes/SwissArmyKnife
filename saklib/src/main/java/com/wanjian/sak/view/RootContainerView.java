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
        init(context);
    }

    private void init(Context context) {
        inflate(context, R.layout.sak_container_layout, this);
        entranceView = (SAKEntranceView) findViewById(R.id.entranceView);

    }

    public void setTapListener(SAKEntranceView.OnTapListener listener) {
        entranceView.setTapListener(listener);
    }

    @Override
    protected void onDetachedFromWindow() {
        entranceView.setTapListener(null);
        super.onDetachedFromWindow();
    }
}
