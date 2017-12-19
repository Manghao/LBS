package org.lpro.boundary.commande;

import org.lpro.control.RandomToken;
import org.lpro.entity.Commande;

import javax.ejb.Stateless;
import javax.persistence.CacheStoreMode;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.transaction.Transactional;
import java.util.List;
import java.util.UUID;

@Stateless
@Transactional
public class CommandeManager {

    @PersistenceContext
    EntityManager em;

    public Commande findById(String id) {
        return this.em.find(Commande.class, id);
    }

    public List<Commande> findAll() {
        Query q = this.em.createQuery("SELECT c FROM Commande c");
        q.setHint("javax.persistence.cache.storeMode", CacheStoreMode.REFRESH);
        return q.getResultList();
    }

    public Commande save(Commande c) {
        c.setToken(new RandomToken().randomString(64));
        c.setId(UUID.randomUUID().toString());

        return this.em.merge(c);
    }
}