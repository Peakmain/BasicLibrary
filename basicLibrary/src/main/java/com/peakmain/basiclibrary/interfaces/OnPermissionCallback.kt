package com.peakmain.basiclibrary.interfaces

/**
 * author ：Peakmain
 * createTime：2022/08/11
 * mail:2726449200@qq.com
 * describe：
 */
interface OnPermissionCallback {
    /**
     * 权限被授予时回调
     * @param permissions 权限组
     */
    fun onGranted(permissions: Array<String>)

    /**
     * 权限被拒绝的时候回调
     * @param permissions 权限组
     * @param never 权限是否被永久拒绝
     */
    fun onDenied(permissions: Array<String>, never: Boolean)
}