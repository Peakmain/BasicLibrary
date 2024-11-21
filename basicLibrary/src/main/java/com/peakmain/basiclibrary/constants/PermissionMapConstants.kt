package com.peakmain.basiclibrary.constants

import android.Manifest
import androidx.annotation.StringDef

/**
 * author ：Peakmain
 * createTime：2024/11/21
 * mail:2726449200@qq.com
 * describe：
 */
object PermissionMapConstants {
    val dangerousPermissionTagMap = mapOf(
        // 日历权限
        Manifest.permission.READ_CALENDAR to "calendar",
        Manifest.permission.WRITE_CALENDAR to "calendar",

        // 相机权限
        Manifest.permission.CAMERA to "camera",

        // 联系人权限
        Manifest.permission.READ_CONTACTS to "contacts",
        Manifest.permission.WRITE_CONTACTS to "contacts",
        Manifest.permission.GET_ACCOUNTS to "contacts",

        // 位置权限
        Manifest.permission.ACCESS_FINE_LOCATION to "location",
        Manifest.permission.ACCESS_COARSE_LOCATION to "location",

        // 麦克风权限
        Manifest.permission.RECORD_AUDIO to "microphone",

        // 电话权限
        Manifest.permission.READ_PHONE_STATE to "phone",
        Manifest.permission.CALL_PHONE to "phone",
        Manifest.permission.READ_CALL_LOG to "phone",
        Manifest.permission.WRITE_CALL_LOG to "phone",
        Manifest.permission.ADD_VOICEMAIL to "phone",
        Manifest.permission.USE_SIP to "phone",
        Manifest.permission.PROCESS_OUTGOING_CALLS to "phone",

        // 传感器权限
        Manifest.permission.BODY_SENSORS to "sensors",

        // 短信权限
        Manifest.permission.SEND_SMS to "sms",
        Manifest.permission.RECEIVE_SMS to "sms",
        Manifest.permission.READ_SMS to "sms",
        Manifest.permission.RECEIVE_WAP_PUSH to "sms",
        Manifest.permission.RECEIVE_MMS to "sms",

        // 存储权限
        Manifest.permission.READ_EXTERNAL_STORAGE to "storage",
        Manifest.permission.WRITE_EXTERNAL_STORAGE to "storage",

        //蓝牙
        Manifest.permission.BLUETOOTH_CONNECT to "blue",
        Manifest.permission.BLUETOOTH_SCAN to "blue",
    )

    @StringDef(
        PermissionTag.CALENDAR,
        PermissionTag.CAMERA,
        PermissionTag.CONTACTS,
        PermissionTag.LOCATION,
        PermissionTag.MICROPHONE,
        PermissionTag.PHONE,
        PermissionTag.SENSORS,
        PermissionTag.SMS,
        PermissionTag.STORAGE,
        PermissionTag.BLUE,
    )
    @Retention(AnnotationRetention.SOURCE)
    annotation class PermissionTag {
        companion object {
            const val CALENDAR = "calendar"
            const val CAMERA = "camera"
            const val CONTACTS = "contacts"
            const val LOCATION = "location"
            const val MICROPHONE = "microphone"
            const val PHONE = "phone"
            const val SENSORS = "sensors"
            const val SMS = "sms"
            const val STORAGE = "storage"
            const val BLUE = "blue"
        }
    }


}