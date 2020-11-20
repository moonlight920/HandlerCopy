package android.server.am

import android.binder.IActivityManager
import android.os.IBinder

class ActivityManagerService private constructor() : IActivityManager.Stub() {

    override fun xxx(params: String) {
        println("来自AMS xxx $params")
    }

    override fun asBinder(): IBinder {
        return this
    }

    companion object {
        private var INSTANCE: ActivityManagerService? = null
        fun getInstance(): ActivityManagerService {
            return if (INSTANCE != null) {
                INSTANCE!!
            } else {
                INSTANCE = ActivityManagerService()
                return INSTANCE!!
            }
        }
    }
}