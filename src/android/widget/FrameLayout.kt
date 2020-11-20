package android.widget

import android.view.ViewGroup

class FrameLayout : ViewGroup() {

    override fun onLayout() {
        childViewList.forEach {
            it.layout()
        }
    }

}