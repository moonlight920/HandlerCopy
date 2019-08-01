package android.content

import android.content.res.Resources

/**
 * Context包装类，所有需要使用到Context功能的，都需要间接或直接继承此类
 *
 * 所有方法默认调用Context的实现类[context.ContextImpl]
 */
open class ContextWrapper(base: Context?) : Context() {

    /**
     * 被装饰的对象，其他装饰类可以对此对象进行扩展
     */
    private var mBase = base


    override fun getResources(): Resources {
        return mBase!!.getResources().wrapperWith("ContextWrapper")
    }

    override fun registerReceiver(receiver: BroadcastReceiver): Intent {
        return mBase!!.registerReceiver(receiver)
    }

    override fun sendBroadcast(intent: Intent) {
        mBase!!.sendBroadcast(intent)
    }

    /**
     * 如果构造中未传入Context，子类要调用此方法对mBase进行赋值
     */
    protected fun attachBaseContext(base: Context) {
        if (mBase != null) {
            throw IllegalStateException("Base context already set")
        }
        mBase = base
    }

    override fun startActivity(intent: Intent) {
        mBase!!.startActivity(intent)
    }
}