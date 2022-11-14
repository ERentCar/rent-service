package com.rentCar.rentService.Shared.Mapping;

import com.rentCar.rentService.Mapping.RentMapper;
import com.rentCar.rentService.Mapping.ReservationMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

@Configuration("rentServiceMappingConfiguration")
public class MappingConfiguration {
    @Bean
    public EnhancedModelMapper modelMapper() {
        return new EnhancedModelMapper();
    }
    @Bean
    public ReservationMapper reservationMapper(){return new ReservationMapper();}
    @Bean
    public RentMapper rentMapper(){return new RentMapper();}
    @Bean
    public RestTemplate restTemplate() {
        return new RestTemplate();
    }
}