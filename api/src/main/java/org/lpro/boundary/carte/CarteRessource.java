package org.lpro.boundary.carte;

import org.lpro.entity.Carte;
import org.lpro.provider.Secured;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.validation.Valid;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

@Stateless
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
@Path("card")
public class CarteRessource {

    @Inject
    CarteManager cm;

    @POST
    @Secured
    public Response getCard(@Valid Carte carte) {
        this.cm.findCarte(carte);
    }

}
