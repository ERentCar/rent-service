package com.rentCar.rentService.Services;

import com.rentCar.rentService.Entities.Reservation;
import com.rentCar.rentService.Resource.CarRentResource;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface ReservationService {
    List<Reservation> getAll();
    Reservation getById(Long reservationId);
    Reservation create (Double costCar,Long carId,Long clientId,Long ownerId,Reservation reservation);
    ResponseEntity<?> delete(Long reservationId);
    Reservation acceptReservation(Long reservationId);
    Reservation declineReservation(Long reservationId);
    Reservation cancelReservation(Long reservationId);
    List<Reservation> getReservationsByOwner(Long ownerId);
    List<Reservation> getReservationsByClient(Long clientId);
    List<CarRentResource>lista();
}
