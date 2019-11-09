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

### Data flow diagram

![alt FullWidthImage](./documentation/GeneralComponents.png "Data flow (communication) diagram")

### UML overview

On this image you can see an overview of main library components:

![alt FullWidthImage](./documentation/UmlOverview.png "UML overview of main components")

### Components details

**Presentation layer:**
<details open>
    <summary>Screen</summary>

| Name            | Screen |
| :---            | :--- |
| Synonyms        | View, Render |
| Component layer | Presentation |
| Responsibility  | It renders a part of application UI (it controls what user see and how he can communicate with that part of UI). |
| UML diagram     | ![alt TableImage](./documentation/ScreenComponent.png "UML diagram for Screen component") |
| Notes           | - It never changes `State` by itself, always delegates it to `ViewModel`. <br /> - It doesn't have any behaviour-related logic. All this staff goes to `ViewModel`. <br /> - It shouldn't have mutable local fields which have influence to UI (except, maybe, a saved `State`). <br /> - `Screen` should have reference to only one `ViewModel`. <br /> - `Screen`  doesn't need any external dependencies, except the `ViewModel` (because it is the only one source of truth for the `Screen`). <br />  - It is recommended to have Espresso tests for the `Screen`. Unit tests are not necessary (because `Screen` have only UI logic and it's not easy to check correctness with regular unit tests). |
| Example link    | TBD |
 
</details>

<details>
    <summary>State</summary>

| Name            | State |
| :---            | :--- |
| Synonyms        | ViewState, ScreenState, UIModel, Model |
| Component layer | Presentation |
| Responsibility  | It describes a state (collection of elements' properties) at specific moment in time for part of UI |
| UML diagram     | ![alt TableImage](./documentation/StateComponent.png "UML diagram for State component") |
| Notes           | - It's a POJO class (data class in Kotlin). <br/> - It doesn't have any logic, only getters and setters. <br /> - Should implement some mechanism for serialization / deseralization. For example, use the `@Parcel` annotation or set JSON annotations of your favorite mapper. <br /> - Fields have descriptive names, but not "action" names (use `newsListLoaderVisible` instead of `showNewsListLoader`). <br /> - No need in Unit tests. |
| Example link    | TBD |
 
</details>

<details>
    <summary>Effect</summary>

| Name            | Effect |
| :---            | :--- |
| Synonyms        | |
| Component layer | Presentation |
| Responsibility  | |
| UML diagram     | ![alt TableImage](./documentation/EffectComponent.png "UML diagram for Effect component") |
| Notes           | |
| Example link    | TBD |
 
</details>

<details>
    <summary>ViewModel</summary>

| Name            | ViewModel |
| :---            | :--- |
| Synonyms        | |
| Component layer | Presentation |
| Responsibility  | |
| UML diagram     | ![alt TableImage](./documentation/ViewModelComponent.png "UML diagram for ViewModel component") |
| Notes           | |
| Example link    | TBD |
 
</details>

**Domain layer:**
<details>
    <summary>UseCaseInput</summary>

| Name            | UseCaseInput |
| :---            | :--- |
| Synonyms        | |
| Component layer | Domain |
| Responsibility  | |
| UML diagram     | ![alt TableImage](./documentation/UseCaseInputComponent.png "UML diagram for UseCaseInput component") |
| Notes           | |
| Example link    | TBD |
 
</details>

<details>
    <summary>UseCaseOutput</summary>

| Name            | UseCaseOutput |
| :---            | :--- |
| Synonyms        | |
| Component layer | Domain |
| Responsibility  | |
| UML diagram     | ![alt TableImage](./documentation/UseCaseOutputComponent.png "UML diagram for UseCaseOutput component") |
| Notes           | |
| Example link    | TBD |
 
</details>

<details>
    <summary>UseCase</summary>

| Name            | UseCase |
| :---            | :--- |
| Synonyms        | |
| Component layer | Domain |
| Responsibility  | |
| UML diagram     | ![alt TableImage](./documentation/UseCaseComponent.png "UML diagram for UseCase component") |
| Notes           | |
| Example link    | TBD |
 
</details>

**Data layer:**
<details>
    <summary>Manager</summary>

| Name            | Manager |
| :---            | :--- |
| Synonyms        | |
| Component layer | Data |
| Responsibility  |  |
| UML diagram     | No specific component diagram |
| Notes           | |
| Example link    | TBD |
 
</details>