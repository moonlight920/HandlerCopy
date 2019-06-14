public class HandlerCopy {

    public static void main(String[] args) {
        Looper.Companion.prepare();

        Handler handler = new Handler();

        // 因为主线程阻塞中，使用其他线程模拟发消息
        new Thread(() -> {
            Message msg = new Message();
            msg.setArg1(666);
            handler.sendMessageDelay(msg, 5000);

            Message msg1 = new Message();
            msg1.setArg1(777);
            handler.sendMessageDelay(msg1, 8000);

            Message msg2 = new Message();
            msg2.setArg1(888);
            handler.sendMessageDelay(msg2, 11000);
        }).start();

        Looper.Companion.loop();
    }
}
