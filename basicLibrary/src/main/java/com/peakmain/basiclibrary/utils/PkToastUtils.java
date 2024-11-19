/**
 *
 */
package com.peakmain.basiclibrary.utils;

import android.app.Activity;

import com.peakmain.basiclibrary.utils.toast.TopToastController;
import com.peakmain.ui.utils.HandlerUtils;


/**
 * author ：Peakmain
 * createTime：2024/11/15
 * mail:2726449200@qq.com
 * describe：
 */
public class PkToastUtils {

    public static void showTipsToast(Activity activity,
                                     String title,
                                     String message) {
        if (activity == null) return;

       TopToastController topToastController = TopToastController.build(activity)
                .setTitle(title)
                .setMessage(message)
                /*   .setCustomViewInitializer(view -> {
                               TextView text = view.findViewById(R.id.tv_message);
                               ViewGroup.MarginLayoutParams lp = (ViewGroup.MarginLayoutParams) text.getLayoutParams();
                               lp.topMargin = NotchScreenUtil.getNotch(activity);
                               text.setLayoutParams(lp);
                               text.setText(message);
                           }
                   )*/
                .show();
        HandlerUtils.runOnUiThreadDelay(new Runnable() {
            @Override
            public void run() {
                topToastController.dismiss();
            }
        },2000);
    }


    private PkToastUtils() {
    }


}
