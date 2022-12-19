package com.peakmain.basiclibrary.utils.bus

import androidx.arch.core.executor.ArchTaskExecutor
import androidx.lifecycle.*
import com.peakmain.basiclibrary.utils.ThreadUtils
import java.util.concurrent.ConcurrentHashMap
import kotlin.collections.set

/**
 * author ：Peakmain
 * createTime：2021/5/12
 * mail:2726449200@qq.com
 * describe：LiveData实现事件分发总线
 */
class RxBus private constructor() {

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
        private var mSticky: Boolean = false
        fun setData(data: T) {
            setValue(data)
        }

        fun postData(data: T) {
            super.postValue(data)
        }

        /**
         * @param sticky true表示是粘性事件,默认是false
         */
        fun isSticky(sticky: Boolean) {
            mSticky = sticky
        }

        override fun setValue(value: T) {
            ThreadUtils.assertMainThread("StickyLiveData setValue")
            mData = value
            mVersion++
            super.setValue(value)
        }

        override fun observe(owner: LifecycleOwner, observer: Observer<in T>) {
            observerSticky(owner, mSticky, observer)
        }

        private fun observerSticky(
            owner: LifecycleOwner,
            sticky: Boolean,
            observer: Observer<in T>
        ) {
            owner.lifecycle.addObserver(LifecycleEventObserver { _, event ->
                if (event == Lifecycle.Event.ON_DESTROY) {
                    eventMap.remove(eventName)
                }
            })
            super.observe(
                owner,
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
        private val stickyLiveData: StickyLiveData<T>,
        private val sticky: Boolean,
        private val observer: Observer<in T>
    ) : Observer<T> {
        private var lastVersion = stickyLiveData.mVersion
        override fun onChanged(t: T) {
            if (lastVersion >= stickyLiveData.mVersion) {
                if (sticky && stickyLiveData.mData != null) {
                    observer.onChanged(t)
                }
                return
            }
            lastVersion = stickyLiveData.mVersion
            observer.onChanged(t)
        }

    }

}
