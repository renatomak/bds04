package com.devsuperior.bds04.services;

import com.devsuperior.bds04.dto.CityDTO;
import com.devsuperior.bds04.entities.City;
import com.devsuperior.bds04.repositories.CityRepository;
import com.devsuperior.bds04.services.exceptions.ResourceNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityNotFoundException;
import java.util.List;

@Service
public class CityService implements ServiceInterface<CityDTO, Long> {

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
    public CityDTO update(Long id, CityDTO dto) {
        try {
            City entity = cityRepository.getOne(id);
            entity.setName(dto.getName());
            entity = cityRepository.save(entity);
            return new CityDTO(entity);
        } catch (EntityNotFoundException e) {
            throw new ResourceNotFoundException("Id not found" + id);
        }
    }

    @Override
    public void delete(Long id) {

    }

    @Override
    public Page<CityDTO> findAll(Pageable pageable) {
        return null;
    }

    @Override
    @Transactional(readOnly = true)
    public CityDTO findById(Long id) {
        return null;
    }
}
