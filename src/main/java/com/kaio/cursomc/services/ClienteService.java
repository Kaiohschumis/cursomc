package com.kaio.cursomc.services;

import com.kaio.cursomc.domain.Cliente;
import com.kaio.cursomc.repositories.ClienteRepository;
import com.kaio.cursomc.services_exception.ObjectNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class ClienteService {
   @Autowired
    private ClienteRepository repos;

    public Cliente find(Integer id){
       Optional<Cliente> obj = repos.findById(id); //consulta repositoryy
       if (obj == null) {
            throw new ObjectNotFoundException("Objeto não encontrado! ID: " + id + ", Tipo: " + Cliente.class.getName());
       }
        return obj.orElse(null);

    }
}
