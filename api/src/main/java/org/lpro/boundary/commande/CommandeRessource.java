package org.lpro.boundary.commande;

import org.lpro.boundary.sandwich.SandwichRessource;
import org.lpro.entity.Commande;
import org.lpro.entity.Sandwich;

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

    /**
     * @api {get} /commandes/:id Récupérer une commande
     * @apiName getOneCommande
     * @apiGroup Commande
     *
     * @apiParam {String} id ID unique d'une commande.
     * @apiParam {String} token token unique d'une commande passé en paramètre de l'url.
     * @apiParam {String} token token unique d'une commande passé en paramètre dans le header.
     *
     * @apiSuccess {Commande} commande Une commande.
     * @apiError CommandeNotFound L'<code>id</code> de la commande n'existe pas.
     * @apiError CommandeForbidden Le <code>token</code> de la commande n'existe pas ou n'est pas le bon.
     */
    @GET
    @Path("{id}")
    public Response getOneCommande(
        @PathParam("id") String id,
        @DefaultValue("") @QueryParam("token") String tokenParam,
        @DefaultValue("") @HeaderParam("X-lbs-token") String tokenHeader
    ) {
        Commande cmd = this.cm.findById(id);
        if (cmd == null) {
            return Response.status(Response.Status.NOT_FOUND).build();
        }

        if (tokenParam.isEmpty() && tokenHeader.isEmpty()) {
            return Response.status(Response.Status.FORBIDDEN).build();
        }

        String token = (tokenParam.isEmpty()) ? tokenHeader : tokenParam;

        if (!cmd.getToken().equals(token)) {
            return Response.status(Response.Status.FORBIDDEN).build();
        } else {
            return Response.ok(this.buildCommandeObject(cmd)).build();
        }
    }

    /**
     * @api {post} /commandes Créer une nouvelle commande
     * @apiName addCommande
     * @apiGroup Commande
     *
     * @apiSuccess {Commande} commande Une commande.
     */
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

    @POST
    @Path("{id}/sandwichs")
    public Response addSandwichToCommande(
            @PathParam("id") String id,
            @DefaultValue("") @QueryParam("token") String tokenParam,
            @DefaultValue("") @HeaderParam("X-lbs-token") String tokenHeader,
            Sandwich s
    ) {
        Commande cmd = this.cm.findById(id);
        if (cmd == null) {
            return Response.status(Response.Status.NOT_FOUND).build();
        }
        if (tokenParam.isEmpty() && tokenHeader.isEmpty()) {
            return Response.status(Response.Status.FORBIDDEN).build();
        }

        String token = (tokenParam.isEmpty()) ? tokenHeader : tokenParam;

        if (!cmd.getToken().equals(token)) {
            return Response.status(Response.Status.FORBIDDEN).build();
        } else {
            Sandwich sand = this.cm.addSandwich(id, s.getId());

            if (sand != null) {
                return Response.ok(
                        Json.createObjectBuilder()
                                .add("résultat", "Sandwich " + sand.getNom() + " bien ajouté à la commande")
                                .build()
                ).build();
            } else {
                return Response.status(Response.Status.NOT_FOUND).build();
            }
        }
    }


    /**
     * @api {put} /commandes/:id Modifier la date et l'heure de livraison d'une commande
     * @apiName updateCommande
     * @apiGroup Commande
     *
     * @apiParam {String} id ID unique d'une commande.
     * @apiParam {String} token token unique d'une commande passé en paramètre de l'url.
     * @apiParam {String} token token unique d'une commande passé en paramètre dans le header.
     * @apiParam {Commande} c commande contenant la nouvelle date et heure de livraison.
     *
     * @apiSuccess {Commande} commande Une commande.
     * @apiError CommandeNotFound L'<code>id</code> de la commande n'existe pas.
     * @apiError CommandeForbidden Le <code>token</code> de la commande n'existe pas ou n'est pas le bon.
     */
    @PUT
    @Path("{id}")
    public Response updateCommande(
            @PathParam("id") String id,
            @DefaultValue("") @QueryParam("token") String tokenParam,
            @DefaultValue("") @HeaderParam("X-lbs-token") String tokenHeader,
            Commande c
    ) {
        Commande cmd = this.cm.findById(id);
        if (cmd == null) {
            return Response.status(Response.Status.NOT_FOUND).build();
        }
        if (tokenParam.isEmpty() && tokenHeader.isEmpty()) {
            return Response.status(Response.Status.FORBIDDEN).build();
        }

        String token = (tokenParam.isEmpty()) ? tokenHeader : tokenParam;

        if (!cmd.getToken().equals(token)) {
            return Response.status(Response.Status.FORBIDDEN).build();
        } else {
            cmd.setDateLivraison(c.getDateLivraison());
            cmd.setHeureLivraison(c.getHeureLivraison());

            return Response.ok(this.buildCommandeObject(cmd)).build();
        }
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