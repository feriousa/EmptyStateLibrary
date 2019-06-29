# EmptyStateLibrary
An Android Library for showing different states of view

![Images](https://github.com/farzadfarazmand/EmptyStateLibrary/blob/master/preview.png)

### Add Library

1. Add the JitPack repository to your build file
```groovy
allprojects {
		repositories {
			...
			maven { url 'https://jitpack.io' }
		}
	}
```

2. Add the dependency
```groovy
dependencies {
    .
    .
    .
    implementation 'com.github.farzadfarazmand:EmptyStateLibrary:v1.0.0'
}
```

### Add EmptyState view

```xml
<com.github.farzadfarazmand.emptystate.EmptyState
        android:id="@+id/emptyStateSample"
        android:layout_width="match_parent"
        android:layout_height="match_parent"
        android:visibility="invisible"
        app:emps_iconSize="NORMAL"
        app:emps_icon="@drawable/ic_sad"
        app:emps_title="No Internet Connection"
        app:emps_titleFontPath="fonts/Montserrat_Bold.ttf"
        app:emps_buttonFontPath="fonts/Montserrat_Bold.ttf"
        app:emps_descriptionFontPath="fonts/Montserrat_Regular.ttf"
        app:emps_description="Please check your internet connection"
        app:emps_showButton="true"
        app:emps_buttonBackgroundColor="@color/button_bg_color"
        app:emps_buttonTextColor="@android:color/white"
        app:emps_buttonText="Enable WiFi"/>
```
### xml variables
Key | Value | Description
--- | ----- | ---
emps_iconSize | SMALL, NORMALL, BIG | size of icon
emps_icon | icon resource id | could be png or vector drawable
emps_title | String | emptyState title, shown below image 
emps_titleFontPath | String | title font
emps_titleSize | Dimention | title text size, Dp or Sp
emps_titleColor | Color | title text color

