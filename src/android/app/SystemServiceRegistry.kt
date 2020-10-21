package android.app

import android.binder.IActivityManager
import android.content.Context
import android.os.ServiceManager
import android.view.WindowManager
import android.view.WindowManagerImpl

object SystemServiceRegistry {
    private val systemServiceNameMap: HashMap<Class<*>, String> = HashMap()
    private val systemServiceMap: HashMap<String, ServiceFetcher<*>> = HashMap()

    init {
        registerService(Context.ACTIVITY_SERVICE,
                ActivityManager::class.java, object : ServiceFetcher<ActivityManager> {
            override fun getService(ctx: ContextImpl): ActivityManager {

                // 获取到AMS
                val iBinder = ServiceManager.getService(Context.ACTIVITY_SERVICE)

                val activityManager = IActivityManager.asInterface(iBinder!!)
                return ActivityManager(activityManager)
            }
        })

        registerService(Context.WINDOW_SERVICE,
                WindowManager::class.java, object : ServiceFetcher<WindowManager> {
            var cache: WindowManager? = null
            override fun getService(ctx: ContextImpl): WindowManager {
                if (cache == null) {
                    cache = WindowManagerImpl(ctx)
                }
                return cache!!
            }
        })
    }

    fun getSystemService(ctx: ContextImpl, name: String): Any? {
        val fetcher: ServiceFetcher<*>? = systemServiceMap[name]
        return fetcher?.getService(ctx)
    }

    fun getSystemServiceName(cls: Class<*>) = systemServiceNameMap[cls]

    private inline fun <reified T> registerService(
            name: String, cls: Class<T>, fetcher: ServiceFetcher<T>
    ) {
        systemServiceNameMap[cls] = name
        systemServiceMap[name] = fetcher
    }

    interface ServiceFetcher<T> {
        fun getService(ctx: ContextImpl): T
    }
}