package org.lpro.boundary.sandwich;

import org.lpro.entity.Sandwich;

import javax.ejb.Stateless;
import javax.json.Json;
import javax.json.JsonObject;
import javax.persistence.CacheStoreMode;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.persistence.*;
import java.util.List;
import java.util.UUID;

@Stateless
public class SandwichManager {

    @PersistenceContext
    EntityManager em;

    public List<Sandwich> findAll() {
        Query q = this.em.createNamedQuery("Sandwich.findAll", Sandwich.class);
        q.setHint("javax.persistance.cache.storeMode", CacheStoreMode.REFRESH);
        return q.getResultList();
    }

    public Sandwich findById(String id) {
        return this.em.find(Sandwich.class, id);
    }

    public List<Sandwich> findWithParam(String ptype, int img, int page, int nbPerPage) {
        String sql = "SELECT s FROM Sandwich s";

        if (ptype.compareTo("all") != 0) {
            if (img == 1) {
                sql += " WHERE s.pain = '" + ptype + "' AND s.img != ''";
            } else {
                sql += " WHERE s.pain = '" + ptype + "'";
            }
        } else {
            if (img == 1) {
                sql += " WHERE s.img != ''";
            }
        }

        double nbSandwichs = this.findAll().size();

        if (page <= 0) {
            page = 1;
        }
        else if (page > Math.ceil(nbSandwichs / (double) nbPerPage)) {
            page = (int) Math.ceil(nbSandwichs / (double) nbPerPage);
        }

        Query query = this.em.createQuery(sql);
        query.setFirstResult((page-1) * nbPerPage);
        query.setMaxResults(nbPerPage);
        return query.getResultList();
    }
  
    public Sandwich save(Sandwich s) {
        s.setId(UUID.randomUUID().toString());
        return this.em.merge(s);
    }

    public void delete(String id) {
        try {
            Sandwich ref = this.em.getReference(Sandwich.class, id);
            this.em.remove(ref);
        } catch (EntityNotFoundException e) { }
    }

    public JsonObject getMeta(long size) {
        return Json.createObjectBuilder()
                .add("count", ((size == -1) ? this.findAll().size() : size))
                .add("date",  "05-12-2017")
                .build();
    }

    public JsonObject getMetaPerPage(long size, int page, int nbPerPage) {
        return Json.createObjectBuilder()
                .add("count", ((size == -1) ? this.findAll().size() : size))
                .add("size", this.findAllPerPage(page, nbPerPage).size())
                .build();
    }
}
