package net.ent.etrs.boutik.model.facades.api.filters;

import io.jsonwebtoken.*;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import io.jsonwebtoken.security.SignatureException;
import net.ent.etrs.boutik.model.entities.User;
import net.ent.etrs.boutik.model.facades.FacadeUser;
import net.ent.etrs.boutik.model.facades.api.FacadeUserRest;
import net.ent.etrs.boutik.model.facades.api.filters.annotations.JWTTokenNeeded;

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
@JWTTokenNeeded
@Priority(Priorities.AUTHENTICATION)
public class JWTFilter implements ContainerRequestFilter {

    @Inject
    private FacadeUser facadeUser;


    @Override
    public void filter(ContainerRequestContext requestContext) throws IOException {
        try {
            String authorizationHeader = requestContext.getHeaderString(HttpHeaders.AUTHORIZATION);
            String token = authorizationHeader.substring("Bearer".length()).trim();
            SecretKey key = Keys.hmacShaKeyFor(Decoders.BASE64.decode(FacadeUserRest.SECRET_KEY));
            JwtParser jwtParser = Jwts.parserBuilder().setSigningKey(key).build();
            Jws<Claims> claims = jwtParser.parseClaimsJws(token);

            Optional<User> oUser = this.facadeUser.findByLogin(claims.getBody().getSubject());

            if (oUser.isEmpty()) {
                this.abort(requestContext);
            }

        } catch (NullPointerException | ClassCastException | UnsupportedJwtException e) {
            this.abort(requestContext);
        } catch (MalformedJwtException e) {
            this.abort(requestContext);
        } catch (IllegalArgumentException e) {
            this.abort(requestContext);
        } catch (SignatureException e) {
            this.abort(requestContext);
        } catch (ExpiredJwtException e) {
            this.abort(requestContext);
        } catch (Exception e) {
            this.abort(requestContext);
        }
    }


    private void abort(ContainerRequestContext rc) {
        rc.abortWith(Response.status(Response.Status.UNAUTHORIZED).build());
    }
}
