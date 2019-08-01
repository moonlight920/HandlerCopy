package android.support.v4.app

import android.content.Context
import android.content.Intent

abstract class FragmentHostCallback(fragmentActivity: FragmentActivity) {

    private val mContext = fragmentActivity

    internal val mFragmentManager: FragmentManagerImpl = FragmentManagerImpl()

    fun getContext(): Context = mContext

    fun getActivity(): FragmentActivity = mContext

    fun getFragmentManagerImpl(): FragmentManagerImpl {
        return this.mFragmentManager
    }

    protected open fun onStartActivityFromFragment(fragment: Fragment, intent: Intent) {

    }
}