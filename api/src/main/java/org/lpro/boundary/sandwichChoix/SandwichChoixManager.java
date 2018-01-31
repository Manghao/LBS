package org.lpro.boundary.sandwichChoix;

import org.lpro.entity.Commande;
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

    public SandwichChoix findById(String idSand) {
        return this.em.find(SandwichChoix.class, idSand);
    }

    public SandwichChoix findById(String idSand, String idTaille) {
        SandwichChoix sc;
        TypedQuery<SandwichChoix> qSc = this.em.createQuery("SELECT sc FROM SandwichChoix sc WHERE sc.sandwich = :idSand AND sc.taille = :idTaille", SandwichChoix.class);
        qSc.setParameter("idSand", idSand);
        qSc.setParameter("idTaille", idTaille);

        try {
            sc = qSc.getSingleResult();
        } catch (Exception e) {
            sc = null;
        }

        return sc;
    }

    public List<SandwichChoix> findAllById(String idSand) {
        TypedQuery<SandwichChoix> qSc = this.em.createQuery("SELECT sc FROM SandwichChoix sc WHERE sc.sandwich = :id", SandwichChoix.class);
        qSc.setParameter("id", idSand);
        return qSc.getResultList();
    }

    public List<SandwichChoix> findAllById(String idSand, String idTaille) {
        TypedQuery<SandwichChoix> qSc = this.em.createQuery("SELECT sc FROM SandwichChoix sc WHERE sc.sandwich = :idSand AND sc.taille = :idTaille", SandwichChoix.class);
        qSc.setParameter("idSand", idSand);
        qSc.setParameter("idTaille", idTaille);
        return qSc.getResultList();
    }

    public SandwichChoix update(SandwichChoix sc) {
        return this.em.merge(sc);
    }

    public void delete(Commande c, String id) {
        try {
            SandwichChoix ref = this.em.getReference(SandwichChoix.class, id);
            c.getSandwichChoix().remove(ref);
            this.em.remove(ref);
        } catch (EntityNotFoundException e) { }
    }
}
