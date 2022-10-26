package com.rentCar.rentService.Controllers;

import com.rentCar.rentService.Mapping.ReservationMapper;
import com.rentCar.rentService.Resource.CreateReservationResource;
import com.rentCar.rentService.Resource.ReservationResource;
import com.rentCar.rentService.Services.ReservationService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/api/v1/reservations")
public class ReservationController {
    private final ReservationService reservationService;
    private final ReservationMapper mapper;

    public ReservationController(ReservationService reservationService, ReservationMapper mapper) {
        this.reservationService = reservationService;
        this.mapper = mapper;
    }
    @GetMapping
    public Page<ReservationResource> getAllReservations(Pageable pageable){
        return mapper.modelListToPage(reservationService.getAll(),pageable);
    }

    @GetMapping("{reservationId}")
    public ReservationResource getReservationById(@PathVariable Long reservationId){
        return  mapper.toResource(reservationService.getById(reservationId));
    }

    @PostMapping("costCar/{costCar}/car/{carId}/client/{clientId}/owner/{ownerId}")
    public ReservationResource createReservation(@PathVariable("costCar")Double costCar,
                                                 @PathVariable("carId")Long carId,
                                                 @PathVariable("clientId")Long clientId,
                                                 @PathVariable("ownerId")Long ownerId,
                                                 @Valid @RequestBody CreateReservationResource request){
        return mapper.toResource(reservationService.create(costCar,carId,clientId,ownerId,mapper.toModel(request)));
    }

    @DeleteMapping("{reservationId}")
    public ResponseEntity<?> deleteReservation(@PathVariable Long reservationId) {
        return reservationService.delete(reservationId);
    }

    @PutMapping("accept/{reservationId}")
    public ReservationResource acceptReservation(@PathVariable("reservationId")Long reservationId)
    {
        return mapper.toResource(reservationService.acceptReservation(reservationId));
    }

    @PutMapping("decline/{reservationId}")
    public ReservationResource declineReservation(@PathVariable("reservationId")Long reservationId)
    {
        return mapper.toResource(reservationService.declineReservation(reservationId));
    }

    @PutMapping("cancel/{reservationId}")
    public ReservationResource cancelReservation(@PathVariable("reservationId")Long reservationId)
    {
        return mapper.toResource(reservationService.cancelReservation(reservationId));
    }

    @GetMapping("client/{clientId}")
    public Page<ReservationResource> getReservationsByClient(Pageable pageable,
                                                             @PathVariable("clientId") Long clientId){
        return mapper.modelListToPage(reservationService.getReservationsByClient(clientId),pageable);
    }
    @GetMapping("owner/{ownerId}")
    public Page<ReservationResource> getReservationsByOwner(Pageable pageable,
                                                            @PathVariable("ownerId") Long ownerId){
        return mapper.modelListToPage(reservationService.getReservationsByOwner(ownerId),pageable);
    }
}
