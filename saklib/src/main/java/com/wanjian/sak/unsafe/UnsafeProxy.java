package com.wanjian.sak.unsafe;

import java.lang.reflect.Field;
import java.lang.reflect.Method;

public class UnsafeProxy {

  private static ReflectUtils sUtils;
  private static Class sUnsafeClass;
  private static Object sUnsafe;

  static {

    try {
      sUtils = ReflectUtils.getInstance();
      sUnsafeClass = Class.forName("sun.misc.Unsafe");
      sUnsafe = sUtils.getUnsafe(sUnsafeClass);
    } catch (ClassNotFoundException e) {
      e.printStackTrace();
    }
  }


  public static long objectFieldOffset(Field field) {
    Method method = sUtils.getMethod(sUnsafeClass, "objectFieldOffset", Field.class);
    Object obj = sUtils.invokeMethod(method, sUnsafe, field);
    return obj == null ? 0 : (Long) obj;
  }


  public static long putObject(Object object, long offset, Object newValue) {
    Method method = sUtils.getMethod(sUnsafeClass, "putObject", Object.class, long.class, Object.class);
    Object obj = sUtils.invokeMethod(method, sUnsafe, object, offset, newValue);
    return obj == null ? 0 : (Long) obj;
  }
}