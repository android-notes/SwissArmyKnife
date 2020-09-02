# SwissArmyKnife


### SwissArmyKnife

SwissArmyKnife 是一款方便调试android UI的工具。可以直接在android设备屏幕上显示控件的相关信息。3.x版本对根View没有任何要求，可以是任意类型的view。
3.x需要弹窗权限。由于时间有限，暂未兼容Android5.0以下设备


### 接入方式

 ```
 allprojects {
		repositories {
			...
			maven { url 'https://jitpack.io' }
		}
	}
 
 dependencies {
	        implementation 'com.github.android-notes.SwissArmyKnife:saklib:3.0.1-alpha1'
         
       
	}
 ```

 ```
  开启
  SAK.init(Application application, Config config)

 ```
 
 备注：需要使用 me.weishu.reflection.Reflection.unseal(this);

### 功能界面

![image](https://raw.githubusercontent.com/android-notes/SwissArmyKnife/master/img/sak_guide_func.jpg)


### 拾取控件

开启拾取控件选项后，拖动圆环到要拾取的控件位置即可。可以获取字体颜色，大小，背景色等信息（支持SpannableString）

![image](https://raw.githubusercontent.com/android-notes/SwissArmyKnife/master/img/sak_guide_pickview.jpg)

 


### 相对距离

开启相对距离选项后，依次`长按`两个控件即可显示两个控件间的水平和竖直距离，距离默认单位是dp，可以通过功能界面的单位选项切换单位`（该功能和编辑控件、移动控件功能冲突，请勿同时开启）`

![image](https://raw.githubusercontent.com/android-notes/SwissArmyKnife/master/img/sak_guide_relative_distance.jpg)



### 移动控件

开启移动控件选项后，`长按`控件即可拖动，距离默认单位是dp，可以通过功能界面的单位选项切换单位`（该功能和编辑控件、移动控件功能冲突，请勿同时开启）`

![image](https://raw.githubusercontent.com/android-notes/SwissArmyKnife/master/img/sak_guide_drag.jpg)


### 取色器

开启取色器选项后，会在屏幕显示 绿色圆环，可以任意拖动，屏幕下方会显示圆环中心的颜色值

![image](https://raw.githubusercontent.com/android-notes/SwissArmyKnife/master/img/sak_guide_take_color.jpg)


### 水平直尺，竖直直尺

开启后会在屏幕显示水平直尺和竖直直尺，可以任意拖动。直尺上有两个值，一个单位是px，另一个是dp

![image](https://raw.githubusercontent.com/android-notes/SwissArmyKnife/master/img/sak_guide_measure.jpg)

 

### 外边距/内边距

开启后可以看到任意一个view的外边距，边距单位默认为dp，可以通过功能界面的单位选项切换单位。`可以通过功能界面的层级区间过滤掉部分view，避免数值相互覆盖`

![image](https://raw.githubusercontent.com/android-notes/SwissArmyKnife/master/img/sak_guide_margin.jpg)

>  宽高、字体大小、字体颜色、背景色等使用方式同 外边距/内边距，也可以通过 单位选项和层级区间进行控制



### Fragment名称、Activity名称

开启后可以看到当前Activity的类名，每一个Fragment的类名。可以通过功能界面的层级区间过滤掉部分Fragment，避免Fragment名称相互覆盖

![image](https://raw.githubusercontent.com/android-notes/SwissArmyKnife/master/img/sak_guide_fragment_name.jpg)

 
## 裁剪绘制范围

若内外边距、宽高、字体颜色等信息`不显示,可以关闭 裁剪绘制`。开启该功能可以避免 可以滚动的控件滚动后导致的信息覆盖。

 
### 效果视频

[http://t.cn/EVB3rcm](http://t.cn/EVB3rcm)

 
