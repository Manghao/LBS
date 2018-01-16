package org.lpro.boundary.categorie;

import org.lpro.boundary.categorie.exception.CategorieNotFound;
import org.lpro.boundary.sandwich.SandwichManager;
import org.lpro.boundary.sandwich.SandwichRessource;
import org.lpro.entity.Categorie;
import org.lpro.entity.Sandwich;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.json.*;
import javax.validation.Valid;
import javax.ws.rs.*;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;
import java.net.URI;
import java.util.Optional;
import java.util.Set;

@Stateless
@Path("categories")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class CategorieRessource {

    @Inject
    CategorieManager cm;

    @Inject
    SandwichManager sm;

    @Context
    UriInfo uriInfo;

    /**
     * @api {get} /categories Récupérer toutes les catégories
     * @apiName getCategories
     * @apiGroup Categorie
     *
     * @apiSuccess {List} categories Liste des catégories.
     */
    @GET
    public Response getCategories() {
        JsonObject json = Json.createObjectBuilder()
                .add("type", "collection")
                .add("categories", this.getCategoriesList())
                .build();
        return Response.ok(json).build();
    }

    /**
     * @api {get} /categories/:id Récupérer une catégorie
     * @apiName getOneCategorie
     * @apiGroup Categorie
     *
     * @apiParam {String} id ID unique d'une catégorie.
     *
     * @apiSuccess {Categorie} categorie Une catégorie.
     * @apiError CategorieNotFound L'<code>id</code> de la catégorie n'existe pas.
     */
    @GET
    @Path("{id}")
    public Response getOneCategorie(@PathParam("id") String id, @Context UriInfo uriInfo) {
        return Optional.ofNullable(this.cm.findById(id))
                .map(c -> Response.ok(categorie2Json(c)).build())
                .orElseThrow(() -> new CategorieNotFound("Ressource non disponible" + uriInfo.getPath()));
    }

    /**
     * @api {get} /categories/:id/sandwichs Récupérer les sandwichs d'une catégorie
     * @apiName getCategorieSandwichs
     * @apiGroup Categorie
     *
     * @apiParam {String} id ID unique d'une catégorie.
     *
     * @apiSuccess {List} sandwichs Les sandwichs d'une catégorie.
     */
    @GET
    @Path("{id}/sandwichs")
    public Response getCategorieSandwichs(@PathParam("id") String id, @Context UriInfo uriInfo) {
        return Optional.ofNullable(this.cm.findById(id))
                .map(c -> Response.ok(this.buildSandwichObject(c)).build())
                .orElseThrow(() -> new CategorieNotFound("Ressource non disponible" + uriInfo.getPath()));
    }

    /**
     * @api {post} /categories/:id/sandwichs Ajouter un sandwich à une catégorie
     * @apiName addSandwichToCategorie
     * @apiGroup Categorie
     *
     * @apiParam {String} id ID unique d'une catégorie.
     *
     * @apiSuccess {Sandwich} sandwich Le sandwich ajouté à la catégorie.
     * @apiError {Status} status Statut 404 NOT FOUND car la catégorie n'existe pas
     */
    @POST
    @Path("{id}/sandwichs")
    public Response addSandwichToCategorie(@PathParam("id") String catId, @Context UriInfo uriInfo, Sandwich sand) {
        Categorie c = this.cm.findById(catId);

        if (c != null) {
            Sandwich s = this.sm.addSandwich(c, sand);
            URI uri = uriInfo.getAbsolutePathBuilder()
                    .path("/")
                    .path(s.getId())
                    .build();
            return Response.created(uri).entity(SandwichRessource.buildJson(s)).build();
        } else {
            return Response.status(Response.Status.NOT_FOUND).build();
        }
    }

    /**
     * @api {post} /categories Créer une nouvelle catégorie
     * @apiName newCategorie
     * @apiGroup Categorie
     *
     * @apiSuccess {Categorie} categorie La catégorie nouvellement créée.
     */
    @POST
    public Response newCategorie(@Valid Categorie c, @Context UriInfo uriInfo) {
        Categorie cat = this.cm.save(c);
        String id = cat.getId();
        URI uri = uriInfo.getAbsolutePathBuilder().path("/" + id).build();
        return Response.created(uri).entity(this.buildJson(cat)).build();
    }

    /**
     * @api {delete} /categories/:id Supprimer une catégorie
     * @apiName removeCategorie
     * @apiGroup Categorie
     *
     * @apiParam {String} id ID unique d'une catégorie.
     *
     * @apiSuccess {Status} status Retourne le code 204 (No Content).
     */
    @DELETE
    @Path("{id}")
    public Response removeCategorie(@PathParam("id") String id) {
        this.cm.delete(id);
        return Response.status(Response.Status.NO_CONTENT).build();
    }

    /**
     * @api {put} /categories/:id Modifier une catégorie
     * @apiName updateCategorie
     * @apiGroup Categorie
     *
     * @apiParam {String} id ID unique d'une catégorie.
     *
     * @apiSuccess {Categorie} categorie La catégorie modifiée.
     */
    @PUT
    @Path("{id}")
    public Categorie updateCategorie(@PathParam("id") String id, Categorie c) {
        c.setId(id);
        return this.cm.save(c);
    }

    private JsonArray getCategoriesList() {
        JsonArrayBuilder jab = Json.createArrayBuilder();
        this.cm.findAll().forEach((c) -> {
            jab.add(buildJson(c));
        });
        return jab.build();
    }

    public static JsonObject buildJson(Categorie c) {
        JsonObject self = Json.createObjectBuilder()
                .add("href", "/categories/" + c.getId())
                .build();

        JsonObject linksSandwichs = Json.createObjectBuilder()
                .add("href", "/categories/" + c.getId() + "/sandwichs")
                .build();

        JsonArrayBuilder sandwichs = Json.createArrayBuilder();
        c.getSandwich().forEach((s) -> {
            JsonObject sandwich = Json.createObjectBuilder()
                    .add("id", s.getId())
                    .add("nom", s.getNom())
                    .build();
            sandwichs.add(sandwich);
        });

        JsonObject links = Json.createObjectBuilder()
                .add("self", self)
                .add("sandwichs", linksSandwichs)
                .build();

        JsonObject details = Json.createObjectBuilder()
                .add("id", c.getId())
                .add("nom", c.getNom())
                .add("description", c.getDescription())
                .add("sandwichs", sandwichs)
                .build();

        return Json.createObjectBuilder()
                .add("categorie", details)
                .add("links", links)
                .build();
    }

    private JsonObject categorie2Json(Categorie c) {
        return Json.createObjectBuilder()
                .add("type", "resource")
                .add("categorie", this.buildJson(c))
                .build();
    }

    private JsonObject buildSandwichObject(Categorie c) {
        Set<Sandwich> sandwichs = c.getSandwich();

        JsonArrayBuilder jab = Json.createArrayBuilder();
        sandwichs.forEach((s) -> {
            jab.add(SandwichRessource.buildJson(s));
        });

        return Json.createObjectBuilder()
                .add("type", "collection")
                .add("meta", Json.createObjectBuilder().add("count", sandwichs.size()).build())
                .add("sandwichs", jab.build())
                .build();
    }
}
