package android.view

import android.content.Context
import android.content.ContextWrapper
import android.content.res.Resources

/**
 * 对ContextWrapper的扩展，包含了主题的功能
 * <br>
 * 同时具备所有Context的功能，默认直接调用实现类
 * <br>
 * 很方便的重写原有功能和增加新功能
 */
open class ContextThemeWrapper(base: Context? = null) : ContextWrapper(base) {

    private var mTheme: Resources.Theme? = null

    constructor(base: Context, theme: Resources.Theme) : this(base) {
        mTheme = theme
    }

    /**
     * 扩展原有功能
     */
    override fun getResources(): Resources {

        val resources = super.getResources()
        resources.TAG = "ContextThemeWrapper[${resources.TAG}]"
        return resources
    }

    /**
     * 增加新功能
     */
    fun getTheme(): Resources.Theme? {
        return mTheme
    }
}