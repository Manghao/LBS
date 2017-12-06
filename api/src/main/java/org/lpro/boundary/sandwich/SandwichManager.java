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

    public List<Sandwich> findByTypePain(String pain) {
        Query query = this.em.createQuery("SELECT s FROM Sandwich s WHERE s.pain = :pain");
        query.setParameter("pain", pain);
        return query.getResultList();
    }

    public List<Sandwich> findAllPerPage(int page, int nbPerPage) {
        Query query = this.em.createNamedQuery("Sandwich.findAll", Sandwich.class);

        if (page <= 0) {
            page = 1;
        }
        else if (page > this.findAll().size() / nbPerPage + 1) {
            page = this.findAll().size() / nbPerPage + 1;
        }

        query.setFirstResult((page-1) * nbPerPage);
        query.setMaxResults(nbPerPage);
        return query.getResultList();
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

    public JsonObject getMeta(long size) {
        return Json.createObjectBuilder()
                .add("count", ((size == -1) ? this.findAll().size() : size))
                .add("date",  "05-12-2017")
                .build();
    }

    public JsonObject getMetaPerPage(long size, int nbPerPage) {
        return Json.createObjectBuilder()
                .add("count", ((size == -1) ? this.findAll().size() : size))
                .add("size", nbPerPage)
                .build();
    }
}
