package com.product.catalog.services.validation;

import java.util.ArrayList;
import java.util.List;

import com.product.catalog.dto.UserInsertDTO;
import com.product.catalog.entities.User;
import com.product.catalog.repositories.UserRepository;
import com.product.catalog.resources.exceptions.FieldMessage;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import org.springframework.beans.factory.annotation.Autowired;

public class UserInsertValidator implements ConstraintValidator<UserInsertValid, UserInsertDTO> {
    @Autowired
    private UserRepository repository;

    @Override
    public void initialize(UserInsertValid ann) {
    }

    @Override
    public boolean isValid(UserInsertDTO dto, ConstraintValidatorContext context) {

        List<FieldMessage> list = new ArrayList<>();

        User user = repository.findByEmail(dto.getEmail());
        if(user != null){
            list.add(new FieldMessage("email", "Esse email ja esta sendo utilizado"));
        }

        for (FieldMessage e : list) { //insert errors in list of bean validation
            context.disableDefaultConstraintViolation();
            context.buildConstraintViolationWithTemplate(e.getMessage()).addPropertyNode(e.getFieldName())
                    .addConstraintViolation();
        }
        return list.isEmpty();
    }
}
