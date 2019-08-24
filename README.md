# EmptyStateLibrary
An Android Library for showing different states of a view

![Images](https://github.com/farzadfarazmand/EmptyStateLibrary/blob/master/preview.png)

## Features
- [x] MinSdk 14
- [x] Support both PNG and SVG as icon
- [x] Could use different custom fonts for title, description and button  
- [x] Support both PNG and SVG as icon
- [x] Easy to use

### Add Library

1. Add the JitPack repository to your build file
```groovy
allprojects {
    repositories {
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
    implementation 'com.github.farzadfarazmand:EmptyStateLibrary:v1.0.1'
}
```

### Add EmptyState view

```xml
<com.github.farzadfarazmand.emptystate.EmptyState
            android:id="@+id/emptyStateSample"
            android:layout_width="match_parent"
            android:layout_height="match_parent"
            android:visibility="invisible"
            app:emps_iconSize="SMALL"
            app:emps_icon="@drawable/ic_sad"
            app:emps_title="@string/noInternet_title"
            app:emps_titleFontPath="fonts/Montserrat_Bold.ttf"
            app:emps_descriptionFontPath="fonts/Montserrat_Regular.ttf"
            app:emps_description="Please check your internet connection"
            app:emps_showButton="true"
            app:emps_buttonFontPath="fonts/Montserrat_Bold.ttf"
            app:emps_buttonText="Enable WiFi"
            app:emps_buttonCorner="5dp"/>
```
### All xml attributes
Key | Value | Description
--- | ----- | ---
emps_iconSize | SMALL, NORMALL, BIG | size of icon
emps_icon | icon resource id | could be png or vector drawable
emps_title | String | emptyState title, will be gone if not set 
emps_titleFontPath | String | title font's path
emps_titleSize | Dimension | title text size, dp or sp
emps_titleColor | Color | title text color, Hex ot color reference
emps_description     | String | emptyState description, will be gone if not set 
emps_descriptionFontPath | String | title font's path
emps_descriptionSize | Dimension | title text size, dp or sp
emps_descriptionColor | Color | title text color, Hex or color reference
emps_showButton | Boolean | show / hide button 
emps_buttonText | String | button's text
emps_buttonFontPath | String | button text font's path
emps_buttonTextSize | Dimension | button text size, dp or sp
emps_buttonTextColor | Color | button text color, Hex ot color reference
emps_buttonBackgroundColor | Color | button background color, Hex ot color reference
emps_buttonCorner | Dimension | button corner size in dp

_**All of these attributes have setters and could be set from code**_

### Show / Hide with animation
You could show or hide emptyState view with animation:
```kotlin
    val emptyStateSample = findViewById(R.id.emptyStateSample)
    
    //show, default animation is fade in 
    emptyStateSample.show(android.R.anim.slide_in_left, OvershootInterpolator())
    
    
    //hide, default animation is fade out
    emptyStateSample.show(android.R.anim.slide_out_right, OvershootInterpolator())
```

## Licenses
```
Copyright 2019 Farzad Farazmand.

Licensed to the Apache Software Foundation (ASF) under one or more contributor
license agreements.  See the NOTICE file distributed with this work for
additional information regarding copyright ownership.  The ASF licenses this
file to you under the Apache License, Version 2.0 (the "License"); you may not
use this file except in compliance with the License.  You may obtain a copy of
the License at

  http://www.apache.org/licenses/LICENSE-2.0

Unless required by applicable law or agreed to in writing, software
distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.  See the
License for the specific language governing permissions and limitations under
the License.
```