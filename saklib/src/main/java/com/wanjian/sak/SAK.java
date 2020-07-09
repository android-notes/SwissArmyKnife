package com.wanjian.sak;

import android.app.Application;
import android.content.Intent;
import android.os.Build;
import android.provider.Settings;
import android.util.Log;
import android.widget.Toast;

import com.wanjian.sak.config.Config;

/**
 * Created by wanjian on 2017/2/20.
 */

public class SAK {
  private static Scaffold sScaffold;

  public static void init(Application application, Config config) {
    if (Build.VERSION.SDK_INT < Build.VERSION_CODES.LOLLIPOP) {
      Log.w("SAK", "暂不支持Android5.0以下设备");
      return;
    }

    if (application.getApplicationInfo().targetSdkVersion >= Build.VERSION_CODES.M && !Settings.canDrawOverlays(application)) {
//      if (ContextCompat.checkSelfPermission(application, Manifest.permission.SYSTEM_ALERT_WINDOW) != PackageManager.PERMISSION_GRANTED) {
      Toast.makeText(application, "需要悬浮窗权限才能使用SwissArmyKnife", Toast.LENGTH_LONG).show();
      Intent intent = new Intent(Settings.ACTION_MANAGE_OVERLAY_PERMISSION);
      intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
      application.startActivity(intent);
      return;
//      }
    }
    if (sScaffold != null) {
      return;
    }
    sScaffold = new Scaffold();
    sScaffold.install(application, config);
  }

  private SAK() {
  }
//
//  public static void unInstall() {
//    if (sScaffold != null) {
//      sScaffold.unInstall();
//      sScaffold = null;
//    }
//  }

}
