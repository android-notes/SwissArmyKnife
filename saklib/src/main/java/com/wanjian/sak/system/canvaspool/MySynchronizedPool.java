package com.wanjian.sak.system.canvaspool;

import android.graphics.Canvas;
import android.graphics.Rect;
import android.util.Pools;
import android.view.DisplayListCanvas;
import android.view.RenderNode;
import android.view.Surface;
import android.view.View;
import android.view.ViewRootImpl;

import java.lang.reflect.Field;

public class MySynchronizedPool extends Pools.SynchronizedPool {
  private Pools.SynchronizedPool origin;

  public MySynchronizedPool(Pools.SynchronizedPool origin) {
    super(1, null);
    this.origin = origin;
  }

  @Override
  public Object acquire() {
    Object o = origin.acquire();
    System.out.println("MySynchronizedPool...acquire:" + o);
    return o;
  }

  @Override
  public boolean release(Object element) {
    System.out.println("MySynchronizedPool...release:" + element);//DisplayListCanvas


//    Canvas canvas = (Canvas) element;
//    int w = canvas.getWidth();
//    int h = canvas.getHeight();

//    canvas.clipRect(0,0,w*2,h*2, Region.Op.REPLACE);
//    canvas.drawColor(0x6600ff00);
//    System.out.println("w:h  " + w + " " + h);



//
    /*
      private RenderNode(String name, View owningView) {
147          mNativeRenderNode = nCreate(name);
148          NoImagePreloadHolder.sRegistry.registerNativeAllocation(this, mNativeRenderNode);
149          mOwningView = owningView;
150      }
151
     */

    ((Canvas) element).drawColor(0x88FF0000);

    try {
      Field mWidthF = DisplayListCanvas.class.getDeclaredField("mWidth");
      mWidthF.setAccessible(true);
      int mWidth = (int) mWidthF.get(element);

      Field mHeightF = DisplayListCanvas.class.getDeclaredField("mHeight");
      mHeightF.setAccessible(true);
      int mHeight = (int) mHeightF.get(element);


      Field field = DisplayListCanvas.class.getDeclaredField("mNode");
      field.setAccessible(true);
      RenderNode renderNode = (RenderNode) field.get(element);
      if (renderNode == null) {
        System.out.println(element + ":" + renderNode + ":" + mWidth + ":" + mHeight);
      } else {
        Field mOwningViewField = RenderNode.class.getDeclaredField("mOwningView");
        mOwningViewField.setAccessible(true);
        View view = (View) mOwningViewField.get(renderNode);
        System.out.println(element + ":" + renderNode + ":" + mWidth + ":" + mHeight + ":" + view);
      }

    } catch (Exception e) {
      throw new RuntimeException(e);
    }

    return origin.release(element);
  }
}
