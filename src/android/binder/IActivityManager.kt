package android.binder

import android.os.Binder
import android.os.IBinder
import android.os.IInterface

interface IActivityManager : IInterface {

    fun xxx(params: String)

    abstract class Stub : Binder(), IActivityManager {

        override fun onTransact(msg: Any) {
            xxx(msg.toString())
        }
    }

    companion object {
        fun asInterface(obj: IBinder): IActivityManager {
            return Proxy(obj)
        }
    }

    private class Proxy(private val mRemote: IBinder) : IActivityManager {
        override fun xxx(params: String) {
            mRemote.transact(params)
        }

        override fun asBinder(): IBinder {
            return mRemote
        }

    }
}