package com.peakmain.basiclibrary.constants

import android.Manifest.permission
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.annotation.StringDef
import java.security.Permission


/**
 * author ：Peakmain
 * createTime：2021/4/22
 * mail:2726449200@qq.com
 * describe：
 */
object PermissionConstants {
    const val CALENDAR = "CALENDAR"
    const val CAMERA = "CAMERA"
    const val CONTACTS = "CONTACTS"
    const val LOCATION = "LOCATION"
    const val MICROPHONE = "MICROPHONE"
    const val PHONE = "PHONE"
    const val SENSORS = "SENSORS"
    const val SMS = "SMS"
    const val STORAGE = "STORAGE"
    const val ACTIVITY_RECOGNITION = "ACTIVITY_RECOGNITION"
    const val NOTIFICATIONS = "NOTIFICATIONS"

    @RequiresApi(33)
    private val GROUP_NOTIFICATIONS = arrayOf(
        permission.POST_NOTIFICATIONS
    )
    private val GROUP_CALENDAR = arrayOf(
        permission.READ_CALENDAR,
        permission.WRITE_CALENDAR
    )
    private val GROUP_CAMERA = arrayOf(
        permission.CAMERA
    )
    private val GROUP_CONTACTS = arrayOf(
        permission.READ_CONTACTS,
        permission.WRITE_CONTACTS,
        permission.GET_ACCOUNTS
    )

    /**
     * 关于位置权限说明:
     * android 10.0
     *  1、ACCESS_BACKGROUND_LOCATION是Android 10新增的，10.0以前谷歌提供了兼容性方案，只要应用申请了老的位置权限ACCESS_FINE_LOCATION或者ACCESS_COARSE_LOCATION，会默认请求ACCESS_BACKGROUND_LOCATION权限
     *  2.大于10.0 如果应用必须要始终定位，可以只申请ACCESS_BACKGROUND_LOCATION即可;如果应用只需要申请前台定位，则只需要申请老的定位权限即可
     * android 11.0
     *  1.先申请前台位置信息访问权限
     *  2.在申请后台位置信息访问权限，引导用户到设置中进行授予
     * android 12.0
     *  1.Android 12 或更高版本为目标平台的应用时，用户可以请求应用只能访问大致位置信息
     *  2.请求 ACCESS_FINE_LOCATION运行时权限，您还必须请求 ACCESS_COARSE_LOCATION权限。不要单独申请ACCESS_FINE_LOCATION运行时权限
     *
     */
    private val GROUP_LOCATION_BELOW_Q = arrayOf(
        permission.ACCESS_FINE_LOCATION,
        permission.ACCESS_COARSE_LOCATION
    )

    @RequiresApi(Build.VERSION_CODES.Q)
    private val GROUP_LOCATION = arrayOf(
        permission.ACCESS_FINE_LOCATION,
        permission.ACCESS_COARSE_LOCATION,
        permission.ACCESS_BACKGROUND_LOCATION
    )
    private val GROUP_MICROPHONE = arrayOf(
        permission.RECORD_AUDIO
    )

    @RequiresApi(Build.VERSION_CODES.O)
    private val GROUP_PHONE = arrayOf(
        permission.READ_PHONE_STATE,
        permission.READ_PHONE_NUMBERS,
        permission.CALL_PHONE,
        permission.READ_CALL_LOG,
        permission.WRITE_CALL_LOG,
        permission.ADD_VOICEMAIL,
        permission.USE_SIP,
        permission.PROCESS_OUTGOING_CALLS,
        permission.ANSWER_PHONE_CALLS
    )

    private val GROUP_PHONE_BELOW_O = arrayOf(
        permission.READ_PHONE_STATE,
        permission.CALL_PHONE,
        permission.READ_CALL_LOG,
        permission.WRITE_CALL_LOG,
        permission.ADD_VOICEMAIL,
        permission.USE_SIP,
        permission.PROCESS_OUTGOING_CALLS
    )

    @RequiresApi(Build.VERSION_CODES.KITKAT_WATCH)
    private val GROUP_SENSORS = arrayOf(
        permission.BODY_SENSORS
    )
    private val GROUP_SMS = arrayOf(
        permission.SEND_SMS, permission.RECEIVE_SMS, permission.READ_SMS,
        permission.RECEIVE_WAP_PUSH, permission.RECEIVE_MMS
    )
    private val GROUP_STORAGE = arrayOf(
        permission.READ_EXTERNAL_STORAGE, permission.WRITE_EXTERNAL_STORAGE
    )

    @RequiresApi(Build.VERSION_CODES.Q)
    private val GROUP_ACTIVITY_RECOGNITION = arrayOf(
        permission.ACTIVITY_RECOGNITION
    )

    @StringDef(
        CALENDAR, CAMERA, CONTACTS, LOCATION, MICROPHONE, PHONE, SENSORS, SMS, STORAGE,
        ACTIVITY_RECOGNITION, NOTIFICATIONS
    )
    @kotlin.annotation.Retention(AnnotationRetention.SOURCE)
    annotation class PermissionGroup

    @JvmStatic
    fun getPermissions(@PermissionGroup permission: String): Array<String> {
        return when (permission) {
            CALENDAR -> GROUP_CALENDAR
            CAMERA -> GROUP_CAMERA
            CONTACTS -> GROUP_CONTACTS
            LOCATION ->
                if (AndroidVersion.isAndroid10())
                    GROUP_LOCATION
                else
                    GROUP_LOCATION_BELOW_Q
            MICROPHONE -> GROUP_MICROPHONE
            PHONE ->
                if (Build.VERSION.SDK_INT < Build.VERSION_CODES.O)
                    GROUP_PHONE_BELOW_O
                else
                    GROUP_PHONE

            SENSORS ->
                if (AndroidVersion.isAndroid4_4_w())
                    GROUP_SENSORS
                else
                    arrayOf(permission)
            SMS -> GROUP_SMS
            STORAGE -> GROUP_STORAGE
            ACTIVITY_RECOGNITION ->
                if (AndroidVersion.isAndroid10())
                    GROUP_ACTIVITY_RECOGNITION
                else
                    arrayOf(permission)
            NOTIFICATIONS ->
                if (AndroidVersion.isAndroid13())
                    GROUP_NOTIFICATIONS
                else
                    arrayOf(permission)
            else -> arrayOf(permission)
        }
    }

}
