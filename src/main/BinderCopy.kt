package android

import android.app.Activity
import android.app.ContextImpl
import android.app.ActivityManager
import android.server.am.Token


class TestBinderActivity : Activity() {

    override fun onCreate() {

        super.onCreate()

        val name = getSystemServiceName(ActivityManager::class.java)
        if (name != null) {
            val service = getSystemService(name) as? ActivityManager
            service?.xxx("hello AMS")
        }
    }

    companion object {
        /**
         * 模拟activity 请求 AMS
         */
        @JvmStatic
        fun main(args: Array<String>) {
            val activity = TestBinderActivity()
            val activityToken = Token()
            activity.attach(ContextImpl(), activityToken)
            activity.onCreate()
        }
    }
}