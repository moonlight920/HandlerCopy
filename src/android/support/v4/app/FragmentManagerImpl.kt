package android.support.v4.app

class FragmentManagerImpl : FragmentManager() {

    override fun beginTransaction(): FragmentTransaction {
        return BackStackRecord()
    }

    var mHost: FragmentHostCallback? = null


    fun attachController(host: FragmentHostCallback) {
        mHost = host
    }

    fun onCreateView() {
        val fragment = Fragment()
        fragment.mHost = mHost
    }
}