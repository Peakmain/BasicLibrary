package com.peakmain.basiclibary.activity

import android.os.Bundle
import android.view.View
import android.widget.FrameLayout
import android.widget.LinearLayout
import androidx.appcompat.app.AppCompatActivity
import com.peakmain.basiclibary.R
import com.peakmain.basiclibrary.helper.BehaviorHelper
import com.peakmain.basiclibrary.interfaces.IBehaviorHelperCallback
import com.peakmain.ui.utils.LogUtils
import com.peakmain.ui.widget.ShapeTextView

/**
 * author ：Peakmain
 * createTime：2022/11/14
 * mail:2726449200@qq.com
 * describe：
 */
class BehaviorActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_behavior)
        val flRoot = findViewById<FrameLayout>(R.id.fl_root)
        val llRoot = findViewById<LinearLayout>(R.id.ll_root)
        val behaviorHelper = BehaviorHelper(flRoot, llRoot, object : IBehaviorHelperCallback {
            override fun open(bottomSheet: View?, newState: Int) {
                LogUtils.e("打开了")
            }

            override fun close(bottomSheet: View?, newState: Int) {
                LogUtils.e("关闭了")
            }

        })
        findViewById<ShapeTextView>(R.id.stv_use_point).setOnClickListener {
            behaviorHelper.toggle()
        }
    }
}