package org.lpro.boundary.sandwich;

import com.sun.jndi.toolkit.url.Uri;
import org.lpro.boundary.categorie.CategorieManager;
import org.lpro.boundary.sandwich.exception.SandwichNotFound;
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
    static UriInfo uriInfo;

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
    public Response getOneSandwich(@PathParam("id") String id, @Context UriInfo uriInfo) {
        return Optional.ofNullable(this.sm.findById(id))
                .map(s -> Response.ok(sandwich2Json(s)).build())
                .orElseThrow(() -> new SandwichNotFound("Ressource non disponible" + uriInfo.getPath()));
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
        JsonObject details = Json.createObjectBuilder()
                .add("id", s.getId())
                .add("nom", s.getNom())
                .add("description", s.getDescription())
                .add("type_pain", s.getTypePain())
                .add("img", ((s.getImg() == null) ? "" : s.getImg()))
                .build();

        JsonObject href = Json.createObjectBuilder()
                .add("href", "/sandwichs/" + s.getId())
                .add("rel", "self")
                .build();

        JsonArrayBuilder categories = Json.createArrayBuilder();
        s.getCategorie().forEach((c) -> {
            JsonObject json = Json.createObjectBuilder()
                    .add("href", "/categories/" + c.getId())
                    .add("rel", c.getNom())
                    .build();
            categories.add(json);
        });

        JsonArray links = Json.createArrayBuilder()
                .add(href)
                .add(Json.createObjectBuilder().add("categories", categories).build())
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
}
