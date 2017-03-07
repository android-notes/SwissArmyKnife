package com.wanjian.sak.view;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.CheckBox;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.wanjian.sak.CanvasManager;
import com.wanjian.sak.ItemLayerLayout;
import com.wanjian.sak.ItemLayerViewLayout;
import com.wanjian.sak.R;
import com.wanjian.sak.canvasimpl.BackgroundColorLayer;
import com.wanjian.sak.canvasimpl.BitmapWidthHeightLayer;
import com.wanjian.sak.canvasimpl.BorderLayer;
import com.wanjian.sak.canvasimpl.ForceBitmapWidthHeightLayer;
import com.wanjian.sak.canvasimpl.InfoLayer;
import com.wanjian.sak.canvasimpl.MarginLayer;
import com.wanjian.sak.canvasimpl.PaddingLayer;
import com.wanjian.sak.canvasimpl.TextColorLayer;
import com.wanjian.sak.canvasimpl.TextSizeLayer;
import com.wanjian.sak.canvasimpl.WidthHeightLayer;
import com.wanjian.sak.layerview.LayerView;

import static android.os.Build.VERSION.SDK_INT;


/**
 * Created by wanjian on 2016/10/23.
 */

public class SAKCoverView extends RelativeLayout {
    private DrawingBoardView drawBoard;
    private OnLayoutChangeListener mOnLayoutChangeListener;
    private OperatorView operatorView;

    public SAKCoverView(Context context) {
        super(context);
        init();
    }

    public void addItem(ItemLayerLayout itemLayout) {
        int res = itemLayout.getLayoutRes();
        try {
            View item = LayoutInflater.from(getContext()).inflate(res, this, false);
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

    private void init() {
        inflate(getContext(), R.layout.sak_container_alyout, this);

        drawBoard = (DrawingBoardView) findViewById(R.id.drawBoard);
        operatorView = (OperatorView) findViewById(R.id.operatorView);

//        initView(operatorView);
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

    }

    public void attach(Activity activity) {
        if (SDK_INT >= 11) {//低版本android不支持
            activity.getWindow().getDecorView().addOnLayoutChangeListener(mOnLayoutChangeListener);
        }
    }

    public void detach(Activity activity) {
        if (SDK_INT >= 11) {//低版本android不支持
            activity.getWindow().getDecorView().removeOnLayoutChangeListener(mOnLayoutChangeListener);
        }
    }

    public SAKCoverView addLayerCheckBox(String desc) {
        View item = LayoutInflater.from(getContext()).inflate(R.layout.sak_layer_item, this, false);
        addView(item);

        return this;
    }

    private void initView(final OperatorView operatorView) {
        final CheckBox out;
        final CheckBox in;
        final CheckBox widthHeight;
        final CheckBox info;
        final WheelView from;
        final WheelView to;
        TextView ok;
        final TextView tree;
        TextView close;

        final CheckBox bmpwidthHeight;
        final CheckBox txtSize;
        final CheckBox txtColor;
        final CheckBox bagColor;
        final CheckBox forcebmpWH;
        final CheckBox stroke;


//        out = (CheckBox) operatorView.findViewById(R.id.out);
//        in = (CheckBox) operatorView.findViewById(R.id.in);
//        widthHeight = (CheckBox) operatorView.findViewById(R.id.widthHeight);
//        info = (CheckBox) operatorView.findViewById(R.id.info);
//        bmpwidthHeight = (CheckBox) operatorView.findViewById(R.id.bmpwidthHeight);
//        txtSize = (CheckBox) operatorView.findViewById(R.id.txtSize);
//        txtColor = (CheckBox) operatorView.findViewById(R.id.txtColor);
//        bagColor = (CheckBox) operatorView.findViewById(R.id.bagColor);
//        forcebmpWH = (CheckBox) operatorView.findViewById(R.id.forcebmpWH);
//        stroke = (CheckBox) operatorView.findViewById(R.id.stroke);

        from = (WheelView) operatorView.findViewById(R.id.from);
        to = (WheelView) operatorView.findViewById(R.id.to);

        to.setNum(20);
//        ok = (TextView) operatorView.findViewById(R.id.ok);
        tree = (TextView) operatorView.findViewById(R.id.tree);
        close = (TextView) operatorView.findViewById(R.id.close);

//        ok.setOnClickListener(new OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                CanvasManager.getInstance(getContext()).resetCanvas();
//                if (in.isChecked()) {
//                    CanvasManager.getInstance(getContext()).addCanvas(new PaddingLayer());
//                } else {
//
//                }
//                if (out.isChecked()) {
//                    CanvasManager.getInstance(getContext()).addCanvas(new MarginLayer());
//                }
//
//                if (widthHeight.isChecked()) {
//                    CanvasManager.getInstance(getContext()).addCanvas(new WidthHeightLayer());
//                }
//                if (info.isChecked()) {
//                    CanvasManager.getInstance(getContext()).addCanvas(new InfoLayer());
//                }
//
//                if (bmpwidthHeight.isChecked()) {
//                    CanvasManager.getInstance(getContext()).addCanvas(new BitmapWidthHeightLayer());
//                }
//
//                if (txtSize.isChecked()) {
//                    CanvasManager.getInstance(getContext()).addCanvas(new TextSizeLayer());
//                }
//                if (txtColor.isChecked()) {
//                    CanvasManager.getInstance(getContext()).addCanvas(new TextColorLayer());
//                }
//                if (bagColor.isChecked()) {
//                    CanvasManager.getInstance(getContext()).addCanvas(new BackgroundColorLayer());
//                }
//                if (forcebmpWH.isChecked()) {
//                    CanvasManager.getInstance(getContext()).addCanvas(new ForceBitmapWidthHeightLayer());
//                }
//                if (stroke.isChecked()) {
//                    CanvasManager.getInstance(getContext()).addCanvas(new BorderLayer());
//                }
//
////                refresh
//                if (((CheckBox) operatorView.findViewById(R.id.refresh)).isChecked()) {
//                    CanvasManager.getInstance(getContext()).needRefresh(true);
//                } else {
//                    CanvasManager.getInstance(getContext()).needRefresh(false);
//
//                }
//                CanvasManager.getInstance(getContext()).setLayer(from.getNum(), to.getNum());
//                operatorView.setVisibility(GONE);
//            }
//        });

        tree.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                CanvasManager.getInstance(getContext()).setLayer(from.getNum(), to.getNum());
                operatorView.setVisibility(GONE);
                showTree();
            }
        });

        close.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                CanvasManager.getInstance(getContext()).setLayer(0, -1);
                operatorView.setVisibility(GONE);
            }
        });

        from.setOnChangeListener(new WheelView.OnChangeListener() {
            @Override
            public void onChange(int num) {
                CanvasManager.getInstance(getContext()).setLayer(0, CanvasManager.getInstance(getContext()).getEndLayer());
            }
        });
        to.setOnChangeListener(new WheelView.OnChangeListener() {
            @Override
            public void onChange(int num) {
                CanvasManager.getInstance(getContext()).setLayer(CanvasManager.getInstance(getContext()).getStartLayer(), num);
            }
        });
        operatorView.findViewById(R.id.exit).setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                operatorView.setVisibility(GONE);
            }
        });
        operatorView.findViewById(R.id.help).setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                final Uri uri = Uri.parse("http://android-notes.github.io");
                final Intent intent = new Intent(Intent.ACTION_VIEW, uri);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                getContext().startActivity(intent);

            }
        });


//        findViewById(R.id.measure).setOnClickListener(new OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                inflate(getContext(), R.layout.sak_horizontal_measure_layout, SAKCoverView.this);
//                inflate(getContext(), R.layout.sak_vertical_measure_layout, SAKCoverView.this);
//                operatorView.setVisibility(GONE);
//            }
//        });
//
//        findViewById(R.id.round).setOnClickListener(new OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                inflate(getContext(), R.layout.sak_corner_measure_layout, SAKCoverView.this);
//
//                operatorView.setVisibility(GONE);
//            }
//        });
//
//        findViewById(R.id.takecolor).setOnClickListener(new OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                inflate(getContext(), R.layout.sak_takecolor_layout, SAKCoverView.this);
//                operatorView.setVisibility(GONE);
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
