# BasicLibrary
- BasicLibrary是基于kotlin+jetpack+mvvm封装的一套框架,提高Android开发效率
- 集成了自己封装的BasicUI库，关于BasicUI大家可以看我之前写的文章[BasicUI常用UI组件和实用工具类封装，提高Android开发的效率](https://www.jianshu.com/p/78bcc6c3bbca)
还包括:
- MMKV的封装
- 防止多次事件的处理
- Retrofit封装实现网络解耦
- 线程的切换，View的抖动效果
- LiveData实现事件分发总线
- 项目地址：https://github.com/Peakmain/BasicLibrary
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
#### Step 2. Add the dependency
```
	dependencies {
	        implementation 'com.github.Peakmain:BasicLibrary:1.0.0'
	}
```

### 功能列表
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
```
 Log.e("TAG","测试${getViewModel(MainViewModel::class.java)}")
 Log.e("TAG","测试${getViewModel(MainViewModel::class.java)}")
```

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
- 初始化
在Application中添加如下代码即可
```
        try {
            TaskDispatcher dispatcher = BasicLibraryConfig.Companion.getInstance().getApp().getDispatcher();
            dispatcher.start();
        } catch (Exception e) {
            e.printStackTrace();
        }
```
或者直接使用MMKV的初始化
```
	MMKV.initialize(BasicLibraryConfig.getInstance().getApp().getApplication())//参数是Context
```
- 使用
保存数据
```
 PreferencesUtils.getInstance(this).saveParams(key,value)//👈🏻key: String, objects: Any
```
获取数据
```
 PreferencesUtils.getInstance(this).getParam(key,defalutParams)//👈🏻key: String, defaultObject: Any?
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
#### 8、CommonRecyclerDataBindingAdapter RecycleView在Binding的使用
```
class TestAdapter(data: MutableList<String>, layoutManager: LinearLayoutManager) :
    CommonRecyclerDataBindingAdapter<String, RecyclerAdpterTestBinding>(
        data,//👈🏻数据源
        R.layout.recycler_adpter_test,//👈自定义布局
        DefaultAdapterFooterConfig<String,RecyclerAdpterTestBinding>(layoutManager).item//👈是否显示加载更多,如果为null，则不显示加载更多，可自定义，可参考DefaultAdapterFooterConfig的源码
    ) {
    override fun convert(
        holder: BaseLibraryViewHolder<RecyclerAdpterTestBinding>,
        itemData: String,
        position: Int
    ) {
        val binding = holder.itemDataBinding
        binding.vm = itemData
    }

}
```
#### 9、获取协程的全局异常
```
       GlobalCoroutineExceptionHandler().coroutineExceptionCallback={ context, exception->
            //context:CoroutineContext
	    //exception:Throwable
        }
```
#### 10、BindingAdapter的使用
	
- ①、文本添加*前缀
```
	app:asteriskPrevText=""//文本的名字
	app:asteriskColor=""//星号的颜色
```
- ②、设置image的url图片
```
	app:loadUrl=""//参数url
```
- ③、设置view的visibility是View.VISIBLE还是View.Gone
```
	app:visibilityOrGone="@{!vm.isShow}"//true表示View.Visible,false则表示View.Gone
```
- ④、设置view的visibility是View.VISIBLE还是View.InVisibile
```
	app:visibilityOrInVisible="@{vm.isShow}"//true表示View.Visible,false则表示View.InVisbile

```
- ⑤、防止多次重复点击
```
	app:clickDelayTime="@{vm.delayTime}"//设置点击事件的延迟时间,参数为long类型
	app:click="@{vm.clickListener}"//设置点击事件的事件处理
```
- ⑥、textView的drawableLeft和drawableRight的点击事件
```
	app:drawableLeftClick=""//drawableLeft的点击事件
	app:drawableRightClick=""//drawableRight的点击事件
```
#### 关于我
- 简书([https://www.jianshu.com/u/3ff32f5aea98](https://www.jianshu.com/u/3ff32f5aea98))
- 我的GitHub地址([https://github.com/Peakmain](https://links.jianshu.com/go?to=https%3A%2F%2Fgithub.com%2FPeakmain))

