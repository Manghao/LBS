package org.lpro.boundary.sandwichChoix;

import org.lpro.entity.SandwichChoix;

import javax.ejb.Stateless;
import javax.persistence.*;
import java.util.List;

@Stateless
public class SandwichChoixManager {

    @PersistenceContext
    EntityManager em;

    public List<SandwichChoix> findAll() {
        Query q = this.em.createNamedQuery("SandwichChoix.findAll", SandwichChoix.class);
        q.setHint("javax.persistance.cache.storeMode", CacheStoreMode.REFRESH);
        return q.getResultList();
    }

    public SandwichChoix findById(String id) {
        return this.em.find(SandwichChoix.class, id);
    }

    public List<SandwichChoix> findAllById(String id) {
        TypedQuery<SandwichChoix> qSc = this.em.createQuery("SELECT sc FROM SandwichChoix sc WHERE sc.sandwich = :id", SandwichChoix.class);
        qSc.setParameter("id", id);
        return qSc.getResultList();
    }
}
