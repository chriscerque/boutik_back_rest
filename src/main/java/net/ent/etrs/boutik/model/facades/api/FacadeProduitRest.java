package net.ent.etrs.boutik.model.facades.api;

import net.ent.etrs.boutik.model.daos.exceptions.DaoException;
import net.ent.etrs.boutik.model.entities.Produit;
import net.ent.etrs.boutik.model.facades.FacadeProduit;
import net.ent.etrs.boutik.model.facades.api.dtos.DtoProduit;
import net.ent.etrs.boutik.model.facades.api.dtos.converters.DtoProduitConverter;
import net.ent.etrs.boutik.model.facades.api.filters.annotations.JWTTokenNeeded;
import net.ent.etrs.boutik.model.facades.api.filters.annotations.RoleAdmin;
import net.ent.etrs.boutik.model.facades.api.filters.annotations.RoleUser;
import net.ent.etrs.boutik.model.facades.exceptions.BusinessException;

import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.Set;

@JWTTokenNeeded
@Path("/produits")
public class FacadeProduitRest {

    @Inject
    private FacadeProduit facadeProduit;


    @GET
    @Path("/")
    @Produces(MediaType.APPLICATION_JSON)
    @RoleUser
    public Response findAll() {
        try {
            Set<Produit> produits = this.facadeProduit.findAll();
            return Response.ok(DtoProduitConverter.toDtoList(produits)).build();
        } catch (Exception e) {
            e.printStackTrace();
            return Response.serverError().build();
        }

    }

    @GET
    @Path("/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    @RoleUser
    public Response findById(@PathParam("id") Long id) {
        try {
            return Response.ok(DtoProduitConverter.toDto(this.facadeProduit.findById(id).orElseThrow(Exception::new))).build();
        } catch (Exception e) {
            return Response.serverError().build();
        }

    }

    @POST
    @Path("/")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    @RoleAdmin
    public Response create(DtoProduit dtoProduit) {

        try {
            Produit p = this.facadeProduit.save(DtoProduitConverter.toEntity(dtoProduit)).orElseThrow(DaoException::new);
            return Response.ok(DtoProduitConverter.toDto(p)).build();
        } catch (DaoException e) {
            return Response.status(Response.Status.CONFLICT).build();
        } catch (BusinessException e) {
            if (e.getCause() instanceof DaoException) {
                return Response.status(Response.Status.CONFLICT).build();
            }
            return Response.serverError().build();
        } catch (Exception e) {
            return Response.serverError().build();
        }
    }

    @PUT
    @Path("/{id}")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    @RoleAdmin
    public Response update(DtoProduit dtoProduit, @PathParam("id") Long id) {

        try {
            if (!this.facadeProduit.exist(id)) {
                return Response.status(Response.Status.NOT_FOUND).build();
            }
            Produit m = this.facadeProduit.save(DtoProduitConverter.toEntity(dtoProduit)).orElseThrow(DaoException::new);
            return Response.ok(DtoProduitConverter.toDto(m)).build();
        } catch (Exception e) {
            return Response.serverError().build();
        }

    }

    @DELETE
    @Path("/{id}")
    @RoleAdmin
    public Response delete(@PathParam("id") Long id) {
        try {
            if (!this.facadeProduit.exist(id)) {
                return Response.status(Response.Status.NOT_FOUND).build();
            }
            this.facadeProduit.delete(id);
            return Response.status(Response.Status.NO_CONTENT).build();
        } catch (BusinessException e) {
            return Response.notModified().build();
        } catch (Exception e) {
            return Response.serverError().build();
        }
    }

}
