package com.rentCar.rentService.Mapping;

import com.rentCar.rentService.Entities.Rent;
import com.rentCar.rentService.Resource.RentResource;
import com.rentCar.rentService.Shared.Mapping.EnhancedModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;

import java.io.Serializable;
import java.util.List;

public class RentMapper implements Serializable {
    @Autowired
    EnhancedModelMapper mapper;

    public RentResource toResource (Rent model){return mapper.map(model,RentResource.class);}
    public Page<RentResource> modelListToPage(List<Rent> modelList, Pageable pageable){
        return new PageImpl<>(mapper.mapList(modelList,RentResource.class),pageable,modelList.size());
    }
}
