package com.peakmain.basiclibrary.utils.bus

import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleEventObserver
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.LiveData
import androidx.lifecycle.Observer
import java.util.concurrent.ConcurrentHashMap

/**
 * author ：Peakmain
 * createTime：2021/5/12
 * mail:2726449200@qq.com
 * describe：LiveData实现事件分发总线
 */
class RxBus {

    companion object {
        private val eventMap = ConcurrentHashMap<String, StickyLiveData<*>>()
        val instance: RxBus by lazy(mode = LazyThreadSafetyMode.SYNCHRONIZED) {
            RxBus()
        }
    }
    /**
     * register bus
     */
    fun <T> register(eventName: String): StickyLiveData<T> {
        var liveData = eventMap[eventName]
        if (liveData == null) {
            liveData =
                StickyLiveData<T>(
                    eventName
                )
            eventMap[eventName] = liveData
        }
        return liveData as StickyLiveData<T>
    }

    class StickyLiveData<T>(private val eventName: String) : LiveData<T>() {
        internal var mData: T? = null
        internal var mVersion = 0
        fun setData(data: T) {
            mData = data
            setValue(data)
        }

        fun postData(data: T) {
            mData = data
            postValue(data)
        }

        override fun setValue(value: T) {
            mVersion++
            super.setValue(value)
        }

        override fun observe(owner: LifecycleOwner, observer: Observer<in T>) {
            observerSticky(owner, false, observer)

        }

        private fun observerSticky(
            owner: LifecycleOwner,
            sticky: Boolean,
            observer: Observer<in T>
        ) {
            owner.lifecycle.addObserver(LifecycleEventObserver { source, event ->
                if (event == Lifecycle.Event.ON_DESTROY) {
                    eventMap.remove(eventName)
                }
            })
            super.observe(owner,
                StickyObserver(
                    this,
                    sticky,
                    observer
                )
            )
        }
    }

    /**
     * sticky不等于true，只能接收到注册之后发送的消息，如果想接受先发送后注册的消息需要设置sticky为true
     */
    class StickyObserver<T>(
        val stickyLiveData: StickyLiveData<T>,
        val sticky: Boolean,
        val observer: Observer<in T>
    ) : Observer<T> {
        private var lastVersion = stickyLiveData.mVersion
        override fun onChanged(t: T) {
            if (lastVersion >= stickyLiveData.mVersion) {
                if(sticky&&stickyLiveData.mData!=null){
                    observer.onChanged(t)
                }
                return
            }
            lastVersion=stickyLiveData.mVersion
            observer.onChanged(t)
        }

    }
}