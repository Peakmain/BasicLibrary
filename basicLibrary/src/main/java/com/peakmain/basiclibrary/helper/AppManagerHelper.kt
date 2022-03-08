package com.peakmain.basiclibrary.helper

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.content.pm.ApplicationInfo
import android.content.pm.PackageManager
import android.net.Uri
import com.peakmain.basiclibrary.R
import com.peakmain.basiclibrary.bean.AppManagerBean
import java.lang.ref.WeakReference

/**
 * author ：Peakmain
 * createTime：2022/3/8
 * mail:2726449200@qq.com
 * describe：App管理帮组类
 */
class AppManagerHelper private constructor(val reference: WeakReference<Context>) {
    private lateinit var mPackageManager: PackageManager
    private val mAllAppList = ArrayList<AppManagerBean>()

    //所有商店包名
    private lateinit var mAllMarkArray: Array<String>

    companion object {
        @Volatile
        private var instance: AppManagerHelper? = null
        fun getInstance(context: Context): AppManagerHelper? {
            if (instance == null) {
                synchronized(this) {
                    if (instance == null) {
                        instance = AppManagerHelper(WeakReference(context))
                    }
                }
            }
            return instance
        }

    }

    @SuppressLint("QueryPermissionsNeeded")
    fun loadAllAppList(): ArrayList<AppManagerBean> {
        if(reference.get()==null)return mAllAppList
        val context = reference.get() as Context
        mPackageManager = context.packageManager
        val intent = Intent(Intent.ACTION_MAIN, null)
        intent.addCategory(Intent.CATEGORY_LAUNCHER)
        val appInfo = mPackageManager.queryIntentActivities(intent, 0)
        appInfo.forEachIndexed { _, resolveInfo ->
            val appManagerBean = AppManagerBean(
                resolveInfo.activityInfo.packageName,
                resolveInfo.loadLabel(mPackageManager) as String,
                resolveInfo.loadIcon(mPackageManager),
                resolveInfo.activityInfo.name,
                (resolveInfo.activityInfo.flags and ApplicationInfo.FLAG_SYSTEM) > 0
            )
            mAllAppList.add(appManagerBean)
        }
        return mAllAppList
    }

    /**
     * 获取非系统应用
     */
    fun getNonSystemLaunchList(): List<AppManagerBean> {
        return mAllAppList.filter { !it.isSystemApp }
    }

    fun startAppStore(appName: String): Boolean {
        if(reference.get()==null)return false
        val context = reference.get() as Context
        mAllMarkArray = context.resources.getStringArray(R.array.library_app_market_array)
        mAllAppList.forEach {
            if (!mAllMarkArray.contains(it.packageName)) return false
            if (mAllAppList.size <= 0) return false
            mAllAppList.forEach { app ->
                if (app.appName == appName) {
                    startAppStore(appName, it.packageName)
                    return true
                }
            }
        }
        return false
    }

    /**
     * 启动卸载App
     */
    fun startUnInstallApp(packageName: String) {
        if(reference.get()==null)return
        val context = reference.get() as Context
        val uri = Uri.parse("package:$packageName")
        val intent = Intent(Intent.ACTION_DELETE)
        intent.data = uri
        intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK
        context.startActivity(intent)
    }

    /**
     * 跳转应用商店
     */
    fun startAppStore(packageName: String, markPackageName: String) {
        if(reference.get()==null)return
        val context = reference.get() as Context
        val uri = Uri.parse("market://details?id=$packageName")
        val intent = Intent(Intent.ACTION_VIEW, uri)
        intent.setPackage(markPackageName)
        intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK
        context.startActivity(intent)
    }
}
