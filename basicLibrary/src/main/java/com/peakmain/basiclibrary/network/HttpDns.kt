package com.peakmain.basiclibrary.network

import com.qiniu.android.dns.DnsManager
import com.qiniu.android.dns.IResolver
import com.qiniu.android.dns.NetworkInfo
import com.qiniu.android.dns.local.Resolver
import okhttp3.Dns
import java.net.InetAddress
import java.net.InterfaceAddress
import java.net.UnknownHostException
import java.util.*
import kotlin.collections.ArrayList

/**
 * author ：Peakmain
 * createTime：2023/06/15
 * mail:2726449200@qq.com
 * describe：
 */
class HttpDns : Dns {
    private val mDnsManager: DnsManager

    init {
        val resolver = InetAddress.getByName("119.29.29.29")
        val resolvers = arrayOf(Resolver(resolver))
        mDnsManager = DnsManager(NetworkInfo.normal, resolvers)
    }

    override fun lookup(hostname: String): MutableList<InetAddress> {
        val ips = mDnsManager.query(hostname)
        if (ips == null || ips.isEmpty()) return Dns.SYSTEM.lookup(hostname)
        val result = ArrayList<InetAddress>()
        for (ip in ips) {
            try {
                result.add(InetAddress.getByName(ip))
            } catch (e: UnknownHostException) {
                e.printStackTrace()
            }
        }
        return result
    }
}