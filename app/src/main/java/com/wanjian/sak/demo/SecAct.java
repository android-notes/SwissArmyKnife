package com.wanjian.sak.demo;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.wanjian.sak.SAK;

/**
 * Created by wanjian on 2017/4/1.
 */

public class SecAct extends MainActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        Button button=((Button) findViewById(R.id.install));

        button.setText("uninstall");
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SAK.unInstall(getApplication());
            }
        });
    }
}
