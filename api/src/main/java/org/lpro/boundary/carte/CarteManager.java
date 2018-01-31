package org.lpro.boundary.carte;

import org.lpro.control.KeyManagement;
import org.lpro.control.PasswordManagement;
import org.lpro.control.RandomToken;
import org.lpro.entity.Carte;
import org.lpro.entity.Utilisateur;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import javax.transaction.Transactional;
import java.util.UUID;

@Stateless
@Transactional
public class CarteManager
{

    @PersistenceContext
    EntityManager em;

    public Carte findByNumCarte(String num) {
        Carte c = null;

        TypedQuery<Carte> query = this.em.createQuery("SELECT c FROM Carte c WHERE c.numcarte = :numcarte", Carte.class);
        query.setParameter("numcarte", num);

        try {
            c = query.getSingleResult();
        } catch (NoResultException e) {
            c = null;
        }
        return c;
    }

    public Carte findCarte(Carte carte) {
        Carte c = null;

        TypedQuery<Carte> query = this.em.createQuery("SELECT c FROM Carte c WHERE c.numcarte = :numcarte", Carte.class);
        query.setParameter("numcarte", carte.getNumCarte());

        try {
            c = query.getSingleResult();
        } catch (NoResultException e) {
            c = null;
        }
        return c;
    }

    public Carte createCarte(Carte carte) {
        TypedQuery<Carte> query = this.em.createQuery("SELECT c FROM Carte c WHERE c.nom = :nom AND c.prenom = :prenom", Carte.class);
        query.setParameter("nom", carte.getNom());
        query.setParameter("prenom", carte.getPrenom());

        try {
            Carte c = query.getSingleResult();
            return null;
        } catch (NoResultException e) {
            carte.setId(UUID.randomUUID().toString());
            carte.setPassword(PasswordManagement.digestPassword(carte.getPassword()));
            carte.setNumCarte(new RandomToken().randomString(12).toUpperCase());
            return this.em.merge(carte);
        }
    }
}
