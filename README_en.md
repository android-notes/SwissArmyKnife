# SwissArmyKnife





### what is SwissArmyKnife

SwissArmyKnife is a convenient tool for debugging android UI, can be compatible with all android version, no root privileges. You can directly on the android phone screen shows the current Activity in the control of all the boundaries of the control, the size of the inside and outside margins, each control size, image size, font color, size, and custom information. At the same time can be directly on the screen color, also provides a ruler, round ruler and other tools, you can directly measure the size. For the android developers also provides a layout tree view function, you can directly in the phone screen to view the current Activity in the control of all the information on the level of control. The latest version also provides view rendering performance and page rendering performance statistics.

You can scroll through the level of the wheel to control only show a certain level of information, to avoid the level of coverage and so on.


### How to use it

`compile 'com.wanjian:sak:0.1.2.4'`


Android 4.0 and above users directly in the application onCreate call `com.wanjian.sak.SAK.init (Application context)`, other versions also need in each activity `onResume` call` com.wanjian.sak.SAK . Resume (Activity act) `, and in the Activity` onPause` call `com.wanjian.sak.SAK.pause (Activity act)`.

Start the app will be in the upper left corner of the screen to see a blue Logo, click to enter the function interface.


### Function

![image](https://raw.githubusercontent.com/android-notes/blogimg/master/newSAK%E5%8A%9F%E8%83%BD1.jpg)

![img](https://raw.githubusercontent.com/android-notes/blogimg/master/newSAK%E5%8A%9F%E8%83%BD2.jpg)


### Border

Open the border option after the phone screen to see all the boundaries of the control, the effect is as follows

![image](https://raw.githubusercontent.com/android-notes/blogimg/master/%E8%BE%B9%E6%A1%86.jpg)



### Padding Margin


![image](https://raw.githubusercontent.com/android-notes/blogimg/master/%E8%BE%B9%E6%A1%86%E5%8F%8A%E5%86%85%E5%A4%96%E8%BE%B9%E8%B7%9D%E5%A4%A7%E5%B0%8F.jpg)

Translucent green represents the inner margin, PT stands for the top padding top, PL stands for padding left, PT is the right padding right, and PB is the padding Bottom.

Translucent red on behalf of the outer margin, ML, MT, MR, MB, respectively, said the left margin, the outer margin, right outer margin, the next margin.

Units can be controlled at the functional interface


### Height Width



![image](https://raw.githubusercontent.com/android-notes/blogimg/master/%E6%8E%A7%E4%BB%B6%E5%A4%A7%E5%B0%8F.jpg)

### Image Width Height

![image](https://github.com/android-notes/blogimg/blob/master/%E5%9B%BE%E7%89%87%E5%A4%A7%E5%B0%8F.jpg?raw=true)


### Text Size

![image](https://raw.githubusercontent.com/android-notes/blogimg/master/%E5%AD%97%E4%BD%93%E5%A4%A7%E5%B0%8F.jpg)

### Text Color


![image](https://raw.githubusercontent.com/android-notes/blogimg/master/%E5%AD%97%E4%BD%93%E9%A2%9C%E8%89%B2.jpg)


### Personal Info

Custom information for the use of android developers to use, developers can set the view `setTag (com.wanjian.sak.SAK.INFO_KEY, Object)`, set and open the custom information option can be seen in the upper left corner of the control To the custom text information.

For example, if the developer wants to see how many text is in the TextView on the screen, you can use `textview.setTag (com.wanjian.sak.SAK.INFO_KEY, textview.getText (). Length ())` After the custom information option, you can see the text length on the TextView.


### Layer

You can scroll through the scroll wheel to control only show a certain range of information, such as just want to see ListView neutron control size, you can scroll the wheel to control only show ListView child control size, the specific adjustment to the number of need to try. The wheel can control the border, width and height, layout tree and so on.


https://github.com/android-notes/SwissArmyKnife





