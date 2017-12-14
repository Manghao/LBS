package org.lpro.boundary.categorie;

import org.lpro.entity.Categorie;

import javax.ejb.Stateless;
import javax.persistence.*;
import java.util.List;
import java.util.UUID;

@Stateless
public class CategorieManager {

    @PersistenceContext
    EntityManager em;

    public Categorie findById(String id) {
        return this.em.find(Categorie.class, id);
    }

    public List<Categorie> findAll() {
        Query q = this.em.createNamedQuery("Categorie.findAll", Categorie.class);
        q.setHint("javax.persistance.cache.storeMode", CacheStoreMode.REFRESH);
        return q.getResultList();
    }

    public Categorie save(Categorie c) {
        c.setId(UUID.randomUUID().toString());
        return this.em.merge(c);
    }

    public void delete(String id) {
        try {
            Categorie ref = this.em.getReference(Categorie.class, id);
            this.em.remove(ref);
        } catch (EntityNotFoundException e) { }
    }

}
