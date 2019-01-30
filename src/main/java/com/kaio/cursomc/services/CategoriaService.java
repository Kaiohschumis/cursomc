package com.kaio.cursomc.services;

import com.kaio.cursomc.domain.Categoria;
import com.kaio.cursomc.repositories.CategoriaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class CategoriaService {
   @Autowired
    private CategoriaRepository repos;

    public Categoria buscar(Integer id){
       Optional<Categoria> obj = repos.findById(id);
       return obj.orElse(null);

    }
}
