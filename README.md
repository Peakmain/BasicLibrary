# BasicLibrary
- BasicLibrary是基于kotlin+jetpack+mvvm封装的一套框架,提高Android开发效率
- 项目地址：https://github.com/Peakmain/BasicLibrary
- **使用文档链接:** https://github.com/Peakmain/BasicLibrary/wiki

### 功能介绍
- Activity Results API实现权限封装
- MMKV的封装
- 防止多次事件的处理
- Retrofit封装实现网络解耦
- 线程的切换，View的抖动效果
- LiveData实现事件分发总线


### 怎样使用
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
but If it is a new version of Android studio,Add it in your root setting.gradle at the end of repositories:
```
dependencyResolutionManagement {
    repositories {
        ...
        maven { url 'https://jitpack.io' }
    }
}
```
#### Step 2. Add the dependency
```
	dependencies {
	        implementation 'com.github.Peakmain:BasicLibrary:1.1.3'
	}
```
#### Screenshot
![demo](https://user-images.githubusercontent.com/26482737/197705839-b359236e-7e81-40f2-a8de-5d765fcbba22.gif)


#### 关于我
- 简书([https://www.jianshu.com/u/3ff32f5aea98](https://www.jianshu.com/u/3ff32f5aea98))
- 我的GitHub地址([https://github.com/Peakmain](https://links.jianshu.com/go?to=https%3A%2F%2Fgithub.com%2FPeakmain))

#### Donations
如果您觉得我的开源库帮您节省了大量的开发时间，请扫描下方的二维码随意打赏，您的支持将激励我不断前进
![微信](https://user-images.githubusercontent.com/26482737/184805287-0561a7e2-da13-4ef4-b367-c5e8672c121d.jpg)
![支付宝](https://user-images.githubusercontent.com/26482737/184805306-f44511a7-7660-4fe1-9f07-305005576c2c.jpg)
