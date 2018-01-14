package org.lpro.boundary.commande;

import org.lpro.control.RandomToken;
import org.lpro.entity.Categorie;
import org.lpro.entity.Commande;
import org.lpro.entity.Sandwich;

import javax.ejb.Stateless;
import javax.persistence.*;
import javax.transaction.Transactional;
import javax.ws.rs.core.Response;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;
import java.util.List;
import java.util.TimeZone;
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

    public Sandwich addSandwich(Commande cmd, Sandwich sand) {
        Sandwich s;
        TypedQuery<Sandwich> query = this.em.createQuery("SELECT s FROM Sandwich s WHERE s.id = :id", Sandwich.class);
        query.setParameter("id", sand.getId());
        try {
            s = query.getSingleResult();
            cmd.getSandwich().add(s);
            this.em.persist(cmd);

            return s;
        } catch (NoResultException e) {
            return null;
        }
    }

    public boolean deleteSandwich(Commande cmd, Sandwich sand) {
        return cmd.getSandwich().remove(sand);
    }
}