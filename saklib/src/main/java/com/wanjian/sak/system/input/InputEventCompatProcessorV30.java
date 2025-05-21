package com.wanjian.sak.system.input;

import android.content.Context;
import android.view.InputEvent;
import android.view.InputEventCompatProcessor;
import android.view.ViewRootImpl;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class InputEventCompatProcessorV30 extends InputEventCompatProcessor {
    private InputEventCompatProcessor originInputEventCompatProcessor;
    private InputEventListener listener;

    public InputEventCompatProcessorV30(Context context, ViewRootImpl viewRootImpl, InputEventListener listener) {
        super(context);
        this.listener = listener;
        try {
            Field field = viewRootImpl.getClass().getDeclaredField("mInputCompatProcessor");
            field.setAccessible(true);
            originInputEventCompatProcessor = (InputEventCompatProcessor) field.get(viewRootImpl);
            field.set(viewRootImpl, this);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public InputEvent processInputEventBeforeFinish(InputEvent e) {
        return originInputEventCompatProcessor.processInputEventBeforeFinish(e);
    }

    @Override
    public List<InputEvent> processInputEventForCompatibility(InputEvent e) {
        List<InputEvent> list;
        if (listener.onBeforeInputEvent(e)) {
            list = new ArrayList<>();
        } else {
            list = originInputEventCompatProcessor.processInputEventForCompatibility(e);
        }
        listener.onAfterInputEvent(e);
        return list;
    }
}
