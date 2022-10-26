package com.rentCar.rentService.Repositories;

import com.rentCar.rentService.Entities.Reservation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface ReservationRepository extends JpaRepository<Reservation,Long> {
    @Query(value="select * from reservations where owner_id=?1 ", nativeQuery = true)
    List<Reservation> getReservationsByOwner(Long ownerId);
    @Query(value="select * from reservations where client_id=?1 ", nativeQuery = true)
    List<Reservation> getReservationsByClient(Long clientId);
}
