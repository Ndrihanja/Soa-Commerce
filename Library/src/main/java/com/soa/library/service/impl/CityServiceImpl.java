package com.soa.library.service.impl;

import com.soa.library.model.City;
import com.soa.library.repository.CityRepository;
import com.soa.library.service.CityService;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

public class CityServiceImpl implements CityService {
    @Autowired
    private CityRepository cityRepository;

    @Override
    public List<City> getAll() {
        return cityRepository.findAll();
    }
}
