package com.devsuperior.bds04.services;

import com.devsuperior.bds04.dto.RoleDto;
import com.devsuperior.bds04.dto.UserDto;
import com.devsuperior.bds04.dto.UserInsertDto;
import com.devsuperior.bds04.dto.UserUpdateDto;
import com.devsuperior.bds04.entities.Role;
import com.devsuperior.bds04.entities.User;
import com.devsuperior.bds04.repositories.RoleRepository;
import com.devsuperior.bds04.repositories.UserRepository;
import com.devsuperior.bds04.services.exceptions.DataBasesException;
import com.devsuperior.bds04.services.exceptions.ResourceNotFoundException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityNotFoundException;
import java.util.Optional;


@Service
public class UserService implements UserDetailsService {

    private static Logger logger = LoggerFactory.getLogger(UserService.class);
    @Autowired
    private BCryptPasswordEncoder passwordEncoder;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RoleRepository roleRepository;

    @Transactional(readOnly = true)
    public Page<UserDto> findAll(Pageable pageable) {
        Page<com.devsuperior.bds04.entities.User> users = userRepository.findAll(pageable);
        return users.map(UserDto::new);
    }

    @Transactional(readOnly = true)
    public UserDto findById(Long id) {
        Optional<User> user = userRepository.findById(id);
        User entity = user.orElseThrow(() -> new ResourceNotFoundException("Entity not found"));
        return new UserDto(entity);
    }

    @Transactional
    public UserDto update(Long id, UserUpdateDto dto) {
        try {
            User entity = userRepository.getOne(id);
            copyDtoToEntity(dto, entity);
            entity = userRepository.save(entity);
            return new UserDto(entity);
        } catch (EntityNotFoundException e) {
            throw new ResourceNotFoundException("Id not found" + id);
        }
    }

    @Transactional
    public UserDto save(UserInsertDto dto) {
        User entity = new User();
        copyDtoToEntity(dto, entity);
        entity.setPassword(passwordEncoder.encode(dto.getPassword()));
        entity = userRepository.save(entity);
        return new UserDto(entity);
    }

    public void delete(Long id) {
        try {
            userRepository.deleteById(id);
        } catch (EmptyResultDataAccessException e) {
            throw new ResourceNotFoundException("Id not found " + id);
        } catch (DataIntegrityViolationException e) {
            throw new DataBasesException("Integrity violation");
        }
    }

    private void copyDtoToEntity(UserDto dto, User entity) {
        entity.setFirstName(dto.getFirstName());
        entity.setLastName(dto.getLastName());
        entity.setEmail(dto.getEmail());

        entity.getRoles().clear();
        for (RoleDto roleDto : dto.getRoles()) {
            Role role = roleRepository.getOne(roleDto.getId());
            entity.getRoles().add(role);
        }
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepository.findByEmail(username);
        if (user == null) {
            logger.error("User not found: " + username);
            throw new UsernameNotFoundException("Email not found.");
        }
        logger.info("User found: " + username);
        return user;
    }
}
