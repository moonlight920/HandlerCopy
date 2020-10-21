package android.view

import android.os.Handler
import android.os.Looper
import android.os.Message

private const val CALLBACK_INPUT = 0
// 动画
private const val CALLBACK_ANIMATION = 1
private const val CALLBACK_INSETS_ANIMATION = 2
// measure、layout、draw
private const val CALLBACK_TRAVERSAL = 3

// 渲染下一帧
private const val MSG_DO_FRAME = 0
// 请求VSync
private const val MSG_DO_SCHEDULE_VSYNC = 1
// 请求执行callback
private const val MSG_DO_SCHEDULE_CALLBACK = 2


class Choreographer(looper: Looper) {

    // 动态的
    private val mFrameIntervalNanos = 16_666_666L
    private val mHandler = FrameHandler(looper)

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
     */
    private fun doFrame(frameTimeNanos: Long) {

        val startNanos = System.nanoTime()
        val jitterNanos = startNanos - frameTimeNanos
        if (jitterNanos >= mFrameIntervalNanos) {
            // 跳帧
            val skippedFrames = jitterNanos / mFrameIntervalNanos
            println("Skipped $skippedFrames frames!  The application may be doing too much work on its main thread.")
        }
        doCallback(CALLBACK_INPUT, frameTimeNanos)
        doCallback(CALLBACK_ANIMATION, frameTimeNanos)
        doCallback(CALLBACK_INSETS_ANIMATION, frameTimeNanos)
        doCallback(CALLBACK_TRAVERSAL, frameTimeNanos)
    }

    private fun doCallback(callbackType: Int, frameTimeNanos: Long) {

    }

    /**
     * 由Massage的callback携带此类，执行run
     */
    inner class FrameDisplayEventReceiver : Runnable {
        private var mTimestampNanos: Long = 0
        /**
         * native调用，由SurfaceFlinger间接调用
         * @param timestampNanos 时间戳 纳秒
         */
        fun onVSync(timestampNanos: Long) {

            mTimestampNanos = timestampNanos

            val msg = Message(mHandler)
            msg.callback = this
            mHandler.sendMessageDelay(msg, timestampNanos / 1000000)
        }

        override fun run() {
            doFrame(mTimestampNanos)
        }
    }
}