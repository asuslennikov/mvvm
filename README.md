# Android MVVM library
 [![Build Status](https://travis-ci.org/asuslennikov/mvvm.svg?branch=master)](https://travis-ci.org/asuslennikov/mvvm) [![Download](https://api.bintray.com/packages/asuslennikov/maven/android-mvvm/images/download.svg)](https://bintray.com/asuslennikov/maven/android-mvvm/_latestVersion)

It's small architectural library, inspired by Android ViewModel, React state and Clean Architecture. 
It has not too many code and you easily can gasp the main idea of this projects just within 15-30 min.

Reference:
- Android ViewModel: https://developer.android.com/topic/libraries/architecture/viewmodel
- React State: https://www.w3schools.com/react/react_state.asp
- Clean Architecture by Uncle Bob: https://blog.cleancoder.com/uncle-bob/2012/08/13/the-clean-architecture.html

## Usage
Artifacts are published in JCenter and Maven Central repository. Make sure that one of them is listed
in the `repository` section of `build.gradle` file in your root project folder:
```groovy
buildscript {
    repositories {
        jcenter()
    }
}
```
or
```groovy
buildscript {
    repositories {
        mavenCentral()
    }
}
```
##### A single module project
If you have just a single module, then in `build.gradle` file of this module add these dependencies:
```groovy
dependencies {
    implementation "com.github.asuslennikov:mvvm-domain:x.y.z"
    implementation "com.github.asuslennikov:mvvm-presentation:x.y.z"
}
```
Please replace the 'x.y.z' by the latest available version (check the JCenter badge at the top of file).

##### A multi-module project
if you follow the clean architecture guideline and have separate modules 
for your business rules (a `domain` module) and presentation (a `presentation` module), then:
- in your `domain` module add this dependency in `build.gradle` file:
    ```groovy
    dependencies {
        api "com.github.asuslennikov:mvvm-domain:x.y.z"
    }
    ```
- and in `presentation` module:
    ```groovy
    dependencies {
        implementation "com.github.asuslennikov:mvvm-presentation:x.y.z"
    }
    ```
Please replace the 'x.y.z' by the latest available version (check the JCenter badge at the top of file).

## Library components

### Communication diagram
![alt FullWidthImage](./documentation/GeneralComponents.png "Communication diagram")

### UML overview

On this image you can see an overview of main library components.

![alt FullWidthImage](./documentation/UmlOverview.png "UML overview of main components")