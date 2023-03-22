package com.lavrentieva.model;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@ToString
@Getter
@Setter
public class StatsDTO {
    private Long totalAmountOfDetails;
    private Long totalAmountOfBrokenMicrochips;
    private Long totalExtractedFuel;

    public StatsDTO(Long totalAmountOfDetails, Long totalAmountOfBrokenMicrochips,
                    Long totalExtractedFuel) {
        this.totalAmountOfDetails = totalAmountOfDetails;
        this.totalAmountOfBrokenMicrochips = totalAmountOfBrokenMicrochips;
        this.totalExtractedFuel = totalExtractedFuel;
    }
}
