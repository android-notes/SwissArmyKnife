# SwissArmyKnife  Autopilot



Autopilot 版基于`com.wanjian:sak:xx.xx.xx`，实现了自动初始化，只需要在gradle中引入依赖即可，不需要编写额外的初始化代码


### 接入方式

 `compile 'com.wanjian:sak-autopilot:2.0.1'`
 
 no opt 包
 `compile 'com.wanjian:sak-nop:0.0.3'`



### 开启及关闭

   在控制台执行 `adb shell am broadcast -a com.sak`命令即可开启及关闭

