package com.wanjian.sak.view;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;

import com.wanjian.sak.layerview.AbsLayerView;
import com.wanjian.sak.mapper.ItemLayerLayout;
import com.wanjian.sak.mapper.ItemLayerViewLayout;
import com.wanjian.sak.R;
import com.wanjian.sak.mapper.UnitLayout;
import com.wanjian.sak.layerview.DragLayerView;

import static android.os.Build.VERSION.SDK_INT;


/**
 * Created by wanjian on 2016/10/23.
 */

public class SAKCoverView extends RelativeLayout {
    private DrawingBoardView mDrawBoard;
    private OnLayoutChangeListener mOnLayoutChangeListener;
    private OperatorView mOperatorView;
    private RadioGroup mUnitGroup;

    public SAKCoverView(Context context) {
        super(context);
        init();
    }


    private void init() {
        inflate(getContext(), R.layout.sak_container_layout, this);

        mDrawBoard = (DrawingBoardView) findViewById(R.id.drawBoard);
        mOperatorView = (OperatorView) findViewById(R.id.operatorView);

        CheckBox checkBox = ((CheckBox) mOperatorView.findViewById(R.id.refresh));
        mDrawBoard.neededRefresh(checkBox.isChecked());
        checkBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                mDrawBoard.neededRefresh(isChecked);
            }
        });
        findViewById(R.id.floatView).setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                mOperatorView.setVisibility(VISIBLE);
            }
        });
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.HONEYCOMB) {
            mOnLayoutChangeListener = new OnLayoutChangeListener() {
                @Override
                public void onLayoutChange(View v, int left, int top, int right, int bottom, int oldLeft, int oldTop, int oldRight, int oldBottom) {
                    mDrawBoard.invalidate();
                }
            };
        }

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
                final Uri uri = Uri.parse("https://github.com/android-notes/SwissArmyKnife");
                final Intent intent = new Intent(Intent.ACTION_VIEW, uri);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                getContext().startActivity(intent);
            }
        });


    }

    public void setOnDrawListener(DrawingBoardView.OnDrawListener drawListener) {
        mDrawBoard.setOnDrawListener(drawListener);
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

    public void attach(Activity activity) {
        if (SDK_INT >= 11) {
            activity.getWindow().getDecorView().addOnLayoutChangeListener(mOnLayoutChangeListener);
        }
    }

    public void detach(Activity activity) {
        if (SDK_INT >= 11) {
            activity.getWindow().getDecorView().removeOnLayoutChangeListener(mOnLayoutChangeListener);
        }
    }


    public void setStartLayerChangeListener(WheelView.OnChangeListener startLayerChangeListener) {
        ((WheelView) mOperatorView.findViewById(R.id.from)).setOnChangeListener(startLayerChangeListener);
    }

    public void setEndLayerChangeListener(WheelView.OnChangeListener endLayerChangeListener) {
        ((WheelView) mOperatorView.findViewById(R.id.to)).setOnChangeListener(endLayerChangeListener);
    }



}
