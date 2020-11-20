package android.view

/**
 * 每次添加Window创建一个
 *
 * 定位：
 * 1. 整个View树的驱动力
 *
 * 职责：
 *
 * 1. 和Choreographer直接交互，向其发送runnable，在下一帧时执行
 */
class ViewRootImpl : ViewParent {

    private var mLayoutRequested = false

    private val mChoreographer = Choreographer.getInstance()
    private var mView: View? = null

    private var mSurface = Surface()

    fun draw() {
        val isHard = false

        if (!isHard) {
            drawSoftware(mSurface)
        }

    }

    private fun drawSoftware(surface: Surface) {
        val canvas = surface.lockCanvas()
        mView?.draw(canvas)
        // 绘制完成
        surface.unLockCanvasAndPost(canvas)
    }

    fun setView(view: View) {
        if (mView == null) {
            mView = view
            requestLayout()
        }
    }

    /**
     * 告知 Choreographer 下一帧执行的命令
     */
    fun scheduleTraversals() {
        println("ViewRootImpl 通知 Choreographer，遍历")
        mChoreographer.postCallback(Choreographer.CALLBACK_TRAVERSAL, Runnable { performTraversals() })
    }

    /**
     * 826行源码
     *
     * 遍历整个视图树，测量，布局，绘制
     */
    private fun performTraversals() {
        println("ViewRootImpl performTraversals")
        val host = mView

        if (mLayoutRequested) {
            performMeasure()
            performLayout()
        }
        performDraw()
    }

    private fun performMeasure() {
        println("ViewRootImpl performMeasure")
        val host = mView
        host?.measure()
    }

    private fun performLayout() {
        println("ViewRootImpl performLayout")
        mLayoutRequested = false
        val host = mView
        host?.layout()
    }

    private fun performDraw() {
        println("ViewRootImpl performDraw")
        var animating = false
        draw()

        //...
        if (animating) {
            // 如果有动画， 再次遍历 刷新
            scheduleTraversals();
        }
    }

    override fun requestLayout() {
        mLayoutRequested = true
        scheduleTraversals()
    }

    override fun invalidateChild(child: View) {
        scheduleTraversals()
    }

    override fun getParent(): ViewParent? = null

    override fun onDescendantInvalidated(child: View, target: View) {
        invalidate()
    }

    private fun invalidate() {
        println("ViewRootImpl invalidate")
        scheduleTraversals()
    }
}