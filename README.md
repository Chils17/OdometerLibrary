# Odometer
This is an Android Library for making Odometer with little customization with reading, slots, colors, font and size.
<br>
Example is available in app module.

<p>
<img src="https://raw.githubusercontent.com/Chils17/OdometerLibrary/master/app/src/main/assets/screenshot%201.png" width="270">

<!-- [![Alt text for your video](https://img.youtube.com/vi/T-D1KVIuvjA/0.jpg)](http://www.youtube.com/watch?v=T-D1KVIuvjA) -->

<img src="https://raw.githubusercontent.com/Chils17/OdometerLibrary/master/app/src/main/assets/Screenshot%202.png" alt="screenshot" width="270">

<img src="https://raw.githubusercontent.com/Chils17/OdometerLibrary/master/app/src/main/assets/Screenshot%203.png" alt="screenshot" width="270">

</p>


## Download

<!-- 
### Gradle dependency:
- Add the following to your project level build.gradle:
~~~
allprojects {
	repositories {
		...
		maven { url "https://jitpack.io" }
	}
}
~~~
- Add this to your app build.gradle:
~~~
dependencies {
	compile 'com.github.droidbyme:DroidDialog:d4b5af0f48'
}
~~~
<!--
 <p>
<a href="https://play.google.com/store/apps/details?id=com.desai.vatsal.myamazingcalendar&hl=en">
<img src="https://github.com/vatsaldesai92/MyDynamicCalendarLibrary/blob/master/app/src/main/assets/google_play_icon.png" alt="screenshot" width="270">
</a>
</p> -->
 -->

### Usage

- Create Odometer with their properties.<br>
~~~
    <com.androidchils.odometer.Odometer
        android:layout_width="match_parent"
        android:layout_height="wrap_content"> 
    </com.androidchils.odometer.Odometer>
    
~~~

- Add Background<br>
  You can change odometer background.
~~~ 
    chils:np_backgroundColor="@drawable/gradient"
~~~


- Add BackgroundColor<br>
  You can change odometer background color.
  You can put drawable and color too.
~~~ 
    chils:np_backgroundColor="@drawable/gradient"
~~~

- Add Reading<br>
~~~
     chils:np_reading="0000"
~~~

- Add Slots<br>
~~~
    chils:np_slots="4"
~~~


- Add TextColor<br>
~~~
    chils:np_textColor="@color/white"
~~~


- Add TextSize<br>
~~~
    chils:np_textSize="18sp"
~~~

- Add custom font from .ttf. Put your .ttf file at assets\fonts. Font will apply everywhere title, content, buttons <br>
~~~
    chils:np_font="@string/lato_regular"
~~~


- Those attributes necessary to add reading and slots together in Odometer <br>
~~~
    chils:np_reading="0000"
    chils:np_slots="4"
~~~

### Odometer

- You can even use the Odometer alone.<br>
~~~
     <com.androidchils.odometer.Odometer
             android:id="@+id/odometer"
             android:layout_width="match_parent"
             android:layout_height="100dp"
             chils:np_background="@color/color_transparent"
             chils:np_backgroundColor="@drawable/gradient"
             chils:np_font="@string/lato_regular"
             chils:np_reading="0000"
             chils:np_slots="4"
             chils:np_textColor="@color/white"
             chils:np_textSize="18sp" />
~~~

### Layout Customization

If you want to get the value of Odometer scrolling value you can create your own.
 
 Note: You can see an example layout in both sample and library modules.
 
 Example XML layout:
~~~
     <TextView
            android:id="@+id/tvOutPut"
            android:layout_width="wrap_content"
            android:layout_height="wrap_content"
            android:layout_gravity="center"
            android:layout_marginTop="20dp"
            android:textColor="@color/black"
            android:textSize="18sp" />
    
        <Button
            android:id="@+id/btn_submit"
            android:layout_width="wrap_content"
            android:layout_height="wrap_content"
            android:layout_gravity="center"
            android:layout_marginTop="20dp"
            android:background="@color/black"
            android:text="Submit"
            android:textColor="@color/white"
            android:textStyle="bold" />
~~~

 Set a listener to be notified when value has changed:
~~~
        btn_submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                tvOutPut.setText(odometer.getFinalOdoMiterValue());
            }
        });
~~~
 
### License
~~~
    Apache Version 2.0

    Copyright 2017.

    Licensed under the Apache License, Version 2.0 (the "License");
    you may not use this file except in compliance with the License.
    You may obtain a copy of the License at

    http://www.apache.org/licenses/LICENSE-2.0

    Unless required by applicable law or agreed to in writing, software
    distributed under the License is distributed on an "AS IS" BASIS,
    WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
    See the License for the specific language governing permissions and
    limitations under the License.
~~~
