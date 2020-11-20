package android

import android.app.Activity
import android.app.ContextImpl
import android.os.Looper
import android.server.am.Token
import android.view.View


class ViewActivity : Activity() {

    private val mView by lazy { View() }

    override fun onCreate() {
        super.onCreate()
        setContentView(mView)

    }

    override fun onResume() {
        super.onResume()
    }

    companion object {
        /**
         * 模拟activity 请求 AMS
         */
        @JvmStatic
        fun main(args: Array<String>) {
            Looper.prepare()

            val activity = ViewActivity()
            val activityToken = Token()
            activity.attach(ContextImpl(), activityToken)

            Looper.loop()
        }
    }
}