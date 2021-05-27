package main;

import android.os.Handler;
import android.os.Looper;
import android.os.Message;

public class HandlerCopy {

    public static void main(String[] args) {
        //1. 创建当前线程
        Looper.Companion.prepare();

        Handler handler = new MyHandler();

        // 因为主线程阻塞中，使用其他线程模拟发消息
        new OtherThread(handler).start();

        Looper.Companion.loop();
    }

    static class MyHandler extends Handler {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            if (msg.getArg2() != null) {
                System.out.println("message arg2:" + msg.getArg2());
            }
        }
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
                        System.out.println("发消息 immediately");
                        mHandler.sendMessage(msg);

                        Message delayMsg = new Message();
                        System.out.println("发消息 dealy 2500");
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
