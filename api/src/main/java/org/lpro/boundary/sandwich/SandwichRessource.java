package org.lpro.boundary.sandwich;

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
public class SandwichRessource {

    @Inject
    SandwichManager sm;

    @Inject
    CategorieManager cm;

    @Context
    UriInfo uriInfo;

    @GET
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
    public Response getOneSandwich(@PathParam("id") String id, @DefaultValue("0") @QueryParam("details") int details, @Context UriInfo uriInfo) {
        return Optional.ofNullable(this.sm.findById(id))
                .map(s -> Response.ok((details == 0) ? sandwichJson(s) : sandwich2Json(s)).build())
                .orElseThrow(() -> new SandwichNotFound("Ressource non disponible" + uriInfo.getPath()));
    }

    @GET
    @Path("{id}/categories")
    public Response getSandwichCategories(@PathParam("id") String id, @Context UriInfo uriInfo) {
        Sandwich s = this.sm.findById(id);
        Set<Categorie> categories = s.getCategorie();

        JsonArrayBuilder jab = Json.createArrayBuilder();
        categories.forEach((c) -> {
            jab.add(CategorieRessource.buildJson(c));
        });

        JsonObject json = Json.createObjectBuilder()
                .add("type", "collection")
                .add("meta", Json.createObjectBuilder().add("count", categories.size()).build())
                .add("categories", jab.build())
                .build();

        return Response.ok(json).build();
    }

    @POST
    public Response newSandwich(@Valid Sandwich s, @Context UriInfo uriInfo) {
        Sandwich sand = this.sm.save(s);
        String id = sand.getId();
        URI uri = uriInfo.getAbsolutePathBuilder().path("/" + id).build();
        return Response.created(uri).entity(buildJson(sand)).build();
    }

    @DELETE
    @Path("{id}")
    public Response removeSandwich(@PathParam("id") String id) {
        this.sm.delete(id);
        return Response.status(Response.Status.NO_CONTENT).build();
    }

    @PUT
    @Path("{id}")
    public Sandwich update(@PathParam("id") String id, Sandwich s) {
        s.setId(id);
        return this.sm.save(s);
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

        JsonArrayBuilder categoriesLinks = Json.createArrayBuilder();
        JsonArrayBuilder categories = Json.createArrayBuilder();
        s.getCategorie().forEach((c) -> {
            JsonObject json = Json.createObjectBuilder()
                    .add("href", "/categories/" + c.getId())
                    .add("rel", c.getNom())
                    .build();
            categoriesLinks.add(json);

            JsonObject json2 = Json.createObjectBuilder()
                    .add("id", c.getId())
                    .add("nom", c.getNom())
                    .build();
            categories.add(json2);
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
                .add("categories", "sandwichs/" + s.getId() + "/categories")
                .add("tailles", linksTailles)
                .build();

        JsonObject details = Json.createObjectBuilder()
                .add("id", s.getId())
                .add("nom", s.getNom())
                .add("description", s.getDescription())
                .add("type_pain", s.getTypePain())
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
                .add("type_pain", s.getTypePain())
                .add("img", ((s.getImg() == null) ? "" : s.getImg()))
                .build();

        return Json.createObjectBuilder()
                .add("type", "resource")
                .add("sandwich", sandwich)
                .add("links", links)
                .build();
    }
}
