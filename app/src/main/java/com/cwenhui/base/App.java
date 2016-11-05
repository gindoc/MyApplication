package com.cwenhui.base;

import android.app.Application;
import android.content.Intent;
import android.os.StrictMode;

import com.cwenhui.dagger.component.ApplicationComponent;
import com.cwenhui.dagger.component.DaggerApplicationComponent;
import com.cwenhui.dagger.module.ApplicationModule;
import com.cwenhui.data.DataManager;
import com.cwenhui.module.main.MainActivity;
import com.cwenhui.utils.ComponentHolder;
import com.cwenhui.utils.LogUtil;
import com.cwenhui.utils.Saver;
import com.squareup.leakcanary.LeakCanary;

import javax.inject.Inject;

import static android.os.Build.VERSION.SDK_INT;
import static android.os.Build.VERSION_CODES.GINGERBREAD;

/**
 * Created by cwenhui on 2016/11/5.
 */
public class App extends Application {
    private ApplicationComponent mComponent;
    @Inject
    DataManager dataManager;

    @Override
    public void onCreate() {
        super.onCreate();

        //内存泄漏分析
        if (LeakCanary.isInAnalyzerProcess(this)) {
            // This process is dedicated to LeakCanary for heap analysis.
            // You should not init your app in this process.
            return;
        }

//        enabledStrictMode();

        LeakCanary.install(this);
        Saver.initSaver(this);

        getApplicationComponent().inject(this);

        //替换系统默认异常处理Handler
//        Thread.setDefaultUncaughtExceptionHandler(new MyUnCaughtExceptionHandler());
    }

    public ApplicationComponent getApplicationComponent() {
        if (mComponent == null) {
            mComponent = DaggerApplicationComponent.builder()
                    .applicationModule(new ApplicationModule(this))
//                    .dataManagerModule()
                    .build();
        }
        ComponentHolder.setAppComponent(mComponent);
        return mComponent;
    }

    private void enabledStrictMode() {
        if (SDK_INT >= GINGERBREAD) {
            StrictMode.setThreadPolicy(new StrictMode.ThreadPolicy.Builder() //
                    .detectAll() //
                    .penaltyLog() //
                    .penaltyDeath() //
                    .build());
        }
    }

    class MyUnCaughtExceptionHandler implements Thread.UncaughtExceptionHandler {
        @Override
        public void uncaughtException(Thread thread, Throwable ex) {
            ex.printStackTrace();
            LogUtil.e(ex.getMessage());
            if (dataManager == null) {
                LogUtil.e("dataManager is null!!!!!!!!!!");
            }
            // 当APP闪退时，所做的一些处理
            LogUtil.e("send error msg to server!!!");


            //重启app
//            restartApp();
        }

        private void restartApp() {
            Intent intent = new Intent(App.this, MainActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            App.this.startActivity(intent);
            android.os.Process.killProcess(android.os.Process.myPid());
            System.exit(1);
        }
    }

}
