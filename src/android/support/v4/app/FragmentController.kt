package android.support.v4.app

class FragmentController(callbacks: FragmentHostCallback) {

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