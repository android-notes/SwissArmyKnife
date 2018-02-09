package com.wanjian.sak.view;

import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.net.Uri;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
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

public class OperatorView extends FrameLayout {
    private ViewGroup container;

    private RadioGroup mUnitGroup;
    private CheckBox mDrawIfOutOfBounds;

    private View mOptContainer;

    public OperatorView(Context context) {
        super(context);
        init();
    }

    public OperatorView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public void show() {
        mOptContainer.setVisibility(VISIBLE);
    }

    public void hide() {
        mOptContainer.setVisibility(GONE);
    }

    private void init() {
//        setGravity(Gravity.CENTER);
//        setOrientation(VERTICAL);
        inflate(getContext(), R.layout.sak_operator_layout, this);
        container = (ViewGroup) findViewById(R.id.container);
        mUnitGroup = ((RadioGroup) findViewById(R.id.unitGroup));
        mDrawIfOutOfBounds = (CheckBox) findViewById(R.id.drawIfOutOfBounds);
        mOptContainer = findViewById(R.id.optContainer);
        findViewById(R.id.ok).setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                mOptContainer.setVisibility(GONE);
            }
        });
        findViewById(R.id.help).setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                final Uri uri = Uri.parse("https://github.com/android-notes/SwissArmyKnife/blob/autopilot/README.md");
                final Intent intent = new Intent(Intent.ACTION_VIEW, uri);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                getContext().startActivity(intent);
            }
        });

    }

    public void addItem(ItemLayerLayout itemLayout) {
        int res = itemLayout.getLayoutRes();
        try {
            View item = LayoutInflater.from(getContext()).inflate(res, this, false);
            container.addView(item);
            itemLayout.onCreate(item);
        } catch (Resources.NotFoundException e) {
            throw e;
        }

    }

    public void addItem(ItemLayerViewLayout itemLayout) {
        AbsLayerView layerView = itemLayout.getLayerView();
        View view = LayoutInflater.from(getContext()).inflate(itemLayout.getLayoutRes(), this, false);
        layerView.setVisibility(GONE);
        container.addView(view);
        layerView.setLayoutParams(layerView.getLayoutParams(generateDefaultLayoutParams()));
        addView(layerView, 0);
        itemLayout.onCreate(view);
    }

    public void addItem(UnitLayout unitLayout) {
        View view = LayoutInflater.from(getContext()).inflate(unitLayout.getLayoutRes(), mUnitGroup, false);
        mUnitGroup.addView(view);
        unitLayout.onCreate(view);
    }
//
//    private void addItem(View view) {
//        container.addView(view);
//    }

    public void setStartLayerChangeListener(WheelView.OnChangeListener startLayerChangeListener) {
        ((WheelView) findViewById(R.id.from)).setOnChangeListener(startLayerChangeListener);
    }

    public void setEndLayerChangeListener(WheelView.OnChangeListener endLayerChangeListener) {
        ((WheelView) findViewById(R.id.to)).setOnChangeListener(endLayerChangeListener);
    }

    public void setDrawIfOutOfBoundsClickListener(CompoundButton.OnCheckedChangeListener listener) {
        mDrawIfOutOfBounds.setOnCheckedChangeListener(listener);
    }
}
