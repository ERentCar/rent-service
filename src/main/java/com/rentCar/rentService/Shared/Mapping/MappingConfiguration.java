package com.rentCar.rentService.Shared.Mapping;

import com.rentCar.rentService.Mapping.ReservationMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration("rentServiceMappingConfiguration")
public class MappingConfiguration {
    @Bean
    public EnhancedModelMapper modelMapper() {
        return new EnhancedModelMapper();
    }
    @Bean
    public ReservationMapper reservationMapper(){return new ReservationMapper();}
}