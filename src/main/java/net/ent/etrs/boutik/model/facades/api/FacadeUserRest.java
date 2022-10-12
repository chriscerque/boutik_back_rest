package net.ent.etrs.boutik.model.facades.api;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import net.ent.etrs.boutik.model.entities.User;
import net.ent.etrs.boutik.model.facades.FacadeUser;
import net.ent.etrs.boutik.model.facades.api.dtos.DtoUserLogin;
import net.ent.etrs.boutik.utils.Hash;

import javax.crypto.SecretKey;
import javax.inject.Inject;
import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.core.HttpHeaders;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.Date;
import java.util.Optional;
import java.util.concurrent.TimeUnit;

@Path("/users")
public class FacadeUserRest {

    public static final String SECRET_KEY = "ghJGZrAzghMI2YU29fl8dT1G90on2qRQM0QvUPonH8BUhKG9UTD33aWoyj2ym28CGL7X/sJxgfkNRneeuqw8DA==";

    @Inject
    private FacadeUser facadeUser;

    @POST
    @Path("/login")
    @Consumes(MediaType.APPLICATION_JSON)
    public Response login(DtoUserLogin dtoUserLogin) {
        Optional<User> oUser = this.facadeUser.findByLogin(dtoUserLogin.getLogin());
        if (oUser.isPresent()) {
            User user = oUser.get();
            if (Hash.checkPassword(dtoUserLogin.getPassword(), user.getPassword())) {

                String token = this.issueToken(user);
                return Response.ok().header(HttpHeaders.AUTHORIZATION, "Bearer " + token).build();
            } else {
                return Response.status(Response.Status.UNAUTHORIZED).build();
            }
        } else {
            return Response.status(Response.Status.UNAUTHORIZED).build();
        }


    }


    private String issueToken(User user) {
        SecretKey key = Keys.hmacShaKeyFor(Decoders.BASE64.decode(SECRET_KEY));

        return Jwts.builder()
                .setSubject(user.getLogin())
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + TimeUnit.MINUTES.toMillis(20)))
                .signWith(key)
                .claim("role", user.getRole()).compact();
    }
}
