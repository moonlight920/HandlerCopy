package android.content

abstract class BroadcastReceiver {

    /**
     * 通过系统服务发送的广播，最终到这里
     * [context.ContextImpl.sendBroadcast]
     */
    abstract fun onReceive(context: Context, intent: Intent)
}