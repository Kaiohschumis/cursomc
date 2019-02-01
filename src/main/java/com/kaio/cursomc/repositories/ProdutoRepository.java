package com.kaio.cursomc.repositories;

import com.kaio.cursomc.domain.Categoria;
import com.kaio.cursomc.domain.Produto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProdutoRepository extends JpaRepository<Produto, Integer> {

}
