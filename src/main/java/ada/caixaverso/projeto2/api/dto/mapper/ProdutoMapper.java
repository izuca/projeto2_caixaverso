package ada.caixaverso.projeto2.api.dto.mapper;

import ada.caixaverso.projeto2.api.dto.ProdutoDTO;
import ada.caixaverso.projeto2.api.entity.Produto;

import java.math.RoundingMode;

public class ProdutoMapper {
    public static ProdutoDTO toDto(Produto produto){
        return ProdutoDTO.builder()
                .id(produto.getId())
                .nome(produto.getNome())
                .prazoMaximoMeses(produto.getPrazoMaximoMeses())
                .taxaJurosAnual(produto.getTaxaJurosAnual().setScale(1, RoundingMode.HALF_UP))
                .build();
    }

    public static Produto toEntity(ProdutoDTO produtoDTO){
        return Produto.builder()
                .id(produtoDTO.getId())
                .nome(produtoDTO.getNome())
                .taxaJurosAnual(produtoDTO.getTaxaJurosAnual())
                .prazoMaximoMeses(produtoDTO.getPrazoMaximoMeses())
                .build();
    }
}


