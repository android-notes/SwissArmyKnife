### 原理
1. 监听window的创建，从而拿到每一个window的根view。
2. 拿到根view（必须是FrameLayout或RelativeLayout）后给根view添加`RootContainerView`，`RootContainerView`内部包含了蓝色的靶心view。每个window都有一个专用的`RootContainerView`。
3. 创建各window共用的`DashBoardView`，`DashBoardView`是其他各功能view的容器，比如开启取色功能后会把`TakeColorView`加到`DashBoardView`中。
4. 点击当前窗口的 靶心 按钮会在当前窗口激活 SwissArmyKnife，其实是把共用的`DashBoardView`添加到了当前窗口的`RootContainerView`中。
5. 每一个功能都是`AbsLayer`的子类，`AbsLayer`继承自`FrameLayout`。当当前窗口的任何一个view需要绘制时都会调用`AbsLayer`的`onUiUpdate`方法，可以在该方法里绘制相关信息，比如 边框，字体颜色等。



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

也可以添加自定义的view，单位转换等。自定义的view要继承自`AbsLayer`或其子类。
AbsLayer是FrameLayout的子类，当激活SwissArmyKnife时会调用 `onAttached(View rootView)`方法，rootView是当前window的根view，比如Activity的根view DecorView。
当停用是会调用`onDetached`




可以添加多个单位转换SizeConverter，默认提供了原始数值SizeConverter，PX2DP SizeConverter，PX2SP SizeConverter，自定义的SizeConverter需要继承自`SizeConverter `，并重写相关方法即可。




## 工程代码：
https://github.com/android-notes/SwissArmyKnife/tree/autopilot   autopilot 分支





