package net.ent.etrs.boutik.model.facades.api.filters;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.JwtParser;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import net.ent.etrs.boutik.model.entities.Role;
import net.ent.etrs.boutik.model.entities.User;
import net.ent.etrs.boutik.model.facades.FacadeUser;
import net.ent.etrs.boutik.model.facades.api.FacadeUserRest;
import net.ent.etrs.boutik.model.facades.api.filters.annotations.RoleAdmin;

import javax.annotation.Priority;
import javax.crypto.SecretKey;
import javax.inject.Inject;
import javax.ws.rs.Priorities;
import javax.ws.rs.container.ContainerRequestContext;
import javax.ws.rs.container.ContainerRequestFilter;
import javax.ws.rs.core.HttpHeaders;
import javax.ws.rs.core.Response;
import javax.ws.rs.ext.Provider;
import java.io.IOException;
import java.util.Optional;

@Provider
@RoleAdmin
@Priority(Priorities.AUTHORIZATION)
public class RoleAdminFilter implements ContainerRequestFilter {

    @Inject
    private FacadeUser facadeUser;


    @Override
    public void filter(ContainerRequestContext requestContext) throws IOException {
        String authorizationHeader = requestContext.getHeaderString(HttpHeaders.AUTHORIZATION);
        String token = authorizationHeader.substring("Bearer".length()).trim();
        SecretKey key = Keys.hmacShaKeyFor(Decoders.BASE64.decode(FacadeUserRest.SECRET_KEY));
        JwtParser jwtParser = Jwts.parserBuilder().setSigningKey(key).build();
        Jws<Claims> claims = jwtParser.parseClaimsJws(token);

        Optional<User> oUser = this.facadeUser.findByLogin(claims.getBody().getSubject());

        if (!oUser.get().getRole().hasRole(Role.ADMIN)) {
            this.abort(requestContext);
        }

    }


    private void abort(ContainerRequestContext rc) {
        rc.abortWith(Response.status(Response.Status.UNAUTHORIZED).build());
    }
}
