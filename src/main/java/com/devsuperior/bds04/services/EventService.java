package com.devsuperior.bds04.services;

import com.devsuperior.bds04.dto.EventDTO;
import com.devsuperior.bds04.entities.City;
import com.devsuperior.bds04.entities.Event;
import com.devsuperior.bds04.repositories.CityRepository;
import com.devsuperior.bds04.repositories.EventRepository;
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
public class EventService implements ServiceInterface<EventDTO, Long> {

    @Autowired
    private EventRepository eventRepository;

    @Autowired
    private CityRepository cityRepository;


    @Override
    @Transactional
    public EventDTO save(EventDTO dto) {
        Event entity = new Event();
        entity.setName(dto.getName());
        entity.setDate(dto.getDate());
        entity.setUrl(dto.getUrl());
        City city = cityRepository.getOne(dto.getCityId());
        entity.setCity(city);
        return new EventDTO(eventRepository.save(entity));
    }

    @Override
    @Transactional
    public EventDTO update(Long id, EventDTO dto) {
        try {
            Event entity = eventRepository.getOne(id);
            entity.setName(dto.getName());
            entity.setDate(dto.getDate());
            entity.setUrl(dto.getUrl());

            City city = cityRepository.getOne(dto.getCityId());
            entity.setCity(city);
            entity = eventRepository.save(entity);
            return new EventDTO(entity);
        } catch (EntityNotFoundException e) {
            throw new ResourceNotFoundException("Id not found" + id);
        }
    }

    @Override
    public void delete(Long id) {
        try {
            eventRepository.deleteById(id);
        } catch (EmptyResultDataAccessException e) {
            throw new ResourceNotFoundException("Id not found " + id);
        } catch (DataIntegrityViolationException e) {
            throw new DataBasesException("Integrity violation");
        }
    }

    @Override
    public Page<EventDTO> findAll(Pageable pageable) {
        Page<Event> categories = eventRepository.findAll(pageable);
        return categories.map(EventDTO::new);
    }

    @Override
    @Transactional(readOnly = true)
    public EventDTO findById(Long id) {
        Optional<Event> optionalCategory = eventRepository.findById(id);
        Event entity = optionalCategory.orElseThrow(() -> new ResourceNotFoundException("Entity not found"));
        return new EventDTO(entity);
    }
}
