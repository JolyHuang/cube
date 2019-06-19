package com.sharingif.cube.batch.core.handler;

import com.sharingif.cube.batch.core.request.JobRequest;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

public class MultithreadDispatcherHandler extends SimpleDispatcherHandler {

    private ThreadPoolTaskExecutor multithreadDispatcherHandlerThreadPoolTaskExecutor;
    private SimpleDispatcherHandler simpleDispatcherHandler;

    public void setMultithreadDispatcherHandlerThreadPoolTaskExecutor(ThreadPoolTaskExecutor multithreadDispatcherHandlerThreadPoolTaskExecutor) {
        this.multithreadDispatcherHandlerThreadPoolTaskExecutor = multithreadDispatcherHandlerThreadPoolTaskExecutor;
    }
    public void setSimpleDispatcherHandler(SimpleDispatcherHandler simpleDispatcherHandler) {
        this.simpleDispatcherHandler = simpleDispatcherHandler;
    }

    @Override
    public void doDispatch(JobRequest request) {
        multithreadDispatcherHandlerThreadPoolTaskExecutor.execute(new Runnable() {
            @Override
            public void run() {
                simpleDispatcherHandler.doDispatch(request);
            }
        });
    }
}
