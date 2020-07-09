package android.graphics;

public class RenderNode {
  public RenderNode(String name) {

  }

  public boolean setLeftTopRightBottom(int left, int top, int right, int bottom) {
    return false;
  }

  public boolean setAlpha(float alpha) {
    return false;
  }

  public boolean setHasOverlappingRendering(boolean hasOverlappingRendering) {
    return false;
  }

  public boolean setClipToBounds(boolean clipToBounds) {
    return false;
  }


  public RecordingCanvas beginRecording(int width, int height) {
    return null;
  }

  public RecordingCanvas beginRecording() {
    return null;
  }


  public void endRecording() {
  }

  public boolean hasDisplayList() {
    throw new IllegalStateException();
  }
}
