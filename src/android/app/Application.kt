package android.app

import android.content.ContextWrapper
import android.content.res.Resources

/**
 * Application内没有theme相关功能，直接继承ContextWrapper
 */
class Application : ContextWrapper(null) {

    /**
     * 对方法进行扩展
     */
    override fun getResources(): Resources {
        val resources = super.getResources()
        resources.TAG = "Application[${resources.TAG}]"
        return resources
    }
}