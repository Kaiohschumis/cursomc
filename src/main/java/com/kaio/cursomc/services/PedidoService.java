package com.kaio.cursomc.services;

import com.kaio.cursomc.domain.ItemPedido;
import com.kaio.cursomc.domain.Pagamento;
import com.kaio.cursomc.domain.PagamentoComBoleto;
import com.kaio.cursomc.domain.Pedido;
import com.kaio.cursomc.domain.enums.EstadoPagamento;
import com.kaio.cursomc.repositories.ItemPedidoRepository;
import com.kaio.cursomc.repositories.PagamentoRepository;
import com.kaio.cursomc.repositories.PedidoRepository;
import com.kaio.cursomc.repositories.ProdutoRepository;
import com.kaio.cursomc.services_exception.ObjectNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.Optional;

@Service
public class PedidoService {
   @Autowired
    private PedidoRepository repos;
    @Autowired
    private BoletoService boletoService;
    @Autowired
    private PagamentoRepository pagamentoRepository;
    @Autowired
    private ProdutoRepository produtoRepository;
    @Autowired
    private ItemPedidoRepository itemPedidoRepository;

    @Autowired
    private ProdutoService produtoService;

    public Pedido find(Integer id){
       Optional<Pedido> obj = repos.findById(id); //consulta repositoryy
       if (obj == null) {
            throw new ObjectNotFoundException("Objeto não encontrado! ID: " + id + ", Tipo: " + Pedido.class.getName());
       }
        return obj.orElse(null);

    }

    public Pedido insert(Pedido obj) {
        obj.setId(null);
        obj.setInstante(new Date());
        obj.getPagamento().setEstado(EstadoPagamento.PENDENTE);
        obj.getPagamento().setPedido(obj);
        if (obj.getPagamento() instanceof PagamentoComBoleto){
            PagamentoComBoleto pagto = (PagamentoComBoleto) obj.getPagamento();
            boletoService.preencherPagamentoComBoleto(pagto, obj.getInstante());
        }
        obj = repos.save(obj);
        pagamentoRepository.save(obj.getPagamento());
        for (ItemPedido ip : obj.getItens()) {
            ip.setPreco(produtoService.find(ip.getProduto().getId()).getPreco());
            ip.setPedido(obj);
        }
        itemPedidoRepository.saveAll(obj.getItens());
        return obj;
    }
}
