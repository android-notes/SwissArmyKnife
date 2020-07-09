package com.wanjian.sak.system.input;

import android.os.Looper;
import android.view.InputChannel;
import android.view.InputEvent;
import android.view.InputEventReceiver;
import android.view.ViewRootImpl;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

public class InputEventReceiverV27Impl extends InputEventReceiver {
  private InputEventListener listener;
  private ViewRootImpl $$ViewRootImpl;
  private InputEventReceiver originInputEventReceiver;

  public InputEventReceiverV27Impl(InputChannel inputChannel, Looper looper, ViewRootImpl viewRoot, InputEventListener listener, InputEventReceiver originInputEventReceiver) {
    super(inputChannel, looper);
    this.$$ViewRootImpl = viewRoot;
    this.listener = listener;
    this.originInputEventReceiver = originInputEventReceiver;
  }


  @Override
  public void onInputEvent(InputEvent event, int displayId) {
    boolean consume = listener.onBeforeInputEvent(event);
    System.out.println("......shoudao " + event);
    if (!consume) {
      enqueueInputEvent(event, this, 0, true);
    } else {
      finishInputEvent(event, true);
    }
    listener.onAfterInputEvent(event);
  }

  @Override
  public void onBatchedInputEventPending() {
    if (getmUnbufferedInputDispatch()) {
      super.onBatchedInputEventPending();
    } else {
      scheduleConsumeBatchedInput();
    }
  }

  @Override
  public void dispose() {
    System.out.println("........自定义dispose");
    unscheduleConsumeBatchedInput();
    super.dispose();
  }


  //add
  private void enqueueInputEvent(InputEvent event, InputEventReceiver receiver, int flags, boolean processImmediately) {
    try {
      viewRootImpl_enqueueInputEventM.invoke($$ViewRootImpl, event, receiver, flags, processImmediately);
    } catch (IllegalAccessException e) {
      throw new RuntimeException(e);
    } catch (InvocationTargetException e) {
      throw new RuntimeException(e);
    }
  }

  //add
  private boolean getmUnbufferedInputDispatch() {
    try {
      return (Boolean) viewRootImpl_mUnbufferedInputDispatchF.get($$ViewRootImpl);
    } catch (IllegalAccessException e) {
      throw new RuntimeException(e);
    }
  }

  //add
  private void scheduleConsumeBatchedInput() {
    try {
      viewRootImpl_scheduleConsumeBatchedInputM.invoke($$ViewRootImpl);
    } catch (IllegalAccessException e) {
      throw new RuntimeException(e);
    } catch (InvocationTargetException e) {
      throw new RuntimeException(e);
    }
  }

  //add
  private void unscheduleConsumeBatchedInput() {
    try {
      viewRootImpl_unscheduleConsumeBatchedInputM.invoke($$ViewRootImpl);
    } catch (IllegalAccessException e) {
      throw new RuntimeException(e);
    } catch (InvocationTargetException e) {
      throw new RuntimeException(e);
    }
  }

  /////////
  private static Method viewRootImpl_enqueueInputEventM;
  private static Method viewRootImpl_scheduleConsumeBatchedInputM;
  private static Method viewRootImpl_unscheduleConsumeBatchedInputM;
  private static Field viewRootImpl_mUnbufferedInputDispatchF;

  static {
    // ViewRootImpl   void enqueueInputEvent(InputEvent event,InputEventReceiver receiver, int flags, boolean processImmediately) {
    try {
      viewRootImpl_enqueueInputEventM = ViewRootImpl.class.getDeclaredMethod("enqueueInputEvent", InputEvent.class, InputEventReceiver.class, int.class, boolean.class);
    } catch (NoSuchMethodException e) {
      throw new RuntimeException(e);
    }
    viewRootImpl_enqueueInputEventM.setAccessible(true);

    // ViewRootImpl    void scheduleConsumeBatchedInput()
    try {
      viewRootImpl_scheduleConsumeBatchedInputM = ViewRootImpl.class.getDeclaredMethod("scheduleConsumeBatchedInput");
    } catch (NoSuchMethodException e) {
      throw new RuntimeException(e);
    }
    viewRootImpl_scheduleConsumeBatchedInputM.setAccessible(true);


    // ViewRootImpl    void unscheduleConsumeBatchedInput()()
    try {
      viewRootImpl_unscheduleConsumeBatchedInputM = ViewRootImpl.class.getDeclaredMethod("unscheduleConsumeBatchedInput");
    } catch (NoSuchMethodException e) {
      throw new RuntimeException(e);
    }
    viewRootImpl_unscheduleConsumeBatchedInputM.setAccessible(true);

    //ViewRootImpl   boolean mUnbufferedInputDispatch;
    try {
      viewRootImpl_mUnbufferedInputDispatchF = ViewRootImpl.class.getDeclaredField("mUnbufferedInputDispatch");
    } catch (NoSuchFieldException e) {
      throw new RuntimeException(e);
    }
    viewRootImpl_mUnbufferedInputDispatchF.setAccessible(true);

  }

}
