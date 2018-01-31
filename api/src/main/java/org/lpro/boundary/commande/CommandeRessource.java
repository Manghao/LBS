package org.lpro.boundary.commande;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.lpro.boundary.authentification.UtilisateurManager;
import org.lpro.boundary.carte.CarteManager;
import org.lpro.boundary.sandwich.SandwichManager;
import org.lpro.boundary.sandwichChoix.SandwichChoixManager;
import org.lpro.boundary.taille.TailleManager;
import org.lpro.entity.*;
import org.lpro.enums.CommandeStatut;
import org.lpro.provider.Secured;

import java.net.URI;
import java.sql.Timestamp;
import java.text.ParseException;
import java.util.*;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.ZoneId;
import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.json.*;
import javax.validation.Valid;
import javax.ws.rs.*;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;

@Stateless
@Path("commandes")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
@Api(value = "Commande")
public class CommandeRessource {

    @Inject
    CommandeManager cm;

    @Inject
    UtilisateurManager um;

    @Inject
    SandwichManager sm;

    @Inject
    TailleManager tm;

    @Inject
    SandwichChoixManager scm;

    @Inject
    CarteManager cardman;

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
    @ApiOperation(value = "Récupère une commande", notes = "Renvoie le JSON associé à la commande")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "OK"),
            @ApiResponse(code = 403, message = "Forbidden"),
            @ApiResponse(code = 404, message = "Not Found"),
            @ApiResponse(code = 500, message = "Internal server error")})
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
     * @apiName newCommande
     * @apiGroup Commande
     *
     * @apiSuccess {Commande} commande Une commande.
     * @apiError BadRequest L'heure de la commande est inférieure à la date courante.
     */
    @POST
    @ApiOperation(value = "Crée une commande", notes = "Crée une commande à partir du JSON fourni")
    @ApiResponses(value = {
            @ApiResponse(code = 201, message = "Created"),
            @ApiResponse(code = 400, message = "Bad Request"),
            @ApiResponse(code = 500, message = "Internal server error")})
    public Response newCommande(@Valid Commande commande) {
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
                                .add("error", "La date de la commande est inférieure a la date courante")
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
                .entity(buildCommandeObject(newCommande))
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
    @Path("{id}/sandwichs")
    @ApiOperation(value = "Ajoute un sandwich à une commande", notes = "Ajoute un sandwich à une commande à partir du JSON fourni")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "OK"),
            @ApiResponse(code = 400, message = "Bad Request"),
            @ApiResponse(code = 403, message = "Forbidden"),
            @ApiResponse(code = 404, message = "Not Found"),
            @ApiResponse(code = 500, message = "Internal server error")})
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
            if (cmd.getStatut() == CommandeStatut.ATTENTE) {
                if (sc.getQte() > 0) {
                    sc = this.cm.addSandwichChoix(cmd, sc);

                    if (sc != null) {
                        return Response.ok(buildCommandeObject(cmd)).build();
                    } else {
                        return Response.status(Response.Status.NOT_FOUND).entity(
                                Json.createObjectBuilder()
                                        .add("error", "Le sandwich ou la taille est introuvable")
                                        .build()
                        ).build();
                    }
                } else {
                    return Response.status(Response.Status.BAD_REQUEST).entity(
                            Json.createObjectBuilder()
                                    .add("error", "Quantité invalide")
                                    .build()
                    ).build();
                }
            } else {
                return Response.status(Response.Status.FORBIDDEN).entity(
                        Json.createObjectBuilder()
                                .add("error", "La commande a été payée")
                                .build()
                ).build();
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
    @Path("{cmdId}/sandwichs/{sandId}")
    @ApiOperation(value = "Supprime un sandwich d'une commande", notes = "Supprime un sandwich d'une commande à partir de l'id du sandwich fourni")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "OK"),
            @ApiResponse(code = 403, message = "Forbidden"),
            @ApiResponse(code = 404, message = "Not Found"),
            @ApiResponse(code = 500, message = "Internal server error")})
    public Response deleteSandwichToCommande(
            @PathParam("cmdId") String cmdId,
            @PathParam("sandId") String sandId,
            @DefaultValue("") @QueryParam("token") String tokenParam,
            @DefaultValue("") @HeaderParam("X-lbs-token") String tokenHeader
    ) {
        Commande cmd = this.cm.findById(cmdId);
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
            if (cmd.getStatut() == CommandeStatut.ATTENTE) {
                List<SandwichChoix> lsc = this.scm.findAllById(sandId);

                if (lsc != null) {
                    Iterator<SandwichChoix> iterator = lsc.iterator();

                    boolean res = false;
                    while (iterator.hasNext()) {
                        res = this.cm.deleteSandwich(cmd, iterator.next());
                    }

                    if (res) {
                        return Response.ok(buildCommandeObject(cmd)).build();
                    } else {
                        return Response.status(Response.Status.NOT_FOUND)
                                .entity(
                                        Json.createObjectBuilder()
                                                .add("error", "Le sandwich n'existe pas dans la commande")
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
            } else {
                return Response.status(Response.Status.FORBIDDEN).entity(
                        Json.createObjectBuilder()
                                .add("error", "La commande a été payée")
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
    @Path("{id}")
    @ApiOperation(value = "Modifie la date de livraison d'une commande", notes = "Modifie la date de livraison d'une commande à partir du JSON fourni")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "OK"),
            @ApiResponse(code = 403, message = "Forbidden"),
            @ApiResponse(code = 404, message = "Not Found"),
            @ApiResponse(code = 500, message = "Internal server error")})
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
            if (cmd.getStatut() == CommandeStatut.ATTENTE) {
                cmd.setDateLivraison(c.getDateLivraison());
                cmd.setHeureLivraison(c.getHeureLivraison());

                return Response.ok(this.buildCommandeObject(cmd)).build();
            } else {
                return Response.status(Response.Status.FORBIDDEN).entity(
                        Json.createObjectBuilder()
                                .add("error", "La commande a été payée")
                                .build()
                ).build();
            }
        }
    }

    @POST
    @Path("{id}/checkout")
    @ApiOperation(value = "Paiement d'une commande", notes = "Peiement d'une commande à partir des données de carte bancaire fourni dans le JSON")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "OK"),
            @ApiResponse(code = 400, message = "Bad Request"),
            @ApiResponse(code = 403, message = "Forbidden"),
            @ApiResponse(code = 404, message = "Not Found"),
            @ApiResponse(code = 500, message = "Internal server error")})
    public Response checkoutCommande(
            @PathParam("id") String id,
            @DefaultValue("") @QueryParam("token") String tokenParam,
            @DefaultValue("") @HeaderParam("X-lbs-token") String tokenHeader,
            JsonObject payCard
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
            if (cmd.getStatut() == CommandeStatut.ATTENTE) {
                if (payCard.get("nom") != null && payCard.get("numeroCarte") != null && payCard.get("cvv") != null && payCard.get("dateExpiration") != null && payCard.get("numeroCarteFidelite") != null) {
                    if (payCard.getString("nom").matches("([a-zA-ZáàâäãåçéèêëíìîïñóòôöõúùûüýÿæœÁÀÂÄÃÅÇÉÈÊËÍÌÎÏÑÓÒÔÖÕÚÙÛÜÝŸÆŒ\\s-]+)") && payCard.getString("numeroCarte").matches("(([0-9]{4}(\\s|-)){3}([0-9]{4}))") && payCard.getString("cvv").matches("([0-9]{3})")) {

                        SimpleDateFormat sdf = new SimpleDateFormat("MM-yy");
                        sdf.setTimeZone(TimeZone.getDefault());

                        Date current = Date.from(LocalDateTime.now()
                                .atZone(ZoneId.systemDefault())
                                .toInstant());
                        try {
                            sdf.setLenient(false);
                            Date dateExpiration = sdf.parse(payCard.getString("dateExpiration"));
                            Timestamp currentTimestamp = new Timestamp(current.getTime());
                            Timestamp timestampExpiration = new Timestamp(dateExpiration.getTime());

                            if (timestampExpiration.after(currentTimestamp)) {
                                Carte card = this.cardman.findByNumCarte(payCard.getString("numeroCarteFidelite"));

                                if (card != null) {
                                    double total = cmd.getSandwichChoix().stream().mapToDouble(sc -> (this.tm.findById(sc.getTaille()).getPrix() * sc.getQte())).sum();

                                    card.setMontant(card.getMontant() + total);
                                    cmd.setStatut(CommandeStatut.PAYEE);

                                    return Response.ok(buildCommandeObject(cmd)).build();
                                } else {
                                    return Response.status(Response.Status.NOT_FOUND).entity(
                                            Json.createObjectBuilder()
                                                    .add("error", "La carte de fidélité est introuvable")
                                                    .build()
                                    ).build();
                                }
                            } else {
                                return Response.status(Response.Status.FORBIDDEN).entity(
                                        Json.createObjectBuilder()
                                                .add("error", "La date d'expiration est inférieure a la date courante")
                                                .build()
                                ).build();
                            }
                        } catch (ParseException pe) {
                            pe.printStackTrace();
                            return Response.status(Response.Status.BAD_REQUEST).build();
                        }
                    } else {
                        return Response.status(Response.Status.FORBIDDEN).entity(
                                Json.createObjectBuilder()
                                        .add("error", "Informations de paiement incorectes")
                                        .build()
                        ).build();
                    }
                } else {
                    return Response.status(Response.Status.FORBIDDEN).entity(
                            Json.createObjectBuilder()
                                    .add("error", "Informations de paiement manquantes")
                                    .build()
                    ).build();
                }
            } else {
                return Response.status(Response.Status.FORBIDDEN).entity(
                        Json.createObjectBuilder()
                                .add("error", "La commande a déjà été payée")
                                .build()
                ).build();
            }
        }
    }

    private JsonObject buildCommandeObject(Commande c) {
        return Json.createObjectBuilder()
                .add("commande", buildJsonForCommande(c))
                .build();
    }

    private JsonObject buildClient(Commande c) {
        return Json.createObjectBuilder()
                .add("nom", c.getNom())
                .add("prenom", c.getPrenom())
                .add("mail", c.getMail())
                .build();
    }

    private JsonArray buildSandwichsCommande(Set<SandwichChoix> setSC) {
        JsonArrayBuilder jab = Json.createArrayBuilder();
        double total = setSC.stream().mapToDouble(sc -> (this.tm.findById(sc.getTaille()).getPrix() * sc.getQte())).sum();
        setSC.forEach((sc) -> {
            Sandwich s = this.sm.findById(sc.getSandwich());
            Taille t = this.tm.findById(sc.getTaille());

            jab.add(Json.createObjectBuilder()
                    .add("nom", s.getNom())
                    .add("taille", t.getNom())
                    .add("quantité", sc.getQte())
                    .add("prix", t.getPrix() * sc.getQte())
                    .add("sandwich", Json.createObjectBuilder().add("href", "/sandwichs/" + s.getId()))
                    .build()
            );
        });

        jab.add(Json.createObjectBuilder().add("total", total));

        return jab.build();
    }

    private JsonObject buildJsonForCommande(Commande c) {
        return Json.createObjectBuilder()
                .add("id", c.getId())
                .add("livraison", buildJsonForLivraison(c))
                .add("token", c.getToken())
                .add("statut", c.getStatut().toString())
                .add("client", this.buildClient(c))
                .add("sandwichs", this.buildSandwichsCommande(c.getSandwichChoix()))
                .build();
    }

    private JsonObject buildJsonForLivraison(Commande c) {
        return Json.createObjectBuilder()
                .add("date", c.getDateLivraison())
                .add("heure", c.getHeureLivraison())
                .add("adresse", c.getAdresseLivraison())
                .build();
    }
}