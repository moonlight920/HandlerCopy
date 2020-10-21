package android.view

/**
 * 全局单例
 */
object WindowManagerGlobal {

    /**
     * 缓存所有window中的decorView
     */
    private val mViews = arrayListOf<View>()
    /**
     * 缓存所有ViewRootImpl
     */
    private val mRoots = arrayListOf<ViewRootImpl>()

    /**
     * 添加window
     *
     * @param view window中的DecorView
     */
    fun addView(view: View, parentWindow: Window?) {
        val root = ViewRootImpl()

        mViews.add(view)
        mRoots.add(root)

        root.setView(view)

    }
}