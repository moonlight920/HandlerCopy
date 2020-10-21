package android.app

import android.content.Context
import android.content.res.Resources
import android.os.IBinder
import android.view.ContextThemeWrapper
import android.view.PhoneWindow
import android.view.Window
import android.view.WindowManager

/**
 * Android 4大组件之Activity
 */
open class Activity : ContextThemeWrapper() {

    private var mWindow: Window? = null
    private var mWindowManager: WindowManager? = null
    private var mToken: IBinder? = null
    /**
     * 实例化之后，调用此函数为Context赋值
     */
    fun attach(context: Context, token: IBinder) {
        attachBaseContext(context)

        mWindow = PhoneWindow(context)
        mToken = token
        mWindow?.setWindowManager(context.getSystemService(WINDOW_SERVICE) as WindowManager, token)
        mWindowManager = mWindow?.getWindowManager()
    }

    fun getWindowManager() = mWindowManager!!
    fun getWindow() = mWindow!!

    override fun getResources(): Resources {
        return super.getResources().wrapperWith("Activity")
    }

    protected open fun onCreate() {
        // balabala
    }


}