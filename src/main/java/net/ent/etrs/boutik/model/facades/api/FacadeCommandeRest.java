package net.ent.etrs.boutik.model.facades.api;

import net.ent.etrs.boutik.model.daos.exceptions.DaoException;
import net.ent.etrs.boutik.model.entities.Commande;
import net.ent.etrs.boutik.model.facades.FacadeCommande;
import net.ent.etrs.boutik.model.facades.api.dtos.DtoCommande;
import net.ent.etrs.boutik.model.facades.api.dtos.converters.DtoCommandeConverter;
import net.ent.etrs.boutik.model.facades.exceptions.BusinessException;

import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.Set;

@Path("/commandes")
public class FacadeCommandeRest {

    @Inject
    private FacadeCommande facadeCommande;


    @GET
    @Path("/")
    @Produces(MediaType.APPLICATION_JSON)
    public Response findAll() {
        try {
            Set<Commande> commandes = this.facadeCommande.findAll();
//            categories.forEach(System.out::println);

            return Response.ok(DtoCommandeConverter.toDtoList(commandes)).build();
        } catch (Exception e) {
            e.printStackTrace();
            return Response.serverError().build();
        }

    }

    @GET
    @Path("/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response findById(@PathParam("id") Long id) {
        try {
            return Response.ok(DtoCommandeConverter.toDtoWithProduits(this.facadeCommande.findByIdWithProduits(id).orElseThrow(BusinessException::new))).build();
        } catch (BusinessException e) {
            return Response.status(Response.Status.NOT_FOUND).build();
        } catch (Exception e) {
            return Response.serverError().build();
        }

    }

    @POST
    @Path("/")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response create(DtoCommande dtoCommande) {

        try {
            Commande c = this.facadeCommande.save(DtoCommandeConverter.toEntity(dtoCommande)).orElseThrow(DaoException::new);
            return Response.ok(DtoCommandeConverter.toDto(c)).build();
        } catch (DaoException e) {
            e.printStackTrace();
            return Response.status(Response.Status.CONFLICT).build();
        } catch (BusinessException e) {
            e.printStackTrace();
            if (e.getCause() instanceof DaoException) {
                return Response.status(Response.Status.CONFLICT).build();
            }
            return Response.serverError().build();
        } catch (Exception e) {
            e.printStackTrace();
            return Response.serverError().build();
        }
    }

    @PUT
    @Path("/{id}")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response update(DtoCommande dtoCommande, @PathParam("id") Long id) {

        try {
            if (!this.facadeCommande.exist(id)) {
                return Response.status(Response.Status.NOT_FOUND).build();
            }
            Commande m = this.facadeCommande.save(DtoCommandeConverter.toEntity(dtoCommande)).orElseThrow(DaoException::new);
            return Response.ok(DtoCommandeConverter.toDtoWithProduits(m)).build();
        } catch (Exception e) {
            e.printStackTrace();
            return Response.serverError().build();
        }

    }

    @DELETE
    @Path("/{id}")
    public Response delete(@PathParam("id") Long id) {
        try {
            if (!this.facadeCommande.exist(id)) {
                return Response.status(Response.Status.NOT_FOUND).build();
            }
            this.facadeCommande.delete(id);
            return Response.status(Response.Status.NO_CONTENT).build();
        } catch (Exception e) {
            return Response.serverError().build();
        }
    }

}
