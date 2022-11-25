package com.rentCar.rentService.Services.impls;

import com.rentCar.rentService.Entities.Rent;
import com.rentCar.rentService.Entities.Reservation;
import com.rentCar.rentService.Repositories.RentRepository;
import com.rentCar.rentService.Repositories.ReservationRepository;
import com.rentCar.rentService.Resource.CarRentResource;
import com.rentCar.rentService.Services.RentService;
import com.rentCar.rentService.Shared.Exception.ResourceNotFoundException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import javax.validation.Validator;
import java.util.Date;
import java.util.List;

@Service
public class RentServiceImpl implements RentService {
    private static final String ENTITY="Rent";
    private final RentRepository rentRepository;
    private final ReservationRepository reservationRepository;
    private final ApiCall apiCall;
    private final Validator validator;

    public RentServiceImpl(RentRepository rentRepository, ReservationRepository reservationRepository,
                           ApiCall apiCall, Validator validator) {
        this.rentRepository = rentRepository;
        this.reservationRepository = reservationRepository;
        this.apiCall = apiCall;
        this.validator = validator;
    }

    @Override
    public List<Rent> getAll() {
        return rentRepository.findAll();
    }

    @Override
    public Page<Rent> getAll(Pageable pageable) {
        return rentRepository.findAll(pageable);
    }

    @Override
    public Rent getById(Long rentId) {
        return rentRepository.findById(rentId)
                .orElseThrow(()->new ResourceNotFoundException(ENTITY,rentId));
    }

    @Override
    public Rent create(Long reservationId) {
        Rent rent=new Rent();
        Reservation reservation=reservationRepository.findById(reservationId)
                .orElseThrow(() -> new ResourceNotFoundException("Reservation", reservationId));

        Date date=new Date();
        reservationRepository.findById(reservationId).map(aux->reservationRepository.save(aux.withStatus(4))).
                orElseThrow(()->new ResourceNotFoundException(ENTITY,reservationId));
        reservation.setStatus(4);
        rent.setReservation(reservation);
        rent.setStatus(0);
        rent.setPayDate(date);
        apiCall.setStateCar(reservation.getCarId(),2);
        return rentRepository.save(rent);
    }

    @Override
    public Rent endRent(Long rentId) {
        Rent rent= rentRepository.findById(rentId).map(aux->rentRepository.save(aux.withStatus(1))).
                orElseThrow(()->new ResourceNotFoundException(ENTITY,rentId));
        rent.setStatus(1);
        apiCall.setStateCar(rent.getReservation().getCarId(),0);
        return rent;
    }

    @Override
    public Rent finalizedRentWithRating(Long rentId) {
        Rent rent= rentRepository.findById(rentId).map(aux->rentRepository.save(aux.withStatus(2))).
                orElseThrow(()->new ResourceNotFoundException(ENTITY,rentId));
        return rent;
    }

    @Override
    public List<Rent> getRentsByClientId(Long clientId) {
        List<Rent> rentList=rentRepository.getRentsByClientId(clientId);
        List<CarRentResource> carRentResourceList=apiCall.getCars();

        for(Rent rent:rentList){
            for(CarRentResource resource:carRentResourceList){
                if(rent.getReservation().getCarId().equals(resource.getId())){
                    rent.getReservation().setCarRentResource(resource);
                }
            }
        }
        return rentList;
    }

    @Override
    public List<Rent> getRentsByOwnerId(Long ownerId) {
        List<Rent> rentList=rentRepository.getRentsByOwnerId(ownerId);
        List<CarRentResource> carRentResourceList=apiCall.getCars();

        for(Rent rent:rentList){
            for(CarRentResource resource:carRentResourceList){
                if(rent.getReservation().getCarId().equals(resource.getId())){
                    rent.getReservation().setCarRentResource(resource);
                }
            }
        }
        return rentList;
    }
}
