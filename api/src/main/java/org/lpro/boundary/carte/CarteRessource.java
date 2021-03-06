package org.lpro.boundary.carte;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.lpro.boundary.carte.CarteManager;
import org.lpro.control.KeyManagement;
import org.lpro.control.PasswordManagement;
import org.lpro.entity.Carte;
import org.lpro.provider.Secured;
import org.mindrot.jbcrypt.BCrypt;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.json.Json;
import javax.json.JsonObject;
import javax.validation.Valid;
import javax.ws.rs.*;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;

import java.security.Key;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;

import static javax.ws.rs.core.HttpHeaders.AUTHORIZATION;

@Stateless
@Path("card")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
@Api(value = "Carte")
public class CarteRessource {

    @Context
    private UriInfo uriInfo;

    @Inject
    private KeyManagement keyManagement;

    @Inject
    CarteManager cm;

    @POST
    @Produces("application/json")
    @Consumes("application/json")
    @Secured
    @ApiOperation(value = "Récupère une carte de fidélité", notes = "Récupère une carte de fidélité à partir du JSON fourni")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "OK"),
            @ApiResponse(code = 401, message = "Unauthorized"),
            @ApiResponse(code = 500, message = "Internal server error")})
    public Response getCard(@Valid Carte carte) {
        try {
            String numCarte = carte.getNumCarte();
            String password = carte.getPassword();

            String digest = PasswordManagement.digestPassword(password);
            System.out.println("hash carte " + digest);
            Carte one = this.cm.findCarte(carte);

            if (one != null) {
                this.authentifie(numCarte, password, one);

                String token = this.issueToken(numCarte);
                return Response.ok(buildJsonForCarte(one)).header(AUTHORIZATION, "Bearer " + token).build();
            } else {
                return Response.status(Response.Status.UNAUTHORIZED).build();
            }
        } catch (Exception e) {
            return Response.status(Response.Status.UNAUTHORIZED).build();
        }
    }

    @POST
    @Path("/create")
    @ApiOperation(value = "Crée une carte de fidélité", notes = "Crée une carte de fidélité à partir du JSON fourni")
    @ApiResponses(value = {
            @ApiResponse(code = 201, message = "Created"),
            @ApiResponse(code = 403, message = "Forbidden"),
            @ApiResponse(code = 500, message = "Internal server error")})
    public Response createCarte(@Valid Carte carte) {
        Carte c = this.cm.createCarte(carte);
        return (c != null) ? Response.ok().entity(buildJsonForCarte(c)).status(Response.Status.CREATED).build() : Response.status(Response.Status.FORBIDDEN).build();
    }

    private void authentifie(String numCarte, String password, Carte carte) throws Exception {
        if (carte != null) {
            if (numCarte.equals(carte.getNumCarte()) && BCrypt.checkpw(password, carte.getPassword())) {

            } else {
                throw new NotAuthorizedException("Problème d'authentification");
            }
        } else {
            throw new NotAuthorizedException("Utilisateur introuvable");
        }
    }

    private String issueToken(String numCarte) {
        Key key = keyManagement.generateKey();
        String jwtToken = Jwts.builder()
                .setSubject(numCarte)
                .setIssuer(uriInfo.getAbsolutePath().toString())
                .setIssuedAt(new Date())
                .setExpiration(toDate(LocalDateTime.now().plusMinutes(5L)))
                .signWith(SignatureAlgorithm.HS512, key)
                .compact();
        System.out.println(">>>> token/key carte : " + jwtToken + " -- " + key);
        return jwtToken;
    }

    private Date toDate(LocalDateTime localDateTime) {
        return Date.from(localDateTime.atZone(ZoneId.systemDefault()).toInstant());
    }

    private JsonObject buildJsonForCarte(Carte c) {
        return Json.createObjectBuilder()
                .add("numcare", c.getNumCarte())
                .add("motant", c.getMontant())
                .build();
    }

}
