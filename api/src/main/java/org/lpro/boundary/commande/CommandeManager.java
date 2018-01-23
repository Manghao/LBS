package org.lpro.boundary.commande;

import org.lpro.control.RandomToken;
import org.lpro.entity.*;
import org.lpro.enums.CommandeStatut;

import javax.ejb.Stateless;
import javax.persistence.*;
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
        c.setStatut(CommandeStatut.ATTENTE);
        c.setToken(new RandomToken().randomString(64));
        c.setId(UUID.randomUUID().toString());

        return this.em.merge(c);
    }

    public SandwichChoix addSandwichChoix(Commande cmd, SandwichChoix sc) {
        Sandwich s;
        TypedQuery<Sandwich> query = this.em.createQuery("SELECT s FROM Sandwich s WHERE s.id = :id", Sandwich.class);
        query.setParameter("id", sc.getSandwich());
        try {
            s = query.getSingleResult();
        } catch (NoResultException e) {
            return null;
        }

        Taille t;
        TypedQuery<Taille> qTaille = this.em.createQuery("SELECT t FROM Taille t WHERE t.id = :id", Taille.class);
        qTaille.setParameter("id", sc.getTaille());
        try {
            t = qTaille.getSingleResult();

            sc = new SandwichChoix(s.getId(), t.getId());
            sc.setId(UUID.randomUUID().toString());
            this.em.merge(sc);

            // cmd.getSandwichChoix().add(sc);
            // this.em.persist(cmd);

            return sc;
        } catch (NoResultException e) {
            return null;
        }
    }

    public boolean deleteSandwich(Commande cmd, Sandwich sand) {
        // return cmd.getSandwich().remove(sand);
        return false;
    }
}