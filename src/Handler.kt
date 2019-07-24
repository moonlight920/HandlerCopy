class Handler(looper: Looper? = null) {
    // handler的looper，构造时创建
    private val mLooper: Looper? = looper ?: Looper.myLooper()

    private val mMsgQueue: MessageQueue = mLooper!!.msgQueue

    /**
     * @time 此消息被执行的时间
     */
    fun sendMessage(msg: Message) {
        msg.target = this
        // 入队
        mMsgQueue.enqueueMsg(msg, SystemClock.uptimeMillis())
    }

    /**
     * @time 此消息被执行的时间
     */
    fun sendMessageDelay(msg: Message, delay: Long) {
        msg.target = this
        // 入队
        mMsgQueue.enqueueMsg(msg, SystemClock.uptimeMillis() + delay)
    }
}