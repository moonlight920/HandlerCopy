package android.app

import android.binder.IActivityManager
import android.os.ServiceManager

object SystemServiceRegistry {
    val systemServiceNameMap: HashMap<Class<*>, String> = HashMap()
    val systemServiceMap: HashMap<String, ServiceFetcher<*>> = HashMap()

    init {
        registerService(
            "activity",
            ActivityManager::class.java,
            object : ServiceFetcher<ActivityManager> {
                override fun getService(ctx: ContextImpl?): ActivityManager {

                    // 获取到AMS
                    val iBinder = ServiceManager.getService("activity")

                    val activityManager = IActivityManager.asInterface(iBinder!!)
                    return ActivityManager(activityManager)
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
        fun getService(ctx: ContextImpl?): T
    }
}