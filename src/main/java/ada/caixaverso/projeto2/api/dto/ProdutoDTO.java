package ada.caixaverso.projeto2.api.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import org.eclipse.microprofile.openapi.annotations.media.Schema;

import java.math.BigDecimal;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Schema(name = "Produto Response",description = "Produto DTO")
public class ProdutoDTO {
    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    @Schema(description = "Id do Produto", examples = "1")
    private Long id;

    @NotBlank(message = "O nome do produto é obrigatório")
    @Schema( description = "Nome do Produto", examples = "Emprestimo Pessoal")
    private String nome;

    @NotNull(message = "A taxa de juros é obrigatória")
    @DecimalMin(value = "0.0", inclusive = false, message = "A taxa de juros deve ser maior que zero")
    @Schema( description = "Taxa de Juros Anual", examples = "18.0")
    private BigDecimal taxaJurosAnual;

    @NotNull(message = "O prazo máximo de meses é obrigatório")
    @Min(value = 1, message = "O prazo mínimo deve ser de 1 mês")
    @Schema( description = "Prazo Máximo do Empréstimo em Meses", examples = "24")
    private Integer prazoMaximoMeses;
}

