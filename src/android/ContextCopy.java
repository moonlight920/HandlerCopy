package android;

import android.app.Activity;
import android.app.ContextImpl;
import android.content.BroadcastReceiver;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;

public class ContextCopy {
    public static void main(String[] args) {

        /**
         * 装饰模式：
         *
         * 持有最原始的实现类，实际调用时结果则被重新装饰
         */

        // 1.创建类
        MyActivity myActivity = new MyActivity();

        // 2.传入一个基本的实现对象
        Context contextImpl = new ContextImpl();
        System.out.println("原始实现：" + contextImpl.getResources().getTAG());
        myActivity.attach(contextImpl);

        // 3.调用函数，输出的是包装之后的结果
        System.out.println("装饰后Activity的实现：" + myActivity.getResources().getTAG());


        // 其他装饰类的实现
        MyService myService = new MyService();
        myService.attach(contextImpl);
        System.out.println("装饰后Service的实现：" + myService.getResources().getTAG());


        // 有一个新的需求，功能完全要自己实现
        ResourcesActivity resourcesActivity = new ResourcesActivity();
        resourcesActivity.attach(contextImpl);
        System.out.println("装饰后ResourcesActivity的实现：" + resourcesActivity.getResources().getTAG());


    }

    /**
     * 我们自己的Activity
     */
    static class MyActivity extends Activity {

        MyBroadcastReceiver mReceiver = new MyBroadcastReceiver();

        @Override
        protected void onCreate() {
            super.onCreate();
            // 注册一个广播
            registerReceiver(mReceiver);
        }

        class MyBroadcastReceiver extends BroadcastReceiver {

            @Override
            public void onReceive(Context context, Intent intent) {

            }
        }
    }

    static class ResourcesActivity extends Activity {

        /**
         * 完全重写，不调用父类的实现即可
         */
        @Override
        public Resources getResources() {
            return new Resources().wrapperWith("完全自己实现的");
        }
    }


    static class MyService extends Service {

        @Override
        public void onBind() {

        }
    }
}
