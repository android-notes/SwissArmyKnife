package com.wanjian.sak.demo;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.TextView;
import android.widget.Toast;

import com.wanjian.sak.SAK;
import com.wanjian.sak.config.Config;
import com.wanjian.sak.config.Res;
import com.wanjian.sak.layer.impl.ActivityNameLayerView;
import com.wanjian.sak.layer.impl.BorderLayer;
import com.wanjian.sak.layer.impl.FragmentNameLayer;
import com.wanjian.sak.layer.impl.GridLayer;
import com.wanjian.sak.layer.impl.HorizontalMeasureView;
import com.wanjian.sak.layer.impl.MarginLayer;
import com.wanjian.sak.layer.impl.PaddingLayer;
import com.wanjian.sak.layer.impl.RelativeLayerView;
import com.wanjian.sak.layer.impl.TakeColorLayer;
import com.wanjian.sak.layer.impl.TextColorLayer;
import com.wanjian.sak.layer.impl.TextSizeLayer;
import com.wanjian.sak.layer.impl.TranslationLayerView;
import com.wanjian.sak.layer.impl.TreeView;
import com.wanjian.sak.layer.impl.VerticalMeasureView;
import com.wanjian.sak.layer.impl.ViewClassLayer;
import com.wanjian.sak.layer.impl.WidthHeightLayer;

/**
 * Created by wanjian on 2017/3/7.
 */

public class MainActivity extends Activity {

    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        return super.dispatchTouchEvent(ev);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        DisplayMetrics app = getResources().getDisplayMetrics();
        setContentView(R.layout.layout);


        findViewById(R.id.install).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                Config config=new Config.Build(getApplicationContext())
//                    .addLayer(BorderLayer.class)
//                    .addLayer(HorizontalMeasureView.class)
//                    .addLayer(RelativeLayerView.class)
//                    .build();
//                SAK.init(getApplication(), config);
//



                Config config=new Config.Build(getApplicationContext())
//                    .addLayer(TestLayer.class)
                    .addLayer(BorderLayer.class, getIcon(Res.Icon.sak_border_icon), getString(Res.Str.sak_border))
                    .addLayer(GridLayer.class, getIcon(Res.Icon.sak_grid_icon), getString(Res.Str.sak_grid))
                    .addLayer(PaddingLayer.class, getIcon(Res.Icon.sak_padding_icon), getString(Res.Str.sak_padding))
                    .addLayer(MarginLayer.class, getIcon(Res.Icon.sak_margin_icon), getString(Res.Str.sak_margin))
                    .addLayer(WidthHeightLayer.class, getIcon(Res.Icon.sak_width_height_icon), getString(Res.Str.sak_width_height))
                    .addLayer(TextColorLayer.class, getIcon(Res.Icon.sak_text_color_icon), getString(Res.Str.sak_txt_color))
                    .addLayer(TextSizeLayer.class, getIcon(Res.Icon.sak_text_size_icon), getString(Res.Str.sak_txt_size))
                    .addLayer(ActivityNameLayerView.class, getIcon(Res.Icon.sak_page_name_icon), getString(Res.Str.sak_activity_name))
                    .addLayer(FragmentNameLayer.class, getIcon(Res.Icon.sak_page_name_icon), getString(Res.Str.sak_fragment_name))
                    .addLayer(HorizontalMeasureView.class, getIcon(Res.Icon.sak_hori_measure_icon), getString(Res.Str.sak_horizontal_measure))
                    .addLayer(VerticalMeasureView.class, getIcon(Res.Icon.sak_ver_measure_icon), getString(Res.Str.sak_vertical_measure))
                    .addLayer(TakeColorLayer.class, getIcon(Res.Icon.sak_color_picker_icon), getString(Res.Str.sak_take_color))
                    .addLayer(ViewClassLayer.class, getIcon(Res.Icon.sak_controller_type_icon), getString(Res.Str.sak_view_name))
                    .addLayer(TreeView.class, getIcon(Res.Icon.sak_layout_tree_icon), getString(Res.Str.sak_layout_tree))
                    .addLayer(RelativeLayerView.class, getIcon(Res.Icon.sak_relative_distance_icon), getString(Res.Str.sak_relative_distance))
                    .addLayer(TranslationLayerView.class, getIcon(Res.Icon.sak_drag_icon), getString(Res.Str.sak_translation_view))
                    .build();
                SAK.init(getApplication(), null);
//                T.test2(getWindow().getDecorView().getRootView().getParent());
//                InputEventReceiverCompact.test2(getWindow().getDecorView().getRootView().getParent());
            }
        });
        findViewById(R.id.uninstall).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                SAK.unInstall();
            }
        });

        findViewById(R.id.open).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(), DialogAct.class));
            }
        });

        findViewById(R.id.openAct).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(), SecAct.class));
            }
        });

        findViewById(R.id.dialog).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog();
            }
        });


        findViewById(R.id.userDialog).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                userDialog();
            }
        });


        findViewById(R.id.popupwindow).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                popupwindow(v);
            }
        });
        findViewById(R.id.reLayout).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                v.requestLayout();
            }
        });

        findViewById(R.id.userWindow).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                userWindow(v);
            }
        });
        findViewById(R.id.toast).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Toast toast = Toast.makeText(getApplicationContext(), "", Toast.LENGTH_LONG);
                View view = LayoutInflater.from(getApplicationContext()).inflate(R.layout.user_window, null);
                toast.setView(view);
                toast.show();
            }
        });

        final ListView listView = (ListView) findViewById(R.id.listview);

        listView.setAdapter(new BaseAdapter() {
            @Override
            public int getCount() {
                return 1000;
            }

            @Override
            public Object getItem(int position) {
                return null;
            }

            @Override
            public long getItemId(int position) {
                return 0;
            }

            @Override
            public View getView(int position, View convertView, ViewGroup parent) {
                if (convertView == null) {
                    convertView = LayoutInflater.from(getApplicationContext()).inflate(R.layout.item, listView, false);
                }
                int value = 1;// (int) (Math.random() * 3);
                if (value == 1) {
                    try {
                        Thread.sleep(30);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
                ((TextView) convertView.findViewById(R.id.txt)).setText("" + position);
                return convertView;
            }
        });
        getWindow().getDecorView().setPadding(dp2px(40), dp2px(40), dp2px(40), dp2px(40));
    }

    public int dp2px(float length) {
        float scale = getResources().getDisplayMetrics().density;
        return (int) (length * scale + 0.5f);
    }

    private void userWindow(View v) {
        final WindowManager windowManager = (WindowManager) getSystemService(WINDOW_SERVICE);
        final View view = LayoutInflater.from(this).inflate(R.layout.user_window, null);
        WindowManager.LayoutParams params = new WindowManager.LayoutParams();
        params.width = WindowManager.LayoutParams.WRAP_CONTENT;
        params.height = WindowManager.LayoutParams.WRAP_CONTENT;
        params.gravity = Gravity.CENTER;
        windowManager.addView(view, params);
        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                windowManager.removeView(view);
            }
        });
    }


    private void popupwindow(View v) {


        View view = LayoutInflater.from(this).inflate(R.layout.popupwindow, null);

        PopupWindow popupWindow = new PopupWindow(view, ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        popupWindow.setBackgroundDrawable(new BitmapDrawable());
        popupWindow.setFocusable(true);
        popupWindow
                .showAsDropDown(v, 0
                        , 0);

    }

    private void dialog() {
        new AlertDialog.Builder(this)
                .setTitle("title")
                .setIcon(R.mipmap.ic_launcher)
                .setMessage("hello sak-autopilot")
                .setNegativeButton("cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                }).
                create().show();
    }

    private void userDialog() {
        new AlertDialog.Builder(this)
                .setView(LayoutInflater.from(getApplicationContext()).inflate(R.layout.popupwindow, null))
//                .setTitle("title")
//                .setNegativeButton("cancel", new DialogInterface.OnClickListener() {
//                    @Override
//                    public void onClick(DialogInterface dialog, int which) {
//
//                    }
//                }).
                .create().show();
    }
   public Drawable getIcon(int id){
        return getResources().getDrawable(id);
    }
}
