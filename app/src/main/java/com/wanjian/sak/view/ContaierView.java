package com.wanjian.sak.view;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.view.View;
import android.widget.CheckBox;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.wanjian.sak.CanvasManager;
import com.wanjian.sak.R;
import com.wanjian.sak.canvasimpl.BackgroundColorCanvas;
import com.wanjian.sak.canvasimpl.BitmapWidthHeightCanvas;
import com.wanjian.sak.canvasimpl.BorderCanvas;
import com.wanjian.sak.canvasimpl.ForceBitmapWidthHeightCanvas;
import com.wanjian.sak.canvasimpl.InfoCanvas;
import com.wanjian.sak.canvasimpl.MarginCanvas;
import com.wanjian.sak.canvasimpl.PaddingCanvas;
import com.wanjian.sak.canvasimpl.TextColorCanvas;
import com.wanjian.sak.canvasimpl.TextSizeCanvas;
import com.wanjian.sak.canvasimpl.WidthHeightCanvas;
import com.wanjian.sak.canvasimpl.performance.DrawTimeCanvas;
import com.wanjian.sak.canvasimpl.performance.PageDrawTime;

import static android.os.Build.VERSION.SDK_INT;


/**
 * Created by wanjian on 2016/10/23.
 */

public class ContaierView extends RelativeLayout {
    //    private Activity mActivity;
    private DrawingBoardView drawBoard;

    public ContaierView(Context activity) {
        super(activity);
//        this.mActivity = activity;
        init();
    }


    private void init() {
        inflate(getContext(), R.layout.sak_container_alyout, this);

        drawBoard = (DrawingBoardView) findViewById(R.id.drawBoard);
        FloatView floatView = (FloatView) findViewById(R.id.floatView);
        final OperatorView operatorView = (OperatorView) findViewById(R.id.operatorView);

        initView(operatorView);
        floatView.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                operatorView.setVisibility(VISIBLE);
            }
        });
        if (SDK_INT >= 11) {//低版本android不支持
//            mActivity.getWindow().getDecorView().addOnLayoutChangeListener(new OnLayoutChangeListener() {
//                @Override
//                public void onLayoutChange(View v, int left, int top, int right, int bottom, int oldLeft, int oldTop, int oldRight, int oldBottom) {
//                    drawBoard.invalidate();
//                }
//            });
        }
    }

    public void invalidate() {
        drawBoard.invalidate();
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
        final CheckBox drawTime;
        final CheckBox pageDrawTime;


        out = (CheckBox) operatorView.findViewById(R.id.out);
        in = (CheckBox) operatorView.findViewById(R.id.in);
        widthHeight = (CheckBox) operatorView.findViewById(R.id.widthHeight);
        info = (CheckBox) operatorView.findViewById(R.id.info);
        bmpwidthHeight = (CheckBox) operatorView.findViewById(R.id.bmpwidthHeight);
        txtSize = (CheckBox) operatorView.findViewById(R.id.txtSize);
        txtColor = (CheckBox) operatorView.findViewById(R.id.txtColor);
        bagColor = (CheckBox) operatorView.findViewById(R.id.bagColor);
        forcebmpWH = (CheckBox) operatorView.findViewById(R.id.forcebmpWH);
        stroke = (CheckBox) operatorView.findViewById(R.id.stroke);
        drawTime = (CheckBox) operatorView.findViewById(R.id.drawTime);
        pageDrawTime = (CheckBox) operatorView.findViewById(R.id.pageDrawTime);

        from = (WheelView) operatorView.findViewById(R.id.from);
        to = (WheelView) operatorView.findViewById(R.id.to);

        to.setNum(20);
        ok = (TextView) operatorView.findViewById(R.id.ok);
        tree = (TextView) operatorView.findViewById(R.id.tree);
        close = (TextView) operatorView.findViewById(R.id.close);

        ok.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                CanvasManager.getInstance(getContext()).resetCanvas();
                if (in.isChecked()) {
                    CanvasManager.getInstance(getContext()).addCanvas(new PaddingCanvas());
                } else {

                }
                if (out.isChecked()) {
                    CanvasManager.getInstance(getContext()).addCanvas(new MarginCanvas());
                }

                if (widthHeight.isChecked()) {
                    CanvasManager.getInstance(getContext()).addCanvas(new WidthHeightCanvas());
                }
                if (info.isChecked()) {
                    CanvasManager.getInstance(getContext()).addCanvas(new InfoCanvas());
                }

                if (bmpwidthHeight.isChecked()) {
                    CanvasManager.getInstance(getContext()).addCanvas(new BitmapWidthHeightCanvas());
                }

                if (txtSize.isChecked()) {
                    CanvasManager.getInstance(getContext()).addCanvas(new TextSizeCanvas());
                }
                if (txtColor.isChecked()) {
                    CanvasManager.getInstance(getContext()).addCanvas(new TextColorCanvas());
                }
                if (bagColor.isChecked()) {
                    CanvasManager.getInstance(getContext()).addCanvas(new BackgroundColorCanvas());
                }
                if (forcebmpWH.isChecked()) {
                    CanvasManager.getInstance(getContext()).addCanvas(new ForceBitmapWidthHeightCanvas());
                }
                if (stroke.isChecked()) {
                    CanvasManager.getInstance(getContext()).addCanvas(new BorderCanvas());
                }
                if (drawTime.isChecked()) {
                    CanvasManager.getInstance(getContext()).addCanvas(new DrawTimeCanvas());
                }
                if (pageDrawTime.isChecked()) {
                    CanvasManager.getInstance(getContext()).addCanvas(new PageDrawTime());
                }

//                refresh
                if (((CheckBox) operatorView.findViewById(R.id.refresh)).isChecked()) {
                    CanvasManager.getInstance(getContext()).needRefresh(true);
                } else {
                    CanvasManager.getInstance(getContext()).needRefresh(false);

                }
                CanvasManager.getInstance(getContext()).setLayer(from.getNum(), to.getNum());
                operatorView.setVisibility(GONE);
            }
        });

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


        findViewById(R.id.measure).setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                inflate(getContext(), R.layout.sak_horizontal_measure_layout, ContaierView.this);
                inflate(getContext(), R.layout.sak_vertical_measure_layout, ContaierView.this);
                operatorView.setVisibility(GONE);
            }
        });

        findViewById(R.id.round).setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                inflate(getContext(), R.layout.sak_corner_measure_layout, ContaierView.this);

                operatorView.setVisibility(GONE);
            }
        });

        findViewById(R.id.takecolor).setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                inflate(getContext(), R.layout.sak_takecolor_layout, ContaierView.this);
                operatorView.setVisibility(GONE);
            }
        });


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
