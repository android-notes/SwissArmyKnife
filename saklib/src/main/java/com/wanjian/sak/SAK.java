package com.wanjian.sak;

import android.app.Application;
import android.content.Intent;
import android.os.Build;
import android.provider.Settings;
import android.util.Log;
import android.widget.Toast;

import com.wanjian.sak.config.Config;
import com.wanjian.sak.layer.impl.ActivityNameLayerView;
import com.wanjian.sak.layer.impl.BackgroundColorLayer;
import com.wanjian.sak.layer.impl.BitmapWidthHeightLayer;
import com.wanjian.sak.layer.impl.BorderLayer;
import com.wanjian.sak.layer.impl.FragmentNameLayer;
import com.wanjian.sak.layer.impl.GridLayer;
import com.wanjian.sak.layer.impl.HorizontalMeasureView;
import com.wanjian.sak.layer.impl.MarginLayer;
import com.wanjian.sak.layer.impl.PaddingLayer;
import com.wanjian.sak.layer.impl.RelativeLayerView;
import com.wanjian.sak.layer.impl.ScalpelFrameLayout;
import com.wanjian.sak.layer.impl.TakeColorLayer;
import com.wanjian.sak.layer.impl.TextColorLayer;
import com.wanjian.sak.layer.impl.TextSizeLayer;
import com.wanjian.sak.layer.impl.TranslationLayerView;
import com.wanjian.sak.layer.impl.TreeView;
import com.wanjian.sak.layer.impl.VerticalMeasureView;
import com.wanjian.sak.layer.impl.ViewClassLayer;
import com.wanjian.sak.layer.impl.WidthHeightLayer;

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

    if (application.getApplicationInfo().targetSdkVersion >= Build.VERSION_CODES.M&&Build.VERSION.SDK_INT>=Build.VERSION_CODES.M && !Settings.canDrawOverlays(application)) {
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


    if (config == null) {
      config = new Config.Build(application)
          .addLayer(BorderLayer.class, application.getDrawable(R.drawable.sak_border_icon), application.getString(R.string.sak_border))
          .addLayer(GridLayer.class, application.getDrawable(R.drawable.sak_grid_icon), application.getString(R.string.sak_grid))
          .addLayer(PaddingLayer.class, application.getDrawable(R.drawable.sak_padding_icon), application.getString(R.string.sak_padding))
          .addLayer(MarginLayer.class, application.getDrawable(R.drawable.sak_margin_icon), application.getString(R.string.sak_margin))
          .addLayer(WidthHeightLayer.class, application.getDrawable(R.drawable.sak_width_height_icon), application.getString(R.string.sak_width_height))
          .addLayer(TextColorLayer.class, application.getDrawable(R.drawable.sak_text_color_icon), application.getString(R.string.sak_txt_color))
          .addLayer(TextSizeLayer.class, application.getDrawable(R.drawable.sak_text_size_icon), application.getString(R.string.sak_txt_size))
          .addLayer(BackgroundColorLayer.class, application.getDrawable(R.drawable.sak_background_color_icon), application.getString(R.string.sak_bag_color))
          .addLayer(ActivityNameLayerView.class, application.getDrawable(R.drawable.sak_page_name_icon), application.getString(R.string.sak_activity_name))
          .addLayer(FragmentNameLayer.class, application.getDrawable(R.drawable.sak_page_name_icon), application.getString(R.string.sak_fragment_name))
          .addLayer(HorizontalMeasureView.class, application.getDrawable(R.drawable.sak_hori_measure_icon), application.getString(R.string.sak_horizontal_measure))
          .addLayer(VerticalMeasureView.class, application.getDrawable(R.drawable.sak_ver_measure_icon), application.getString(R.string.sak_vertical_measure))
          .addLayer(TakeColorLayer.class, application.getDrawable(R.drawable.sak_color_picker_icon), application.getString(R.string.sak_take_color))
          .addLayer(ViewClassLayer.class, application.getDrawable(R.drawable.sak_controller_type_icon), application.getString(R.string.sak_view_name))
          .addLayer(TreeView.class, application.getDrawable(R.drawable.sak_layout_tree_icon), application.getString(R.string.sak_layout_tree))
          .addLayer(RelativeLayerView.class, application.getDrawable(R.drawable.sak_relative_distance_icon), application.getString(R.string.sak_relative_distance))
          .addLayer(TranslationLayerView.class, application.getDrawable(R.drawable.sak_drag_icon), application.getString(R.string.sak_translation_view))
          .addLayer(ScalpelFrameLayout.class, application.getDrawable(R.drawable.sak_layer_icon), "Scalpel")
//          .addLayer(ViewDrawPerformLayer.class, application.getDrawable(R.drawable.sak_performance_icon),application.getString(R.string.sak_performance) )
          .addLayer(BitmapWidthHeightLayer.class, application.getDrawable(R.drawable.sak_img_size),application.getString(R.string.sak_image_w_h) )
          .build();
    }
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
