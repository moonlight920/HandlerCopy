package android.view

import android.graphics.Canvas
import android.graphics.RenderNode

open class View {

    protected var mParent: ViewParent? = null
    private val mRenderNode = RenderNode.create()


    fun measure() {
        println("View measure")
    }

    fun layout() {
        println("View layout")
    }

    fun draw(canvas: Canvas) {
        println("View draw")
    }

    /**
     * 实际源码中不存在的方法，为了运行起来而增加
     */
    fun setParent(parent: ViewParent?) {
        mParent = parent
    }

    fun getParent() = mParent

    fun invalidate() {
        println("view invalidate")
        mParent?.invalidateChild(this)
    }
}