package com.wanjian.sak.layer;

import android.app.Activity;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.view.View;

import com.wanjian.sak.R;

import java.util.List;

public class FragmentNameLayer extends ActivityNameLayer {


    public FragmentNameLayer(Context context) {
        super(context);
    }

    @Override
    public String description() {
        return mContext.getString(R.string.sak_fragment_name);
    }

    private int mStartLayer = 0;
    private int mEndLayer = 100;
    private int mCurLayer = -1;

    @Override
    protected void onDraw(Canvas canvas, Paint paint, View view, int startLayer, int endLayer) {
        this.mStartLayer = startLayer;
        this.mEndLayer = endLayer;
        mCurLayer = -1;
        super.onDraw(canvas, paint, view, startLayer, endLayer);
    }


    @Override
    protected void onDrawActInfo(Activity activity, Canvas canvas, Paint paint, View view) {
        if (activity instanceof FragmentActivity) {
            FragmentManager fragmentManager = ((FragmentActivity) activity).getSupportFragmentManager();
            traversal(canvas, paint, fragmentManager);
        }
    }

    private void traversal(Canvas canvas, Paint paint, FragmentManager fragmentManager) {
        List<Fragment> fragments = fragmentManager.getFragments();
        if (fragments == null) {
            return;
        }
        if (mCurLayer + 1 > mEndLayer) {
            return;
        }

        mCurLayer++;
        if (mCurLayer >= mStartLayer && mCurLayer <= mEndLayer) {
            for (Fragment fragment : fragments) {
                if (fragment == null) {
                    continue;
                }
                if (fragment.isVisible()) {
                    View view = fragment.getView();
                    if (view != null) {
                        int localSize[] = getLocationAndSize(view);
                        drawInfo(canvas, paint, localSize[0], localSize[1], localSize[2], localSize[3], fragment.getClass().getName());
                    }
                }
            }
        }
        for (Fragment fragment : fragments) {
            if (fragment.isVisible()) {
                View view = fragment.getView();
                if (view != null) {
                    traversal(canvas, paint, fragment.getChildFragmentManager());
                }
            }
        }
        mCurLayer--;


    }


}
