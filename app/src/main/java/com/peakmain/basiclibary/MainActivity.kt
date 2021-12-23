package com.peakmain.basiclibary

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import com.peakmain.basiclibary.network.DataResponse
import com.peakmain.basiclibary.network.ProjectTree
import com.peakmain.basiclibary.network.WanAndroidApi
import com.peakmain.basiclibrary.network.RetrofitManager
import com.peakmain.basiclibrary.network.status.ApiStatus
import com.peakmain.basiclibrary.utils.PreferencesUtils

class MainActivity : AppCompatActivity() {
    private lateinit var  api:WanAndroidApi
    val REQUEST_BASE_URL = "https://wanandroid.com/"
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        api=RetrofitManager.createService(WanAndroidApi::class.java,REQUEST_BASE_URL)
        RetrofitManager.createData(api.projectTree,object :ApiStatus<DataResponse<ProjectTree>>(){
            override fun baseData(entity: DataResponse<ProjectTree>) {

            }

            override fun success(t: DataResponse<ProjectTree>) {
                Log.e("TAG",t.toString())
            }

            override fun error(exception: Exception) {
            }


        })
        PreferencesUtils.getInstance(this).saveParams("key",1)
    }
}