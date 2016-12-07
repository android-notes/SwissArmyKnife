# SwissArmyKnife
android免root兼容所有版本ui调试工具


### SwissArmyKnife是什么
   
SwissArmyKnife 是一款方便调试android UI的工具，可以兼容所有android版本，不需要root权限。可以直接在android手机屏幕上显示当前Activity中所有控件（不管是否隐藏）的边界，内外边距大小，每一个控件大小，图片大小，字体颜色，大小，以及自定义信息。同时可以直接在屏幕上取色，另外还提供了直尺（单位为px和dp），圆角尺（单位dp）工具，可以直接测量大小。针对android开发者还提供了布局树查看功能，可以直接在手机屏幕查看当前Activity中所有控件层次信息等。

可以通过滚动层级滚轮来控制只显示某一层级的信息，避免层级覆盖等。

<!-- more -->

### 使用方式


`compile 'com.wanjian:sak:0.0.2'`

android 4.0及以上用户直接在application的onCreate中调用 `com.wanjian.sak.LayoutManager.init(Application context) ` ,其他版本可以在activity的`onResume`中调用`com.wanjian.sak.LayoutManager.init(Activity act) `初始化。

启动app后会在屏幕左上角看到一个 android logo ，点击即可进入功能界面。



### 功能界面

![image](https://github.com/android-notes/blogimg/blob/master/%E8%AE%BE%E7%BD%AE%E7%95%8C%E9%9D%A21.jpg?raw=true)

![img](https://raw.githubusercontent.com/android-notes/blogimg/master/%E8%AE%BE%E7%BD%AE%E7%95%8C%E9%9D%A22.jpg)


### 边框选项

开启边框选项后可以在手机屏幕看到所有控件的边界，效果如下

![image](https://raw.githubusercontent.com/android-notes/blogimg/master/%E8%BE%B9%E6%A1%86.jpg)



### 内外边距选项

开启后会在屏幕看到如下效果

![image](https://raw.githubusercontent.com/android-notes/blogimg/master/%E8%BE%B9%E6%A1%86%E5%8F%8A%E5%86%85%E5%A4%96%E8%BE%B9%E8%B7%9D%E5%A4%A7%E5%B0%8F.jpg)

半透明绿色代表内边距，PT表示上边内边距（padding top），PL表示左内边距（padding left），PT表示右内边距（padding right），PB便是下内边距（padding bottom）。

半透明红色代表外边距， ML，MT，MR，MB分别表示左外边距，上外边距，右外边距，下外边距。

所有边距单位都是dp（pt），android中会四舍五入，所有边距都是整数，所以可能跟设计图中有不超过1dp（pt）的误差。


### 宽高选项

开启后会看到控件的宽高，单位是dp（pt）

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

自定义信息用于android开发者调试使用，开发者可以为view设置`setTag(com.wanjian.sak.CanvasManager.INFO_KEY,Object)`,设置后并开启自定义信息选项后就可以在控件左上角看到自定义的文本信息了。

比如开发者想要在屏幕上看到TextView中有多少个文字，就可以这样使用`textview.setTag(com.wanjian.sak.CanvasManager.INFO_KEY,textview.getText().length())`,这样开启自定义信息选项后就可以在TextView上看到文字长度了。

### 实时刷新

开启实时刷新后当手指在屏幕上移动时就可以实时看到相关信息，开启实时刷新后可能会导致滑动卡顿，也可能导致触摸，点击等失效。


### 层级信息

可以通过滚动滚轮来控制只显示某一层次区间的信息，比如只想看ListView中子控件的大小，就可以滚动滚轮来控制只显示ListView子控件大小，具体调整成多少需要多次尝试。滚轮可以控件边框，宽高，布局树等等。


### 直尺圆角尺，取色器

开启后会在屏幕左上角显示，取色器刚开启时只会在屏幕左上角看到一个黑框，拖动到要取色的位置后抬起手机即可完成取色，取色器四个角可以获取所指像素的颜色值。取色器可以获取native页面每个像素颜色，也可以获取webview中每一个像素的颜色。

![image](https://raw.githubusercontent.com/android-notes/blogimg/master/%E5%88%BB%E5%BA%A6%E5%B0%BA%E5%8F%8A%E5%8F%96%E8%89%B2%E5%99%A8.jpg)


### 布局树
 
 布局树可以双指缩放
 
![image](https://github.com/android-notes/blogimg/blob/master/%E5%B8%83%E5%B1%80%E6%A0%91.jpg?raw=true)



### 扩展

用户可以方便的对SAK进行扩展，只需要继承自`AbsCanvas`并重新` onDraw(Canvas canvas, Paint paint, ViewGroup viewGroup, int startLayer, int endLayer)`,

或者继承自`CanvasLayerAdapter`并重写`drawLayer(Canvas canvas, Paint paint, View view)`

又或者继承自`CanvasLayerTxtAdapter`

区别在于onDraw中没对层级进行处理，drawLayer中的view已经是层级区间的view，CanvasLayerTxtAdapter提供了
`drawTxt(String txt, Canvas canvas, Paint paint, View view) `方法，可以直接调用drawTxt就可以把txt绘制到当前view左上角。


写完自定义的Canvas后还需要在OperatorView布局中加入布局代码，用于开启自定义的Canvas，比如在布局中加入开关按钮，并为按钮添加监听，开启后调用
`CanvasManager.getInstance(getContext()).addCanvas(new 你的Canvas())`

比如我想自定义一个查看TextView字体大小的，我可以先实现一个Canvas

```java


public class TextSizeCanvas extends CanvasLayerTxtAdapter {

    @Override
    protected void drawLayer(Canvas canvas, Paint paint, View view) {
        if (view instanceof TextView) {
            float size = ((TextView) view).getTextSize();
            String txt = px2sp(view.getContext(), size) + "sp/ " + px2dp(view.getContext(), size) + "dp";
            drawTxt(txt, canvas, paint, view);
        }

    }
}


```

然后在OperatorView布局中加入开关，当开启后执行

```java

CanvasManager.getInstance(getContext()).addCanvas(new TextSizeCanvas());

```

就可以了。



## 工程代码：
https://github.com/android-notes/SwissArmyKnife        



 

