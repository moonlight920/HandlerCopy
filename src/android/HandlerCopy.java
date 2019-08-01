package android;

import android.os.Handler;
import android.os.Looper;
import android.os.Message;

public class HandlerCopy {

    public static void main(String[] args) {
        //1. 创建当前线程
        Looper.Companion.prepare();

        Handler handler = new Handler();

        // 因为主线程阻塞中，使用其他线程模拟发消息
        new OtherThread(handler).start();

        Looper.Companion.loop();
    }

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
}
