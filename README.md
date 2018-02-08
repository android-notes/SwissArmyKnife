# SwissArmyKnife Autopilot版

android免root兼容所有版本ui调试工具
支持Activity，dialog，popupwindow，手动通过windowmanager添加的window（根view需要是FrameLayout）,甚至是自定义的Toast（根view需要是FrameLayout）


### 更新日志  

* `compile 'com.wanjian:sak-autopilot:0.0.3'`
   监听window创建

### 使用方式

* `compile 'com.wanjian:sak-autopilot:0.0.3'`
不需要执行其他任意代码即可


### 功能

同 https://github.com/android-notes/SwissArmyKnife 相同，但支持所有window

### 原理

参考 `com.wanjian.sak.Autopilot`
 
android 4.2以下：

`WindowManager windowManager = (WindowManager) getContext().getSystemService(Context.WINDOW_SERVICE);`
`windowManager`对象是`WindowManagerImpl`中的静态内部类的实例，内部持有了外部`WindowManagerImpl`引用，
通过`windowManager`对象最终获取到`WindowManagerImpl`的`View[] mView`数组，当启动、移除window（Activity，dialog，popupwindow，toast）时都会创建新的View数组并赋值给`View[] mView`，通过延时检查`View[] mView`值是否改变即可得到新启动的window的根view（FrameLayout），然后插入自定义的coverview即可


android 4.4及以上：

通过替换`WindowManagerGlobal.getInstance()`对象中的mViews为自定义的ArrayList，重写ArrayList的add方法监听view添加。
 *  Android sdk中并没有`WindowManagerGlobal`类，因此直接使用`WindowManagerGlobal`类会报错，我们可以吧`WindowManagerGlobal`类复制到我们的工程中，保证包名，方法签名和系统中的相同，然后在`getInstance`方法中直接返回null即可，这样就可以编译通过了，由于Android的双亲委派类加载机制，Android最终加载的还是系统的`WindowManagerGlobal`，并不会使用我们代码中的`WindowManagerGlobal`类
 
 
 android 4.2 4.3：
 
 原理同上类似
 

## 工程代码：
https://github.com/android-notes/SwissArmyKnife/tree/autopilot   autopilot 分支





