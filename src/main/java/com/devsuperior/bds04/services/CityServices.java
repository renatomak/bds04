package com.devsuperior.bds04.services;

import com.devsuperior.bds04.dto.CityDTO;
import com.devsuperior.bds04.entities.City;
import com.devsuperior.bds04.repositories.CityRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class CityServices implements ServiceInterface<CityDTO, Long> {

    @Autowired
    private CityRepository cityRepository;


    @Override
    @Transactional
    public CityDTO save(CityDTO dto) {
        City entity = new City();
        entity.setName(dto.getName());
        return new CityDTO(cityRepository.save(entity));
    }

    @Override
    @Transactional
    public CityDTO update(CityDTO dto) {
        return null;
    }

    @Override
    public void delete(Long id) {

    }

    @Override
    public List<CityDTO> list() {
        return null;
    }

    @Override
    @Transactional(readOnly = true)
    public CityDTO findById(Long id) {
        return null;
    }
}
