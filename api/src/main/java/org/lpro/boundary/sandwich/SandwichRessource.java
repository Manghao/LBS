package org.lpro.boundary.sandwich;

import org.lpro.boundary.categorie.exception.CategorieNotFound;
import org.lpro.entity.Sandwich;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.json.Json;
import javax.json.JsonArray;
import javax.json.JsonArrayBuilder;
import javax.json.JsonObject;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.List;
import java.util.Optional;

@Stateless
@Path("sandwichs")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class SandwichRessource {

    @Inject
    SandwichManager sm;

    @GET
    public Response getSandwichs() {
        JsonObject json = Json.createObjectBuilder()
                .add("type", "collection")
                .add("meta", this.sm.getMeta(-1))
                .add("sandwichs", this.getSandwichsList(this.sm.findAll()))
                .build();
        return Response.ok(json).build();
    }

    @GET
    @Produces("application/json")
    public Response getSandwichs(@QueryParam("pain") String ptype) {
        List<Sandwich> sandwichs = this.sm.findByTypePain(ptype);
        JsonObject json = Json.createObjectBuilder()
                .add("type", "collection")
                .add("meta", this.sm.getMeta(sandwichs.size()))
                .add("sandwichs", this.getSandwichsList(sandwichs))
                .build();
        return Response.ok(json).build();
    }

    private JsonArray getSandwichsList(List<Sandwich> sandwichs) {
        JsonArrayBuilder jab = Json.createArrayBuilder();
        sandwichs.forEach((s) -> {
            jab.add(buildJson(s));
        });
        return jab.build();
    }

    private JsonObject buildJson(Sandwich s) {
        JsonObject details = Json.createObjectBuilder()
                .add("id", s.getId())
                .add("nom", s.getNom())
                .add("description", s.getDescription())
                .add("type_pain", s.getPain())
                .build();

        JsonObject href = Json.createObjectBuilder()
                .add("href", ((s.getImg() == null) ? "" : s.getImg()))
                .build();

        JsonObject self = Json.createObjectBuilder()
                .add("self", href)
                .build();

        return Json.createObjectBuilder()
                .add("sandwich", details)
                .add("links", self)
                .build();
    }


}
