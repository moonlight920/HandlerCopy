package android.content

import android.content.res.Resources


/**
 * Context接口类，定义基本功能
 */
abstract class Context {
    companion object {
        const val WINDOW_SERVICE = "window"
        const val ACTIVITY_SERVICE = "activity"
    }
    /**
     * 获取app包中的资源
     */
    abstract fun getResources(): Resources

    /**
     * 发送广播
     */
    abstract fun sendBroadcast(intent: Intent)

    /**
     * 动态注册广播
     */
    abstract fun registerReceiver(receiver: BroadcastReceiver): Intent

    /**
     * 启动一个Activity
     */
    abstract fun startActivity(intent: Intent)



    abstract fun getSystemService(name: String): Any?
    abstract fun getSystemServiceName(serviceClass: Class<*>): String?
}