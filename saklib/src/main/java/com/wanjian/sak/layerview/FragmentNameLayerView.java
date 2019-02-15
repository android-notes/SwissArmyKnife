package com.wanjian.sak.layerview;

import android.app.Activity;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.view.View;

import com.wanjian.sak.R;
import com.wanjian.sak.layerview.ActivityNameLayerView;
import com.wanjian.sak.utils.Utils;

import java.util.List;

public class FragmentNameLayerView extends ActivityNameLayerView {

    private final Paint mPaint;
    private int mStartLayer = 0;
    private int mEndLayer = 100;
    private int mCurLayer = -1;

    public FragmentNameLayerView(Context context) {
        super(context);
        mPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mPaint.setTextSize(dp2px(10));
    }

    @Override
    public Drawable icon() {
        return getContext().getResources().getDrawable(R.drawable.sak_page_name_icon);
    }

    @Override
    public String description() {
        return getContext().getString(R.string.sak_fragment_name);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        Activity activity = Utils.findAct(getRootView());

        if (activity instanceof FragmentActivity) {
            mStartLayer = getStartRange();
            mEndLayer = getEndRange();
            mCurLayer = -1;
            traversal(canvas, ((FragmentActivity) activity).getSupportFragmentManager());
        }
    }

    private void traversal(Canvas canvas, FragmentManager fragmentManager) {
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
                        drawInfo(canvas, localSize[0], localSize[1], localSize[2], localSize[3], fragment.getClass().getName());
                    }
                }
            }
        }
        for (Fragment fragment : fragments) {
            if (fragment.isVisible()) {
                View view = fragment.getView();
                if (view != null) {
                    traversal(canvas, fragment.getChildFragmentManager());
                }
            }
        }
        mCurLayer--;


    }

    private int[] getLocationAndSize(View view) {
        int[] locations = new int[2];
        view.getLocationInWindow(locations);
        View decorView = view.getRootView();// dialog 内边距
        return new int[]{locations[0] - decorView.getPaddingLeft(), locations[1] - decorView.getPaddingTop(), view.getWidth(), view.getHeight()};
    }

    @Override
    protected void onAttachedToWindow() {
        super.onAttachedToWindow();
        Activity activity = Utils.findAct(getRootView());
        if (activity == null) {
            return;
        }
        if (activity instanceof FragmentActivity) {
            setWillNotDraw(false);
            invalidate();
            observerSupportFragment(((FragmentActivity) activity));
        } else {
//            observerFragment(activity);
        }
    }

    @Override
    protected void getActivityName() {

    }

    @Override
    protected void drawActivityInfo(Canvas canvas) {

    }
    //    private void observerFragment(final Activity activity) {
//        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
//            activity.getFragmentManager().registerFragmentLifecycleCallbacks(new android.app.FragmentManager.FragmentLifecycleCallbacks() {
//                @Override
//                public void onFragmentCreated(android.app.FragmentManager fm, android.app.Fragment f, Bundle savedInstanceState) {
//                    super.onFragmentCreated(fm, f, savedInstanceState);
//                    invalidate();
//                }
//            }, true);
//        }
//    }

    private void observerSupportFragment(final FragmentActivity activity) {
        activity.getSupportFragmentManager().registerFragmentLifecycleCallbacks(new FragmentManager.FragmentLifecycleCallbacks() {
            @Override
            public void onFragmentCreated(FragmentManager fm, Fragment f, Bundle savedInstanceState) {
                super.onFragmentCreated(fm, f, savedInstanceState);
                invalidate();
            }
        }, true);
    }


}
