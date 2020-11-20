package android.os

class MessageQueue {
    /**
     * 当前队列的第一条消息
     */
    var mFirstMsg: Message? = null

    /**
     * 获取队列中的第一个消息
     */
    fun next(): Message? {
        while (true) {
            // 1. block 开始阻塞线程..........模拟线程阻塞，实际调用了native方法
            Thread.sleep(16)
//            println("查看队列中的message......")
            // 2. 有消息进入队列被唤醒
            // 3. 取出消息，判断时间，返回，否则继续阻塞

            val now = Util.uptimeMillis()

            val msg = mFirstMsg
            if (msg != null && msg.time < now) {
                // 消费掉，当前指针指向下一个message
                mFirstMsg = msg.next
                return msg
            }
        }
    }

    /**
     * 插入一个消息进队列
     */
    fun enqueueMsg(msg: Message, `when`: Long) {
        msg.time = `when`
        var p = mFirstMsg
        // 入队的这条消息，变为第一条消息
        if (p == null || `when` < p.time) {
            msg.next = p
            mFirstMsg = msg
        } else {
            var prev: Message
            // prev 表示在链表中的前一条消息，开始循环
            while (true) {
                prev = p!!
                p = prev.next
                // 这条消息是空的 或者 时间在我们入队这条消息的后面，跳出循环，插入队列
                if (p == null || `when` < p.time)
                    break
            }
            // 找到队列中合适的位置，插队
            msg.next = p
            prev.next = msg
        }
    }
}


