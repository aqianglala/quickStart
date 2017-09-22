package com.example.mynewbaseapp.modules.test;


import com.example.mynewbaseapp.R;
import com.example.mynewbaseapp.base.BaseActivity;
import com.example.mynewbaseapp.utils.LocationUtils;
import com.tencent.bugly.crashreport.CrashReport;
import com.tencent.stat.StatConfig;
import com.tencent.stat.StatService;

import butterknife.OnClick;

public class TestActivity extends BaseActivity<TestPresenter> implements TestContract.View {

    @OnClick(R.id.btn_test_bugly)
    void testBugly() {
        CrashReport.testJavaCrash();
    }

    @OnClick(R.id.btn_test_baidu_location)
    void testBaiduLocation() {
        new LocationUtils().getLocation();
    }

    @OnClick(R.id.btn_test_mta)
    void testMta() {
        // 请在初始化时调用，参数为Application或Activity或Service
        StatService.setContext(this);
        // 初始化MTA配置
        initMTAConfig(true);
        // 注册Activity生命周期监控，自动统计时长
        StatService.registerActivityLifecycleCallbacks(getApplication());
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_test;
    }

    @Override
    protected TestPresenter loadPresenter() {
        return new TestPresenter();
    }

    @Override
    protected void initInjector() {

    }

    @Override
    protected void initView() {

    }

    @Override
    protected void setListener() {

    }

    @Override
    protected void doBusiness() {
        mPresenter.getLaunchImage();
    }

    /**
     * 根据不同的模式，建议设置的开关状态，可根据实际情况调整，仅供参考。
     *
     * @param isDebugMode 根据调试或发布条件，配置对应的MTA配置
     */
    private void initMTAConfig(boolean isDebugMode) {

        if (isDebugMode) { // 调试时建议设置的开关状态
            // 查看MTA日志及上报数据内容
            StatConfig.setDebugEnable(true);
            // StatConfig.setEnableSmartReporting(false);
            // Thread.setDefaultUncaughtExceptionHandler(new
            // UncaughtExceptionHandler() {
            //
            // @Override
            // public void uncaughtException(Thread thread, Throwable ex) {
            // logger.error("setDefaultUncaughtExceptionHandler");
            // }
            // });
            // 调试时，使用实时发送
            // StatConfig.setStatSendStrategy(StatReportStrategy.BATCH);
            // // 是否按顺序上报
            // StatConfig.setReportEventsByOrder(false);
            // // 缓存在内存的buffer日志数量,达到这个数量时会被写入db
            // StatConfig.setNumEventsCachedInMemory(30);
            // // 缓存在内存的buffer定期写入的周期
            // StatConfig.setFlushDBSpaceMS(10 * 1000);
            // // 如果用户退出后台，记得调用以下接口，将buffer写入db
            // StatService.flushDataToDB(getApplicationContext());

            // StatConfig.setEnableSmartReporting(false);
            // StatConfig.setSendPeriodMinutes(1);
            // StatConfig.setStatSendStrategy(StatReportStrategy.PERIOD);
        } else { // 发布时，建议设置的开关状态，请确保以下开关是否设置合理
            // 禁止MTA打印日志
            StatConfig.setDebugEnable(false);
            // 根据情况，决定是否开启MTA对app未处理异常的捕获
//            StatConfig.setAutoExceptionCaught(true);
            // 选择默认的上报策略
//            StatConfig.setStatSendStrategy(StatReportStrategy.PERIOD);
            // 10分钟上报一次的周期
//            StatConfig.setSendPeriodMinutes(10);
        }

//        // 初始化java crash捕获
//        StatCrashReporter.getStatCrashReporter(getApplicationContext()).setJavaCrashHandlerStatus(true);
//        // 初始化native crash捕获，记得复制so文件
//        StatCrashReporter.getStatCrashReporter(getApplicationContext()).setJniNativeCrashStatus(true);
//        // crash的回调，请根据需要添加
//        StatCrashReporter.getStatCrashReporter(getApplicationContext()).addCrashCallback(new StatCrashCallback() {
//
//            @Override
//            public void onJniNativeCrash(String tombstoneMsg) {
//                Log.d("Test", "Native crash happened, tombstone message:" +tombstoneMsg);
//            }
//
//            @Override
//            public void onJavaCrash(Thread thread, Throwable throwable) {
//                Log.d("Test", "Java crash happened, thread: " + thread + ",Throwable:" +throwable.toString());
//            }
//        });

    }
}
