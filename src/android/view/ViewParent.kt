package android.view


interface ViewParent {
    fun requestLayout()

    /**
     * 重新绘制子view
     */
    fun invalidateChild(child: View)

    /**
     * 当一个子view调用invalidate()时，逐级调用parent的[onDescendantInvalidated]方法，最终到DecorView到ViewRootImpl中
     *
     * @param child Direct child of this ViewParent containing target
     * @param target The view that needs to redraw
     */
    fun onDescendantInvalidated(child: View, target: View) {
        // Note: should pass 'this' as default, but can't since we may not be a View
        getParent()?.onDescendantInvalidated(child, target)
    }

    fun getParent(): ViewParent?
}