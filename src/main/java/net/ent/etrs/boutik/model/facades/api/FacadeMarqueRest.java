package net.ent.etrs.boutik.model.facades.api;

import net.ent.etrs.boutik.model.daos.exceptions.DaoException;
import net.ent.etrs.boutik.model.entities.Marque;
import net.ent.etrs.boutik.model.facades.FacadeMarque;
import net.ent.etrs.boutik.model.facades.api.dtos.DtoMarque;
import net.ent.etrs.boutik.model.facades.api.dtos.converters.DtoMarqueConverter;
import net.ent.etrs.boutik.model.facades.api.filters.annotations.JWTTokenNeeded;
import net.ent.etrs.boutik.model.facades.exceptions.BusinessException;

import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.Set;

@JWTTokenNeeded
@Path("/marques")
public class FacadeMarqueRest {

    @Inject
    private FacadeMarque facadeMarque;


    @GET
    @Path("/")
    @Produces(MediaType.APPLICATION_JSON)
    public Response findAll() {
        try {
            Set<Marque> marques = this.facadeMarque.findAll();
            return Response.ok(DtoMarqueConverter.toDtoList(marques)).build();
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
            return Response.ok(DtoMarqueConverter.toDto(this.facadeMarque.findById(id).orElseThrow(Exception::new))).build();
        } catch (Exception e) {
            return Response.serverError().build();
        }

    }

    @POST
    @Path("/")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response create(DtoMarque dtoMarque) {

        try {
            Marque m = this.facadeMarque.save(DtoMarqueConverter.toEntity(dtoMarque)).orElseThrow(DaoException::new);
            return Response.ok(DtoMarqueConverter.toDto(m)).build();
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
    public Response update(DtoMarque dtoMarque, @PathParam("id") Long id) {

        try {
            if (!this.facadeMarque.exist(id)) {
                return Response.status(Response.Status.NOT_FOUND).build();
            }
            Marque m = this.facadeMarque.save(DtoMarqueConverter.toEntity(dtoMarque)).orElseThrow(DaoException::new);
            return Response.ok(DtoMarqueConverter.toDto(m)).build();
        } catch (Exception e) {
            return Response.serverError().build();
        }

    }

    @DELETE
    @Path("/{id}")
    public Response delete(@PathParam("id") Long id) {
        try {
            if (!this.facadeMarque.exist(id)) {
                return Response.status(Response.Status.NOT_FOUND).build();
            }
            this.facadeMarque.delete(id);
            return Response.status(Response.Status.NO_CONTENT).build();
        } catch (Exception e) {
            return Response.serverError().build();
        }
    }

}
