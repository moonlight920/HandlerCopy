package android.support.v4.app

class BackStackRecord : FragmentTransaction() {

    override fun add(fragment: Fragment): FragmentTransaction {
        // 这里将add事件保存在缓存中，等待commit
        return this
    }

    override fun commit() {
        // 处理缓存中的所有事件
    }

}