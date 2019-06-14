class Looper {
    val msgQueue = MessageQueue()

    /**
     * 获取和准备 当前线程的looper
     */
    companion object {

        /**
         * 保证每个线程有唯一的Looper
         */
        private val sThreadLocal = ThreadLocal<Looper>()

        /**
         * 开始执行循环
         */
        fun loop() {
            val looper = myLooper()
            looper ?: throw RuntimeException("This looper is not prepare")

            // 开始死循环
            while (true) {
                val msg = looper.msgQueue.next() // 阻塞
                msg ?: return

                println("拿到消息:${msg.arg1}")
            }
        }

        /**
         * 为当前线程准备Looper
         */
        fun prepare() {
            if (sThreadLocal.get() != null) {
                throw RuntimeException("only one created looper")
            }
            sThreadLocal.set(Looper())
        }

        /**
         * 获取当前线程的Looper，需要先调用prepare
         */
        fun myLooper(): Looper? {
            return sThreadLocal.get()
        }
    }
}