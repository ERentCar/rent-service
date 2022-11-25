package com.rentCar.rentService.Repositories;

import com.rentCar.rentService.Entities.Rent;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface RentRepository extends JpaRepository<Rent,Long> {
    @Query(value="select R.*from rents as R left join reservations RV " +
            "on R.reservation_id=RV.id where RV.client_id=?1 group by R.id,R.pay_date,R.status,R.reservation_id; ", nativeQuery = true)
    List<Rent> getRentsByClientId(Long clientId);

    @Query(value="select R.*from rents as R left join reservations RV " +
            "on R.reservation_id=RV.id where RV.owner_id=?1 group by R.id,R.pay_date,R.status,R.reservation_id; ", nativeQuery = true)
    List<Rent> getRentsByOwnerId(Long ownerId);
}
