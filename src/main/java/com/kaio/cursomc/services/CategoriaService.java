package com.kaio.cursomc.services;

import com.kaio.cursomc.domain.Categoria;
import com.kaio.cursomc.repositories.CategoriaRepository;
import com.kaio.cursomc.services_exception.ObjectNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CategoriaService {
    @Autowired
    private CategoriaRepository repos;

    public Categoria find(Integer id) {
        Optional<Categoria> obj = repos.findById(id); //consulta repositoryy
        if (obj == null) {
            throw new ObjectNotFoundException("Objeto não encontrado! ID: " + id + ", Tipo: " + Categoria.class.getName());
        }
        return obj.orElse(null);

    }

    public Categoria insert(Categoria obj) {
        obj.setId(null);
        return repos.save(obj);
    }

    public Categoria update(Categoria obj) {
        find(obj.getId());
        return repos.save(obj);
    }

    public void delete(Integer id) {
        find(id);
        try {
            repos.deleteById(id);
        } catch (DataIntegrityViolationException e) {
            throw new DataIntegrityViolationException("Não é possível excluir uma categoria que possui produtos");
        }
    }

    public List<Categoria> findAll() {
        return repos.findAll();
    }
}