# BasicLibrary
BasicLibrary是基于kotlin+jetpack+mvvm封装的一套框架，提高Android开发效率

### How to

To get a Git project into your build:

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
	        implementation 'com.github.Peakmain:BasicLibrary:Tag'
	}
```
