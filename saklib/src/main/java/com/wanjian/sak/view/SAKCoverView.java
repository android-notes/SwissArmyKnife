package com.wanjian.sak.view;

import android.app.Activity;
import android.content.Context;
import android.content.res.Resources;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.wanjian.sak.ItemLayerLayout;
import com.wanjian.sak.ItemLayerViewLayout;
import com.wanjian.sak.R;
import com.wanjian.sak.UnitLayout;
import com.wanjian.sak.layerview.LayerView;

import static android.os.Build.VERSION.SDK_INT;


/**
 * Created by wanjian on 2016/10/23.
 */

public class SAKCoverView extends RelativeLayout {
    private DrawingBoardView drawBoard;
    private OnLayoutChangeListener mOnLayoutChangeListener;
    private OperatorView operatorView;
    private RadioGroup mUnitGroup;

    public SAKCoverView(Context context) {
        super(context);
        init();
    }


    private void init() {
        inflate(getContext(), R.layout.sak_container_alyout, this);

        drawBoard = (DrawingBoardView) findViewById(R.id.drawBoard);
        operatorView = (OperatorView) findViewById(R.id.operatorView);

        CheckBox checkBox = ((CheckBox) operatorView.findViewById(R.id.refresh));
        drawBoard.neededRefresh(checkBox.isChecked());
        checkBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                drawBoard.neededRefresh(isChecked);
            }
        });
        findViewById(R.id.floatView).setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                operatorView.setVisibility(VISIBLE);
            }
        });
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.HONEYCOMB) {
            mOnLayoutChangeListener = new OnLayoutChangeListener() {
                @Override
                public void onLayoutChange(View v, int left, int top, int right, int bottom, int oldLeft, int oldTop, int oldRight, int oldBottom) {
                    drawBoard.invalidate();
                }
            };
        }

        operatorView.findViewById(R.id.ok).setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                operatorView.setVisibility(GONE);
            }
        });

        mUnitGroup = ((RadioGroup) operatorView.findViewById(R.id.unitGroup));
        operatorView.findViewById(R.id.help).setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                final Uri uri = Uri.parse("http://android-notes.github.io");
                final Intent intent = new Intent(Intent.ACTION_VIEW, uri);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                getContext().startActivity(intent);
            }
        });


    }

    public void setOnDrawListener(DrawingBoardView.OnDrawListener drawListener) {
        drawBoard.setOnDrawListener(drawListener);
    }

    public void addItem(ItemLayerLayout itemLayout) {
        int res = itemLayout.getLayoutRes();
        try {
            View item = LayoutInflater.from(getContext()).inflate(res, null);
            itemLayout.onCreate(item);
            operatorView.addItem(item);
        } catch (Resources.NotFoundException e) {
            throw e;
        }

    }

    public void addItem(ItemLayerViewLayout itemLayout) {
        LayerView view = itemLayout.getLayerView();

        itemLayout.onCreate(view);

        operatorView.addItem(view);
    }

    public void addItem(UnitLayout unitLayout) {
        View view = LayoutInflater.from(getContext()).inflate(unitLayout.getLayoutRes(), mUnitGroup, false);
        unitLayout.onCreate(view);
        mUnitGroup.addView(view);
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

//    public SAKCoverView addLayerCheckBox(String desc) {
//        View item = LayoutInflater.from(getContext()).inflate(R.layout.sak_layer_item, this, false);
//        addView(item);
//
//        return this;
//    }

    public void setStartLayerChangeListener(WheelView.OnChangeListener startLayerChangeListener) {
        ((WheelView) operatorView.findViewById(R.id.from)).setOnChangeListener(startLayerChangeListener);
    }

    public void setEndLayerChangeListener(WheelView.OnChangeListener endLayerChangeListener) {
        ((WheelView) operatorView.findViewById(R.id.to)).setOnChangeListener(endLayerChangeListener);
    }

    public void setOnCloseListener(final OnClickListener listener){
        operatorView.findViewById(R.id.close).setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {

                listener.onClick(v);
                operatorView.setVisibility(GONE);
            }
        });
    }
    private void initView(final OperatorView operatorView) {

//        from = (WheelView) operatorView.findViewById(R.id.from);
//        to = (WheelView) operatorView.findViewById(R.id.to);
//
//        to.setNum(20);
//        tree = (TextView) operatorView.findViewById(R.id.tree);
//        close = (TextView) operatorView.findViewById(R.id.close);
//
//
//        tree.setOnClickListener(new OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                CanvasManager.getInstance(getContext()).setLayer(from.getNum(), to.getNum());
//                operatorView.setVisibility(GONE);
//                showTree();
//            }
//        });
//
//        close.setOnClickListener(new OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                CanvasManager.getInstance(getContext()).setLayer(0, -1);
//                operatorView.setVisibility(GONE);
//            }
//        });
//
//        from.setOnChangeListener(new WheelView.OnChangeListener() {
//            @Override
//            public void onChange(int num) {
//                CanvasManager.getInstance(getContext()).setLayer(0, CanvasManager.getInstance(getContext()).getEndLayer());
//            }
//        });
//        to.setOnChangeListener(new WheelView.OnChangeListener() {
//            @Override
//            public void onChange(int num) {
//                CanvasManager.getInstance(getContext()).setLayer(CanvasManager.getInstance(getContext()).getStartLayer(), num);
//            }
//        });
//        operatorView.findViewById(R.id.exit).setOnClickListener(new OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                operatorView.setVisibility(GONE);
//            }
//        });
//        operatorView.findViewById(R.id.help).setOnClickListener(new OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                final Uri uri = Uri.parse("http://android-notes.github.io");
//                final Intent intent = new Intent(Intent.ACTION_VIEW, uri);
//                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
//                getContext().startActivity(intent);
//
//            }
//        });


    }

    private void showTree() {
        final TreeViewOperator viewOperator = new TreeViewOperator(getContext());
        addView(viewOperator);
        viewOperator.findViewById(R.id.exit).setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                removeView(viewOperator);
            }
        });
    }
}
