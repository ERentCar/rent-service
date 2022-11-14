package com.rentCar.rentService.Resource;

import lombok.Getter;
import lombok.Setter;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

@Getter
@Setter
public class ReservationResource {
    private Long id;
    @DateTimeFormat(pattern = "MM-dd-yyyy")
    private Date reserveDate;
    @DateTimeFormat(pattern = "MM-dd-yyyy")
    private Date returnDate;
    private Double amount;
    private int status;
    private Long carId;
    private Long clientId;
    private Long ownerId;
    private CarRentResource carRentResource;
}
