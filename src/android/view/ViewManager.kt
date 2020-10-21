package android.view

interface ViewManager {
    fun addView(view: View)
    fun updateViewLayout(view: View)
    fun removeView(view: View)
}