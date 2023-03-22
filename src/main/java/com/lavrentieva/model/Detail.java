package com.lavrentieva.model;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.text.SimpleDateFormat;
import java.util.Date;

@Entity
@Table(name = "details")
@Getter
@Setter
public class Detail {
    @Id
    private String id;

    @Temporal(TemporalType.TIMESTAMP)
    private Date date;

    @Column(name = "remaining_fuel")
    private int fuel;

    @Column(name = "spent_fuel")
    private int spentFuel;

    @Column(name = "spent_time")
    private long spentTime;

    @Column(name = "broken_microchips")
    private int brokenMicrochips;

    public Detail() {

    }

    @Override
    public String toString() {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        return String.format("Detail {id: %s, production date: %s, remaining fuel: %s, " +
                        "spent fuel: %s, spent time: %s sec., broken microchips: %s}", id,
                simpleDateFormat.format(date), fuel, spentFuel, spentTime, brokenMicrochips);
    }
}
