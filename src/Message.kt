class Message(var target: Handler? = null) {
    var arg1 = 0

    /**
     * 这个消息需要在什么时间处理
     */
    var time: Long = 0

    /**
     * 链表结构，这个message后面跟的message
     */
    var next: Message? = null
}