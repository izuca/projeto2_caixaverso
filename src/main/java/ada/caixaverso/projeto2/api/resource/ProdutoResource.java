package ada.caixaverso.projeto2.api.resource;

import ada.caixaverso.projeto2.api.dto.ProdutoDTO;
import ada.caixaverso.projeto2.api.services.ProdutoService;
import jakarta.annotation.security.RolesAllowed;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import org.eclipse.microprofile.openapi.annotations.Operation;
import org.eclipse.microprofile.openapi.annotations.media.Content;
import org.eclipse.microprofile.openapi.annotations.responses.APIResponse;
import org.eclipse.microprofile.openapi.annotations.tags.Tag;

@Path("/produtos")
@Tag(name = "Produtos", description = "Endpoints utilizados para o CRUD de produtos")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class ProdutoResource {
    @Inject
    ProdutoService produtoService;

    @GET
    @Operation(summary = "Lista todos os produtos", description = "Lista todos os produtos cadastrados no sistema")
    @APIResponse(responseCode = "200", description = "Lista de produtos retornada com sucesso", content = @Content(mediaType = "application/json"))
    @RolesAllowed({"admin", "user"}) // <- apenas admin e user
    public Response listaProdutos(){
        return Response.status(Response.Status.OK)
                .entity(produtoService.listarProdutos())
                .build();
    }

    @GET
    @Path("/{id}")
    @Operation(summary = "Busca um produto por ID", description = "Busca um produto cadastrado no sistema pelo seu ID")
    @APIResponse(responseCode = "200", description = "Produto retornado com sucesso", content = @Content(mediaType = "application/json"))
    @APIResponse(responseCode = "404", description = "Produto não encontrado", content = @Content(mediaType = "application/json"))
    @RolesAllowed({"admin", "user"})
    public Response buscaProduto(@PathParam("id") Long id){
        return Response.status(Response.Status.OK)
                .entity(produtoService.buscarProdutoByID(id))
                .build();
    }

    @POST
    @Transactional
    @Operation(summary = "Criar produto", description = "Cria um novo produto")
    @APIResponse(responseCode = "201", description = "Produto criado com sucesso", content = @Content(mediaType = "application/json"))
    @RolesAllowed("admin")
    public Response criarProduto(@Valid ProdutoDTO produtoDTO) {
        return Response.status(Response.Status.CREATED)
                .entity(produtoService.criarProduto(produtoDTO))
                .build();
    }

    @PUT
    @Transactional
    @Path("/{id}")
    @Operation(summary = "Alterar produto por ID", description = "Altera todas as informações de um produto")
    @APIResponse(responseCode = "200", description = "Produto alterado com sucesso", content = @Content(mediaType = "application/json"))
    @APIResponse(responseCode = "404", description = "Produto não encontrado", content = @Content(mediaType = "application/json"))
    @RolesAllowed("admin")
    public Response alteraProduto(@PathParam("id") Long id,@Valid ProdutoDTO produtoDTO) {
        return Response.status(Response.Status.OK)
                .entity(produtoService.alteraProduto(id, produtoDTO))
                .build();
    }

    @DELETE
    @Transactional
    @Path("/{id}")
    @Operation(summary = "Deletar produto por ID", description = "Deleta um produto por ID")
    @APIResponse(responseCode = "204", description = "Produto deletado com sucesso", content = @Content(mediaType = "application/json"))
    @APIResponse(responseCode = "404", description = "Produto não encontrado", content = @Content(mediaType = "application/json"))
    @RolesAllowed("admin")
    public Response deletaProduto(@PathParam("id") Long id) {
        return Response.status(Response.Status.NO_CONTENT)
                .entity(produtoService.deletaProduto(id))
                .build();
    }

}
