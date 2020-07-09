package android.view;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Rect;

import java.io.File;
import java.io.FileDescriptor;
import java.io.PrintWriter;

public abstract class HardwareRenderer {

  public static void disable(boolean system) {

  }

  public static boolean sTrimForeground = false;

  /**
   * Controls whether or not the hardware renderer should aggressively
   * trim memory. Note that this must not be set for any process that
   * uses WebView! This should be only used by system_process or similar
   * that do not go into the background.
   */
  public static void enableForegroundTrimming() {
    sTrimForeground = true;
  }

  /**
   * Indicates whether hardware acceleration is available under any form for
   * the view hierarchy.
   *
   * @return True if the view hierarchy can potentially be hardware accelerated,
   *         false otherwise
   */
  public static boolean isAvailable() {
    return false;
  }

  /**
   * Destroys the hardware rendering context.
   */
  abstract void destroy();

  /**
   * Initializes the hardware renderer for the specified surface.
   *
   * @param surface The surface to hardware accelerate
   *
   * @return True if the initialization was successful, false otherwise.
   */
  abstract boolean initialize(Surface surface) throws Surface.OutOfResourcesException;

  /**
   * Updates the hardware renderer for the specified surface.
   *
   * @param surface The surface to hardware accelerate
   */
  abstract void updateSurface(Surface surface) throws Surface.OutOfResourcesException;

  /**
   * Stops any rendering into the surface. Use this if it is unclear whether
   * or not the surface used by the HardwareRenderer will be changing. It
   * Suspends any rendering into the surface, but will not do any destruction
   */
  abstract boolean pauseSurface(Surface surface);

  /**
   * Destroys all hardware rendering resources associated with the specified
   * view hierarchy.
   *
   * @param view The root of the view hierarchy
   */
  abstract void destroyHardwareResources(View view);

  /**
   * This method should be invoked whenever the current hardware renderer
   * context should be reset.
   *
   * @param surface The surface to hardware accelerate
   */
  abstract void invalidate(Surface surface);

  /**
   * Detaches the layer's surface texture from the GL context and releases
   * the texture id
   */
  abstract void detachSurfaceTexture(long hardwareLayer);

  /**
   * Gets the current width of the surface. This is the width that the surface
   * was last set to in a call to {@link #setup(int, int, View.AttachInfo, Rect)}.
   *
   * @return the current width of the surface
   */
  abstract int getWidth();

  /**
   * Gets the current height of the surface. This is the height that the surface
   * was last set to in a call to {@link #setup(int, int, View.AttachInfo, Rect)}.
   *
   * @return the current width of the surface
   */
  abstract int getHeight();

  /**
   * Outputs extra debugging information in the specified file descriptor.
   */
  abstract void dumpGfxInfo(PrintWriter pw, FileDescriptor fd, String[] args);

  /**
   * Loads system properties used by the renderer. This method is invoked
   * whenever system properties are modified. Implementations can use this
   * to trigger live updates of the renderer based on properties.
   *
   * @return True if a property has changed.
   */
  abstract boolean loadSystemProperties();

  /**
   * Sets the directory to use as a persistent storage for hardware rendering
   * resources.
   *
   * @param cacheDir A directory the current process can write to
   *
   * @hide
   */
  public static void setupDiskCache(File cacheDir) {
  }

  /**
   * Indicates that the specified hardware layer needs to be updated
   * as soon as possible.
   *
   * @param layer The hardware layer that needs an update
   */
  abstract void pushLayerUpdate(HardwareLayer layer);

  /**
   * Tells the HardwareRenderer that the layer is destroyed. The renderer
   * should remove the layer from any update queues.
   */
  abstract void onLayerDestroyed(HardwareLayer layer);

  /**
   * Interface used to receive callbacks whenever a view is drawn by
   * a hardware renderer instance.
   */
  interface HardwareDrawCallbacks {
    /**
     * Invoked before a view is drawn by a hardware renderer.
     * This method can be used to apply transformations to the
     * canvas but no drawing command should be issued.
     *
     * @param canvas The Canvas used to render the view.
     */
    void onHardwarePreDraw(DisplayListCanvas canvas);

    /**
     * Invoked after a view is drawn by a hardware renderer.
     * It is safe to invoke drawing commands from this method.
     *
     * @param canvas The Canvas used to render the view.
     */
    void onHardwarePostDraw(DisplayListCanvas canvas);
  }

  /**
   *  Indicates that the content drawn by HardwareDrawCallbacks needs to
   *  be updated, which will be done by the next call to draw()
   */
  abstract void invalidateRoot();

  /**
   * Draws the specified view.
   *
   * @param view The view to draw.
   * @param attachInfo AttachInfo tied to the specified view.
   * @param callbacks Callbacks invoked when drawing happens.
   */
//  abstract void draw(View view, View.AttachInfo attachInfo, HardwareDrawCallbacks callbacks);

  /**
   * Creates a new hardware layer. A hardware layer built by calling this
   * method will be treated as a texture layer, instead of as a render target.
   *
   * @return A hardware layer
   */
  abstract HardwareLayer createTextureLayer();

  abstract void buildLayer(RenderNode node);

  abstract boolean copyLayerInto(HardwareLayer layer, Bitmap bitmap);

  /**
   * Initializes the hardware renderer for the specified surface and setup the
   * renderer for drawing, if needed. This is invoked when the ViewAncestor has
   * potentially lost the hardware renderer. The hardware renderer should be
   * reinitialized and setup when the render {@link #isRequested()} and
   * {@link #isEnabled()}.
   *
   * @param width The width of the drawing surface.
   * @param height The height of the drawing surface.
   * @param attachInfo Information about the window.
   * @param surface The surface to hardware accelerate
   * @param surfaceInsets The drawing surface insets to apply
   *
   * @return true if the surface was initialized, false otherwise. Returning
   *         false might mean that the surface was already initialized.
   */
//  boolean initializeIfNeeded(int width, int height, View.AttachInfo attachInfo,
//                             Surface surface, Rect surfaceInsets) throws Surface.OutOfResourcesException {
//    if (isRequested()) {
//      // We lost the gl context, so recreate it.
//      if (!isEnabled()) {
//        if (initialize(surface)) {
//          setup(width, height, attachInfo, surfaceInsets);
//          return true;
//        }
//      }
//    }
//    return false;
//  }

  /**
   * Sets up the renderer for drawing.
   *
   * @param width The width of the drawing surface.
   * @param height The height of the drawing surface.
   * @param attachInfo Information about the window.
   * @param surfaceInsets The drawing surface insets to apply
   */
//  abstract void setup(int width, int height, View.AttachInfo attachInfo, Rect surfaceInsets);

  /**
   * Updates the light position based on the position of the window.
   *
   * @param attachInfo Information about the window.
   */
//  abstract void setLightCenter(View.AttachInfo attachInfo);

  /**
   * Optional, sets the name of the renderer. Useful for debugging purposes.
   *
   * @param name The name of this renderer, can be null
   */
  abstract void setName(String name);

  /**
   * Change the HardwareRenderer's opacity
   */
  abstract void setOpaque(boolean opaque);

  /**
   * Creates a hardware renderer using OpenGL.
   *
   * @param translucent True if the surface is translucent, false otherwise
   *
   * @return A hardware renderer backed by OpenGL.
   */
  static HardwareRenderer create(Context context, boolean translucent) {

    return null;
  }

  /**
   * Invoke this method when the system is running out of memory. This
   * method will attempt to recover as much memory as possible, based on
   * the specified hint.
   *
   * @param level Hint about the amount of memory that should be trimmed,
   *              see {@link android.content.ComponentCallbacks}
   */
  static void trimMemory(int level) {
    ThreadedRenderer.trimMemory(level);
  }

  /**
   * Indicates whether hardware acceleration is currently enabled.
   *
   * @return True if hardware acceleration is in use, false otherwise.
   */
  boolean isEnabled() {
    return false;
  }

  /**
   * Indicates whether hardware acceleration is currently enabled.
   *
   * @param enabled True if the hardware renderer is in use, false otherwise.
   */
  void setEnabled(boolean enabled) {
  }

  /**
   * Indicates whether hardware acceleration is currently request but not
   * necessarily enabled yet.
   *
   * @return True if requested, false otherwise.
   */
  boolean isRequested() {
    return false;
  }

  /**
   * Indicates whether hardware acceleration is currently requested but not
   * necessarily enabled yet.
   *
   * @return True to request hardware acceleration, false otherwise.
   */
  void setRequested(boolean requested) {
  }

  /**
   * Blocks until all previously queued work has completed.
   */
  abstract void fence();

  /**
   * Prevents any further drawing until draw() is called. This is a signal
   * that the contents of the RenderNode tree are no longer safe to play back.
   * In practice this usually means that there are Functor pointers in the
   * display list that are no longer valid.
   */
  abstract void stopDrawing();

  /**
   * Called by {@link ViewRootImpl} when a new performTraverals is scheduled.
   */
  abstract void notifyFramePending();

  abstract void registerAnimatingRenderNode(RenderNode animator);
}
