package com.kaio.cursomc.resources;

import com.kaio.cursomc.domain.Categoria;
import com.kaio.cursomc.domain.Pedido;

import com.kaio.cursomc.dto.CategoriaDTO;
import com.kaio.cursomc.services.PedidoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.validation.Valid;
import java.net.URI;

@RestController
@RequestMapping(value="/pedidos")
public class PedidoResource {

    @Autowired
    private PedidoService service;

    @RequestMapping(value="/{id}", method=RequestMethod.GET)
    public ResponseEntity<Pedido> find(@PathVariable Integer id){
            Pedido obj = service.find(id);
            return ResponseEntity.ok().body(obj);
        }
    @RequestMapping(method = RequestMethod.POST)
    public ResponseEntity<Void> Insert(@Valid @RequestBody CategoriaDTO objDto) {
        Categoria obj = service.fromDTO(objDto);
        obj = service.insert(obj);
        URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(obj.getId()).toUri();
        return ResponseEntity.created(uri).build();
    }

}
