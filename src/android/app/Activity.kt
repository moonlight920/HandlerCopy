package android.app

import android.content.Context
import android.content.res.Resources
import android.view.ContextThemeWrapper

/**
 * Android 4大组件之Activity
 */
open class Activity : ContextThemeWrapper() {

    /**
     * 实例化之后，调用此函数为Context赋值
     */
    fun attach(context: Context) {
        attachBaseContext(context)
    }

    override fun getResources(): Resources {
        return super.getResources().wrapperWith("Activity")
    }

    protected open fun onCreate() {
        // balabala
    }


}