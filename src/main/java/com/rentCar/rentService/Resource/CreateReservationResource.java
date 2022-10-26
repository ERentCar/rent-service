package com.rentCar.rentService.Resource;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotNull;
import java.util.Date;

@Getter
@Setter
public class CreateReservationResource {
    @NotNull
    private Date reserveDate;
    @NotNull
    private Date returnDate;

}
