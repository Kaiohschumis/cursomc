package com.kaio.cursomc.services.validation;

import com.amazonaws.HttpMethod;
import com.kaio.cursomc.domain.Cliente;
import com.kaio.cursomc.dto.ClienteDTO;
import com.kaio.cursomc.dto.ClienteNewDTO;
import com.kaio.cursomc.repositories.ClienteRepository;
import com.kaio.cursomc.resources.exception.FieldMassage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.servlet.HandlerMapping;

import javax.servlet.http.HttpServletRequest;
import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class ClienteUpdateValidator implements ConstraintValidator<ClienteUpdate, ClienteDTO> {

    @Autowired
    private HttpServletRequest request;
    @Autowired
    private ClienteRepository repos;

    @Override
    public void initialize(ClienteUpdate ann) {
    }

    @Override public boolean isValid(ClienteDTO objDto, ConstraintValidatorContext context) {

        Map<String, String> map = (Map<String, String>) request.getAttribute(HandlerMapping.URI_TEMPLATE_VARIABLES_ATTRIBUTE);
        Integer uriId = Integer.parseInt(map.get("id"));

        List<FieldMassage> list = new ArrayList<>();

        Cliente aux = repos.findByEmail(objDto.getEmail());
        if (aux != null && ! aux.getId().equals(uriId)) {
            list.add(new FieldMassage("email", "Email já cadastrado"));

        }

        for (FieldMassage e : list) {
            context.disableDefaultConstraintViolation();
            context.buildConstraintViolationWithTemplate(e.getMessage()).addPropertyNode(e.getFieldName())
                    .addConstraintViolation();
        }
        return list.isEmpty();
    }
}

