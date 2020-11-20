package android.view

abstract class ViewGroup : View(), ViewParent, ViewManager {

    override fun invalidateChild(child: View) {
        println("ViewGroup invalidateChild")
        onDescendantInvalidated(child, child)
    }

    override fun addView(view: View) {
        view.setParent(this)
    }

    override fun removeView(view: View) {
        if (view.getParent() == this){
            view.setParent(null)
        }
    }
}