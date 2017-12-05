package org.lpro.boundary.sandwich;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

@Stateless
public class SandwichManager {

    @PersistenceContext
    EntityManager em;



}
