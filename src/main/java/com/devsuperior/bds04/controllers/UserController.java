package com.devsuperior.bds04.controllers;

import com.devsuperior.bds04.dto.UserDto;
import com.devsuperior.bds04.dto.UserInsertDto;
import com.devsuperior.bds04.dto.UserUpdateDto;
import com.devsuperior.bds04.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.validation.Valid;
import java.net.URI;

@RestController
@RequestMapping("/users")
public class UserController {

    @Autowired
    private UserService userService;

    @GetMapping
    public ResponseEntity<Page<UserDto>> findAll(Pageable pageable) {
        Page<UserDto> list = userService.findAll(pageable);
        return ResponseEntity.ok(list);
    }

    @GetMapping(value = "/{id}")
    public ResponseEntity<UserDto> findById(@PathVariable Long id) {
        return ResponseEntity.ok(userService.findById(id));
    }

    @PostMapping
    public ResponseEntity<UserDto> create(@Valid @RequestBody UserInsertDto userDto) {
        UserDto dto = userService.save(userDto);
        URI uri = ServletUriComponentsBuilder.fromCurrentRequestUri().path("/{id}")
                .buildAndExpand(dto.getId()).toUri();
        return ResponseEntity.created(uri).body(dto);
    }

    @PutMapping(value = "/{id}")
    public ResponseEntity<UserDto> update(@Valid @PathVariable Long id, @Valid @RequestBody UserUpdateDto dto) {
        return ResponseEntity.ok(userService.update(id, dto));
    }

    @DeleteMapping(value = "/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        userService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
