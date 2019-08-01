package android.support.v4.app

class FragmentController(callbacks: FragmentHostCallback) {


    /**
     * 这个host的职责是关心fragment的生命周期
     */
    private val mHost = callbacks

    fun attachHost() {
        mHost.mFragmentManager.attachController(mHost)
    }

    fun getSupportFragmentManager(): FragmentManager {
        return this.mHost.getFragmentManagerImpl()
    }

    companion object {
        fun createController(callbacks: FragmentHostCallback): FragmentController {
            return FragmentController(callbacks)
        }
    }

}