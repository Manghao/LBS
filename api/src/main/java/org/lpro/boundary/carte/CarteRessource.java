
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.lpro.control.KeyManagement;
import org.lpro.control.PasswordManagement;
import org.lpro.entity.Carte;
import org.lpro.entity.Commande;
import org.lpro.entity.Utilisateur;
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
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
@Path("card")
public class CarteRessource {

    @Context
    private UriInfo uriInfo;

    @Inject
    private KeyManagement keyManagement;

    @Inject
    CarteManager cm;

    /**
     * @api {post} /card Récuperer une carte de fidélité
     * @apiName getCard
     * @apiGroup Carte
     *
     * @apiSuccess {Carte} carte Une carte de fidélité.
     * @apiError Unauthorized le numéro de carte ou le mot de passe est invalide
     */
    @POST
    @Produces("application/json")
    @Consumes("application/json")
    @Secured
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

    /**
     * @api {post} /card/create Créer une carte de fidélité
     * @apiName createCarte
     * @apiGroup Carte
     *
     * @apiSuccess {Carte} carte Une carte de fidélité.
     * @apiError Forbidden Impossible de créer une carte de fidélité
     */
    @POST
    @Path("/create")
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
