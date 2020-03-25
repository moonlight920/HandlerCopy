# AndroidCopy

> 对一些Android中不易理解的设计进行仿写，方便理解

如何深刻的掌握知识，不是学习很多次，而是去实践它。

为了弄懂Handler的机制和流程，脱离Android SDK，在Idea上实现了一下。

## 入口函数

在ActivityThread的main方法中

```java
public class HandlerCopy {

    public static void main(String[] args) {
        //1. 创建当前线程
        Looper.Companion.prepare();

        Handler handler = new Handler();

        // 因为主线程阻塞中，使用其他线程模拟发消息
        new OtherThread(handler).start();

        Looper.Companion.loop();
    }
}
```

创建Looper，loop内部是一个while(true)循环，所以主线程一直卡在这里，不会终止。

```kotlin
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

        when {
            msg.arg1 != null -> println("message arg1:${msg.arg1}")
            msg.arg2 != null -> println("message arg2:${msg.arg2}")
        }
    }
}
```

### 那么为什么应用不会卡死呢？

相反，正是这里的无限循环，才保证了Android应用更好的活下去。

对于线程即是一段可执行的代码，当可执行代码执行完成后，线程生命周期便该终止了，线程退出。而对于主线程肯定不能运行一段时间后就自动结束了，那么如何保证一直存活呢？？简单的做法就是可执行代码能一直执行下去，死循环便能保证不会被退出。

我们所有的UI操作，都会post到这个线程来执行，执行完成之后继续阻塞。

> 主线程的死循环一直运行会不会特别消耗CPU资源呢？其实不然这里就涉及到Linux pipe/epoll机制，简单说就是在主线程的MessageQueue没有消息时，便阻塞在loop的queue.next()中的nativePollOnce()方法里，此时主线程会释放CPU资源进入休眠状态，直到下个消息到达或者有事务发生，通过往pipe管道写端写入数据来唤醒主线程工作。这里采用的epoll机制，是一种IO多路复用机制，可以同时监控多个描述符，当某个描述符就绪(读或写就绪)，则立刻通知相应程序进行读或写操作，本质同步I/O，即读写是阻塞的。 所以说，主线程大多数时候都是处于休眠状态，并不会消耗大量CPU资源。

## 模拟post操作，向主线程发消息

实际上所有的UI操作，最终都会通过post消息给Handler的方式去执行，这样才能保证UI的有序执行

```java
/**
 * 模拟一个其他线程，向主线程发消息
 */
static class OtherThread extends Thread {
    private Handler mHandler;
    private int count = 0;
    public OtherThread(Handler handler) {
        mHandler = handler;
    }
    @Override
    public void run() {
        while (true) {
            try {
                Thread.sleep(1000);
                count++;
                if (count % 5 == 0) {
                    Message msg = new Message();
                    msg.setArg2("change UI immediately");
                    mHandler.sendMessage(msg);
                    Message delayMsg = new Message();
                    delayMsg.setArg2("change UI dealy 2500");
                    mHandler.sendMessageDelay(delayMsg, 2500);
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
```

所有发送的消息，都会封装成Message，形成一个链式队列

```kotlin
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
```

每条消息都可以通过next来获取到下一条消息，所以无需维护一个数组或list，这种链表高性能的实现了需要。

Looper会一直从MessageQueue中拿到存在的Message去执行，否则就保持阻塞状态。
