package android.view

import android.content.Context

class WindowManagerImpl(private val mContext: Context, private val parentWindow: Window? = null) : WindowManager {

    fun createLocalWindowManager(parentWindow: Window) = WindowManagerImpl(mContext, parentWindow)

    override fun addView(view: View) {
        WindowManagerGlobal.addView(view, parentWindow)
    }

    override fun removeView(view: View) {

    }
}