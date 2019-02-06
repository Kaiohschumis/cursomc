package com.kaio.cursomc.services;

import com.kaio.cursomc.domain.Pedido;
import com.kaio.cursomc.repositories.PedidoRepository;
import com.kaio.cursomc.services_exception.ObjectNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class PedidoService {
   @Autowired
    private PedidoRepository repos;

    public Pedido find(Integer id){
       Optional<Pedido> obj = repos.findById(id); //consulta repositoryy
       if (obj == null) {
            throw new ObjectNotFoundException("Objeto não encontrado! ID: " + id + ", Tipo: " + Pedido.class.getName());
       }
        return obj.orElse(null);

    }
}
