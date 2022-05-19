package com.devsuperior.bds04.services.validation;

import com.devsuperior.bds04.controllers.exceptions.FieldMessage;
import com.devsuperior.bds04.dto.CityDTO;
import com.devsuperior.bds04.entities.City;
import com.devsuperior.bds04.repositories.CityRepository;
import org.springframework.beans.factory.annotation.Autowired;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.util.ArrayList;
import java.util.List;

public class CityInsertValidator implements ConstraintValidator<CityInsertValid, CityDTO> {

    @Autowired
    private CityRepository cityRepository;

    @Override
    public void initialize(CityInsertValid ann) {
    }

    @Override
    public boolean isValid(CityDTO dto, ConstraintValidatorContext context) {

        List<FieldMessage> list = new ArrayList<>();

        City entity = cityRepository.findByName(dto.getName());
        if (entity != null) {
            list.add(new FieldMessage("nome", "Nome já existe."));
        }

        for (FieldMessage e : list) {
            context.disableDefaultConstraintViolation();
            context.buildConstraintViolationWithTemplate(e.getMessage()).addPropertyNode(e.getFieldName())
                    .addConstraintViolation();
        }
        return list.isEmpty();
    }
}