package android.view

class ViewRootImpl {

    private var mView: View? = null

    fun draw() {
        val isHard = false

        if (!isHard) {
            drawSoftware()
        }

    }

    private fun drawSoftware() {
//        surface.lockCanvas()
//        surface.unlockCanvasAndPost()
    }

    public fun setView(view: View) {
        if (mView == null) {
            mView = view
        }
    }
}