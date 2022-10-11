# ShadowLayout

## 支持自定义阴影颜色

[![](https://jitpack.io/v/FlyJingFish/ShadowLayout.svg)](https://jitpack.io/#FlyJingFish/ShadowLayout)


<img src="https://github.com/FlyJingFish/ShadowLayout/blob/master/screenshot/Screenshot_20221011_141145.jpg" width="405px" height="842px" alt="show" />


## 第一步，根目录build.gradle

```gradle
    allprojects {
        repositories {
            ...
            maven { url 'https://jitpack.io' }
        }
    }
```
## 第二步，需要引用的build.gradle （最新版本[![](https://jitpack.io/v/FlyJingFish/ShadowLayout.svg)](https://jitpack.io/#FlyJingFish/ShadowLayout)）

```gradle
    dependencies {
        implementation 'com.github.FlyJingFish:ShadowLayout:1.0.1'
    }
```
## 第三步，使用说明

### 示例

```xml
<com.flyjingfish.shadowlayoutlib.ShadowLayout
    android:layout_width="wrap_content"
    android:layout_height="wrap_content"
    android:paddingStart="8dp"
    android:paddingEnd="5dp"
    android:paddingTop="5dp"
    android:paddingBottom="9dp"
    android:layout_marginStart="10dp"
    app:shadow_start_color="@color/test_start_color"
    app:shadow_max_length="9dp"
    app:shadow_inscribed_radius="15dp"
    >
    <FrameLayout
        android:layout_width="90dp"
        android:layout_height="90dp"
        android:visibility="visible"
        android:background="@drawable/bg_in_shadow">

    </FrameLayout>
</com.flyjingfish.shadowlayoutlib.ShadowLayout>
```

**1，需要特别设置一下padding，通过设置各个方向的padding值可调整各边漏出的阴影长度,宽高设置wrap_content最佳**

2，阴影颜色分布可通过setGradientPositions设置，默认null值均匀分布

3，阴影想自定义更多颜色可通过setGradientColors设置

### 属性一览

|attr|format|description|
|---|:---:|:---:|
|shadow_max_length|dimension|阴影绘制最大长度|
|shadow_inscribed_radius|dimension|阴影内切圆角|
|shadow_start_color|color|阴影开始颜色|
|shadow_end_color|color|阴影结束颜色（不设置则默认透明色）|

# 最后推荐我写的另一个库，轻松实现在应用内点击小图查看大图的动画放大效果

- [OpenImage](https://github.com/FlyJingFish/OpenImage)

- [主页查看更多开源库](https://github.com/FlyJingFish)


