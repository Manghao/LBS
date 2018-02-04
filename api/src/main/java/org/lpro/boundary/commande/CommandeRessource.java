package org.lpro.boundary.commande;

import io.swagger.annotations.*;
import org.lpro.boundary.authentification.UtilisateurManager;
import org.lpro.boundary.carte.CarteManager;
import org.lpro.boundary.sandwich.SandwichManager;
import org.lpro.boundary.sandwichChoix.SandwichChoixManager;
import org.lpro.boundary.taille.TailleManager;
import org.lpro.entity.*;
import org.lpro.entity.apiModels.*;
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

    @GET
    @Secured
    @ApiOperation(value = "Récupère toutes les commandes", notes = "Renvoie le JSON associé à la collection de commandes")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "OK"),
            @ApiResponse(code = 403, message = "Forbidden"),
            @ApiResponse(code = 404, message = "Not Found"),
            @ApiResponse(code = 500, message = "Internal server error")})
    public Response getCommandes(
            @DefaultValue("") @QueryParam("token") String tokenParam,
            @DefaultValue("") @HeaderParam("X-lbs-token") String tokenHeader,
            @QueryParam("statut") String statut
    ) {
        List<Commande> commandes = this.cm.findAll(statut);

        return Response.ok(buildJsonCommandes(commandes)).build();
    }

    @GET
    @Path("{id}")
    @Secured
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
            return Response.ok(this.buildCommandeObject(cmd, false)).build();
        }
    }

    @GET
    @Path("{id}/statut")
    @ApiOperation(value = "Récupère l'état d'une commande", notes = "Renvoie le JSON associé à l'état de la commande")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "OK"),
            @ApiResponse(code = 403, message = "Forbidden"),
            @ApiResponse(code = 404, message = "Not Found"),
            @ApiResponse(code = 500, message = "Internal server error")})
    public Response getCommandeStatut(
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
            return Response.ok(buildJsonCommandeStatut(cmd)).build();
        }
    }

    @PUT
    @Path("{id}/statut")
    @Secured
    @ApiOperation(value = "Change le statut d'une commande", notes = "Change le statut d'une commande à partir du JSON fourni")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "OK"),
            @ApiResponse(code = 403, message = "Forbidden"),
            @ApiResponse(code = 404, message = "Not Found"),
            @ApiResponse(code = 500, message = "Internal server error")})
    public Response updateCommandeStatut(
            @PathParam("id") String id,
            @DefaultValue("") @QueryParam("token") String tokenParam,
            @DefaultValue("") @HeaderParam("X-lbs-token") String tokenHeader,
            CommandeUpdateStatut updateStatut
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
            if (updateStatut != null) {
                if (cmd.getStatut() != CommandeStatut.EXPEDIEE) {
                    switch (updateStatut.getStatut().toUpperCase()) {
                        case "ATTENTE":
                            cmd.setStatut(CommandeStatut.ATTENTE);
                            break;
                        case "PAYEE":
                            cmd.setStatut(CommandeStatut.PAYEE);
                            break;
                        case "PREPARATION":
                            cmd.setStatut(CommandeStatut.PREPARATION);
                            break;
                        case "EXPEDIEE":
                            cmd.setStatut(CommandeStatut.EXPEDIEE);
                            break;
                        default:
                            return Response.status(Response.Status.NOT_FOUND).entity(
                                    Json.createObjectBuilder()
                                            .add("error", "Le statut n'existe pas")
                                            .build()
                            ).build();
                    }

                    this.cm.update(cmd);

                    return Response.ok(buildCommandeObject(cmd, false)).build();
                } else {
                    return Response.status(Response.Status.FORBIDDEN).entity(
                            Json.createObjectBuilder()
                                    .add("error", "La commande a déjà été expédiée")
                                    .build()
                    ).build();
                }
            } else {
                return Response.status(Response.Status.FORBIDDEN).entity(
                        Json.createObjectBuilder()
                                .add("error", "Le statut est introuvable")
                                .build()
                ).build();
            }
        }
    }

    @POST
    @ApiOperation(value = "Crée une commande", notes = "Crée une commande à partir du JSON fourni")
    @ApiResponses(value = {
            @ApiResponse(code = 201, message = "Created"),
            @ApiResponse(code = 400, message = "Bad Request"),
            @ApiResponse(code = 500, message = "Internal server error")})
    public Response newCommande(NewCommande commande) {
        SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy HH:mm");
        sdf.setTimeZone(TimeZone.getDefault());

        Date current = Date.from(LocalDateTime.now()
                .atZone(ZoneId.systemDefault())
                .toInstant());

        Commande c;

        try {
            sdf.setLenient(false);
            Date dateCommande = sdf.parse(commande.getDateLivraison() + " " + commande.getHeureLivraison());
            Timestamp currentTimestamp = new Timestamp(current.getTime());
            Timestamp timestampCommande = new Timestamp(dateCommande.getTime());

            c = new Commande(timestampCommande, commande.getAdresseLivraison(), commande.getNom(), commande.getPrenom(), commande.getMail(), currentTimestamp);

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

        Commande newCommande = this.cm.save(c);
        URI uri = uriInfo.getAbsolutePathBuilder().path(newCommande.getId()).build();
        return Response.created(uri)
                .entity(buildCommandeObject(newCommande, false))
                .build();
    }

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
                    Sandwich s = this.sm.findById(sc.getSandwich());

                    if (s != null) {
                        Taille t = this.tm.findById(sc.getTaille());

                        if (t != null) {
                            if (s.getTaille().contains(t)) {
                                this.cm.addSandwichChoix(cmd, sc);

                                return Response.ok(buildCommandeObject(cmd, false)).build();
                            } else {
                                return Response.status(Response.Status.FORBIDDEN).entity(
                                        Json.createObjectBuilder()
                                                .add("error", "La taille est indisponible pour ce sandwich")
                                                .build()
                                ).build();
                            }
                        } else {
                            return Response.status(Response.Status.NOT_FOUND).entity(
                                    Json.createObjectBuilder()
                                            .add("error", "La taille est introuvable")
                                            .build()
                            ).build();
                        }
                    } else {
                        return Response.status(Response.Status.NOT_FOUND).entity(
                                Json.createObjectBuilder()
                                        .add("error", "Le sandwich est introuvable")
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
            @DefaultValue("") @HeaderParam("X-lbs-token") String tokenHeader,
            JsonObject taille
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
                List<SandwichChoix> lsc;

                if (taille.get("taille") != null) {
                    lsc = this.scm.findAllById(sandId, taille.getString("taille"));
                } else {
                    lsc = this.scm.findAllById(sandId);
                }

                if (lsc != null) {
                    Iterator<SandwichChoix> iterator = lsc.iterator();

                    boolean res = false;
                    while (iterator.hasNext()) {
                        res = this.cm.deleteSandwich(cmd, iterator.next());
                    }

                    if (res) {
                        return Response.ok(buildCommandeObject(cmd, false)).build();
                    } else {
                        return Response.status(Response.Status.NOT_FOUND)
                                .entity(
                                        Json.createObjectBuilder()
                                                .add("error", "Le sandwich ou la taille n'existe pas dans la commande")
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

    @PUT
    @Path("{cmdId}/sandwichs/{scId}")
    @ApiOperation(value = "Modifie un sandwich d'une commande", notes = "Modifie un sandwich d'une commande à partir du JSON fourni")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "OK"),
            @ApiResponse(code = 400, message = "Bad Request"),
            @ApiResponse(code = 403, message = "Forbidden"),
            @ApiResponse(code = 404, message = "Not Found"),
            @ApiResponse(code = 500, message = "Internal server error")})
    public Response updateCommandeSandwich(
            @PathParam("cmdId") String cmdId,
            @PathParam("scId") String scId,
            @DefaultValue("") @QueryParam("token") String tokenParam,
            @DefaultValue("") @HeaderParam("X-lbs-token") String tokenHeader,
            SandwichUpdate json
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
                SandwichChoix sc = this.scm.findById(scId);

                if (json != null) {
                    if (sc != null) {
                        if (json.getQte() != 0) {
                            if (json.getQte() > 0) {
                                sc.setQte(json.getQte());
                            } else {
                                return Response.status(Response.Status.BAD_REQUEST).entity(
                                        Json.createObjectBuilder()
                                                .add("error", "Quantité invalide")
                                                .build()
                                ).build();
                            }
                        }
                        if (json.getTaille() != null) {
                            Taille t = this.tm.findById(json.getTaille());

                            if (t != null) {
                                Sandwich s = this.sm.findById(sc.getSandwich());

                                if (s.getTaille().contains(t)) {
                                    SandwichChoix sc2 = this.scm.findById(sc.getSandwich(), json.getTaille());

                                    if (sc2 != null && cmd.getSandwichChoix().contains(sc2)) {
                                        sc2.setQte(sc2.getQte() + sc.getQte());
                                        this.scm.update(sc2);
                                        if (!sc2.getId().equals(sc.getId())) {
                                            this.scm.delete(cmd, sc.getId());
                                        }

                                        return Response.ok(buildCommandeObject(cmd, false)).build();
                                    } else {
                                        sc.setTaille(json.getTaille());
                                    }
                                } else {
                                    return Response.status(Response.Status.FORBIDDEN).entity(
                                            Json.createObjectBuilder()
                                                    .add("error", "La taille est indisponible pour ce sandwich")
                                                    .build()
                                    ).build();
                                }
                            } else {
                                return Response.status(Response.Status.NOT_FOUND)
                                        .entity(
                                                Json.createObjectBuilder()
                                                        .add("error", "La taille n'existe pas")
                                                        .build()
                                        ).build();
                            }
                        }
                        this.scm.update(sc);

                        return Response.ok(buildCommandeObject(cmd, false)).build();
                    } else {
                        return Response.status(Response.Status.NOT_FOUND).entity(
                                Json.createObjectBuilder()
                                        .add("error", "Le sandwichChoix n'existe pas dans la commande")
                                        .build()
                        ).build();
                    }
                } else {
                    return Response.status(Response.Status.FORBIDDEN).entity(
                            Json.createObjectBuilder()
                                    .add("error", "Aucune valeur du sandwich à modifier")
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

    @PUT
    @Path("{id}")
    @ApiOperation(value = "Modifie la date de livraison d'une commande", notes = "Modifie la date de livraison d'une commande à partir du JSON fourni")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "OK"),
            @ApiResponse(code = 403, message = "Forbidden"),
            @ApiResponse(code = 404, message = "Not Found"),
            @ApiResponse(code = 500, message = "Internal server error")})
    public Response updateCommandeLivraison(
            @PathParam("id") String id,
            @DefaultValue("") @QueryParam("token") String tokenParam,
            @DefaultValue("") @HeaderParam("X-lbs-token") String tokenHeader,
            CommandeUpdateLivraison c
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
                SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy HH:mm");
                sdf.setTimeZone(TimeZone.getDefault());

                Date current = Date.from(LocalDateTime.now()
                        .atZone(ZoneId.systemDefault())
                        .toInstant());

                try {
                    sdf.setLenient(false);
                    Date dateCommande = sdf.parse(c.getDateLivraison() + " " + c.getHeureLivraison());
                    Timestamp timestampCommande = new Timestamp(dateCommande.getTime());
                    Timestamp currentTimestamp = new Timestamp(current.getTime());

                    if (timestampCommande.before(currentTimestamp)) {
                        return Response.status(Response.Status.BAD_REQUEST).entity(
                                Json.createObjectBuilder()
                                        .add("error", "La date de livraison est inférieure a la date courante")
                                        .build()
                        ).build();
                    }

                    cmd.setDateLivraison(timestampCommande);

                    this.cm.update(cmd);

                    return Response.ok(this.buildCommandeObject(cmd,false)).build();
                } catch (ParseException pe) {
                    pe.printStackTrace();
                    return Response.status(Response.Status.BAD_REQUEST).entity(
                            Json.createObjectBuilder()
                                    .add("error", "La date de livraison est incorrecte")
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
            PayCard payCard
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
                if (payCard.getNom() != null && payCard.getNumeroCarte() != null && payCard.getCvv() != null && payCard.getDateExpiration() != null) {
                    if (payCard.getNom().matches("([a-zA-ZáàâäãåçéèêëíìîïñóòôöõúùûüýÿæœÁÀÂÄÃÅÇÉÈÊËÍÌÎÏÑÓÒÔÖÕÚÙÛÜÝŸÆŒ\\s-]+)") && payCard.getNumeroCarte().matches("(([0-9]{4}(\\s|-)){3}([0-9]{4}))") && payCard.getCvv().matches("([0-9]{3})")) {

                        SimpleDateFormat sdf = new SimpleDateFormat("MM-yy");
                        sdf.setTimeZone(TimeZone.getDefault());

                        Date current = Date.from(LocalDateTime.now()
                                .atZone(ZoneId.systemDefault())
                                .toInstant());
                        try {
                            sdf.setLenient(false);
                            Date dateExpiration = sdf.parse(payCard.getDateExpiration());
                            Timestamp currentTimestamp = new Timestamp(current.getTime());
                            Timestamp timestampExpiration = new Timestamp(dateExpiration.getTime());

                            if (timestampExpiration.after(currentTimestamp)) {
                                double total = cmd.getSandwichChoix().stream().mapToDouble(sc -> (this.tm.findById(sc.getTaille()).getPrix() * sc.getQte())).sum();
                                boolean reduction = false;

                                if (payCard.getNumeroCarteFidelite() != null) {
                                    Carte card = this.cardman.findByNumCarte(payCard.getNumeroCarteFidelite());

                                    if (card != null) {
                                        if (card.getMontant() >= 100) {
                                            reduction = true;
                                            total = (total * 0.95);
                                            card.setMontant(card.getMontant() - 100);
                                        }
                                        card.setMontant(card.getMontant() + total);
                                    } else {
                                        return Response.status(Response.Status.NOT_FOUND).entity(
                                                Json.createObjectBuilder()
                                                        .add("error", "La carte de fidélité est introuvable")
                                                        .build()
                                        ).build();
                                    }
                                }

                                cmd.setStatut(CommandeStatut.PAYEE);

                                return Response.ok(buildCommandeObject(cmd, reduction)).build();
                            } else {
                                return Response.status(Response.Status.FORBIDDEN).entity(
                                        Json.createObjectBuilder()
                                                .add("error", "La date d'expiration est inférieure a la date courante")
                                                .build()
                                ).build();
                            }
                        } catch (ParseException pe) {
                            return Response.status(Response.Status.BAD_REQUEST).entity(
                                    Json.createObjectBuilder()
                                            .add("error", "La date d'expiration est incorrecte")
                                            .build()
                            ).build();
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

    @GET
    @Path("{id}/bill")
    @ApiOperation(value = "Récupère la facture d'une commande", notes = "Renvoie le JSON associé à la facture d'une commande")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "OK"),
            @ApiResponse(code = 403, message = "Forbidden"),
            @ApiResponse(code = 404, message = "Not Found"),
            @ApiResponse(code = 500, message = "Internal server error")})
    public Response getFacture(
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
            if (cmd.getStatut() != CommandeStatut.ATTENTE) {
                return Response.ok(buildJsonForFacture(cmd)).build();
            } else {
                return Response.status(Response.Status.FORBIDDEN).entity(
                        Json.createObjectBuilder()
                                .add("error", "La commande n'a pas encore été payée")
                                .build()
                ).build();
            }
        }
    }

    private JsonObject buildCommandeObject(Commande c, boolean reduction) {
        return Json.createObjectBuilder()
                .add("commande", buildJsonForCommande(c, reduction))
                .build();
    }

    private JsonObject buildClient(Commande c) {
        return Json.createObjectBuilder()
                .add("nom", c.getNom())
                .add("prenom", c.getPrenom())
                .add("mail", c.getMail())
                .build();
    }

    private JsonArray buildSandwichsCommande(Commande c, boolean reduction) {
        Set<SandwichChoix> setSC = c.getSandwichChoix();
        JsonArrayBuilder jab = Json.createArrayBuilder();

        double total = setSC.stream().mapToDouble(sc -> (this.tm.findById(sc.getTaille()).getPrix() * sc.getQte())).sum();
        if (reduction) {
            total = (total * 0.95);
        }

        setSC.forEach((sc) -> {
            Sandwich s = this.sm.findById(sc.getSandwich());
            Taille t = this.tm.findById(sc.getTaille());

            jab.add(Json.createObjectBuilder()
                    .add("nom", s.getNom())
                    .add("taille", t.getNom())
                    .add("quantité", sc.getQte())
                    .add("prix", t.getPrix() * sc.getQte())
                    .add("sandwich", Json.createObjectBuilder()
                            .add("href", "/sandwichs/" + s.getId())
                            .add("toUpdate", "/commandes/" + c.getId() + "/sandwichs/" + sc.getId()))
                    .build()
            );
        });

        jab.add(Json.createObjectBuilder().add("total", total));

        return jab.build();
    }

    private JsonObject buildJsonForCommande(Commande c, boolean reduction) {
        return Json.createObjectBuilder()
                .add("id", c.getId())
                .add("livraison", buildJsonForLivraison(c))
                .add("token", c.getToken())
                .add("statut", c.getStatut().toString())
                .add("client", this.buildClient(c))
                .add("sandwichs", this.buildSandwichsCommande(c, reduction))
                .build();
    }

    private JsonObject buildJsonForLivraison(Commande c) {
        Calendar cal = Calendar.getInstance();
        cal.setTimeInMillis(c.getDateLivraison().getTime());

        String dateLivraison = cal.get(Calendar.DATE) + "-" + (cal.get(Calendar.MONTH) + 1) + "-" + cal.get(Calendar.YEAR);
        String heureLivraison = cal.get(Calendar.HOUR) + ":" + cal.get(Calendar.MINUTE);

        return Json.createObjectBuilder()
                .add("date", dateLivraison)
                .add("heure", heureLivraison)
                .add("adresse", c.getAdresseLivraison())
                .build();
    }

    private JsonObject buildJsonForFacture(Commande c) {
        return Json.createObjectBuilder()
                .add("facture", buildCommandeObject(c, false))
                .build();
    }

    private JsonObject buildJsonCommandes(List<Commande> commandes) {
        JsonArrayBuilder jab = Json.createArrayBuilder();

        commandes.forEach(c -> {
            jab.add(buildCommandeObject(c, false));
        });

        return Json.createObjectBuilder()
                .add("commandes", jab.build())
                .build();
    }

    private JsonObject buildJsonCommandeStatut(Commande c) {
        return Json.createObjectBuilder()
                .add("id", c.getId())
                .add("livraison", buildJsonForLivraison(c))
                .add("token", c.getToken())
                .add("statut", c.getStatut().toString())
                .add("client", this.buildClient(c))
                .build();
    }
}
