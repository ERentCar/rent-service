package com.rentCar.rentService.Controllers;

import com.rentCar.rentService.Mapping.RentMapper;
import com.rentCar.rentService.Resource.RentResource;
import com.rentCar.rentService.Services.RentService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@CrossOrigin
@RestController
@RequestMapping("/api/v1/rents")
public class RentController {
    private final RentService rentService;
    private final RentMapper mapper;

    public RentController(RentService rentService, RentMapper mapper) {
        this.rentService = rentService;
        this.mapper = mapper;
    }

    @GetMapping
    public Page<RentResource> getAllRents(Pageable pageable){
        return mapper.modelListToPage(rentService.getAll(),pageable);
    }

    @GetMapping("{rentId}")
    public RentResource getRentById(@PathVariable Long rentId){
        return  mapper.toResource(rentService.getById(rentId));
    }

    @PostMapping("/reservation/{reservationId}")
    public RentResource createRent(@PathVariable("reservationId")Long reservationId){
        return mapper.toResource(rentService.create(reservationId));
    }

    @PutMapping("endRent/{rentId}")
    public RentResource endRent(@PathVariable("rentId")Long rentId){
        return mapper.toResource(rentService.endRent(rentId));
    }
    @GetMapping("client/{clientId}")
    public Page<RentResource> getAllRentsByClientId(@PathVariable("clientId")Long clientId,
                                                    Pageable pageable){
        return mapper.modelListToPage(rentService.getRentsByClientId(clientId),pageable);
    }
    @GetMapping("owner/{ownerId}")
    public Page<RentResource> getAllRentsByOwnerId(@PathVariable("ownerId")Long ownerId,
                                                   Pageable pageable){
        return mapper.modelListToPage(rentService.getRentsByOwnerId(ownerId),pageable);
    }
    @PutMapping("setStateRent/{rentId}")
    public RentResource setStateRent(@PathVariable("rentId")Long rentId){
        return mapper.toResource(rentService.finalizedRentWithRating(rentId));
    }
}
