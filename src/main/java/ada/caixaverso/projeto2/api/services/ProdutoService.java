package ada.caixaverso.projeto2.api.services;

import ada.caixaverso.projeto2.api.dto.ProdutoDTO;
import ada.caixaverso.projeto2.api.dto.mapper.ProdutoMapper;
import ada.caixaverso.projeto2.api.entity.Produto;
import ada.caixaverso.projeto2.api.repository.ProdutoRepository;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.ws.rs.NotFoundException;

import java.util.List;

@ApplicationScoped
public class ProdutoService {
    @Inject
    ProdutoRepository produtoRepository;

    public List<ProdutoDTO> listarProdutos() {
        return  produtoRepository.listAll()
                .stream()
                .map(ProdutoMapper::toDto)
                .toList();
    }

    public ProdutoDTO buscarProdutoByID(Long id) {
        Produto produto = produtoRepository.findByIdOptional(id).orElseThrow(NotFoundException::new);
        return ProdutoMapper.toDto(produto);
    }

    public ProdutoDTO criarProduto(ProdutoDTO produtoDTO) {
        Produto produto = ProdutoMapper.toEntity(produtoDTO);
        produtoRepository.persist(produto);
        return ProdutoMapper.toDto(produto);
    }

    public ProdutoDTO alteraProduto(Long id,ProdutoDTO produtoDTO) {
        Produto produto = ProdutoMapper.toEntity(produtoDTO);
        Produto produtoAtualizar = produtoRepository.findByIdOptional(id).orElseThrow(NotFoundException::new);

        produtoAtualizar.setNome(produto.getNome());
        produtoAtualizar.setTaxaJurosAnual(produto.getTaxaJurosAnual());
        produtoAtualizar.setPrazoMaximoMeses(produto.getPrazoMaximoMeses());

        produtoRepository.persist(produtoAtualizar);
        return ProdutoMapper.toDto(produtoAtualizar);
    }

    public ProdutoDTO deletaProduto(Long id) {
        Produto produto = produtoRepository.findByIdOptional(id).orElseThrow(NotFoundException::new);
        produtoRepository.delete(produto);

        return ProdutoMapper.toDto(produto);
    }
}

