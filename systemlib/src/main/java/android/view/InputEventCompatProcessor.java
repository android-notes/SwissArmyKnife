

package android.view;

import android.content.Context;
import android.os.Build;

import java.util.ArrayList;
import java.util.List;

public class InputEventCompatProcessor {


    public InputEventCompatProcessor(Context context) {

    }


    public List<InputEvent> processInputEventForCompatibility(InputEvent e) {

        return null;
    }


    public InputEvent processInputEventBeforeFinish(InputEvent e) {
        // No changes needed
        return e;
    }
}