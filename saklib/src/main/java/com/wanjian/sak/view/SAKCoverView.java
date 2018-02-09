package com.wanjian.sak.view;

import android.content.Context;
import android.graphics.Bitmap;
import android.widget.FrameLayout;

import com.wanjian.sak.R;


/**
 * Created by wanjian on 2016/10/23.
 */

public class SAKCoverView extends FrameLayout {
    private DrawingBoardView mDrawBoard;

    public SAKCoverView(Context context) {
        super(context);
        init();
    }

    public void setOnFloatClickListener(OnClickListener l) {
        findViewById(R.id.floatView).setOnClickListener(l);
    }

    private void init() {

        inflate(getContext(), R.layout.sak_container_layout, this);

        mDrawBoard = (DrawingBoardView) findViewById(R.id.drawBoard);


    }


    public void setInfo(Bitmap info) {
        mDrawBoard.setInfo(info);
    }


    @Override
    protected void onDetachedFromWindow() {
        removeAllViews();
        super.onDetachedFromWindow();
    }
}
