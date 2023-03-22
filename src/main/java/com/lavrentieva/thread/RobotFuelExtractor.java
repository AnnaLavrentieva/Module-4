package com.lavrentieva.thread;

import lombok.SneakyThrows;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.Random;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;

public class RobotFuelExtractor extends Thread {

    private final int timeForTransportation = 3;
    private volatile AtomicInteger fuel;
    private final Lock lock;
    private final Condition fuelAvailable;
    private final Random random = new Random();
    private final static Logger LOGGER = LogManager.getLogger(RobotFuelExtractor.class);

    public RobotFuelExtractor(final AtomicInteger extractedFuel, final Lock lock,
                              final Condition fuelAvailable) {
        this.fuel = extractedFuel;
        this.lock = lock;
        this.fuelAvailable = fuelAvailable;
    }

    @SneakyThrows
    @Override
    public void run() {
        while (!Thread.currentThread().isInterrupted()) {
            LOGGER.info("{} starts working", Thread.currentThread().getName());
            try {
                lock.lock();
                final int number = random.nextInt(500, 701);
                fuel.addAndGet(number);
                LOGGER.info("{} extracts {} gallons of fuel, total fuel: {}",
                        Thread.currentThread().getName(), number, fuel.get());
                fuelAvailable.signalAll();
            } finally {
                lock.unlock();
            }
            LOGGER.info("{} transports within {} seconds", Thread.currentThread().getName(),
                    timeForTransportation);
            try {
                TimeUnit.SECONDS.sleep(timeForTransportation);
            } catch (InterruptedException e) {
                LOGGER.info("{} has been interrupted", Thread.currentThread().getName());
                Thread.currentThread().interrupt();
            }
        }
        LOGGER.info("{} has finished working", Thread.currentThread().getName());
    }
}
