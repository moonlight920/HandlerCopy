package android.support.v4.app

import android.app.Activity
import android.content.Intent


open class FragmentActivity : Activity() {

    private val mFragments = FragmentController.createController(HostCallbacks())

    override fun onCreate() {
        mFragments.attachHost()
        super.onCreate()
    }

    fun getSupportFragmentManager(): FragmentManager {
        return this.mFragments.getSupportFragmentManager()
    }

    inner class HostCallbacks : FragmentHostCallback(this) {
        override fun onStartActivityFromFragment(fragment: Fragment, intent: Intent) {
            this@FragmentActivity.startActivityFromFragment(fragment, intent)
        }
    }


    fun startActivityFromFragment(fragment: Fragment, intent: Intent) {
        // balabala
    }
}