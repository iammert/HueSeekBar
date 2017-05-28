# HueSeekBar
Yet another seek bar inspired from Philips Hue app

<img src="https://raw.githubusercontent.com/iammert/HueSeekBar/master/art/art.gif"/>


# Usage

```java
<iammert.com.huelib.HueSeekBar
            android:layout_width="match_parent"
            android:layout_height="120dp"
            app:bottomSeekHeight="20dp"
            app:bottomSeekToggleWidth="20dp"
            app:colorEmpty="#a39e8c"
            app:colorFill="#e1ddd1"
            app:colorToggle="#ffffff"
            app:maxProgress="100"
            app:currentProgress="20"/>
```
```java
hueSeekBar = (HueSeekBar) findViewById(R.id.hueSeekBar);
```
```java
hueSeekBar.setProgressListener(new ProgressListener() {
            @Override
            public void onProgressChange(int progress) {
                //do somth with progress value
            }
        });
```
```java
hueSeekBar2.setVerticalAnimationListener(new VerticalAnimationListener() {
            @Override
            public void onAnimProgressChanged(int percentage) {
                  // Seekbar is scaled up/down vertically on pressed/released
                  // You might want to do some layout changes with given percentage.
            }
});
```

License
--------


    Copyright 2017 Mert Şimşek.

    Licensed under the Apache License, Version 2.0 (the "License");
    you may not use this file except in compliance with the License.
    You may obtain a copy of the License at

       http://www.apache.org/licenses/LICENSE-2.0

    Unless required by applicable law or agreed to in writing, software
    distributed under the License is distributed on an "AS IS" BASIS,
    WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
    See the License for the specific language governing permissions and
    limitations under the License.






