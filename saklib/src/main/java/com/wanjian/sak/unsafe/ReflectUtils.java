package com.wanjian.sak.unsafe;

import java.lang.reflect.Method;

public class ReflectUtils {

  private static ReflectUtils sInstance;

  private ReflectUtils() {
  }

  public static ReflectUtils getInstance() {
    if (sInstance == null) {
      sInstance = new ReflectUtils();
    }
    return sInstance;
  }

  public Method getMethod(Class<?> clazz, String methodName, Class<?>... values) {
    Method method = null;
    try {
      if (values != null) {
        method = clazz.getDeclaredMethod(methodName, values);
      } else {
        method = clazz.getDeclaredMethod(methodName);
      }
    } catch (NoSuchMethodException e) {
      e.printStackTrace();
    }
    return method;
  }

  public Object invokeStaticMethod(Method method, Object... values) {
    return invokeMethod(method, null, values);
  }

  public Object invokeMethod(Method method, Object classValue, Object... values) {
    if (method != null) {
      try {
        return method.invoke(classValue, values);
      } catch (Exception e) {
        throw new RuntimeException(e);
      }
    }
    return null;
  }

  /**
   * 这里我们通过反射静态方法 getUnsafe()方法来获取Unsafe对象，其实在之前的代码
   * 的时候我们也可以直接反射静态的成员变量来直接获取Unsafe对象的，其实都是差不多的。
   */
  public Object getUnsafe(Class<?> clazz) {
    Method method = getMethod(clazz, "getUnsafe");
    return invokeStaticMethod(method);
  }
}