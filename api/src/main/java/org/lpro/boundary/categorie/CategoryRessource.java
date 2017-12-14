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
public class CategoryRessource {

    @Inject
    CategorieManager cm;

    @Inject
    SandwichManager sm;

    @GET
    public Response getCategories() {
        JsonObject json = Json.createObjectBuilder()
                .add("type", "collection")
                .add("categories", this.getCategoriesList())
                .build();
        return Response.ok(json).build();
    }

    @GET
    @Path("{id}")
    public Response getOneCategorie(@PathParam("id") String id, @Context UriInfo uriInfo) {
        return Optional.ofNullable(this.cm.findById(id))
                .map(c -> Response.ok(categorie2Json(c)).build())
                .orElseThrow(() -> new CategorieNotFound("Ressource non disponible" + uriInfo.getPath()));
    }

    @GET
    @Path("{id}/sandwichs")
    public Response getCategorieSandwichs(@PathParam("id") String id, @Context UriInfo uriInfo) {
        Categorie c = this.cm.findById(id);
        Set<Sandwich> sandwichs = c.getSandwich();

        JsonArrayBuilder jab = Json.createArrayBuilder();
        sandwichs.forEach((s) -> {
            jab.add(SandwichRessource.buildJson(s));
        });

        JsonObject json = Json.createObjectBuilder()
                .add("type", "collection")
                .add("meta", Json.createObjectBuilder().add("count", sandwichs.size()).build())
                .add("sandwichs", jab.build())
                .build();

        return Response.ok(json).build();
    }

    @POST
    public Response newCategorie(@Valid Categorie c, @Context UriInfo uriInfo) {
        Categorie cat = this.cm.save(c);
        String id = cat.getId();
        URI uri = uriInfo.getAbsolutePathBuilder().path("/" + id).build();
        return Response.created(uri).build();
    }

    @DELETE
    @Path("{id}")
    public Response removeCategorie(@PathParam("id") String id) {
        this.cm.delete(id);
        return Response.status(Response.Status.NO_CONTENT).build();
    }

    @PUT
    @Path("{id}")
    public Categorie update(@PathParam("id") String id, Categorie c) {
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

    private JsonObject buildJson(Categorie c) {
        return Json.createObjectBuilder()
                .add("id", c.getId())
                .add("nom", c.getNom())
                .add("description", c.getDescription())
                .build();
    }

    private JsonObject categorie2Json(Categorie c) {
        return Json.createObjectBuilder()
                .add("type", "resource")
                .add("categorie", this.buildJson(c))
                .build();
    }
}
