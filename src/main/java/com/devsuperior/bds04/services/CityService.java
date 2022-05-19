package com.devsuperior.bds04.services;

import com.devsuperior.bds04.dto.CityDTO;
import com.devsuperior.bds04.entities.City;
import com.devsuperior.bds04.repositories.CityRepository;
import com.devsuperior.bds04.services.exceptions.DataBasesException;
import com.devsuperior.bds04.services.exceptions.ResourceNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityNotFoundException;
import java.util.Optional;

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
        try {
            cityRepository.deleteById(id);
        } catch (EmptyResultDataAccessException e) {
            throw new ResourceNotFoundException("Id not found " + id);
        } catch (DataIntegrityViolationException e) {
            throw new DataBasesException("Integrity violation");
        }
    }

    @Override
    public Page<CityDTO> findAll(Pageable pageable) {
        Page<City> categories = cityRepository.findAll(pageable);
        return categories.map(CityDTO::new);
    }

    @Override
    @Transactional(readOnly = true)
    public CityDTO findById(Long id) {
        Optional<City> optionalCategory = cityRepository.findById(id);
        City entity = optionalCategory.orElseThrow(() -> new ResourceNotFoundException("Entity not found"));
        return new CityDTO(entity);
    }
}
