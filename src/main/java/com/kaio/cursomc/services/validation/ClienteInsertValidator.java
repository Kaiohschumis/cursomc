package com.kaio.cursomc.services.validation;

import com.kaio.cursomc.domain.Cliente;
import com.kaio.cursomc.domain.enums.TipoCliente;
import com.kaio.cursomc.dto.ClienteNewDTO;
import com.kaio.cursomc.repositories.ClienteRepository;
import com.kaio.cursomc.resources.exception.FieldMassage;
import com.kaio.cursomc.services.validation.utils.BR;
import org.springframework.beans.factory.annotation.Autowired;

import javax.validation.ConstraintValidator;

import java.util.ArrayList; import java.util.List;

import javax.validation.ConstraintValidatorContext;

public class ClienteInsertValidator implements ConstraintValidator<ClienteInsert, ClienteNewDTO> {

    @Autowired
    private ClienteRepository repos;

    @Override
    public void initialize(ClienteInsert ann) {
    }

    @Override public boolean isValid(ClienteNewDTO objDto, ConstraintValidatorContext context) {

        List<FieldMassage> list = new ArrayList<>();

    if(objDto.getTipo().equals(TipoCliente.PESSOAFISICA.getCod()) && BR.isValidCPF(objDto.getCpfOuCnpj())) {
         list.add(new FieldMassage("cpfOuCnpj", "CPF inválido"));
    }
        if(objDto.getTipo().equals(TipoCliente.PESSOAJURIDICA.getCod()) && BR.isValidCNPJ(objDto.getCpfOuCnpj())) {
            list.add(new FieldMassage("cpfOuCnpj", "CNPJ inválido"));
        }

        Cliente aux = repos.findByEmail(objDto.getEmail());
        if (aux != null) {
            list.add(new FieldMassage("email", "Email já cadastrado"));

        }

       for (FieldMassage e : list) {
            context.disableDefaultConstraintViolation();
            context.buildConstraintViolationWithTemplate(e.getMessage()).addPropertyNode(e.getFieldName())
                    .addConstraintViolation();
        }         return list.isEmpty();     }
}