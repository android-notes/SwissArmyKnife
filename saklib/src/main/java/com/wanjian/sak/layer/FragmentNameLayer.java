package com.wanjian.sak.layer;

import android.app.Activity;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.drawable.Drawable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.view.View;

import com.wanjian.sak.R;
import com.wanjian.sak.utils.Utils;

import java.util.List;

public class FragmentNameLayer extends ActivityNameLayerView {

    private int mStartLayer;
    private int mEndLayer;
    private FragmentActivity mActivity;

    public FragmentNameLayer(Context context) {
        super(context);
    }

    @Override
    public Drawable icon() {
        return getContext().getResources().getDrawable(R.drawable.sak_page_name_icon);
    }

    @Override
    protected void onUiUpdate(Canvas canvas, View rootView) {
        super.onUiUpdate(canvas, rootView);
        if (mActivity == null || mActivity.isFinishing()) {
            return;
        }
        mStartLayer = getStartRange();
        mEndLayer = getEndRange();
        traversal(canvas, mActivity.getSupportFragmentManager(), 0);
    }

    @Override
    public String description() {
        return getContext().getString(R.string.sak_fragment_name);
    }


    private void traversal(Canvas canvas, FragmentManager fragmentManager, int curLayer) {
        List<Fragment> fragments = fragmentManager.getFragments();
        if (fragments == null || fragments.isEmpty()) {
            return;
        }
        if (curLayer > mEndLayer) {
            return;
        }

        if (curLayer >= mStartLayer) {
            for (Fragment fragment : fragments) {
                if (fragment == null) {
                    continue;
                }
                if (fragment.isVisible()) {
                    View view = fragment.getView();
                    if (view != null) {
                        int localSize[] = getLocationAndSize(view);
                        drawInfo(canvas, localSize[0], localSize[1], localSize[2], localSize[3], fragment.getClass().getName());
                    }
                }
            }
        }
        for (Fragment fragment : fragments) {
            if (fragment.isVisible()) {
                View view = fragment.getView();
                if (view != null) {
                    traversal(canvas, fragment.getChildFragmentManager(), curLayer + 1);
                }
            }
        }


    }

    @Override
    public void onAttached(View view) {
        Activity activity = Utils.findAct(getRootView());
        if (activity instanceof FragmentActivity) {
            mActivity = (FragmentActivity) activity;
        }
    }

    @Override
    protected void onDetached(View rootView) {
        mActivity = null;
    }
}
