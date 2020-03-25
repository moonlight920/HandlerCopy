package android.os

import android.os.IBinder
import android.server.am.ActivityManagerService

/**
 * 管理着系统的所有Service
 */
object ServiceManager {
    fun getService(name: String): IBinder? {
        return when (name) {

            "activity" -> {
                ActivityManagerService.getInstance()
            }
            else -> null
        }
    }
}