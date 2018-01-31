package org.lpro.boundary.sandwich;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.lpro.boundary.categorie.CategorieManager;
import org.lpro.boundary.categorie.CategorieRessource;
import org.lpro.boundary.sandwich.exception.SandwichNotFound;
import org.lpro.entity.Categorie;
import org.lpro.entity.Sandwich;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.json.Json;
import javax.json.JsonArray;
import javax.json.JsonArrayBuilder;
import javax.json.JsonObject;
import javax.validation.Valid;
import javax.ws.rs.*;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.*;
import javax.ws.rs.core.UriInfo;
import java.net.URI;

@Stateless
@Path("sandwichs")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
@Api(value = "Sandwich")
public class SandwichRessource {

    @Inject
    SandwichManager sm;

    @Inject
    CategorieManager cm;

    @Context
    UriInfo uriInfo;

    @GET
    @ApiOperation(value = "Récupère tous les sandwichs", notes = "Renvoie le JSON associé à la collection de sandwichs")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "OK"),
            @ApiResponse(code = 500, message = "Internal server error")})
    public Response getSandwichs(
            @DefaultValue("1") @QueryParam("page") int page,
            @DefaultValue("10") @QueryParam("size") int nbPerPage,
            @QueryParam("t") String ptype,
            @DefaultValue("0") @QueryParam("img") int img
    ) {
        JsonObject json = Json.createObjectBuilder()
                .add("type", "collection")
                .add("meta", this.sm.getMetaPerPage(-1, ptype, img, page, nbPerPage))
                .add("sandwichs", this.getSandwichsList(this.sm.findWithParam(this.sm.createQuery(ptype, img), page, nbPerPage)))
                .build();
        return Response.ok(json).build();
    }

    @GET
    @Path("{id}")
    @ApiOperation(value = "Récupère un sandwich", notes = "Renvoie le JSON associé au sandwich")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "OK"),
            @ApiResponse(code = 404, message = "Not Found"),
            @ApiResponse(code = 500, message = "Internal server error")})
    public Response getOneSandwich(@PathParam("id") String id, @DefaultValue("0") @QueryParam("details") int details, @Context UriInfo uriInfo) {
        return Optional.ofNullable(this.sm.findById(id))
                .map(s -> Response.ok((details == 0) ? sandwichJson(s): sandwich2Json(s)).build())
                .orElseThrow(() -> new SandwichNotFound("Ressource non disponible" + uriInfo.getPath()));
    }

    @GET
    @Path("{id}/categories")
    @ApiOperation(value = "Récupère toutes les catégories d'un sandwich", notes = "Renvoie le JSON associé à la collection de catégories")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "OK"),
            @ApiResponse(code = 500, message = "Internal server error")})
    public Response getSandwichCategories(@PathParam("id") String id, @Context UriInfo uriInfo) {
        return Optional.ofNullable(this.sm.findById(id))
                .map(s -> Response.ok(this.buildCategorieObject(s)).build())
                .orElseThrow(() -> new SandwichNotFound("Ressource non disponible" + uriInfo.getPath()));
    }

    @POST
    @ApiOperation(value = "Crée un sandwich", notes = "Crée un sandwich à partir du JSON fourni")
    @ApiResponses(value = {
            @ApiResponse(code = 201, message = "Created"),
            @ApiResponse(code = 500, message = "Internal server error")})
    public Response newSandwich(@Valid Sandwich s, @Context UriInfo uriInfo) {
        Sandwich sand = this.sm.save(s);
        String id = sand.getId();
        URI uri = uriInfo.getAbsolutePathBuilder().path("/" + id).build();
        return Response.created(uri).entity(buildJson(sand)).build();
    }

    @DELETE
    @Path("{id}")
    @ApiOperation(value = "Supprime un sandwich", notes = "Supprime le sandwich dont l'ID est fourni")
    @ApiResponses(value = {
            @ApiResponse(code = 204, message = "No content"),
            @ApiResponse(code = 500, message = "Internal server error")})
    public Response removeSandwich(@PathParam("id") String id) {
        this.sm.delete(id);
        return Response.status(Response.Status.NO_CONTENT).build();
    }

    @PUT
    @Path("{id}")
    @ApiOperation(value = "Modifie un sandwich", notes = "Modifie le sandwich dont l'ID est fourni")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "OK"),
            @ApiResponse(code = 404, message = "Not Found"),
            @ApiResponse(code = 500, message = "Internal server error")})
    public Response updateSandwich(@PathParam("id") String id, Sandwich s) {
        Sandwich sand = this.sm.findById(id);

        if (sand != null) {
            if (s.getNom() != null) {
                if (!s.getNom().equals(""))
                    sand.setNom(s.getNom());
            }
            if (s.getDescription() != null) {
                if (!s.getDescription().equals(""))
                    sand.setDescription(s.getDescription());
            }
            if (s.getType_pain() != null) {
                if (!s.getType_pain().equals(""))
                    sand.setType_pain(s.getType_pain());
            }
            if (s.getImg() != null) {
                if (!s.getImg().equals(""))
                    sand.setImg(s.getImg());
            }
            if (s.getCategorie().size() > 0)
                sand.setCategorie(s.getCategorie());
            if (s.getTaille().size() > 0)
                sand.setTaille(s.getTaille());

            this.sm.update(sand);

            return Response.ok(buildJson(sand)).build();
        } else {
            return Response.status(Response.Status.NOT_FOUND).entity(
                    Json.createObjectBuilder()
                            .add("error", "Le sandwich " + id + " n'existe pas")
                            .build()
            ).build();
        }
    }

    private JsonArray getSandwichsList(List<Sandwich> sandwichs) {
        JsonArrayBuilder jab = Json.createArrayBuilder();
        sandwichs.forEach((s) -> {
            jab.add(buildJson(s));
        });
        return jab.build();
    }

    public static JsonObject buildJson(Sandwich s) {
        JsonObject self = Json.createObjectBuilder()
                .add("href", "/sandwichs/" + s.getId())
                .build();

        JsonObject linksTailles = Json.createObjectBuilder()
                .add("href", "/sandwichs/" + s.getId() + "/tailles")
                .build();

        JsonObject linksCategories = Json.createObjectBuilder()
                .add("href", "sandwichs/" + s.getId() + "/categories")
                .build();

        JsonArrayBuilder categories = Json.createArrayBuilder();
        s.getCategorie().forEach((c) -> {
            JsonObject categorie = Json.createObjectBuilder()
                    .add("id", c.getId())
                    .add("nom", c.getNom())
                    .build();
            categories.add(categorie);
        });

        JsonArrayBuilder tailles = Json.createArrayBuilder();
        s.getTaille().forEach((t) -> {
            JsonObject taille = Json.createObjectBuilder()
                    .add("id", t.getId())
                    .add("nom", t.getNom())
                    .add("prix", t.getPrix())
                    .build();
            tailles.add(taille);
        });

        JsonObject links = Json.createObjectBuilder()
                .add("self", self)
                .add("categories", linksCategories)
                .add("tailles", linksTailles)
                .build();

        JsonObject details = Json.createObjectBuilder()
                .add("id", s.getId())
                .add("nom", s.getNom())
                .add("description", s.getDescription())
                .add("type_pain", s.getType_pain())
                .add("img", ((s.getImg() == null) ? "" : s.getImg()))
                .add("categories", categories)
                .add("tailles", tailles)
                .build();

        return Json.createObjectBuilder()
                .add("sandwich", details)
                .add("links", links)
                .build();
    }

    private JsonObject sandwich2Json(Sandwich s) {
        return Json.createObjectBuilder()
                .add("type", "resource")
                .add("sandwich", buildJson(s))
                .build();
    }

    private JsonObject sandwichJson(Sandwich s) {
        JsonObject self = Json.createObjectBuilder()
                .add("href", "/sandwichs/" + s.getId())
                .build();

        JsonObject links = Json.createObjectBuilder()
                .add("self", self)
                .build();

        JsonObject sandwich = Json.createObjectBuilder()
                .add("id", s.getId())
                .add("nom", s.getNom())
                .add("description", s.getDescription())
                .add("type_pain", s.getType_pain())
                .add("img", ((s.getImg() == null) ? "" : s.getImg()))
                .build();

        return Json.createObjectBuilder()
                .add("type", "resource")
                .add("sandwich", sandwich)
                .add("links", links)
                .build();
    }


    private JsonObject buildCategorieObject(Sandwich s) {
        Set<Categorie> categories = s.getCategorie();

        JsonArrayBuilder jab = Json.createArrayBuilder();
        categories.forEach((c) -> {
            jab.add(CategorieRessource.buildJson(c));
        });

        return Json.createObjectBuilder()
                .add("type", "collection")
                .add("meta", Json.createObjectBuilder().add("count", categories.size()).build())
                .add("categories", jab.build())
                .build();
    }
}
