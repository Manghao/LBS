package org.lpro.boundary.carte;

import org.lpro.entity.Carte;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;

@Stateless
@Transactional
public class CarteManager
{

    @PersistenceContext
    EntityManager em;

    public Carte findCarte(Carte carte) {
        return this.em.find(Carte.class, carte.getId());
    }
}
