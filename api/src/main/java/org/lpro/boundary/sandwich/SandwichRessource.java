package org.lpro.boundary.sandwich;

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
                .add("meta", this.sm.getMeta())
                .add("sandwichs", this.getSandwichsList())
                .build();
        return Response.ok(json).build();
    }

    private JsonArray getSandwichsList() {
        JsonArrayBuilder jab = Json.createArrayBuilder();
        this.sm.findAll().forEach((s) -> {
            jab.add(buildJson(s));
        });
        return jab.build();
    }

    private JsonObject buildJson(Sandwich s) {
        return Json.createObjectBuilder()
                .add("sandwich", Json.createObjectBuilder()
                        .add("id", s.getId())
                        .add("nom", s.getNom())
                        .add("description", s.getDescription())
                        .add("type_pain", s.getPain())
                        .build())
                .add("links", Json.createObjectBuilder()
                        .add("self", Json.createObjectBuilder()
                                .add("href", ((s.getImg() == null) ? "" : s.getImg()))
                                .build())
                        .build())
                .build();
    }


}
