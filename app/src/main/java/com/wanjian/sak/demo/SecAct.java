package com.wanjian.sak.demo;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.DisplayMetrics;
import android.view.View;

import com.wanjian.sak.SAK;
import com.wanjian.sak.utils.BitmapCreater;

/**
 * Created by wanjian on 2017/4/1.
 */

public class SecAct extends AppCompatActivity {

    Bitmap bitmap;//Activity是否泄漏更明显

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.sec_act);


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
                startActivity(new Intent(getApplicationContext(), SecAct.class));
            }
        });

        DisplayMetrics metrics = getResources().getDisplayMetrics();
        bitmap = BitmapCreater.create(metrics.widthPixels, metrics.heightPixels, Bitmap.Config.ARGB_8888);
        if (bitmap != null) {
            bitmap.eraseColor(Color.BLACK);
        }

        getSupportFragmentManager().beginTransaction().replace(R.id.fragContainer, new ContainerFragment())
                .commit();
    }
}
