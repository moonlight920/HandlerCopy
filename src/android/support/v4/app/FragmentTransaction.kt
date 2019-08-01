package android.support.v4.app

abstract class FragmentTransaction {

    abstract fun add(fragment: Fragment): FragmentTransaction
    abstract fun commit()
}