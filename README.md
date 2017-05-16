Compressor
======
[![Android Arsenal](https://img.shields.io/badge/Android%20Arsenal-Compressor-blue.svg?style=flat)](http://android-arsenal.com/details/1/3758)
<p align="center"><img src="https://raw.githubusercontent.com/zetbaitsu/Compressor/master/ss.png" width="50%" /></p>
Compressor is a lightweight and powerful android image compression library. Compressor will allow you to compress large photos into smaller sized photos with very less or negligible loss in quality of the image.

# Gradle
```groovy
    allprojects {
        repositories {
            ...
            maven { url 'https://jitpack.io' }
        }
    }
    
    dependencies {
        compile 'com.github.eprendre:Compressor:v1.0.6'
    }
```
# Let's compress the image size!
#### Compress Image File
```java
compressedImageFile = Compressor.getDefault(this).compressToFile(actualImageFile);
```
#### Compress Image File to Bitmap
```java
compressedImageBitmap = Compressor.getDefault(this).compressToBitmap(actualImageFile);
```
### I want custom Compressor!
```java
compressedImage = new Compressor.Builder(this)
            .setMaxSize(720)
            .setQuality(75)
            .setCompressFormat(Bitmap.CompressFormat.WEBP)
            .setDestinationDirectoryPath(Environment.getExternalStoragePublicDirectory(
              Environment.DIRECTORY_PICTURES).getAbsolutePath())
            .build()
            .compressToFile(actualImage);
```
### Stay cool compress image asynchronously with RxJava!
```java
Compressor.getDefault(this)
        .compressToFileAsObservable(actualImage)
        .subscribeOn(Schedulers.io())
        .observeOn(AndroidSchedulers.mainThread())
        .subscribe(new Action1<File>() {
            @Override
            public void call(File file) {
                compressedImage = file;
            }
        }, new Action1<Throwable>() {
            @Override
            public void call(Throwable throwable) {
                showError(throwable.getMessage());
            }
        });
```
### What's different in my fork
1. RxJava 1 to RxJava 2
2. I don't care about the orientation of source image, so `setMaxWidth()` and `setMaxHeight()` are replaced by 
`setMaxSize()`. For example: with `setMaxSize(720)`, an image of `1920x1080` or `1080x1920` will be compressed to 
`1280x720` or `720x1080`.

License
-------
    Copyright (c) 2016 Zetra.
    
    Licensed under the Apache License, Version 2.0 (the "License");
    you may not use this file except in compliance with the License.
    You may obtain a copy of the License at

       http://www.apache.org/licenses/LICENSE-2.0

    Unless required by applicable law or agreed to in writing, software
    distributed under the License is distributed on an "AS IS" BASIS,
    WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
    See the License for the specific language governing permissions and
    limitations under the License.
