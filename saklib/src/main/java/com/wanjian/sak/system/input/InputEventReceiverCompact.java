package com.wanjian.sak.system.input;

import android.os.Build;
import android.os.Looper;
import android.util.SparseIntArray;
import android.view.InputChannel;
import android.view.InputEventReceiver;
import android.view.View;
import android.view.ViewRootImpl;

import com.wanjian.sak.unsafe.UnsafeProxy;

import java.lang.reflect.Field;

public abstract class InputEventReceiverCompact {

  public static void get(ViewRootImpl viewRootImpl, InputEventListener listener) {
    InputChannel inputChannel = getInputChannel(viewRootImpl);
    Looper looper = Looper.getMainLooper();//todo
    InputEventReceiver originInputEventReceiver = getOriginInputEventReceiver(viewRootImpl);
    InputEventReceiver inputEventReceiver;
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
      inputEventReceiver = new InputEventReceiverV29Impl(inputChannel, looper, viewRootImpl, listener, originInputEventReceiver);
    } else if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O_MR1) {
      inputEventReceiver = new InputEventReceiverV27Impl(inputChannel, looper, viewRootImpl, listener, originInputEventReceiver);
    } else if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
      inputEventReceiver = new InputEventReceiverV21Impl(inputChannel, looper, viewRootImpl, listener, originInputEventReceiver);
    } else {
      throw new RuntimeException("unsuooprt");
    }
    replace(viewRootImpl, originInputEventReceiver, inputEventReceiver);
  }

  private static InputEventReceiver getOriginInputEventReceiver(ViewRootImpl viewRootImpl) {
    try {
      Field mInputEventReceiverF = ViewRootImpl.class.getDeclaredField("mInputEventReceiver");
      mInputEventReceiverF.setAccessible(true);
      return (InputEventReceiver) mInputEventReceiverF.get(viewRootImpl);
    } catch (Exception e) {
      throw new RuntimeException(e);
    }
  }


  private static InputChannel getInputChannel(ViewRootImpl viewRoot) {
    try {
      Field mInputChannelF = ViewRootImpl.class.getDeclaredField("mInputChannel");
      mInputChannelF.setAccessible(true);
      return (InputChannel) mInputChannelF.get(viewRoot);
    } catch (Exception e) {
      throw new RuntimeException(e);
    }
  }

  private static void replace(ViewRootImpl viewRootImpl, InputEventReceiver originInputEventReceiver, InputEventReceiver myInputEventReceiver) {
    try {
      Field mInputEventReceiverF = ViewRootImpl.class.getDeclaredField("mInputEventReceiver");
      Field mSeqMapField = InputEventReceiver.class.getDeclaredField("mSeqMap");
      mSeqMapField.setAccessible(true);
      SparseIntArray originmSeqMap = (SparseIntArray) mSeqMapField.get(originInputEventReceiver);

      ////
      mSeqMapField.set(myInputEventReceiver, originmSeqMap); // TODO: 2020/6/26  必须？

      long offset = UnsafeProxy.objectFieldOffset(mInputEventReceiverF);
      UnsafeProxy.putObject(viewRootImpl, offset, myInputEventReceiver);
    } catch (Exception e) {
      throw new RuntimeException();
    }
  }
}
