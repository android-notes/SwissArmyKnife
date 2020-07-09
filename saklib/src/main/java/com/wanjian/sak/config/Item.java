package com.wanjian.sak.config;

import android.graphics.drawable.Drawable;

import com.wanjian.sak.layer.Layer;

public class Item {
  public Class<? extends Layer> layerType;
  public Drawable icon;
  public String name;
  private boolean isEnable;
  public Item(Class<? extends Layer> layerType, Drawable icon, String name) {
    this.layerType = layerType;
    this.icon = icon;
    this.name = name;
  }

  public boolean isEnable() {
    return isEnable;
  }

  public void setEnable(boolean enable) {
    isEnable = enable;
  }
}
