package com.wanjian.sak.system.input;

import android.os.Looper;
import android.view.InputChannel;
import android.view.InputEvent;
import android.view.InputEventCompatProcessor;
import android.view.InputEventReceiver;
import android.view.ViewRootImpl;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.List;

public class InputEventReceiverV29Impl extends InputEventReceiver {
  private ViewRootImpl $$ViewRootImpl;
  private InputEventListener listener;
  private InputEventReceiver originInputEventReceiver;

  public InputEventReceiverV29Impl(InputChannel inputChannel, Looper looper, ViewRootImpl viewRoot, InputEventListener listener, InputEventReceiver originInputEventReceiver) {
    super(inputChannel, looper);
    this.$$ViewRootImpl = viewRoot;
    this.listener = listener;
    this.originInputEventReceiver = originInputEventReceiver;
  }

  @Override
  public void onInputEvent(InputEvent event) {
    boolean consume = listener.onBeforeInputEvent(event);
    System.out.println("......shoudao " + event);
    if (!consume) {
      processEvent(event);
    } else {
      finishInputEvent(event, true);
    }
    listener.onAfterInputEvent(event);//add
  }

  private void processEvent(InputEvent event) {
    //    Trace.traceBegin(Trace.TRACE_TAG_VIEW, "processInputEventForCompatibility");// delete
    List<InputEvent> processedEvents;
    try {
      processedEvents =
          getmInputCompatProcessor().processInputEventForCompatibility(event);
    } finally {
//      Trace.traceEnd(Trace.TRACE_TAG_VIEW);//delete
    }
    if (processedEvents != null) {
      if (processedEvents.isEmpty()) {
        // InputEvent consumed by mInputCompatProcessor
        finishInputEvent(event, true);
      } else {
        for (int i = 0; i < processedEvents.size(); i++) {
          final int FLAG_MODIFIED_FOR_COMPATIBILITY = 1 << 6;//add
          enqueueInputEvent(
              processedEvents.get(i), this,
              FLAG_MODIFIED_FOR_COMPATIBILITY, true);//modify
        }
      }
    } else {
      enqueueInputEvent(event, this, 0, true);
    }
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
  private InputEventCompatProcessor getmInputCompatProcessor() {
    try {
      return (InputEventCompatProcessor) viewRootImpl_mInputCompatProcessorF.get($$ViewRootImpl);
    } catch (IllegalAccessException e) {
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
  private static Field viewRootImpl_mInputCompatProcessorF;
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


    //ViewRootImpl    private final InputEventCompatProcessor mInputCompatProcessor;
    try {
      viewRootImpl_mInputCompatProcessorF = ViewRootImpl.class.getDeclaredField("mInputCompatProcessor");
    } catch (NoSuchFieldException e) {
      throw new RuntimeException(e);
    }
    viewRootImpl_mInputCompatProcessorF.setAccessible(true);

    //ViewRootImpl   boolean mUnbufferedInputDispatch;
    try {
      viewRootImpl_mUnbufferedInputDispatchF = ViewRootImpl.class.getDeclaredField("mUnbufferedInputDispatch");
    } catch (NoSuchFieldException e) {
      throw new RuntimeException(e);
    }
    viewRootImpl_mUnbufferedInputDispatchF.setAccessible(true);

  }

}
