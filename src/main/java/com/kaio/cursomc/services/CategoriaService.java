package com.kaio.cursomc.services;

import com.kaio.cursomc.domain.Categoria;

import com.kaio.cursomc.dto.CategoriaDTO;
import com.kaio.cursomc.repositories.CategoriaRepository;
import com.kaio.cursomc.services_exception.ObjectNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
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

    public Page<Categoria> findPage(Integer page, Integer linesPerPage, String orderBy, String direction) {
        PageRequest pageRequest = PageRequest.of(page, linesPerPage, Sort.Direction.valueOf(direction), orderBy);
        return repos.findAll(pageRequest);
    }

    public Categoria fromDTO(CategoriaDTO objDto) {
        return new Categoria(objDto.getId(), objDto.getNome());
    }

    private void updateData(Categoria newObj, Categoria obj) {
        newObj.setNome(obj.getNome());

    }
}