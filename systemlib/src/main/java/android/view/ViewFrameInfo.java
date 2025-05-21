

package android.view;

import android.graphics.FrameInfo;


public class ViewFrameInfo {
    public long drawStart;
    public long flags;

    private int mInputEventId;


    public void populateFrameInfo(FrameInfo frameInfo) {

    }

    public void reset() {

    }

    public void markDrawStart() {
        drawStart = System.nanoTime();
    }


    public void setInputEvent(int eventId) {
        mInputEventId = eventId;
    }
}