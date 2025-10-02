package ada.caixaverso.projeto2.api.exception;

import lombok.*;
import org.eclipse.microprofile.openapi.annotations.media.Schema;

import java.time.ZonedDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Schema(name = "Erro Response", description = "Estrutura de Erro da API")
public class ErrorResponseDTO {
    @Schema(description = "Identificador da requisição", examples = "123e4567-e89b-12d3-a456-426614174000", required = true)
    private String traceId;

    @Schema(description = "Data e hora do erro", examples = "2025-09-20T23:55:08.355293-03:00", required = true)
    private ZonedDateTime timestamp;

    @Schema(description = "Status HTTP do Erro", examples = "404", required = true)
    private int status;

    @Schema(description = "Título do erro", examples = "Recurso não encontrado", required = true)
    private String titulo;

    @Schema(description = "Erro detalhado", examples = "Recurso não encontrado ou inexistente na aplicação", required = true)
    private String mensagem;

    @Schema(description = "Caminho da requisição", examples = "/api-emprestimo/v1/produto", required = true)
    private String path;
}
