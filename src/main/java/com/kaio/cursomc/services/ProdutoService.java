package com.kaio.cursomc.services;

import com.kaio.cursomc.domain.Categoria;
import com.kaio.cursomc.domain.Produto;
import com.kaio.cursomc.repositories.CategoriaRepository;
import com.kaio.cursomc.repositories.ProdutoRepository;
import com.kaio.cursomc.services_exception.ObjectNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;

@Service
public class ProdutoService {
   @Autowired
    private ProdutoRepository repos;
   @Autowired
   private CategoriaRepository categoriaRepository;

    public Produto find(Integer id){
       Optional<Produto> obj = repos.findById(id); //consulta repositoryy
       if (obj == null) {
            throw new ObjectNotFoundException("Objeto não encontrado! ID: " + id + ", Tipo: " + Produto.class.getName());
       }
        return obj.orElse(null);

    }

    public Page<Produto> search(String nome, List<Integer> ids, Integer page, Integer linesPerPage, String orderBy, String direction) {
        PageRequest pageRequest = PageRequest.of(page, linesPerPage, Sort.Direction.valueOf(direction), orderBy);
        List<Categoria> categorias = categoriaRepository.findAllById(ids);
        return repos.findDistinctByNomeContainingAndCategoriasIn(nome, categorias, pageRequest);
    }
}
