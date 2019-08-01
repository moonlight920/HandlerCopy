package android.app

import android.content.Intent
import android.content.res.Resources
import android.content.BroadcastReceiver
import android.content.Context

/**
 * 具体Context的实现类，所有包装类默认使用此类的实现方式
 * <br>
 * 也可以在包装类中对实现进行扩展或者完全重新实现
 */
class ContextImpl : Context() {

    override fun getResources(): Resources {
        return Resources().wrapperWith("ContextImpl")
    }

    override fun registerReceiver(receiver: BroadcastReceiver): Intent {
        // val intent = ActivityManager.getService().registerReceiver()
        // 实际是调用系统服务注册广播，并返回
        return Intent()
    }

    override fun sendBroadcast(intent: Intent) {
        // 调用系统服务发送广播
        //  ActivityManager.getService().broadcastIntent(intent)
    }

    override fun startActivity(intent: Intent) {
        // 调用系统服务开启Activity
//        mMainThread.getInstrumentation().execStartActivity(
//            getOuterContext(), mMainThread.getApplicationThread(), null,
//            (Activity) null, intent, -1, options);
    }

    companion object {
        /**
         * 根据一些参数，创建一个Activity需要的context
         */
        @JvmStatic
        fun createActivityContext(xxx: Any): ContextImpl {
            return ContextImpl()
        }
    }
}