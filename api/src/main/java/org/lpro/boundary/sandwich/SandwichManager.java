package org.lpro.boundary.sandwich;

import javax.ejb.Stateless;
import javax.json.Json;
import javax.json.JsonObject;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

@Stateless
public class SandwichManager {

    @PersistenceContext
    EntityManager em;

    public JsonObject getMeta() {
        return Json.createObjectBuilder()
                .add("count", this.em.findAll())
                .add("date", '05-12-2017')
                .build();
    }

}
