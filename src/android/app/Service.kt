package android.app

import android.content.Context
import android.content.ContextWrapper
import android.content.res.Resources

/**
 * Android 4大组件之Service
 */
abstract class Service : ContextWrapper(null) {

    /**
     * 实例化之后，调用此函数为Context赋值
     */
    fun attach(context: Context) {
        attachBaseContext(context)
    }

    override fun getResources(): Resources {
        return super.getResources().wrapperWith("Service")
    }

    /**
     * 在此进行扩展自己的功能
     */
    abstract fun onBind()
}