package com.rentCar.rentService.Services.impls;

import com.rentCar.rentService.Entities.Reservation;
import com.rentCar.rentService.Mapping.ReservationMapper;
import com.rentCar.rentService.Repositories.ReservationRepository;
import com.rentCar.rentService.Resource.CarRentResource;
import com.rentCar.rentService.Services.ReservationService;
import com.rentCar.rentService.Shared.Exception.ResourceNotFoundException;
import com.rentCar.rentService.Shared.Exception.ResourceValidationException;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import javax.validation.ConstraintViolation;
import javax.validation.Validator;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.Set;
import java.util.concurrent.TimeUnit;

@Service
public class ReservationServiceImpl implements ReservationService {
    private static final String ENTITY="RESERVATION";
    private final ReservationRepository reservationRepository;
    private final ApiCall apiCall;
    private final Validator validator;

    public ReservationServiceImpl(ReservationRepository reservationRepository,
                                  ApiCall apiCall, Validator validator) {
        this.reservationRepository = reservationRepository;
        this.apiCall = apiCall;
        this.validator = validator;
    }

    @Override
    public List<Reservation> getAll() {
        return reservationRepository.findAll();
    }

    @Override
    public Reservation getById(Long reservationId) {
        return reservationRepository.findById(reservationId)
                .orElseThrow(()->new ResourceNotFoundException(ENTITY,reservationId));
    }

    @Override
    public Reservation create(Double costCar,Long carId,Long clientId,Long ownerId,Reservation reservation) {
        Set<ConstraintViolation<Reservation>> violations=validator.validate(reservation);
        if(!violations.isEmpty()){
            throw new ResourceValidationException(ENTITY,violations);
        }
        Long duration=reservation.getReturnDate().getTime()-reservation.getReserveDate().getTime();
        Long daysDifference= TimeUnit.MILLISECONDS.toDays(duration);
        reservation.setAmount(costCar*(daysDifference+1));
        reservation.setStatus(0);
        reservation.setCarId(carId);
        reservation.setOwnerId(ownerId);
        reservation.setClientId(clientId);
        apiCall.setStateCar(carId,1);
        return reservationRepository.save(reservation);
    }

    @Override
    public ResponseEntity<?> delete(Long reservationId) {
        return reservationRepository.findById(reservationId).map(reservation -> {
            reservationRepository.delete(reservation);
            return ResponseEntity.ok().build();
        }).orElseThrow(() -> new ResourceNotFoundException(ENTITY, reservationId));
    }

    @Override
    public Reservation acceptReservation(Long reservationId) {
        Reservation reservation=reservationRepository.findById(reservationId).map(reservationAux->
                        reservationRepository.save(reservationAux.withStatus(1))).
                orElseThrow(()->new ResourceNotFoundException(ENTITY,reservationId));
        reservation.setStatus(1);
        return reservation;
    }

    @Override
    public Reservation declineReservation(Long reservationId) {
        Reservation reservation=reservationRepository.findById(reservationId).map(reservationAux->
                        reservationRepository.save(reservationAux.withStatus(2))).
                orElseThrow(()->new ResourceNotFoundException(ENTITY,reservationId));
        reservation.setStatus(2);
        apiCall.setStateCar(reservation.getCarId(),0);
        return reservation;
    }

    @Override
    public Reservation cancelReservation(Long reservationId) {
        Reservation reservation=reservationRepository.findById(reservationId).map(reservationAux->
                        reservationRepository.save(reservationAux.withStatus(3))).
                orElseThrow(()->new ResourceNotFoundException(ENTITY,reservationId));
        reservation.setStatus(3);
        apiCall.setStateCar(reservation.getCarId(),0);
        return reservation;
    }

    @Override
    public List<Reservation> getReservationsByOwner(Long ownerId) {
        List<Reservation> reservationList=reservationRepository.getReservationsByOwner(ownerId);
        List<CarRentResource> carRentResourceList=apiCall.getCars();

        for(Reservation reservations:reservationList){
            for(CarRentResource resource:carRentResourceList){
                if(reservations.getCarId().equals(resource.getId())){
                    reservations.setCarRentResource(resource);
                }
            }
        }

        return reservationList;
    }

    @Override
    public List<Reservation> getReservationsByClient(Long clientId) {
        List<Reservation> reservationList=reservationRepository.getReservationsByClient(clientId);
        List<CarRentResource> carRentResourceList=apiCall.getCars();
        for(Reservation reservations:reservationList){
            for(CarRentResource resource:carRentResourceList){
                if(reservations.getCarId().equals(resource.getId())){
                    reservations.setCarRentResource(resource);
                }
            }
        }
        return reservationList;
    }

    @Override
    public List<CarRentResource> lista() {
        List<CarRentResource> prueba=apiCall.getCars();

        return prueba;
    }
}
