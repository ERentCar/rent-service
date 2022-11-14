package com.rentCar.rentService.Services.impls;

import com.rentCar.rentService.Resource.CarRentResource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import java.util.Arrays;
import java.util.List;

@Service
public class ApiCall {
    @Autowired
    RestTemplate restTemplate;
    public List<CarRentResource> getCars()throws RestClientException {
        return Arrays.asList(restTemplate.getForObject("http://localhost:8115/api/v1/cars/ForRents",
                CarRentResource[].class));
    }
    public void setStateCar(Long carId,int state)throws RestClientException {
        restTemplate.exchange("http://localhost:8115/api/v1/cars/setState/"+carId+"state/"+state,
                HttpMethod.PUT,null,void.class);
    }
}
