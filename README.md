# ShadowLayout

## 支持自定义阴影颜色，自定义四个边的阴影宽度

[![](https://jitpack.io/v/FlyJingFish/ShadowLayout.svg)](https://jitpack.io/#FlyJingFish/ShadowLayout)
[![GitHub stars](https://img.shields.io/github/stars/FlyJingFish/ShadowLayout.svg)](https://github.com/FlyJingFish/ShadowLayout/stargazers)
[![GitHub forks](https://img.shields.io/github/forks/FlyJingFish/ShadowLayout.svg)](https://github.com/FlyJingFish/ShadowLayout/network)
[![GitHub issues](https://img.shields.io/github/issues/FlyJingFish/ShadowLayout.svg)](https://github.com/FlyJingFish/ShadowLayout/issues)
[![GitHub license](https://img.shields.io/github/license/FlyJingFish/ShadowLayout.svg)](https://github.com/FlyJingFish/ShadowLayout/blob/master/LICENSE)


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
        implementation 'com.github.FlyJingFish:ShadowLayout:1.0.3'
    }
```
## 第三步，使用说明

### 示例

**限制内部布局宽高，外层ShadowLayout宽高设置为wrap_content，然后设置ShadowLayout的padding来达到阴影效果**

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

**限制外部ShadowLayout布局宽高，内层布局宽高设置为match_parent，然后设置ShadowLayout的padding来达到阴影效果**

```xml

<com.flyjingfish.shadowlayoutlib.ShadowLayout
    android:layout_width="100dp"
    android:layout_height="100dp"
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
        android:layout_width="match_parent"
        android:layout_height="match_parent"
        android:visibility="visible"
        android:background="@drawable/bg_in_shadow">
    
    </FrameLayout>
</com.flyjingfish.shadowlayoutlib.ShadowLayout>

```

**1，需要特别设置一下padding，通过设置各个方向的padding值可调整各边漏出的阴影长度**

2，阴影颜色分布可通过setGradientPositions设置，默认null值均匀分布

3，阴影想自定义更多颜色可通过setGradientColors设置

### 属性一览

| attr                    |  format   |    description    |
|-------------------------|:---------:|:-----------------:|
| shadow_max_length       | dimension |     阴影绘制最大长度      |
| shadow_inscribed_radius | dimension |      阴影内切圆角       |
| shadow_start_color      |   color   |      阴影开始颜色       |
| shadow_end_color        |   color   | 阴影结束颜色（不设置则默认透明色） |

**特别说明：shadow_inscribed_radius低于shadow_max_length时，内切圆角将会变成直角，请尽量使shadow_inscribed_radius大于shadow_max_length值**


# 最后推荐我写的另一个库，轻松实现在应用内点击小图查看大图的动画放大效果

- [OpenImage](https://github.com/FlyJingFish/OpenImage)

- [主页查看更多开源库](https://github.com/FlyJingFish)


