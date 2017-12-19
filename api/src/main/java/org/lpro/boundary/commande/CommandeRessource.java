package org.lpro.boundary.commande;

import org.lpro.entity.Commande;

import java.net.URI;
import java.sql.Timestamp;
import java.text.ParseException;
import java.util.Date;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.TimeZone;
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

@Stateless
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
@Path("commandes")
public class CommandeRessource {

    @Inject
    CommandeManager cm;
    @Context
    UriInfo uriInfo;

    @GET
    @Path("/{commandeId}")
    public Response getOneCommande(
        @PathParam("commandeId") String commandeId,
        @DefaultValue("") @QueryParam("token") String tokenParam,
        @DefaultValue("") @HeaderParam("X-lbs-token") String tokenHeader
    ) {

        Commande cmd = this.cm.findById(commandeId);
        if (cmd == null) {
            return Response.status(Response.Status.NOT_FOUND).build();
        }

        if (tokenParam.isEmpty() && tokenHeader.isEmpty()) {
            return Response.status(Response.Status.FORBIDDEN).build();
        }

        String token = (tokenParam.isEmpty()) ? tokenHeader : tokenParam;
        boolean isValid = cmd.getToken().equals(token);
        if (!isValid) {
            return Response.status(Response.Status.FORBIDDEN).build();
        } else {
            return Response.ok(this.buildCommandeObject(cmd)).build();
        }
    }

    @POST
    public Response addCommande(@Valid Commande commande) {
        try {
            SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy HH:mm");
            sdf.setTimeZone(TimeZone.getDefault());

            Date current = Date.from(LocalDateTime.now()
                    .atZone(ZoneId.systemDefault())
                    .toInstant());

            Date dateCommande = sdf.parse(commande.getDateLivraison() + " " + commande.getHeureLivraison());

            Timestamp currentTimestamp = new Timestamp(current.getTime());
            Timestamp timestampCommande = new Timestamp(dateCommande.getTime());

            if (timestampCommande.before(currentTimestamp)) {
                return Response.status(Response.Status.BAD_REQUEST).build();
            }
        } catch (ParseException pe) { }

        Commande newCommande = this.cm.save(commande);
        URI uri = uriInfo.getAbsolutePathBuilder().path(newCommande.getId()).build();
        return Response.created(uri)
                .entity(newCommande)
                .build();
    }

    private JsonObject buildCommandeObject(Commande c) {
        return Json.createObjectBuilder()
                .add("commande", buildJsonForCommande(c))
                .build();
    }

    private JsonObject buildJsonForCommande(Commande c) {
        return Json.createObjectBuilder()
                .add("id", c.getId())
                .add("nom_client", c.getNom())
                .add("mail_client", c.getMail())
                .add("livraison", buildJsonForLivraison(c))
                .add("token", c.getToken())
                .build();
    }

    private JsonObject buildJsonForLivraison(Commande c) {
        return Json.createObjectBuilder()
                .add("date", c.getDateLivraison())
                .add("heure", c.getHeureLivraison())
                .build();
    }
}