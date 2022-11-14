package com.rentCar.rentService.Resource;

import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
public class RentResource {
    private Long id;
    private Date payDate;
    private ReservationResource reservation;
    private int status;
}
