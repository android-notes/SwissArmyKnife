//package com.wanjian.sak.system.canvas.compact;
//
//import android.graphics.Canvas;
//import android.view.ViewRootImpl;
//
//import java.util.HashMap;
//
//public class CanvasHolder {
//  private static HashMap<ViewRootImpl, CanvasCompact> sMap = new HashMap<>();
//
//  public static Canvas requireCanvasFor(ViewRootImpl viewRootImpl) {
//    CanvasCompact compact = sMap.get(viewRootImpl);
//    if (compact == null) {
//      compact = CanvasCompact.get(viewRootImpl);
//      sMap.put(viewRootImpl, compact);
//    }
//    return compact.requireCanvas();
//  }
//
//  public static void releaseCanvasFor(ViewRootImpl viewRootImpl) {
//    CanvasCompact compact = sMap.get(viewRootImpl);
//    if (compact == null) {
//      throw new IllegalStateException();
//    }
//    compact.releaseCanvas();
//
//  }
//
////  public static void release(ViewRootImpl viewRoot) {
////    sMap.remove(viewRoot);
////  }
//}
