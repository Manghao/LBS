package org.lpro.boundary.commande;

import org.lpro.boundary.authentification.UtilisateurManager;
import org.lpro.boundary.sandwich.SandwichManager;
import org.lpro.entity.*;
import org.lpro.provider.Secured;

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

    @Inject
    UtilisateurManager um;

    @Inject
    SandwichManager sm;

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
            return Response.status(Response.Status.NOT_FOUND).entity(
                    Json.createObjectBuilder()
                            .add("error", "La commande n'existe pas")
                            .build()
            ).build();
        }

        if (tokenParam.isEmpty() && tokenHeader.isEmpty()) {
            return Response.status(Response.Status.FORBIDDEN).entity(
                    Json.createObjectBuilder()
                            .add("error", "Le token n'existe pas")
                            .build()
            ).build();
        }

        String token = (tokenParam.isEmpty()) ? tokenHeader : tokenParam;

        if (!cmd.getToken().equals(token)) {
            return Response.status(Response.Status.FORBIDDEN).entity(
                    Json.createObjectBuilder()
                            .add("error", "Le token n'est pas le bon")
                            .build()
            ).build();
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
     * @apiError BadRequest L'heure de la commande est inférieure à la date courante.
     */
    @POST
    @Secured
    public Response addCommande(@Valid Commande commande) {
        Utilisateur utilisateur = this.um.findById(commande.getUtilisateur().getId());
        if (utilisateur == null) {
            return Response.status(Response.Status.BAD_REQUEST).entity(
                    Json.createObjectBuilder()
                            .add("error", "Utilisateur introuvable")
                            .build()
            ).build();
        }

        SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy HH:mm");
        sdf.setTimeZone(TimeZone.getDefault());

        Date current = Date.from(LocalDateTime.now()
                .atZone(ZoneId.systemDefault())
                    .toInstant());
        try {
            sdf.setLenient(false);
            Date dateCommande = sdf.parse(commande.getDateLivraison() + " " + commande.getHeureLivraison());
            Timestamp currentTimestamp = new Timestamp(current.getTime());
            Timestamp timestampCommande = new Timestamp(dateCommande.getTime());

            if (timestampCommande.before(currentTimestamp)) {
                return Response.status(Response.Status.BAD_REQUEST).entity(
                        Json.createObjectBuilder()
                                .add("error", "La date de la commande est inferieure a la date courante")
                                .build()
                ).build();
            }
        } catch (ParseException pe) {
            pe.printStackTrace();
            return Response.status(Response.Status.BAD_REQUEST).build();
        }

        Commande newCommande = this.cm.save(commande);
        URI uri = uriInfo.getAbsolutePathBuilder().path(newCommande.getId()).build();
        return Response.created(uri)
                .entity(newCommande)
                .build();
    }

    /**
     * @api {post} /commandes/:id/sandwichs Ajouter un sandwich à une commande
     * @apiName addSandwichToCommande
     * @apiGroup Commande
     *
     * @apiParam {String} id ID unique d'une commande.
     * @apiParam {String} token token unique d'une commande passé en paramètre de l'url.
     * @apiParam {String} token token unique d'une commande passé en paramètre dans le header.
     * @apiParam {Sandwich} s sandwich à ajouter à la commande.
     *
     * @apiSuccess {String} res String indiquant que le sandwich a bien été ajouté à la commande.
     * @apiError CommandeNotFound L'<code>id</code> de la commande n'existe pas.
     * @apiError CommandeForbidden Le <code>token</code> de la commande n'existe pas ou n'est pas le bon.
     * @apiError SandwichNotFound Le <code>sandwich</code> à ajouter n'existe pas.
     */
    @POST
    @Secured
    @Path("{id}/sandwichs")
    public Response addSandwichToCommande(
            @PathParam("id") String id,
            @DefaultValue("") @QueryParam("token") String tokenParam,
            @DefaultValue("") @HeaderParam("X-lbs-token") String tokenHeader,
            SandwichChoix sc
    ) {
        Commande cmd = this.cm.findById(id);
        if (cmd == null) {
            return Response.status(Response.Status.NOT_FOUND).entity(
                    Json.createObjectBuilder()
                            .add("error", "La commande n'existe pas")
                            .build()
            ).build();
        }
        if (tokenParam.isEmpty() && tokenHeader.isEmpty()) {
            return Response.status(Response.Status.FORBIDDEN).entity(
                    Json.createObjectBuilder()
                            .add("error", "Le token n'existe pas")
                            .build()
            ).build();
        }

        String token = (tokenParam.isEmpty()) ? tokenHeader : tokenParam;

        if (!cmd.getToken().equals(token)) {
            return Response.status(Response.Status.FORBIDDEN).entity(
                    Json.createObjectBuilder()
                            .add("error", "Le token n'est pas le bon")
                            .build()
            ).build();
        } else {
            sc = this.cm.addSandwichChoix(cmd, sc);

            if (sc != null) {
                return Response.ok(
                        Json.createObjectBuilder()
                                .add("success", "Le sandwich " + sc.getSandwich() + " a bien été ajouté à la commande")
                                .build()
                ).build();
            } else {
                return Response.status(Response.Status.NOT_FOUND).build();
            }
        }
    }

    /**
     * @api {delete} /commandes/:id/sandwichs/:id Supprimer un sandwich d'une commande
     * @apiName deleteSandwichToCommande
     * @apiGroup Commande
     *
     * @apiParam {String} catId ID unique d'une commande.
     * @apiParam {String} sandId ID unique d'un sandwich.
     * @apiParam {String} token token unique d'une commande passé en paramètre de l'url.
     * @apiParam {String} token token unique d'une commande passé en paramètre dans le header.
     *
     * @apiSuccess {String} res String indiquant que le sandwich a bien été supprimé de la commande.
     * @apiError CommandeNotFound L'<code>id</code> de la commande n'existe pas.
     * @apiError CommandeForbidden Le <code>token</code> de la commande n'existe pas ou n'est pas le bon.
     * @apiError SandwichNotFound Le <code>sandwich</code> à supprimer n'existe pas.
     * @apiError SandwichNotFound Le <code>sandwich</code> à supprimer n'existe pas dans la commande.
     */
    @DELETE
    @Secured
    @Path("{catId}/sandwichs/{sandId}")
    public Response deleteSandwichToCommande(
            @PathParam("catId") String catId,
            @PathParam("sandId") String sandId,
            @DefaultValue("") @QueryParam("token") String tokenParam,
            @DefaultValue("") @HeaderParam("X-lbs-token") String tokenHeader
    ) {
        Commande cmd = this.cm.findById(catId);
        if (cmd == null) {
            return Response.status(Response.Status.NOT_FOUND).entity(
                    Json.createObjectBuilder()
                            .add("error", "La commande n'existe pas")
                            .build()
            ).build();
        }
        if (tokenParam.isEmpty() && tokenHeader.isEmpty()) {
            return Response.status(Response.Status.FORBIDDEN).entity(
                    Json.createObjectBuilder()
                            .add("error", "Le token n'existe pas")
                            .build()
            ).build();
        }

        String token = (tokenParam.isEmpty()) ? tokenHeader : tokenParam;

        if (!cmd.getToken().equals(token)) {
            return Response.status(Response.Status.FORBIDDEN).entity(
                    Json.createObjectBuilder()
                            .add("error", "Le token n'est pas le bon")
                            .build()
            ).build();
        } else {
            Sandwich sand = this.sm.findById(sandId);

            if (sand != null) {
                boolean res = this.cm.deleteSandwich(cmd, sand);

                if (res) {
                    return Response.ok(
                            Json.createObjectBuilder()
                                    .add("success", "Le sandwich " + sand.getNom() + " a bien été supprimé de la commande")
                                    .build()
                    ).build();
                } else {
                    return Response.status(Response.Status.NOT_FOUND)
                            .entity(
                                    Json.createObjectBuilder()
                                            .add("error", "Le sandwich " + sand.getNom() + " n'existe pas dans la commande")
                                            .build()
                            ).build();
                }
            } else {
                return Response.status(Response.Status.NOT_FOUND).entity(
                        Json.createObjectBuilder()
                                .add("error", "Le sandwich n'existe pas")
                                .build()
                ).build();
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
    @Secured
    @Path("{id}")
    public Response updateCommande(
            @PathParam("id") String id,
            @DefaultValue("") @QueryParam("token") String tokenParam,
            @DefaultValue("") @HeaderParam("X-lbs-token") String tokenHeader,
            Commande c
    ) {
        Commande cmd = this.cm.findById(id);
        if (cmd == null) {
            return Response.status(Response.Status.NOT_FOUND).entity(
                    Json.createObjectBuilder()
                            .add("error", "La commande n'existe pas")
                            .build()
            ).build();
        }
        if (tokenParam.isEmpty() && tokenHeader.isEmpty()) {
            return Response.status(Response.Status.FORBIDDEN).entity(
                    Json.createObjectBuilder()
                            .add("error", "Le token n'existe pas")
                            .build()
            ).build();
        }

        String token = (tokenParam.isEmpty()) ? tokenHeader : tokenParam;

        if (!cmd.getToken().equals(token)) {
            return Response.status(Response.Status.FORBIDDEN).entity(
                    Json.createObjectBuilder()
                            .add("error", "Le token n'est pas le bon")
                            .build()
            ).build();
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

    private JsonObject buildUtilisateur(Utilisateur u) {
        return Json.createObjectBuilder()
                .add("id", u.getId())
                .add("nom", u.getNom())
                .add("prenom", u.getPrenom())
                .add("mail", u.getMail())
                .build();
    }

    private JsonObject buildSandwichsCommande(SandwichChoix st) {
        // TODO: Affichage du sandwich
        return Json.createObjectBuilder()
                .add("nom", st.getSandwich())
                .add("taille", st.getTaille())
                .add("prix", 0)
                .build();
    }

    private JsonObject buildJsonForCommande(Commande c) {
        return Json.createObjectBuilder()
                .add("id", c.getId())
                .add("livraison", buildJsonForLivraison(c))
                .add("token", c.getToken())
                .add("statut", c.getStatut().toString())
                .add("utilisateur", this.buildUtilisateur(c.getUtilisateur()))
                // .add("sandwichs", this.buildSandwichsCommande(c.getSandwichTaille()))
                .build();
    }

    private JsonObject buildJsonForLivraison(Commande c) {
        return Json.createObjectBuilder()
                .add("date", c.getDateLivraison())
                .add("heure", c.getHeureLivraison())
                .build();
    }
}