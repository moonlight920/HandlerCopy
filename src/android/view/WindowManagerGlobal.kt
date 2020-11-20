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
     * 添加window，每个window构造一个ViewRootImpl，作为Decor的parent
     *
     * @param view window中的DecorView
     */
    fun addView(view: View, parentWindow: Window?) {
        val root = ViewRootImpl()
        // 将decor的parent设置为ViewRootImpl
        println("DecorView绑定ViewRootImpl")
        view.setParent(root)

        mViews.add(view)
        mRoots.add(root)

        root.setView(view)

    }
}