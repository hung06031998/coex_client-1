package com.upit.coex.user.service.executor;

import android.os.Handler;
import android.os.Looper;
import android.os.Message;

import com.upit.coex.user.service.logger.L;

import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.TimeUnit;

import static com.upit.coex.user.constants.CommonConstants.COMMON_COEX_EXECUTOR_TAG;

public class CoexExecutor {

    private static StringBuilder mCoexThreadFactoryName = new StringBuilder("CoexExecutorThreadFactory - ");
    private static final ScheduledExecutorService sExecutor;
    private static final Handler sHandler;
    private static final int PRIORITY = Thread.NORM_PRIORITY;

    static {
        int numOfThread = Runtime.getRuntime().availableProcessors() - 1 < 1 ? 1 : Runtime.getRuntime().availableProcessors() - 1;

        sExecutor = Executors.newScheduledThreadPool(numOfThread, new ThreadFactory() {
            @Override
            public Thread newThread(Runnable r) {
                Thread thread = new Thread(r);
                thread.setPriority(PRIORITY);
                thread.setName(mCoexThreadFactoryName.append(thread.getId()).toString());
                return null;
            }
        });

        sHandler = new Handler(Looper.getMainLooper());
    }

    private CoexExecutor(){

    }

    private static CoexRunnableThrowExcept wrapRunnable(Runnable command) {
        return new CoexRunnableThrowExcept(command);
    }

    private static CoexHandlerThrowExcept wrapRunnableHandler(Runnable command) {
        return new CoexHandlerThrowExcept(command);
    }

    public static void shutdown() {
        List<Runnable> list = sExecutor.shutdownNow();
    }



    public static void executeAfterDelay(Runnable command,long timeDelay){
        sExecutor.schedule(command,timeDelay, TimeUnit.MILLISECONDS);
    }

    public static void executeThenPostOnMainThread(Runnable command){
        //sHandler.post(sExecutor.execute(wrapRunnableHandler(command)))
        sExecutor.execute(wrapRunnableHandler(command));
    }

    public static void executeThenPostOnMainThreadAfterDelay(Runnable command,long timeDelay){
        sHandler.postDelayed(command,timeDelay);
    }

    public static void execute(Runnable command){
        sExecutor.execute(wrapRunnable(command));
    }

    public static Future executeWithResult(Callable command){
        return sExecutor.submit(command);
    }

    public static ScheduledFuture executeWithResultAfterDelay(Callable command , long timeDelay){
        return sExecutor.schedule(command,timeDelay,TimeUnit.MILLISECONDS);
    }

    private static class CoexRunnableThrowExcept implements Runnable{

        private Runnable mCommand;

        private CoexRunnableThrowExcept(Runnable r){
            super();
            this.mCommand = r;
        }

        @Override
        public void run() {
            try{
                this.mCommand.run();
            } catch (Throwable e){
                L.e(COMMON_COEX_EXECUTOR_TAG,e,"thread name",Thread.currentThread().getName());
            }
        }
    }

    private static class CoexHandlerThrowExcept implements Runnable{

        private Runnable mCommand;

        private Handler mmHandler;

        private CoexHandlerThrowExcept(Runnable r){
            super();
            this.mCommand = r;
            this.mmHandler = new Handler(){
                @Override
                public void handleMessage(Message msg) {
                    super.handleMessage(msg);
                    mCommand.run();
                }
            };
        }

        @Override
        public void run() {
            try{
                this.mmHandler.sendEmptyMessage(0);
                //this.mmHandler.post(this);

            } catch (Throwable e){
                L.e(COMMON_COEX_EXECUTOR_TAG,e,"thread name",Thread.currentThread().getName());
            }
        }
    }

}
