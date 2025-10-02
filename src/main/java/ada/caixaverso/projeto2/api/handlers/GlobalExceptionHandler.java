package ada.caixaverso.projeto2.api.handlers;


import ada.caixaverso.projeto2.api.exception.ErrorResponseDTO;
import io.quarkus.security.UnauthorizedException;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.persistence.EntityNotFoundException;
import jakarta.persistence.PersistenceException;
import jakarta.validation.ConstraintViolationException;
import jakarta.ws.rs.NotAllowedException;
import jakarta.ws.rs.NotFoundException;
import jakarta.ws.rs.core.Context;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.core.UriInfo;
import lombok.extern.slf4j.Slf4j;
import org.jboss.resteasy.reactive.server.ServerExceptionMapper;

import java.time.ZonedDateTime;
import java.util.UUID;
import java.util.stream.Collectors;

import static jakarta.ws.rs.core.Response.Status.*;

@ApplicationScoped
@Slf4j
public class GlobalExceptionHandler {

    @Context
    UriInfo uriInfo;

    @ServerExceptionMapper
    public Response handleUnauthorized(UnauthorizedException e) {
        return createErrorResponse(
                UNAUTHORIZED.getStatusCode(),
                "Não autorizado",
                "Acesso negado: credenciais ausentes ou inválidas"
        );
    }

    @ServerExceptionMapper
    public Response handleMethodNotAllower(NotAllowedException e) {
        return createErrorResponse(
                METHOD_NOT_ALLOWED.getStatusCode(),
                "Método não permitido",
                "O método HTTP utilizado não é suportado para este recursos"
        );
    }

    @ServerExceptionMapper
    public Response handleConstraintViolation(ConstraintViolationException e) {
        String violations = e.getConstraintViolations()
                .stream()
                .map(v -> v.getPropertyPath() + ": " + v.getMessage() )
                .collect(Collectors.joining("; "));

        return createErrorResponse(
                BAD_REQUEST.getStatusCode(),
                "Erro de validação",
                violations
        );
    }

    @ServerExceptionMapper
    public Response handlePersistantce(PersistenceException e) {
        String message = e.getMessage() != null ? e.getMessage() : "";
        if (message.contains("constraint") || message.contains("unique")) {
            return createErrorResponse(
                    CONFLICT.getStatusCode(),
                    "Conflito de dados",
                    "Violação de restrição no banco de dados(duplicidade)"
            );
        }

        return createErrorResponse(
                INTERNAL_SERVER_ERROR.getStatusCode(),
                "Erro no Banco de Dados",
                "Falha ao acessar ou manipular dados"
        );
    }

    @ServerExceptionMapper
    public Response handleEntityNotFound(EntityNotFoundException e) {
        return createErrorResponse(
                NOT_FOUND.getStatusCode(),
                "Recurso não encontrado",
                "Recurso solicitado não encontrado/inexistente no sistema"
        );
    }

    @ServerExceptionMapper
    public Response handleNotFound(NotFoundException e) {
        return createErrorResponse(
                NOT_FOUND.getStatusCode(),
                "Produto não encontrado",
                "Produto solicitado não encontrado no Banco de Dados"
        );
    }

    private Response createErrorResponse(int status,String titulo, String mensagem) {
        ErrorResponseDTO errorResponseDTO = ErrorResponseDTO.builder()
                .traceId( UUID.randomUUID().toString())
                .timestamp(ZonedDateTime.now())
                .status(status)
                .titulo(titulo)
                .mensagem(mensagem)
                .path(getCurrentPath())
                .build();

        return Response.status(status)
                .entity(errorResponseDTO)
                .build();
    }

    private String getCurrentPath() {
        return uriInfo != null ? uriInfo.getPath() : "Caminho não disponível";
    }
}

