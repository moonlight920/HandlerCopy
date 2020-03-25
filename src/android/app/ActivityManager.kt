package android.app

import android.binder.IActivityManager

/**
 * 可以理解为 对AMS的封装
 */
class ActivityManager(private val realActivityManager: IActivityManager) {
    fun xxx(params:String) {
        realActivityManager.xxx(params)
    }
}