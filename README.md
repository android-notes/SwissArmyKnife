# SwissArmyKnife Autopilot版


### 更新日志  

* `'com.wanjian:sak-autopilot:0.2.0'`

   *  随时开启关闭

* `'com.wanjian:sak-autopilot:0.0.3'`

   *  监听window创建


### SwissArmyKnife是什么

SwissArmyKnife 是一款方便调试android UI的工具，可以兼容所有android版本，不需要root权限。可以直接在android手机屏幕上显示Activity，dialog，popupwindow，手动通过windowmanager添加的window（根view需要是FrameLayout）,甚至是自定义的Toast（根view需要是FrameLayout）的边界，内外边距大小，每一个控件大小，图片大小，字体颜色，大小，以及自定义信息。同时可以直接在屏幕上取色，另外还提供了直尺，圆角尺等工具，可以直接测量大小。针对android开发者还提供了布局树查看功能，可以直接在手机屏幕查看当前所有控件层次信息等。

#### 可以通过滚动层级滚轮来控制只显示某一层级的信息，避免层级覆盖等。
#### 默认不允许超出父控件边界。当父控件和子控件都比较小时，子控件的字体大小等文本信息可能显示不完整，允许超出边界绘制后可以完整显示
#### 可以通过单位选项控制所有长度的单位，支持px，dp，sp


### 使用方式


 `compile 'com.wanjian:sak-autopilot:0.2.0'`
 
 可以多次开启及关闭
 
 ```java
 
  SAK.init(Application application);
  
  //同样支持自定义功能
  SAK.init(Application application, Config config)
  
  SAK.unInstall();
 
 ```

启动app后会在屏幕上角看到一个 蓝色Logo ，点击即可进入功能界面。



### 功能界面

![image](https://raw.githubusercontent.com/android-notes/blogimg/master/newSAK%E5%8A%9F%E8%83%BD1.jpg)

![img](https://raw.githubusercontent.com/android-notes/blogimg/master/newSAK%E5%8A%9F%E8%83%BD2.jpg)


### 边框选项

开启边框选项后可以在手机屏幕看到所有控件的边界，效果如下

![image](https://raw.githubusercontent.com/android-notes/blogimg/master/%E8%BE%B9%E6%A1%86.jpg)



### 内外边距选项

开启后会在屏幕看到如下效果

![image](https://raw.githubusercontent.com/android-notes/blogimg/master/%E8%BE%B9%E6%A1%86%E5%8F%8A%E5%86%85%E5%A4%96%E8%BE%B9%E8%B7%9D%E5%A4%A7%E5%B0%8F.jpg)

半透明绿色代表内边距，PT表示上边内边距（padding top），PL表示左内边距（padding left），PT表示右内边距（padding right），PB便是下内边距（padding bottom）。

半透明红色代表外边距， ML，MT，MR，MB分别表示左外边距，上外边距，右外边距，下外边距。

单位可以在功能界面控制


### 宽高选项

开启后会看到控件的宽高，单位可以在功能界面控制

![image](https://raw.githubusercontent.com/android-notes/blogimg/master/%E6%8E%A7%E4%BB%B6%E5%A4%A7%E5%B0%8F.jpg)

### 图片宽高

![image](https://github.com/android-notes/blogimg/blob/master/%E5%9B%BE%E7%89%87%E5%A4%A7%E5%B0%8F.jpg?raw=true)


### 字体大小

![image](https://raw.githubusercontent.com/android-notes/blogimg/master/%E5%AD%97%E4%BD%93%E5%A4%A7%E5%B0%8F.jpg)

### 字体颜色

16进制，ARGB

![image](https://raw.githubusercontent.com/android-notes/blogimg/master/%E5%AD%97%E4%BD%93%E9%A2%9C%E8%89%B2.jpg)

### 强制图片宽高

若开启图片宽高选项后看不到图片大小，可以尝试开启强制图片宽高选项。  ps 该选项可能会导致滑动卡顿

### 自定义信息

自定义信息用于android开发者调试使用，开发者可以为view设置`setTag(com.wanjian.sak.SAK.INFO_KEY,Object)`,设置后并开启自定义信息选项后就可以在控件左上角看到自定义的文本信息了。

比如开发者想要在屏幕上看到TextView中有多少个文字，就可以这样使用`textview.setTag(com.wanjian.sak.SAK.INFO_KEY,textview.getText().length())`,这样开启自定义信息选项后就可以在TextView上看到文字长度了。

### 层级信息

可以通过滚动滚轮来控制只显示某一层次区间的信息，比如只想看ListView中子控件的大小，就可以滚动滚轮来控制只显示ListView子控件大小，具体调整成多少需要多次尝试。滚轮可以控件边框，宽高，布局树等等。


### 直尺圆角尺，取色器

开启后会在屏幕左上角显示，取色器刚开启时只会在屏幕左上角看到一个马赛克方块，拖动到要取色的位置后抬起手机即可完成取色，取色器四个角可以获取所指像素的颜色值。取色器可以获取native页面每个像素颜色，也可以获取webview中每一个像素的颜色。

![image](https://raw.githubusercontent.com/android-notes/blogimg/master/%E5%88%BB%E5%BA%A6%E5%B0%BA%E5%8F%8A%E5%8F%96%E8%89%B2%E5%99%A8.jpg)


### 布局树

 布局树可以双指缩放

![image](https://github.com/android-notes/blogimg/blob/master/%E5%B8%83%E5%B1%80%E6%A0%91.jpg?raw=true)

### 允许超出边界绘制
 
默认不允许超出父控件边界。当父控件和子控件差不多大小时，子控件的字体大小等文本信息可能显示不完整，允许超出边界绘制后可以完整显示

### 自定义功能

```java


        Config config = new Config.Build(this)
                .viewFilter(new ViewFilter() {
                    @Override
                    public boolean apply(View view) {
                        if (view instanceof ImageView) {
                            return true;
                        }
                        if (view instanceof LinearLayout) {
                            return true;
                        }
                        return false;
                    }
                })
                .addLayerView(new TakeColorView(this))
                .addLayer(new InfoLayer(this))
                .addLayer(new BorderLayer(this))
                .addLayer(new BitmapWidthHeightLayer(this))
                .addLayer(new ViewClassLayer(this))
                .addSizeConverter(new SizeConverter() {
                    @Override
                    public String desc() {
                        return "my converter";
                    }

                    @Override
                    public Size convert(Context context, float length) {
                        return Size.obtain().setLength(length / 2).setUnit("myU");
                    }
                })
                .build();
        SAK.init(this, config);



```

可以按照如上所示定义自己的ViewFilter，决定要显示哪种view，比如只显示ImageView子类和LinearLayout子类，若想要显示所有可见的view，则直接返回
view.getVisibility()==View.VISIBLE即可。

也可以添加自定义的view和图层，单位转换等。自定义的view要继承自`AbsLayerView`，自定义图层要继承自`AbsLayer`或`LayerAdapter`或`LayerTxtAdapter`，区别在于`LayerAdapter `的子类可以通过功能界面的层级滚轮进行控制，`LayerTxtAdapter `继承自`LayerAdapter `，提供了绘制文本的功能。

可以添加多个单位转换SizeConverter，默认提供了原始数值SizeConverter，PX2DP SizeConverter，PX2SP SizeConverter，子定义的SizeConverter需要继承自`SizeConverter `，并重写相关方法即可。

然后参考上述初始化SAK即可。


目前SAK提供的Layer如下,默认初始化（SAK.init(Application)）会包含如下所有的Layer，通过Config初始化时不会包含任何Layer，可以根据需要添加如下Layer
中的若干个，也可以添加自定义的Layer

* BackgroundColorLayer
* BitmapWidthHeightLayer
* BorderLayer
* ForceBitmapWidthHeightLayer
* InfoLayer
* MarginLayer
* PaddingLayer
* PageDrawPerformanceLayer
* TextColorLayer
* TextSizeLayer
* ViewClassLayer
* ViewDrawPerformanceLayer
* WidthHeightLayer


 


### 原理

参考 `com.wanjian.sak.Inject`
 
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





