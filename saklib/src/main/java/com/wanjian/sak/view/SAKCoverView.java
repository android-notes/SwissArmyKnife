package com.wanjian.sak.view;

import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.FrameLayout;
import android.widget.RadioGroup;

import com.wanjian.sak.R;
import com.wanjian.sak.layerview.AbsLayerView;
import com.wanjian.sak.mapper.ItemLayerLayout;
import com.wanjian.sak.mapper.ItemLayerViewLayout;
import com.wanjian.sak.mapper.UnitLayout;


/**
 * Created by wanjian on 2016/10/23.
 */

public class SAKCoverView extends FrameLayout {
    private DrawingBoardView mDrawBoard;
    private OperatorView mOperatorView;
    private RadioGroup mUnitGroup;

    private CheckBox mDrawIfOutOfBounds;

    public SAKCoverView(Context context) {
        super(context);
        init();
    }


    private void init() {

        inflate(getContext(), R.layout.sak_container_layout, this);

        mDrawBoard = (DrawingBoardView) findViewById(R.id.drawBoard);
        mOperatorView = (OperatorView) findViewById(R.id.operatorView);
        mDrawIfOutOfBounds = (CheckBox) findViewById(R.id.drawIfOutOfBounds);
        findViewById(R.id.floatView).setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mOperatorView.getVisibility() != VISIBLE) {
                    mOperatorView.setVisibility(VISIBLE);
                } else {
                    mOperatorView.setVisibility(GONE);
                }
            }
        });

        mOperatorView.findViewById(R.id.ok).setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                mOperatorView.setVisibility(GONE);
            }
        });

        mUnitGroup = ((RadioGroup) mOperatorView.findViewById(R.id.unitGroup));
        mOperatorView.findViewById(R.id.help).setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                final Uri uri = Uri.parse("https://github.com/android-notes/SwissArmyKnife/blob/master/README.md");
                final Intent intent = new Intent(Intent.ACTION_VIEW, uri);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                getContext().startActivity(intent);
            }
        });


    }


    public void addItem(ItemLayerLayout itemLayout) {
        int res = itemLayout.getLayoutRes();
        try {
            View item = LayoutInflater.from(getContext()).inflate(res, null);
            mOperatorView.addItem(item);
            itemLayout.onCreate(item);
        } catch (Resources.NotFoundException e) {
            throw e;
        }

    }

    public void setInfo(Bitmap info) {
        mDrawBoard.setInfo(info);
    }

    public void addItem(ItemLayerViewLayout itemLayout) {
        AbsLayerView layerView = itemLayout.getLayerView();
        View view = LayoutInflater.from(getContext()).inflate(itemLayout.getLayoutRes(), null);
        layerView.setVisibility(GONE);
        mOperatorView.addItem(view);
        layerView.setLayoutParams(layerView.getLayoutParams(generateDefaultLayoutParams()));
        addView(layerView, 1);
        itemLayout.onCreate(view);
    }

    public void addItem(UnitLayout unitLayout) {
        View view = LayoutInflater.from(getContext()).inflate(unitLayout.getLayoutRes(), mUnitGroup, false);
        mUnitGroup.addView(view);
        unitLayout.onCreate(view);
    }

    public void setStartLayerChangeListener(WheelView.OnChangeListener startLayerChangeListener) {
        ((WheelView) mOperatorView.findViewById(R.id.from)).setOnChangeListener(startLayerChangeListener);
    }

    public void setEndLayerChangeListener(WheelView.OnChangeListener endLayerChangeListener) {
        ((WheelView) mOperatorView.findViewById(R.id.to)).setOnChangeListener(endLayerChangeListener);
    }

    public void setDrawIfOutOfBoundsClickListener(CompoundButton.OnCheckedChangeListener listener) {
        mDrawIfOutOfBounds.setOnCheckedChangeListener(listener);
    }

}
