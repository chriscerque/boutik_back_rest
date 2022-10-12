package net.ent.etrs.boutik.model.facades.api;

import net.ent.etrs.boutik.model.daos.exceptions.DaoException;
import net.ent.etrs.boutik.model.entities.Categorie;
import net.ent.etrs.boutik.model.facades.FacadeCategorie;
import net.ent.etrs.boutik.model.facades.api.dtos.DtoCategorie;
import net.ent.etrs.boutik.model.facades.api.dtos.converters.DtoCategorieConverter;
import net.ent.etrs.boutik.model.facades.exceptions.BusinessException;

import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.Set;

@Path("/categories")
public class FacadeCategorieRest {

    @Inject
    private FacadeCategorie facadeCategorie;


    @GET
    @Path("/")
    @Produces(MediaType.APPLICATION_JSON)
    public Response findAll() {
        try {
            Set<Categorie> categories = this.facadeCategorie.findAll();
//            categories.forEach(System.out::println);

            return Response.ok(DtoCategorieConverter.toDtoList(categories)).build();
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
            return Response.ok(DtoCategorieConverter.toDto(this.facadeCategorie.findById(id).get())).build();
        } catch (Exception e) {
            return Response.serverError().build();
        }

    }

    @POST
    @Path("/")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response create(DtoCategorie dtoCategorie) {

        try {
            Categorie m = this.facadeCategorie.save(DtoCategorieConverter.toEntity(dtoCategorie)).orElseThrow(DaoException::new);
            return Response.ok(DtoCategorieConverter.toDto(m)).build();
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
    public Response update(DtoCategorie dtoCategorie, @PathParam("id") Long id) {

        try {
            if (!this.facadeCategorie.exist(id)) {
                return Response.status(Response.Status.NOT_FOUND).build();
            }
            Categorie m = this.facadeCategorie.save(DtoCategorieConverter.toEntity(dtoCategorie)).orElseThrow(DaoException::new);
            return Response.ok(DtoCategorieConverter.toDto(m)).build();
        } catch (Exception e) {
            e.printStackTrace();
            return Response.serverError().build();
        }

    }

    @DELETE
    @Path("/{id}")
    public Response delete(@PathParam("id") Long id) {
        try {
            if (!this.facadeCategorie.exist(id)) {
                return Response.status(Response.Status.NOT_FOUND).build();
            }
            this.facadeCategorie.delete(id);
            return Response.status(Response.Status.NO_CONTENT).build();
        } catch (Exception e) {
            return Response.serverError().build();
        }
    }

}
