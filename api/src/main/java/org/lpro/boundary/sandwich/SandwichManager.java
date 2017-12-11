package org.lpro.boundary.sandwich;

import org.lpro.entity.Sandwich;

import javax.ejb.Stateless;
import javax.json.Json;
import javax.json.JsonObject;
import javax.persistence.CacheStoreMode;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import java.util.ArrayList;
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

    public List<Sandwich> findByTypePain(String pain) {
        Query query = this.em.createQuery("SELECT s FROM Sandwich s WHERE s.pain = :pain");
        query.setParameter("pain", pain);
        return query.getResultList();
    }

    public JsonObject getMeta(long size) {
        return Json.createObjectBuilder()
                .add("count", ((size == -1) ? this.findAll().size() : size))
                .add("date",  "05-12-2017")
                .build();
    }

}
