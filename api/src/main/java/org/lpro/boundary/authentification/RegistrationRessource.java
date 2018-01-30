package org.lpro.boundary.authentification;

import org.lpro.entity.Utilisateur;

import javax.inject.Inject;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.core.Response;

@Path("/registration")
public class RegistrationRessource {

    @Inject
    private UtilisateurManager um;

    @POST
    public Response creerUtilisateur(Utilisateur utilisateur) {
        return Response.ok().build();
    }

}
