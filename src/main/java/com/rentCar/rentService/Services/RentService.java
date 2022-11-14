package com.rentCar.rentService.Services;

import com.rentCar.rentService.Entities.Rent;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface RentService {
    List<Rent> getAll();
    Page<Rent> getAll(Pageable pageable);
    Rent getById(Long rentId);
    Rent create(Long reservationId);
    Rent endRent(Long rentId);
    List<Rent> getRentsByClientId(Long clientId);
    List<Rent> getRentsByOwnerId(Long ownerId);
}
