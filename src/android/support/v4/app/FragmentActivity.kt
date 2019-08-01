package android.support.v4.app

import android.app.Activity
import android.content.Intent


/**
 * ## 模拟实现Android系统的FragmentActivity
 *
 * - 实现fragment的事务系统
 * - 实现FragmentActivity和Fragment之间的Context传递
 */
open class FragmentActivity : Activity() {

    /**
     * ## Fragment的控制器，随着FragmentActivity的创建可创建
     *
     * - 内部持有外部类Activity的引用，通过HostCallbacks传参进去；
     * 每个FragmentActivity对应一个HostCallbacks，并且HostCallbacks持有Activity的引用
     *
     * - Fragment的所有操作几乎都直接或间接通过这个HostCallbacks来完成
     */
    private val mFragments = FragmentController.createController(HostCallbacks())


    override fun onCreate() {
        mFragments.attachHost()
        super.onCreate()
    }

    /**
     * [FragmentManager]这个类在[HostCallbacks]的父类[FragmentHostCallback]中初始化<br>
     * 即每个[FragmentActivity]对应一个[HostCallbacks]对应一个[FragmentManager]
     * <br>
     * [FragmentManager]是一个抽象类，具体实现类是[FragmentManagerImpl]
     */
    fun getSupportFragmentManager(): FragmentManager {
        return this.mFragments.getSupportFragmentManager()
    }


    inner class HostCallbacks : FragmentHostCallback(this) {
        override fun onStartActivityFromFragment(fragment: Fragment, intent: Intent) {
            this@FragmentActivity.startActivityFromFragment(fragment, intent)
        }
    }


    /**
     * 此方法代表一系列只存在于[FragmentActivity]类中的，在[Activity]基础上的扩展方法
     */
    fun startActivityFromFragment(fragment: Fragment, intent: Intent) {
        // balabala
    }
}