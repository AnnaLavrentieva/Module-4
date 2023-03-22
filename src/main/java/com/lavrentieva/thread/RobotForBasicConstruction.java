package com.lavrentieva.thread;

import lombok.SneakyThrows;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.Random;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

public class RobotForBasicConstruction extends Thread {

    private final int timeForReloading = 2;
    private volatile AtomicInteger workProgress;
    private final CountDownLatch latch;
    private final Random random = new Random();
    private final static Logger LOGGER = LogManager.getLogger(RobotForBasicConstruction.class);

    public RobotForBasicConstruction(final CountDownLatch latch, final AtomicInteger workProgress) {
        this.latch = latch;
        this.workProgress = workProgress;
    }

    @SneakyThrows
    @Override
    public void run() {
        while (workProgress.get() < 100) {
            LOGGER.info("{} starts working", Thread.currentThread().getName());
            final int number = random.nextInt(10, 21);
            workProgress.addAndGet(number);
            LOGGER.info("{} work progress: {}, work done: {}", Thread.currentThread().getName(),
                    number, workProgress);
            LOGGER.info("{} reloads within {} seconds", Thread.currentThread().getName(),
                    timeForReloading);
            TimeUnit.SECONDS.sleep(timeForReloading);
        }
        latch.countDown();
        LOGGER.info("{} has finished working", Thread.currentThread().getName());
    }
}












