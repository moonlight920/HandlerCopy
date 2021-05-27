package android

import android.app.Activity
import android.app.ContextImpl
import android.os.Looper
import android.server.am.Token
import android.view.View
import android.widget.FrameLayout


class ViewActivity : Activity() {

    class CustomView : View()

    override fun onCreate() {
        super.onCreate()
        val frameLayout = FrameLayout()
        frameLayout.addView(CustomView())
        setContentView(frameLayout)

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