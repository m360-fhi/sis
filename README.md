# School Information System

This project allowed to manage a school and transfer the information from this school to a centralized server that loads all the information.

## Getting Started

The project has three parts :
* **android-app** : it is the main application that runs in every school over a tablet.
* **webservices** : the php files that are used to save the information on the database
* **database_structure** : the script to create the database that saves all the information

### Prerequisites

To run the application you need the following software :
* **Android Studio** : to run and generate the apk with the application
* **MySQL** : A Mysql instance to run the script of database_structure
* **Apache & PHP** : You need apache and PHP to run the files in webservice folder

### Installing

Fist of all you need to set your variables in the file 

### JSONaINSERT_v5.php
Replace the following code with the credentials and host of your MySQL instance :

```
$host = “server domain or ip of your database server”;
$user = “your user”;
$pass = “password defined for your user”;
```
### upload.php
You need to create a folder upload in the place where you put this file

### debug.keystore
You need to generate a keystore to sign the apk, you can need to name it debug.keystore and save in <your app path>/app/external 
To generate your keystore run the followint command

```
keytool -genkey -v -keystore debug.keystore -alias alias_name -keyalg RSA -keysize 2048 -validity 10000
```

### app/build.gradle
Be sure that you generate the debug.keystore and set the password Abc12345, you can change it but modify this configuration in build.gradle

```
signingConfigs {
    debug {
        storeFile file("external/debug.keystore")
        storePassword "Abc12345"
        keyAlias "alias_name"
        keyPassword "Abc12345"
    }
}
```

You need to generate a key for anyChart, when you get it change the following line in build config

```
buildTypes.each{
    it.buildConfigField 'String', 'AnyChartAPIKey', '"<anyChart key>"'
}
```

### BuidConfig.java
Replace the following value with the anyChart key  

```
 public static final String AnyChartAPIKey = "<anyChart key>";
```

### data/SISConstants.java
Replace the following constants with the correct values of your configuration :

```
    public final static String API_URL = "URL where it can find the file JSONaINSERT_v5.php";
    public final static String APK_LOCATION = "url with the file.apk that is public to update";
    public final static String URL_BACKUP_DB = "url where you create the upload folder";
    public final static String PING_URL = "URL to check if the internet is available";
    public final static String PHP_TO_UPLOAD_FILE = "URL where is the file get_list.php and the parameter ?emis=";
    public final static String ACRA_URL = "Address of the acra service to catch errors";
```

## Libraries in the project

This project uses the following open source project
* [ButterKinife](https://github.com/JakeWharton/butterknife) - Bind Android views and callbacks to fields and methods. 
* [MPAndroidChart](https://github.com/PhilJay/MPAndroidChart) - A powerful Android chart view / graph view library, supporting line- bar- pie- radar- bubble- and candlestick charts as well as scaling, dragging and animations.
* [Tooltip](https://github.com/ViHtarb/Tooltip) - Simple to use customizable Android Tooltips library based on PopupWindow
* [LoadingView](https://github.com/buraktamturk/LoadingView) - LoadingView is easy to implement Loading indicators for your views or layouts
* [CurrencyEditText](https://github.com/BlacKCaT27/CurrencyEditText) - A module designed to encapsulate the use of an Android EditText field for gathering currency information from a user. Supports all ISO-3166 compliant locales/currencies.
* [AutoformatEditText](https://github.com/aldoKelvianto/AutoFormatEditText) - Android Library for auto-formatting money on EditText

## Licence
This project is licensed under the Apache License 
```
Copyright 2019 m360 team

Licensed under the Apache License, Version 2.0 (the "License");
you may not use this file except in compliance with the License.
You may obtain a copy of the License at

http://www.apache.org/licenses/LICENSE-2.0

Unless required by applicable law or agreed to in writing, software
distributed under the License is distributed on an "AS IS" BASIS,
WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
See the License for the specific language governing permissions and
limitations under the License.
```