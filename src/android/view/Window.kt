package android.view

import android.content.Context
import android.os.IBinder

abstract class Window(private val mContext: Context) {

    private var mWindowManager: WindowManager? = null
    private var mAppToken: IBinder? = null

    /**
     * 每个window有自己的windowManager，可能token一致
     */
    fun setWindowManager(wm: WindowManager, appToken: IBinder) {
        mAppToken = appToken


        val wmi = mContext.getSystemService(Context.WINDOW_SERVICE) as WindowManagerImpl
        mWindowManager = wmi.createLocalWindowManager(this)
    }

    fun getWindowManager(): WindowManager? {
        return mWindowManager
    }

    abstract fun getDecorView(): View
    abstract fun setContentView(view: View)
}

class PhoneWindow(context: Context) : Window(context) {

    init {
        println("PhoneWindow init")
    }

    private val mDecor = DecorView()

    override fun getDecorView(): View {
        return mDecor
    }

    override fun setContentView(view: View) {
        mDecor.addView(view)
    }
}
