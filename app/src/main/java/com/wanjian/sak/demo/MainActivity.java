package com.wanjian.sak.demo;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.text.Html;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.style.AbsoluteSizeSpan;
import android.text.style.BackgroundColorSpan;
import android.text.style.ForegroundColorSpan;
import android.text.style.RelativeSizeSpan;
import android.util.DisplayMetrics;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.TextView;
import android.widget.Toast;

import com.wanjian.sak.SAK;

/**
 * Created by wanjian on 2017/3/7.
 */

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        DisplayMetrics app = getResources().getDisplayMetrics();
        setContentView(R.layout.layout);


        findViewById(R.id.install).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SAK.init(getApplication(), null);
            }
        });
        findViewById(R.id.uninstall).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SAK.unInstall();
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
}
