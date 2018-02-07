# SwissArmyKnife
android免root兼容所有版本ui调试工具


### 更新日志  

* autopilot 版
	*  https://github.com/android-notes/SwissArmyKnife/tree/autopilot   autopilot 分支
	*  支持Activity，dialog，popupwindow等，不侵入业务代码

* `compile 'com.wanjian:sak:0.3.1'`  2018-1-17
	*  解决dialog主题activity显示偏移问题
	

* `compile 'com.wanjian:sak:0.3.0'`  2018-1-16
	*  允许超出边界绘制。 默认不允许
	


* `compile 'com.wanjian:sak:0.2.1'`  2018-1-15
	*  ui 调整
	


* `compile 'com.wanjian:sak:0.2.0'`  2018-1-12
	*  父控件不可见时不绘制子view信息 
	


* `compile 'com.wanjian:sak:0.1.2.8'` （测试版）2017-4-1
	*  自动刷新
    *  关闭功能 `SAK.unInstall(application)`

	
* `compile 'com.wanjian:sak:0.1.2.4'` （测试版）2017-3-10
	*  新增view渲染性能，页面渲染性能
	*  新增单位转换功能，可以控制所有长度的单位
	*  新增view类型功能
	*  新增自定义扩展功能，参考最后的  自定义功能
	*  修复取色器等无法关闭的bug
	*  修复可能存在的内存泄露
	*  修复布局树缩放时的bug



### SwissArmyKnife是什么

SwissArmyKnife 是一款方便调试android UI的工具，可以兼容所有android版本，不需要root权限。可以直接在android手机屏幕上显示当前Activity中所有控件的边界，内外边距大小，每一个控件大小，图片大小，字体颜色，大小，以及自定义信息。同时可以直接在屏幕上取色，另外还提供了直尺，圆角尺等工具，可以直接测量大小。针对android开发者还提供了布局树查看功能，可以直接在手机屏幕查看当前Activity中所有控件层次信息等。最新的版本还提供了view渲染性能和页面渲染性能统计工具。

可以通过滚动层级滚轮来控制只显示某一层级的信息，避免层级覆盖等。



### 使用方式


android 4.0及以上用户直接在application的onCreate中调用 `com.wanjian.sak.SAK.init(Application context) ` ,其他版本还需要在每一个activity的`onResume`中调用`com.wanjian.sak.SAK.resume(Activity act) `，并在Activity的`onPause`中调用`com.wanjian.sak.SAK.pause(Activity act) `。

启动app后会在屏幕左上角看到一个 蓝色Logo ，点击即可进入功能界面。



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

获取当前显示的Activity的根布局（ ViewGroup root=(ViewGroup) activity.getWindow().getDecorView()），并为其添加蒙层view（ root.addView(mCoverView)），遍历view树，把相关信息绘制在蒙层上即可


4.0及以上通过  application.registerActivityLifecycleCallbacks(）获取当前显示的Activity。

手动调用view的draw（new Canvas（bmp）），统计该方法的执行时间即可得到渲染时间


## 工程代码：
https://github.com/android-notes/SwissArmyKnife





