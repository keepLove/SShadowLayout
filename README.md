#阴影控件 [![SShadowLayout](https://jitpack.io/v/com.github.keepLove/SShadowLayout.svg)](https://jitpack.io/#com.github.keepLove/SShadowLayout)

## Dependency

#### Step 1. Add the JitPack repository to your build file

Add it in your root build.gradle at the end of repositories:


```
    allprojects {
    	repositories {
			...
			maven { url 'https://jitpack.io' }
		}
	}
```

#### Step 2. Add the dependency


```
    dependencies {
        implementation 'com.github.keepLove:SShadowLayout:Tag'
	}
```

## Use

```
    <com.s.android.shadowlayout.SShadowFrameLayout
        ...
        app:ssl_background="#FFF"
        app:ssl_radius="10dp"
        app:ssl_radius_bottomLeft="30dp"
        app:ssl_radius_topRight="30dp"
        app:ssl_shadowAlpha="0.5"
        app:ssl_shadowColor="#F19EC2"
        app:ssl_shadowElevation="5dp"
        app:ssl_shadowType="padding">
        ...
    </com.s.android.shadowlayout.SShadowFrameLayout>
```

使用两种方式设置阴影，当sdk大于21时，可以设置ssl_shadowType="outline"，但只有ssl_radius，ssl_shadowAlpha和ssl_shadowElevation有用。
设置ssl_shadowType="padding"，参数都可以使用。

    ssl_background 设置背景颜色
    ssl_radius 圆角
    ssl_radius_bottomLeft 下左方向圆角
    ssl_radius_bottomRight 下右方向圆角
    ssl_radius_topLeft 上左方向圆角
    ssl_radius_topRight 上右方向圆角
    ssl_shadowAlpha 透明度
    ssl_shadowColor 阴影颜色
    ssl_shadowElevation 阴影偏移量
    ssl_showDirection 显示方向，可以设置left、top、right、bottom、no
