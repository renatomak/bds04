package com.devsuperior.bds04.controllers;

import com.devsuperior.bds04.dto.CityDTO;
import com.devsuperior.bds04.services.CityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.validation.Valid;
import java.net.URI;

@RestController
@RequestMapping("/cities")
public class CityController {
    
    @Autowired
    private CityService cityService;

    @GetMapping
    public ResponseEntity<Page<CityDTO>> findAll(Pageable pageable) {
        Page<CityDTO> list = cityService.findAll(pageable);
        return ResponseEntity.ok(list);
    }

    @GetMapping(value = "/{id}")
    public ResponseEntity<CityDTO> findById(@PathVariable Long id) {
        return ResponseEntity.ok(cityService.findById(id));
    }

    @PostMapping
    public ResponseEntity<CityDTO> create(@Valid @RequestBody CityDTO cityDto) {
        cityDto = cityService.save(cityDto);
        URI uri = ServletUriComponentsBuilder.fromCurrentRequestUri().path("/{id}")
                .buildAndExpand(cityDto.getId()).toUri();
        return ResponseEntity.created(uri).body(cityDto);
    }

    @PutMapping(value = "/{id}")
    public ResponseEntity<CityDTO> update(@PathVariable Long id, @RequestBody CityDTO cityDto) {
        return ResponseEntity.ok(cityService.update(id, cityDto));
    }

    @DeleteMapping(value = "/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        cityService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
