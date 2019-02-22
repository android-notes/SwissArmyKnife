# SwissArmyKnife Beta


### 更新日志  

* `'com.wanjian:sak-autopilot:0.4.0-beta2'`


### SwissArmyKnife

SwissArmyKnife 是一款方便调试android UI的工具。可以直接在android设备屏幕上显示控件的相关信息


### 接入方式

 `compile 'com.wanjian:sak-autopilot:0.4.0-beta'`
 

 ```java
  开启
  SAK.init(Application application, Config config)

  关闭
  SAK.unInstall();
 
 ```

启动app后会在屏幕右侧看到一个 蓝色靶心 ，双击即可进入功能界面。


### 功能界面

![image](https://raw.githubusercontent.com/android-notes/SwissArmyKnife/autopilot/img/sak_guide_func.jpg)


### 编辑控件

开启编辑控件选项后，长按需要编辑的控件即可弹出编辑窗口，可以修改内外边距大小，字体颜色、大小、背景色等（该功能和相对距离、移动控件功能冲突，请勿同时开启）

![image](https://raw.githubusercontent.com/android-notes/SwissArmyKnife/autopilot/img/sak_guide_edit_panel.jpg)
![image](https://raw.githubusercontent.com/android-notes/SwissArmyKnife/autopilot/img/sak_guide_edit.jpg)



### 相对距离

开启相对距离选项后，依次长按两个控件即可显示两个控件间的水平和竖直距离，距离默认单位是dp，可以通过功能界面的单位选项切换单位（该功能和编辑控件、移动控件功能冲突，请勿同时开启）

![image](https://raw.githubusercontent.com/android-notes/SwissArmyKnife/autopilot/img/sak_guide_relative_distance.jpg)



### 移动控件

开启移动控件选项后，长按控件即可拖动，距离默认单位是dp，可以通过功能界面的单位选项切换单位（该功能和编辑控件、移动控件功能冲突，请勿同时开启）

![image](https://raw.githubusercontent.com/android-notes/SwissArmyKnife/autopilot/img/sak_guide_drag.jpg)


### 取色器

开启取色器选项后，会在屏幕显示 绿色圆环，可以任意拖动，屏幕下方会显示圆环中心的颜色值

![image](https://raw.githubusercontent.com/android-notes/SwissArmyKnife/autopilot/img/sak_guide_take_color.jpg)


### 水平直尺，竖直直尺

开启后会在屏幕显示水平直尺和竖直直尺，可以任意拖动。直尺上有两个值，一个单位是px，另一个是dp

![image](https://raw.githubusercontent.com/android-notes/SwissArmyKnife/autopilot/img/sak_guide_measure.jpg)


### Scalpel

开启后会立体显示view，可以直观的查看view的层级关系，拖动屏幕左侧的按钮可以切换角度

![image](https://raw.githubusercontent.com/android-notes/SwissArmyKnife/autopilot/img/sak_guide_scalpel.jpg)


### 外边距/内边距

开启后可以看到任意一个view的外边距，边距单位默认为dp，可以通过功能界面的单位选项切换单位。可以通过功能界面的层级区间过滤掉部分view，避免数值相互覆盖

![image](https://raw.githubusercontent.com/android-notes/SwissArmyKnife/autopilot/img/sak_guide_margin.jpg)

>>  宽高、字体大小、字体颜色、背景色等使用方式同 外边距/内边距，也可以通过 单位选项和层级区间进行控制



### Fragment名称、Activity名称

开启后可以看到当前Activity的类名，每一个Fragment的类名。可以通过功能界面的层级区间过滤掉部分Fragment，避免Fragment名称相互覆盖

![image](https://raw.githubusercontent.com/android-notes/SwissArmyKnife/autopilot/img/sak_guide_fragment_name.jpg)


### 裁剪绘制范围

若内外边距、宽高等信息显示不完整可以关闭 裁剪绘制。开启该功能可以避免 可以滚动的控件滚动后导致的信息覆盖。


## 特别注意：
当开启新窗口时，需要手动点击一次屏幕右侧的 靶心 按钮，以此激活当前窗口，不然当前窗口不会启用 SwissArmyKnife！


### 功能拓展
[https://github.com/android-notes/SwissArmyKnife/blob/autopilot/README_Dev.md](https://github.com/android-notes/SwissArmyKnife/blob/autopilot/README_Dev.md)
