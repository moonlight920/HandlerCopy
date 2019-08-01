package android.content

import android.content.res.Resources


/**
 * Context接口类，定义基本功能
 */
abstract class Context {
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
}