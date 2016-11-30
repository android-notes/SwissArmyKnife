# SwissArmyKnife
android免root兼容所有版本ui调试工具





### SwissArmyKnife是什么
   
SwissArmyKnife 是一款方便调试android UI的工具，可以兼容所有android版本，不需要root权限。可以直接在android手机屏幕上显示当前Activity中所有控件（不管是否隐藏）的边界，内外边距大小，每一个控件大小，图片大小，字体颜色，大小，以及自定义信息。同时可以直接在屏幕上取色，另外还提供了直尺（单位为px和dp），圆角尺（单位dp）工具，可以直接测量大小。针对android开发者还提供了布局树查看功能，可以直接在手机屏幕查看当前Activity中所有控件层次信息等。

可以通过滚动层级滚轮来控制只显示某一层级的信息，避免层级覆盖等。

### 使用方式

android 4.0及以上用户直接在application的onCreate中调用 `com.wanjian.sak.LayoutManager.init(Application context) ` ,其他版本可以在activity的`onResume`中调用`com.wanjian.sak.LayoutManager.init(Activity act) `初始化。

启动app后会在屏幕左上角看到一个 android logo ，点击即可进入功能界面。



### 功能界面

![image](https://thumbnail0.baidupcs.com/thumbnail/5e9f4ae7a28829372c798c08640e765f?fid=555885822-250528-588498717107669&time=1480510800&rt=sh&sign=FDTAER-DCb740ccc5511e5e8fedcff06b081203-npRokEwKu55H17vNS%2BdcCCYlPZ0%3D&expires=8h&chkv=0&chkbd=0&chkpc=&dp-logid=7767215077249327193&dp-callid=0&size=c710_u400&quality=100)

![img](https://thumbnail0.baidupcs.com/thumbnail/75c7b6291ad4e2ed6952174547a406bf?fid=555885822-250528-793968547586161&time=1480510800&rt=sh&sign=FDTAER-DCb740ccc5511e5e8fedcff06b081203-mMu8Ld8lgzWvEYGHcaH7A%2BaTLns%3D&expires=8h&chkv=0&chkbd=0&chkpc=&dp-logid=7767343151315079449&dp-callid=0&size=c710_u400&quality=100)


### 边框选项

开启边框选项后可以在手机屏幕看到所有控件的边界，效果如下

![image](https://thumbnail0.baidupcs.com/thumbnail/0a91cd6da71a471aad9ffcff515cf68a?fid=555885822-250528-50479474395734&time=1480510800&rt=sh&sign=FDTAER-DCb740ccc5511e5e8fedcff06b081203-FV4YAvjQYIKJHaC%2F2kP6UrcdRWw%3D&expires=8h&chkv=0&chkbd=0&chkpc=&dp-logid=7767414550595420692&dp-callid=0&size=c710_u400&quality=100)



### 内外边距选项

开启后会在屏幕看到如下效果

![image](https://thumbnail0.baidupcs.com/thumbnail/a73fecf5ae4239f7730a5753f283fcba?fid=555885822-250528-698272847480876&time=1480510800&rt=sh&sign=FDTAER-DCb740ccc5511e5e8fedcff06b081203-FBHw2GHVv7AW5vT7lCi8oWGFoGY%3D&expires=8h&chkv=0&chkbd=0&chkpc=&dp-logid=7767498614104691399&dp-callid=0&size=c710_u400&quality=100)

半透明绿色代表内边距，PT表示上边内边距（padding top），PL表示左内边距（padding left），PT表示右内边距（padding right），PB便是下内边距（padding bottom）。

半透明红色代表外边距， ML，MT，MR，MB分别表示左外边距，上外边距，右外边距，下外边距。

所有边距单位都是dp（pt），android中会四舍五入，所有边距都是整数，所以可能跟设计图中有不超过1dp（pt）的误差。


### 宽高选项

开启后会看到控件的宽高，单位是dp（pt）

![image](https://thumbnail0.baidupcs.com/thumbnail/e73ba08691e5fbbd54d2810a6bc923b7?fid=555885822-250528-836704082111446&time=1480510800&rt=sh&sign=FDTAER-DCb740ccc5511e5e8fedcff06b081203-VgY7PFA6PJCoAaDu%2BlUnvSRIQCk%3D&expires=8h&chkv=0&chkbd=0&chkpc=&dp-logid=7767612777723748888&dp-callid=0&size=c710_u400&quality=100)

### 图片宽高

![image](https://thumbnail0.baidupcs.com/thumbnail/97ee37b67cc32e449dcc3568ce82cd5e?fid=555885822-250528-507940912182455&time=1480510800&rt=sh&sign=FDTAER-DCb740ccc5511e5e8fedcff06b081203-t%2BOqev8m2gjbIcB5dR4pWmcbtJY%3D&expires=8h&chkv=0&chkbd=0&chkpc=&dp-logid=7767630468732386489&dp-callid=0&size=c710_u400&quality=100)


### 字体大小

![image](https://thumbnail0.baidupcs.com/thumbnail/05bb927d05ea9529e3a3433c6105bc2f?fid=555885822-250528-221709577288847&time=1480510800&rt=sh&sign=FDTAER-DCb740ccc5511e5e8fedcff06b081203-Cmeo91FQnaUvgPhEN5J%2FjB72Jos%3D&expires=8h&chkv=0&chkbd=0&chkpc=&dp-logid=7767648166155731589&dp-callid=0&size=c710_u400&quality=100)

### 字体颜色

16进制，ARGB

![image](https://thumbnail0.baidupcs.com/thumbnail/3ca30fc6ac31dd5a14be6c8ea3addf2d?fid=555885822-250528-211348384050459&time=1480510800&rt=sh&sign=FDTAER-DCb740ccc5511e5e8fedcff06b081203-8TGX9pUftBLiw1yaJp7ABLjaaB4%3D&expires=8h&chkv=0&chkbd=0&chkpc=&dp-logid=7767656303987361601&dp-callid=0&size=c710_u400&quality=100)

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

![image](https://thumbnail0.baidupcs.com/thumbnail/eeecddebe63a354b6486131f1c855da9?fid=555885822-250528-345581106229083&time=1480514400&rt=sh&sign=FDTAER-DCb740ccc5511e5e8fedcff06b081203-pMUf6mzHZANi2Kc1o5Br6BNHQuU%3D&expires=8h&chkv=0&chkbd=0&chkpc=&dp-logid=7768159078261866978&dp-callid=0&size=c710_u400&quality=100)


### 布局树
 
 布局树可以双指缩放
 
![image](https://thumbnail0.baidupcs.com/thumbnail/599f04860ec4657590f3ea06af6d047c?fid=555885822-250528-889020960262449&time=1480514400&rt=sh&sign=FDTAER-DCb740ccc5511e5e8fedcff06b081203-rqGqdIAb60t4ZBIxEj0NQPTKiOg%3D&expires=8h&chkv=0&chkbd=0&chkpc=&dp-logid=7768224679638263815&dp-callid=0&size=c710_u400&quality=100)



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




