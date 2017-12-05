package org.lpro.boundary.sandwich;

import org.lpro.entity.Sandwich;

import javax.ejb.Stateless;
import javax.json.Json;
import javax.json.JsonObject;
import javax.persistence.*;
import java.util.List;

@Stateless
public class SandwichManager {

    @PersistenceContext
    EntityManager em;

    public List<Sandwich> findAll() {
        Query q = this.em.createNamedQuery("Sandwich.findAll", Sandwich.class);
        q.setHint("javax.persistance.cache.storeMode", CacheStoreMode.REFRESH);
        return q.getResultList();
    }

    public Sandwich findById(long id) {
        return this.em.find(Sandwich.class, id);
    }

    public Sandwich save(Sandwich s) {
        s.setId(this.findAll().get(this.findAll().size() - 1).getId() + 1);
        return this.em.merge(s);
    }

    public void delete(long id) {
        try {
            Sandwich ref = this.em.getReference(Sandwich.class, id);
            this.em.remove(ref);
        } catch (EntityNotFoundException e) { }
    }

    public JsonObject getMeta() {
        return Json.createObjectBuilder()
                .add("count", this.findAll().size())
                .add("date",  "05-12-2017")
                .build();
    }

}
