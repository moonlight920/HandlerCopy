package android.os


open class Binder : IBinder {
    override fun onTransact(msg: Any) {

    }

    override fun transact(msg: Any) {
        // 模拟native binder通信。。。
        onTransact(msg)
    }
}