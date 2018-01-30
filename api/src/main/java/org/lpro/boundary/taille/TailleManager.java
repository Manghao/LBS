package org.lpro.boundary.taille;

import org.lpro.entity.Taille;

import javax.ejb.Stateless;
import javax.persistence.*;
import java.util.List;

@Stateless
public class TailleManager {

    @PersistenceContext
    EntityManager em;

    public List<Taille> findAll() {
        Query q = this.em.createNamedQuery("Taille.findAll", Taille.class);
        q.setHint("javax.persistance.cache.storeMode", CacheStoreMode.REFRESH);
        return q.getResultList();
    }

    public Taille findById(String id) {
        return this.em.find(Taille.class, id);
    }
}
