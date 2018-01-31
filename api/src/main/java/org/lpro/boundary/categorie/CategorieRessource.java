package org.lpro.boundary.categorie;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
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
@Api(value = "Catégorie")
public class CategorieRessource {

    @Inject
    CategorieManager cm;

    @Inject
    SandwichManager sm;

    @Context
    UriInfo uriInfo;

    @GET
    @ApiOperation(value = "Récupère toutes les catégories", notes = "Renvoie le JSON associé à la collection de catégories")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "OK"),
            @ApiResponse(code = 500, message = "Internal server error")})
    public Response getCategories() {
        JsonObject json = Json.createObjectBuilder()
                .add("type", "collection")
                .add("categories", this.getCategoriesList())
                .build();
        return Response.ok(json).build();
    }

    @GET
    @Path("{id}")
    @ApiOperation(value = "Récupère une catégorie", notes = "Renvoie le JSON associé à la catégorie")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "OK"),
            @ApiResponse(code = 404, message = "Not Found"),
            @ApiResponse(code = 500, message = "Internal server error")})
    public Response getOneCategorie(@PathParam("id") String id, @Context UriInfo uriInfo) {
        return Optional.ofNullable(this.cm.findById(id))
                .map(c -> Response.ok(categorie2Json(c)).build())
                .orElseThrow(() -> new CategorieNotFound("Ressource non disponible" + uriInfo.getPath()));
    }

    @GET
    @Path("{id}/sandwichs")
    @ApiOperation(value = "Récupère tous les sandwichs d'une catégorie", notes = "Renvoie le JSON associé à la collection de sandwichs")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "OK"),
            @ApiResponse(code = 500, message = "Internal server error")})
    public Response getCategorieSandwichs(@PathParam("id") String id, @Context UriInfo uriInfo) {
        return Optional.ofNullable(this.cm.findById(id))
                .map(c -> Response.ok(this.buildSandwichObject(c)).build())
                .orElseThrow(() -> new CategorieNotFound("Ressource non disponible" + uriInfo.getPath()));
    }

    @POST
    @Path("{id}/sandwichs")
    @ApiOperation(value = "Ajoute un sandwich à une catégorie", notes = "Ajoute un sandwich à une catégorie à partir du JSON fourni")
    @ApiResponses(value = {
            @ApiResponse(code = 201, message = "Created"),
            @ApiResponse(code = 404, message = "Not Found"),
            @ApiResponse(code = 500, message = "Internal server error")})
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

    @POST
    @ApiOperation(value = "Crée une catégorie", notes = "Crée une catégorie à partir du JSON fourni")
    @ApiResponses(value = {
            @ApiResponse(code = 201, message = "Created"),
            @ApiResponse(code = 500, message = "Internal server error")})
    public Response newCategorie(@Valid Categorie c, @Context UriInfo uriInfo) {
        Categorie cat = this.cm.save(c);
        String id = cat.getId();
        URI uri = uriInfo.getAbsolutePathBuilder().path("/" + id).build();
        return Response.created(uri).entity(this.buildJson(cat)).build();
    }

    @DELETE
    @Path("{id}")
    @ApiOperation(value = "Supprime une catégorie", notes = "Supprime la catégorie dont l'ID est fourni")
    @ApiResponses(value = {
            @ApiResponse(code = 204, message = "No content"),
            @ApiResponse(code = 500, message = "Internal server error")})
    public Response removeCategorie(@PathParam("id") String id) {
        this.cm.delete(id);
        return Response.status(Response.Status.NO_CONTENT).build();
    }

    @PUT
    @Path("{id}")
    @ApiOperation(value = "Modifie une catégorie", notes = "Modifie la catégorie dont l'ID est fourni")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "OK"),
            @ApiResponse(code = 404, message = "Not Found"),
            @ApiResponse(code = 500, message = "Internal server error")})
    public Response updateCategorie(@PathParam("id") String id, Categorie c) {
        Categorie cat = this.cm.findById(id);

        if (cat != null) {
            if (c.getNom() != null) {
                if (!c.getNom().equals(""))
                    cat.setNom(c.getNom());
            }
            if (c.getDescription() != null) {
                if (!c.getDescription().equals(""))
                    cat.setDescription(c.getDescription());
            }
            if (c.getSandwich().size() > 0)
                cat.setSandwich(c.getSandwich());

            this.cm.update(cat);

            return Response.ok(buildJson(cat)).build();
        } else {
            return Response.status(Response.Status.NOT_FOUND).entity(
                    Json.createObjectBuilder()
                            .add("error", "Le sandwich " + id + " n'existe pas")
                            .build()
            ).build();
        }
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
