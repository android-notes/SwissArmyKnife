package com.wanjian.sak.demo;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.Html;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.style.AbsoluteSizeSpan;
import android.text.style.BackgroundColorSpan;
import android.text.style.ForegroundColorSpan;
import android.text.style.RelativeSizeSpan;
import android.util.DisplayMetrics;
import android.view.View;
import android.widget.Button;

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
//                SAK.unInstall();
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


        Button viewPicker1 = (Button) findViewById(R.id.viewPicker1);
        SpannableString string = new SpannableString("AAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAA");
        string.setSpan(new AbsoluteSizeSpan(20, true), 3, 5, Spanned.SPAN_EXCLUSIVE_INCLUSIVE);
        string.setSpan(new AbsoluteSizeSpan(30, true), 5, 10, Spanned.SPAN_EXCLUSIVE_INCLUSIVE);
        string.setSpan(new RelativeSizeSpan(2), 2, 15, Spanned.SPAN_EXCLUSIVE_INCLUSIVE);
        string.setSpan(new RelativeSizeSpan(2), 2, 15, Spanned.SPAN_EXCLUSIVE_INCLUSIVE);

        string.setSpan(new ForegroundColorSpan(Color.RED), 2, 15, Spanned.SPAN_EXCLUSIVE_INCLUSIVE);
        string.setSpan(new ForegroundColorSpan(Color.GREEN), 5, 10, Spanned.SPAN_EXCLUSIVE_INCLUSIVE);
        string.setSpan(new ForegroundColorSpan(Color.BLUE), 6, 8, Spanned.SPAN_EXCLUSIVE_INCLUSIVE);

        string.setSpan(new BackgroundColorSpan(Color.MAGENTA), 2, 15, Spanned.SPAN_EXCLUSIVE_INCLUSIVE);
        string.setSpan(new BackgroundColorSpan(Color.YELLOW), 1, 3, Spanned.SPAN_EXCLUSIVE_INCLUSIVE);
        string.setSpan(new BackgroundColorSpan(Color.GRAY), 5, 8, Spanned.SPAN_EXCLUSIVE_INCLUSIVE);

        viewPicker1.setText(string);

        Button viewPicker2 = (Button) findViewById(R.id.viewPicker2);
        Spanned spanned = Html.fromHtml("北京市发布霾黄色预警，<font color='#ff0000'><small><small>外出携带好</small></small></font>口罩");
        viewPicker2.setText(spanned);


//        Button viewPicker3 = findViewById(R.id.viewPicker3);
//        string = new SpannableString("AAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAA");
//        string.setSpan(new AbsoluteSizeSpan(20, true), 0, 5, Spanned.SPAN_EXCLUSIVE_INCLUSIVE);
//        string.setSpan(new AbsoluteSizeSpan(30, true), 5, 10, Spanned.SPAN_EXCLUSIVE_INCLUSIVE);
//        string.setSpan(new RelativeSizeSpan(2), 2, 15, Spanned.SPAN_EXCLUSIVE_INCLUSIVE);
//        string.setSpan(new RelativeSizeSpan(2), 2, 15, Spanned.SPAN_EXCLUSIVE_INCLUSIVE);
//
//        viewPicker3.setText(string);

    }
}
