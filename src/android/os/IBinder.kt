package android.os

interface IBinder {
    fun onTransact(msg: Any)
    fun transact(msg: Any)
}