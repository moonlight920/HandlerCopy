package android.app

import android.os.Handler
import android.os.IBinder
import android.os.Message

private const val CREATE = 0
private const val RESUME = 1

class ActivityThread {

    private val mActivities = hashMapOf<IBinder, Activity>()

    inner class H : Handler() {
        override fun handleMessage(msg: Message) {
            super.handleMessage(msg)
            when (msg.what) {
                CREATE -> {
                    val pair = msg.obj as? Pair<*, *>
                    val iBinder = pair?.first as? IBinder
                    val activity = pair?.second as? Activity
                    if (iBinder != null && activity != null) {
                        mActivities[iBinder] = activity
                    }

                }
                RESUME -> {
                    val iBinder = msg.obj as? IBinder
                    if (iBinder != null) {
                        val activity = mActivities[iBinder]!!

                        val decorView = activity.getWindow().getDecorView()
                        activity.getWindowManager().addView(decorView)
                    }
                }
            }

        }
    }
}