package com.wanjian.sak.demo;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.TextView;
import android.widget.Toast;

/**
 * Created by wanjian on 2017/3/7.
 */

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout);


        findViewById(R.id.open).setOnClickListener(new View.OnClickListener() {
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
        final ListView listView = (ListView) findViewById(R.id.listview);

        listView.setAdapter(new BaseAdapter() {
            @Override
            public int getCount() {
                return 100;
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
                ((TextView) convertView.findViewById(R.id.txt)).setText("" + position);
                return convertView;
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
