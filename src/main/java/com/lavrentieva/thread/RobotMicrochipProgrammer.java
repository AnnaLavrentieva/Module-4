package com.lavrentieva.thread;

import lombok.SneakyThrows;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.Random;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

public class RobotMicrochipProgrammer extends Thread {

    private final int timeForReloading = 1;
    private volatile AtomicInteger workProgress = new AtomicInteger(0);
    private final CountDownLatch latch;
    private volatile AtomicInteger brokenMicrochips;
    private final Random random = new Random();
    private final static Logger LOGGER = LogManager.getLogger(RobotMicrochipProgrammer.class);

    public RobotMicrochipProgrammer(AtomicInteger brokenMicrochips, CountDownLatch latch) {
        this.brokenMicrochips = brokenMicrochips;
        this.latch = latch;
    }

    @SneakyThrows
    @Override
    public void run() {
        latch.await(20, TimeUnit.SECONDS);
        while (workProgress.get() < 100) {
            LOGGER.info("{} starts working", Thread.currentThread().getName());
            final int number = random.nextInt(25, 36);
            workProgress.addAndGet(number);
            LOGGER.info("{} work progress: {}, work done: {}", Thread.currentThread().getName(),
                    number, workProgress);
            LOGGER.info("{} reloads within {} seconds", Thread.currentThread().getName(),
                    timeForReloading);
            if (workProgress.get() > 100) {
                break;
            }
            final double probability = random.nextDouble();
            if (probability <= 0.3) {
                LOGGER.info("{} broke the microchip, starts from the beginning",
                        Thread.currentThread().getName());
                workProgress.set(0);
                brokenMicrochips.addAndGet(1);
                continue;
            }
            TimeUnit.SECONDS.sleep(timeForReloading);
        }
        LOGGER.info("{} has finished working", Thread.currentThread().getName());
    }
}


