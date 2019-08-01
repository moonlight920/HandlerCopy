package android.support.v4.app

import android.content.Context
import android.view.View


open class Fragment {
    var mHost: FragmentHostCallback? = null

    fun getContext(): Context? {
        return mHost?.getContext()
    }

    fun getActivity(): FragmentActivity? {
        return mHost?.getActivity()
    }


    open fun onViewCreated(view: View) {}

}