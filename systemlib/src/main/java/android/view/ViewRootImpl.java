package android.view;

import android.graphics.Point;
import android.graphics.Rect;
import android.os.Bundle;
import android.view.accessibility.AccessibilityEvent;

public final class ViewRootImpl implements ViewParent {

  public View getView() {
    return null;
  }


  @Override
  public void requestLayout() {

  }

  @Override
  public boolean isLayoutRequested() {
    return false;
  }

  @Override
  public void requestTransparentRegion(View child) {

  }

  @Override
  public void invalidateChild(View child, Rect r) {

  }

  @Override
  public ViewParent invalidateChildInParent(int[] location, Rect r) {
    return null;
  }

  @Override
  public ViewParent getParent() {
    return null;
  }

  @Override
  public void requestChildFocus(View child, View focused) {

  }

  @Override
  public void recomputeViewAttributes(View child) {

  }

  @Override
  public void clearChildFocus(View child) {

  }

  @Override
  public boolean getChildVisibleRect(View child, Rect r, Point offset) {
    return false;
  }

  @Override
  public View focusSearch(View v, int direction) {
    return null;
  }

  @Override
  public View keyboardNavigationClusterSearch(View currentCluster, int direction) {
    return null;
  }

  @Override
  public void bringChildToFront(View child) {

  }

  @Override
  public void focusableViewAvailable(View v) {

  }

  @Override
  public boolean showContextMenuForChild(View originalView) {
    return false;
  }

  @Override
  public boolean showContextMenuForChild(View originalView, float x, float y) {
    return false;
  }

  @Override
  public void createContextMenu(ContextMenu menu) {

  }

  @Override
  public ActionMode startActionModeForChild(View originalView, ActionMode.Callback callback) {
    return null;
  }

  @Override
  public ActionMode startActionModeForChild(View originalView, ActionMode.Callback callback, int type) {
    return null;
  }

  @Override
  public void childDrawableStateChanged(View child) {

  }

  @Override
  public void requestDisallowInterceptTouchEvent(boolean disallowIntercept) {

  }

  @Override
  public boolean requestChildRectangleOnScreen(View child, Rect rectangle, boolean immediate) {
    return false;
  }

  @Override
  public boolean requestSendAccessibilityEvent(View child, AccessibilityEvent event) {
    return false;
  }

  @Override
  public void childHasTransientStateChanged(View child, boolean hasTransientState) {

  }

  @Override
  public void requestFitSystemWindows() {

  }

  @Override
  public ViewParent getParentForAccessibility() {
    return null;
  }

  @Override
  public void notifySubtreeAccessibilityStateChanged(View child, View source, int changeType) {

  }

  @Override
  public boolean canResolveLayoutDirection() {
    return false;
  }

  @Override
  public boolean isLayoutDirectionResolved() {
    return false;
  }

  @Override
  public int getLayoutDirection() {
    return 0;
  }

  @Override
  public boolean canResolveTextDirection() {
    return false;
  }

  @Override
  public boolean isTextDirectionResolved() {
    return false;
  }

  @Override
  public int getTextDirection() {
    return 0;
  }

  @Override
  public boolean canResolveTextAlignment() {
    return false;
  }

  @Override
  public boolean isTextAlignmentResolved() {
    return false;
  }

  @Override
  public int getTextAlignment() {
    return 0;
  }

  @Override
  public boolean onStartNestedScroll(View child, View target, int nestedScrollAxes) {
    return false;
  }

  @Override
  public void onNestedScrollAccepted(View child, View target, int nestedScrollAxes) {

  }

  @Override
  public void onStopNestedScroll(View target) {

  }

  @Override
  public void onNestedScroll(View target, int dxConsumed, int dyConsumed, int dxUnconsumed, int dyUnconsumed) {

  }

  @Override
  public void onNestedPreScroll(View target, int dx, int dy, int[] consumed) {

  }

  @Override
  public boolean onNestedFling(View target, float velocityX, float velocityY, boolean consumed) {
    return false;
  }

  @Override
  public boolean onNestedPreFling(View target, float velocityX, float velocityY) {
    return false;
  }

  @Override
  public boolean onNestedPrePerformAccessibilityAction(View target, int action, Bundle arguments) {
    return false;
  }
}
