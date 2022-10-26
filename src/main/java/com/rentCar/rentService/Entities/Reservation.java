package com.rentCar.rentService.Entities;

import lombok.*;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.Date;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@With
@Entity
@Table(name = "reservations")
public class Reservation implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
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
}
