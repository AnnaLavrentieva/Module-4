package com.lavrentieva.thread;

import lombok.SneakyThrows;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.Random;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;

public class RobotForFormationReadyDetail extends Thread {

    private final int timeForReloading = 1;
    private volatile AtomicInteger fuel;
    private final Lock lock;
    private final Condition fuelAvailable;
    private volatile AtomicInteger spentFuel;
    private volatile AtomicInteger workProgress = new AtomicInteger(0);
    private final Random random = new Random();
    private final static Logger LOGGER = LogManager.getLogger(RobotForFormationReadyDetail.class);

    public RobotForFormationReadyDetail(AtomicInteger fuel, AtomicInteger spentFuel,
                                        Lock lock, Condition fuelAvailable) {
        this.fuel = fuel;
        this.spentFuel = spentFuel;
        this.lock = lock;
        this.fuelAvailable = fuelAvailable;
    }

    @SneakyThrows
    @Override
    public void run() {
        while (workProgress.get() < 100) {
            LOGGER.info("{} starts working", Thread.currentThread().getName());
            lock.lock();
            final int number = random.nextInt(350, 701);
            while (fuel.get() < number) {
                LOGGER.info("{} is waiting for fuel: {}", Thread.currentThread().getName(),
                        number);
                fuelAvailable.await();
            }
            LOGGER.info("{} continues working, uses {} fuel", Thread.currentThread().getName(),
                    number);
            fuel.addAndGet(-number);
            lock.unlock();
            spentFuel.addAndGet(number);
            workProgress.addAndGet(10);
            LOGGER.info("{} work progress: {}", Thread.currentThread().getName(), workProgress);
            LOGGER.info("{} reloads within {} seconds", Thread.currentThread().getName(),
                    timeForReloading);
            TimeUnit.SECONDS.sleep(timeForReloading);
        }
        LOGGER.info("{} has finished working", Thread.currentThread().getName());
    }
}
