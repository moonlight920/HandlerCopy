package android.view

import android.graphics.Canvas
import android.graphics.RenderNode

open class View {

    protected var mParent: ViewParent? = null
    private val mRenderNode = RenderNode.create()


    open fun measure() {
        println("${this.javaClass.simpleName} measure")
        onMeasure()
    }

    open fun layout() {
        println("${this.javaClass.simpleName} layout")
        onLayout()
    }

    protected open fun onLayout() {

    }

    protected open fun onMeasure() {

    }

    open fun draw(canvas: Canvas) {
        println("${this.javaClass.simpleName} draw")
        onDraw(canvas)
        dispatchDraw(canvas);
    }

    protected open fun dispatchDraw(canvas: Canvas) {

    }


    open fun onDraw(canvas: Canvas) {

    }

    /**
     * 实际源码中不存在的方法，为了运行起来而增加
     */
    fun setParent(parent: ViewParent?) {
        mParent = parent
    }

    fun getParent() = mParent

    open fun requestLayout() {
        mParent?.requestLayout()
    }

    open fun invalidate() {
        println("view invalidate")
        mParent?.invalidateChild(this)
    }
}