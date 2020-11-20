package android.view

import android.graphics.Canvas

abstract class ViewGroup : View(), ViewParent, ViewManager {

    protected val childViewList = arrayListOf<View>()

    override fun invalidateChild(child: View) {
        println("ViewGroup invalidateChild")
        onDescendantInvalidated(child, child)
    }

    override fun addView(view: View) {
        view.setParent(this)
        childViewList.add(view)
    }

    override fun removeView(view: View) {
        if (view.getParent() == this) {
            view.setParent(null)
            childViewList.remove(view)
        }
    }

    override fun dispatchDraw(canvas: Canvas) {
        super.dispatchDraw(canvas)
        // 调用子view的draw
        childViewList.forEach {
            it.draw(canvas)
        }
    }

    abstract override fun onLayout()
}