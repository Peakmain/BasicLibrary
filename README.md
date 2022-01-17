# BasicLibrary
- BasicLibrary是基于kotlin+jetpack+mvvm封装的一套框架,提高Android开发效率
- 还包括MMKV的封装，防止多次事件的处理
- Retrofit封装实现网络解耦
- 线程的切换
- View的抖动效果
- LiveData实现事件分发总线

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
	        implementation 'com.github.Peakmain:BasicLibrary:0.1.2'
	}
```

### wiki
#### 1、集成BaseActivity
```
class XXActivity(override val layoutId: Int = 自定义布局) :
    BaseActivity<T : ViewDataBinding, E : BaseViewModel>() {
    override fun initView() {
    
    }
}
```
- T一般是Databinding布局实现自动生成的类
- E需要继承于BaseViewModel，之后会自动实现initModel方法
```
class MainViewModel : BaseViewModel() {
    override fun initModel() {

    }
}
```
- initModel在initView之前，setContentView之后实现的方法
- 之后可在activity中直接使用mViewModel来获取E的实例，无需初始化
- 也可通过getViewModel(modelClass: Class<E>)获取ViewModel，并多次获取是同一个ViewModel实例

#### 2、集成BaseFragment
```
class XXFragment(override val layoutId: Int = 自定义布局) :
    BaseFragment<T : ViewDataBinding, E : BaseViewModel>() {
    override fun initView(fragmentView:View) {
    
    }
}
```
- 其他性质和BaseActivity一样，不再阐述

#### 3、MMKV的使用
- 在Application中添加如下代码即可
```
        try {
            TaskDispatcher dispatcher = BasicLibraryConfig.Companion.getInstance().getApp().getDispatcher();
            dispatcher.start();
        } catch (Exception e) {
            e.printStackTrace();
        }
```
- 关于启动优化——启动器TaskDispatcher大家可以看我之前关于BasicUI的wiki：[有向无环图实现启动器优化](https://github.com/Peakmain/BasicUI/wiki/有向无环图实现启动器优化)

#### 4、防止多次点击事件的处理
立即处理
```
        mBinding.tvTitle.click{
            //todo 事件处理
        }
```
延时处理
```
        mBinding.tvTitle.clickDelay{
            //todo 事件处理
        }
        //或者
        mBinding.tvTitle.clickViewDelay{
            //todo 事件处理
        }        

```
TextView事件的拆分
```
        mBinding.tvTitle.clickClipListener( mBinding.tvTitle,leftClick = {
              //todo drawableLeft的点击事件
        }){
            //todo drawableRight的点击事件
        }
```
#### 5、关于线程切换
切换到主线程
```
        mBinding.tvTitle.ktxRunOnUiThread { 
        
         }

```
延迟delayMills切换到主线程
```
        mBinding.tvTitle.ktxRunOnUiThreadDelay(delayMills) { 
        
        }
```
延迟加载,默认是500ms
```
        mBinding.tvTitle.wait { 
            
        }
```
延迟加载，设置延迟时间
```
        mBinding.tvTitle.wait(600) {

        }

```
延迟加载，设置延迟时间和单位
```
        mBinding.tvTitle.wait(600,TimeUnit.SECONDS) {

        }
```
#### 5、View的抖动效果
```
View.shakeAnimation( fromXDelta: Float = 0f,
                     toXDelta: Float = 5f,
                     fromYDelta: Float = 0f,
                     toYDelta: Float = 0f,
                     duration: Long = 500)
```

#### 6、网络库的使用
- 推荐在ViewModel中进行初始化
- 创建Service

1.service就是Retrofit请求网络层的Service接口

```
private var api: WanAndroidApi = RetrofitManager.createService(WanAndroidApi::class.java,"https://wanandroid.com/")
```

2.如果想用自己的封装方法实现Service，可以调用以下方法
```
    private var api: WanAndroidApi = RetrofitManager.createService(WanAndroidApi::class.java) {
        //todo 自己创建的Service
    }
```

- 请求网络
第一种写法
```
        RetrofitManager.createData(api.getList(0,10), object : ApiStatus<ApiModel<ProjectBean>>() {
            override fun before() {
                //todo 调用请求网络前
            }

            override fun error(exception: Exception) {
               //todo 返回错误
            }

            override fun success(t: ApiModel<ProjectBean>) {
              //返回成功
            }


        })
```

第二种写法
```
        RetrofitManager.createData(api.projectTree, {
            //todo before()
        }, {
            //todo success(
        }, {
            //todo error()
        })
```

#### 7、LiveData实现事件分发总线
- 注册或获取实例
```
val rxBus = RxBus.instance.register<Int>("test")
```
- 发送数据
```
 rxBus.setData(100)
```
- 接受数据
```
val value=rxBus.value
```
#### 关于我
- 简书([https://www.jianshu.com/u/3ff32f5aea98](https://www.jianshu.com/u/3ff32f5aea98))
- 我的GitHub地址([https://github.com/Peakmain](https://links.jianshu.com/go?to=https%3A%2F%2Fgithub.com%2FPeakmain))
