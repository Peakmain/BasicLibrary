package com.peakmain.basiclibary

import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import android.widget.ProgressBar
import androidx.recyclerview.widget.LinearLayoutManager
import com.peakmain.basiclibary.adapter.TestAdapter
import com.peakmain.basiclibary.databinding.ActivityMainBinding
import com.peakmain.basiclibary.viewModel.MainViewModel
import com.peakmain.basiclibrary.adapter.CommonRecyclerDataBindingAdapter
import com.peakmain.basiclibrary.base.activity.BaseActivity
import com.peakmain.basiclibrary.extend.wait
import com.peakmain.basiclibrary.utils.GlobalCoroutineExceptionHandler
import com.peakmain.ui.recyclerview.listener.OnItemClickListener
import com.peakmain.ui.utils.ToastUtils
import kotlin.coroutines.CoroutineContext

class MainActivity(override val layoutId: Int = R.layout.activity_main) :
    BaseActivity<ActivityMainBinding, MainViewModel>() {

    private lateinit var progressDialog: ProgressBar
    override fun initView() {
        val testAdapter = TestAdapter(getData(), LinearLayoutManager(this))
        testAdapter.bindToRecyclerView(mBinding.recyclerview)
        mBinding.recyclerview.layoutManager=LinearLayoutManager(this)
        testAdapter.setOnLoadMoreListener(object :CommonRecyclerDataBindingAdapter.OnLoadMoreListener{
            override fun onLoadMoreListener() {
              if(data.size<60){
                  updateData()
                  testAdapter.loadMore()
              }else{
                  testAdapter.loadNoMore()
              }
            }

        })
        GlobalCoroutineExceptionHandler().coroutineExceptionCallback={ context, exception->

        }
    }
    fun setClickView(view:View){
        ToastUtils.showLong("测试")
    }
    val data = ArrayList<String>()
    private fun getData(): MutableList<String> {
       for (i in 1..40){
           data.add("test$i")
       }
        return data
    }

    private fun updateData(): MutableList<String> {
        data.add("peakmain")
        data.add("peakmain1")
        data.add("peakmain2")
        data.add("peakmain3")
        data.add("peakmain4")
        data.add("peakmain5")
        data.add("peakmain6")
        return data
    }
}