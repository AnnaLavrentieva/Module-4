package com.lavrentieva.service;

import com.lavrentieva.model.Detail;
import com.lavrentieva.model.StatsDTO;
import com.lavrentieva.repository.DetailRepository;
import com.lavrentieva.thread.RobotForBasicConstruction;
import com.lavrentieva.thread.RobotForFormationReadyDetail;
import com.lavrentieva.thread.RobotFuelExtractor;
import com.lavrentieva.thread.RobotMicrochipProgrammer;
import lombok.SneakyThrows;
import lombok.ToString;

import java.util.Date;
import java.util.List;
import java.util.Objects;
import java.util.UUID;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

@ToString
public class DetailService {

    private final DetailRepository detailRepository = DetailRepository.getInstance();
    private final CountDownLatch latch = new CountDownLatch(2);
    private final Lock lock = new ReentrantLock();
    private final Condition fuelAvailable = lock.newCondition();
    private volatile AtomicInteger workProgress = new AtomicInteger(0);
    private volatile AtomicInteger fuel = new AtomicInteger(0);
    private volatile AtomicInteger spentFuel = new AtomicInteger(0);
    private volatile AtomicInteger brokenMicrochips = new AtomicInteger(0);

    @SneakyThrows
    public Detail create() {
        long startTime = System.currentTimeMillis();
        final RobotFuelExtractor robotOne = new RobotFuelExtractor(fuel, lock, fuelAvailable);
        robotOne.start();
        final List<RobotForBasicConstruction> robotsTwoAndThree = List.of(
                new RobotForBasicConstruction(latch, workProgress),
                new RobotForBasicConstruction(latch, workProgress)
        );
        robotsTwoAndThree.forEach(RobotForBasicConstruction::start);
        final Detail detail = new Detail();
        detail.setId(UUID.randomUUID().toString());
        final RobotMicrochipProgrammer robotFour = new RobotMicrochipProgrammer(brokenMicrochips, latch);
        robotFour.start();
        robotFour.join();
        detail.setBrokenMicrochips(brokenMicrochips.get());
        final RobotForFormationReadyDetail robotFive = new RobotForFormationReadyDetail(
                fuel, spentFuel, lock, fuelAvailable);
        robotFive.start();
        robotFive.join();
        detail.setDate(new Date());
        long endTime = System.currentTimeMillis();
        long spentTime = (endTime - startTime) / 1000;
        detail.setSpentTime(spentTime);
        robotOne.interrupt();
        detail.setFuel(fuel.get());
        detail.setSpentFuel(spentFuel.get());
        System.out.println(detail);
        detailRepository.save(detail);
        return detail;
    }

    public StatsDTO showStatistic() {
        return detailRepository.showStatistic();
    }

    public Detail getDetailById(String id) {
        return Objects.requireNonNull(detailRepository.getDetailById(id));
    }

    public List<String> getAllId() {
        return detailRepository.getAllId();
    }
}
