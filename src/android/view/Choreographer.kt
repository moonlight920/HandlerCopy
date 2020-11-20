package android.view

import Util
import android.os.Handler
import android.os.Looper
import android.os.Message
import java.util.concurrent.Executors

// 渲染下一帧
private const val MSG_DO_FRAME = 0
// 请求VSync
private const val MSG_DO_SCHEDULE_VSYNC = 1
// 请求执行callback
private const val MSG_DO_SCHEDULE_CALLBACK = 2

/**
 * 接收和发送VSync信号
 * 协调 动画、布局、绘制 的顺序
 *
 * 使用ThreadLocal线程内单例
 */
class Choreographer(looper: Looper) {

    companion object {
        const val CALLBACK_INPUT = 0
        // 动画
        const val CALLBACK_ANIMATION = 1
        const val CALLBACK_INSETS_ANIMATION = 2
        // measure、layout、draw
        const val CALLBACK_TRAVERSAL = 3
        const val CALLBACK_COMMIT = 4
        private const val CALLBACK_LAST = CALLBACK_COMMIT + 1

        /**
         * callback token，标记由应用发送的callback都携带此token
         */
        val FRAME_CALLBACK_TOKEN = object : Any() {
            override fun toString(): String {
                return "FRAME_CALLBACK_TOKEN"
            }
        }

        /**
         * 线程单例
         */
        private val sInstance: ThreadLocal<Choreographer> = object : ThreadLocal<Choreographer>() {
            override fun initialValue(): Choreographer {
                val looper = Looper.myLooper() ?: throw IllegalStateException("The current thread must have a looper!")
                return Choreographer(looper)
            }
        }

        fun getInstance(): Choreographer {
            return sInstance.get()
        }
    }

    // 动态的
    private val mFrameIntervalNanos = 16_666_666L
    private val mHandler = FrameHandler(looper)

    /**
     * 初始化队列列表，每个类型的callback 插入同一队列
     */
    private val mCallbackQueues = Array(CALLBACK_LAST + 1) {
        CallbackQueue()
    }

    private val mFrameDisplayEventReceiver = FrameDisplayEventReceiver(looper)

    private inner class FrameHandler(looper: Looper?) : Handler(looper) {

        override fun handleMessage(msg: Message) {
            when (msg.what) {
                MSG_DO_FRAME -> doFrame(System.nanoTime())
                MSG_DO_SCHEDULE_VSYNC -> {
                    // 向SurfaceFlinger请求Vsync信号
//                    doScheduleVsync()
                }
                MSG_DO_SCHEDULE_CALLBACK -> {
//                    doScheduleCallback(msg.arg1)
                }
            }
        }
    }

    /**
     * 绘制 当前帧
     * 同步调用整个视图树的measure，layout，draw
     */
    private fun doFrame(frameTimeNanos: Long) {

        val startNanos = System.nanoTime()
        val jitterNanos = startNanos - frameTimeNanos
        if (jitterNanos >= mFrameIntervalNanos) {
            // 跳帧
//            val skippedFrames = jitterNanos / mFrameIntervalNanos
//            println("Skipped $skippedFrames frames!  The application may be doing too much work on its main thread.")
        }
        doCallback(CALLBACK_INPUT, frameTimeNanos)
        doCallback(CALLBACK_ANIMATION, frameTimeNanos)
        doCallback(CALLBACK_INSETS_ANIMATION, frameTimeNanos)
        doCallback(CALLBACK_TRAVERSAL, frameTimeNanos)
        doCallback(CALLBACK_COMMIT, frameTimeNanos)
    }

    private fun doCallback(callbackType: Int, frameTimeNanos: Long) {
        val now = Util.uptimeMillis()
        val callbacks = mCallbackQueues[callbackType].extractDueCallbacksLocked(now)
        var current = callbacks
        while (current != null) {
            current.run(frameTimeNanos)
            current = current.next
        }
    }

    fun postCallback(callbackType: Int, action: Runnable, token: Any? = null, delayMillis: Long = 0) {

        val now = Util.uptimeMillis()
        val dueTime = now + delayMillis

        mCallbackQueues[callbackType].addCallbackLocked(dueTime, action, token)

        if (dueTime <= now) {
            // 先判断时间，如果要现在执行，立刻请求VSync
            scheduleVsyncLocked()
        } else {
            // 延迟操作，延迟调用
            println("delay postCallback")
        }
    }

    /**
     * 同步方法，去SurfaceFlinger请求VSync信号
     */
    private fun scheduleVsyncLocked() {
        println("请求Vsync")
        mFrameDisplayEventReceiver.scheduleVsync()
    }

    /**
     * 由Massage的callback携带此类，执行run
     */
    inner class FrameDisplayEventReceiver(looper: Looper) : Runnable {
        private var mTimestampNanos: Long = 0

        fun scheduleVsync() {
//            nativeScheduleVsync()

            Executors.newSingleThreadExecutor().submit {
                // 模拟下一帧，异步16ms后回调
                Thread.sleep(16)
                // 模拟native回调
                dispatchVsync(Util.uptimeMillis(), 0, 0)
            }
        }

        /**
         * native方法，请求一个VSync信号
         */
        private external fun nativeScheduleVsync()

        /**
         * 由native方法回调回来
         */
        private fun dispatchVsync(timestampNanos: Long, physicalDisplayId: Long, frame: Int) {
            println("回调onVsync")
            onVsync(timestampNanos)
        }

        /**
         * native调用，由SurfaceFlinger间接调用
         * @param timestampNanos 时间戳 应是纳秒，这里用毫秒代替
         */
        fun onVsync(timestampNanos: Long) {

            val now = Util.uptimeMillis()
            mTimestampNanos = if (timestampNanos > now) {
                now
            } else {
                timestampNanos
            }

            // 发送时间，在指定时间执行run方法
            val msg = Message(mHandler)
            msg.callback = this
            mHandler.sendMessageAtTime(msg, mTimestampNanos)
        }

        /**
         * 由onVsync发送事件触发
         */
        override fun run() {
            println("doFrame")
            doFrame(mTimestampNanos)
        }
    }

    /**
     * 应用可以实现此类，post后，会在下一帧时回调
     */
    interface FrameCallback {
        fun doFrame(frameTimeNanos: Long)
    }


    private class CallbackRecord(val dueTime: Long = 0, val action: Any? = null, val token: Any? = null) {
        var next: CallbackRecord? = null

        fun run(frameTimeNanos: Long) {
            if (token === FRAME_CALLBACK_TOKEN) {
                (action as FrameCallback).doFrame(frameTimeNanos)
            } else {
                (action as Runnable?)!!.run()
            }
        }
    }

    /**
     * 根据时间排序的callback队列
     */
    private inner class CallbackQueue {
        private var mHead: CallbackRecord? = null

        fun hasDueCallbacksLocked(now: Long): Boolean {
            return mHead != null && mHead!!.dueTime <= now
        }

        /**
         * 提取 现在需要执行的callback
         */
        fun extractDueCallbacksLocked(now: Long): CallbackRecord? {
            val callbacks: CallbackRecord? = mHead
            if (callbacks == null || callbacks.dueTime > now) {
                return null
            }
            var last: CallbackRecord = callbacks
            var next: CallbackRecord? = last.next
            while (next != null) {
                if (next.dueTime > now) {
                    last.next = null
                    break
                }
                last = next
                next = next.next
            }
            mHead = next
            return callbacks
        }

        fun addCallbackLocked(dueTime: Long, action: Any?, token: Any?) {
            val callback = CallbackRecord(dueTime, action, token)
            if (mHead == null) {
                mHead = callback
                return
            }
            var entry: CallbackRecord = mHead!!

            if (dueTime < entry.dueTime) {
                callback.next = entry
                mHead = callback
                return
            }
            while (entry.next != null) {
                if (dueTime < entry.next!!.dueTime) {
                    callback.next = entry.next
                    break
                }
                entry = entry.next!!
            }
            entry.next = callback
        }
    }
}